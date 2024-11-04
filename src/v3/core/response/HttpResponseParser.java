package v3.core.response;

import v3.core.cache.CacheManager;
import v3.core.cookie.CookieManager;
import v3.core.header.HttpHeaders;
import v3.core.util.URLParser;
import v3.decoding.ContentEncoding;
import v3.decoding.ContentType;
import v3.decoding.TransferEncoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpResponseParser {
    private final URLParser urlParser;
    private final CacheManager cacheManager;
    private final CookieManager cookieManager;

    public HttpResponseParser(final URLParser urlParser, final CacheManager cacheManager, final CookieManager cookieManager) {
        this.urlParser = urlParser;
        this.cacheManager = cacheManager;
        this.cookieManager = cookieManager;
    }

    public HttpResponse parseResponse(BufferedReader reader) throws IOException {
        final String url = urlParser.getUrl();

        final StatusLine statusLine = new StatusLine(reader.readLine());
        final HttpHeaders responseHeaders = parseHeaders(reader);
        String body;
        if (statusLine.is304()) {
            System.out.println("304 Not Modified - 캐시에서 콘텐츠를 가져옵니다.");
            body = cacheManager.getContent(url);
        } else {
            body = parseBody(responseHeaders, reader);
            updateCache(responseHeaders, body);
        }
        updateCookie(responseHeaders, url);

        return new HttpResponse(statusLine, responseHeaders, body);
    }

    private void updateCookie(final HttpHeaders responseHeaders, final String domain) {
        if (!responseHeaders.containsHeader("Set-Cookie")) {
            return;
        }
        final String cookieValue = responseHeaders.getHeader("Set-Cookie");
        cookieManager.addCookie(domain, cookieValue);
    }

    private void updateCache(final HttpHeaders responseHeaders, final String responseBody) {
        if (!responseHeaders.containsHeader("ETag")) {
            return;
        }
        final String eTag = responseHeaders.getHeader("ETag");
        final String lastModified = responseHeaders.getHeader("Last-Modified");
        cacheManager.put(urlParser.getUrl(), eTag, responseBody, lastModified, 3600L);
    }

    // 응답 헤더 파싱 메서드
    private HttpHeaders parseHeaders(BufferedReader reader) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();

        // HTTP/1.1 200 OK
        // Date: XX
        // Server: XX
        // Last-Modified: XX
        // ETag: XX
        // ...
        // Content-Type: text/html
        // \r\n
        // 응답 Body 시작

        while (true) {
            String line = reader.readLine();
            if (line == null || line.isEmpty()) {
                break; // 빈 줄을 만나면 헤더 파싱 종료
            }
            String[] headerParts = line.split(": ", 2);
            if (headerParts.length == 2) {
                String headerName = headerParts[0];
                String headerValue = headerParts[1];
                responseHeaders.addHeader(headerName, headerValue);
            }
        }

        return responseHeaders;
    }

    // 응답 Body를 가져오는 메서드
    private String parseBody(final HttpHeaders responseHeaders, BufferedReader reader) throws IOException {
        // 헤더에서 필요한 정보 추출
        String contentTypeHeader = responseHeaders.getHeader("Content-Type");
        String contentEncodingHeader = responseHeaders.getHeader("Content-Encoding");
        String transferEncodingHeader = responseHeaders.getHeader("Transfer-Encoding");

        ContentType contentType = ContentType.fromHeader(contentTypeHeader);
        ContentEncoding contentEncoding = ContentEncoding.fromHeader(contentEncodingHeader);
        TransferEncoding transferEncoding = TransferEncoding.fromHeader(transferEncodingHeader);

        int contentLength = -1;
        if (responseHeaders.getHeader("Content-Length") != null) {
            contentLength = Integer.parseInt(responseHeaders.getHeader("Content-Length"));
        }

        final InputStream transferDecodedStream = transferEncoding.decode(reader, contentLength);
        final InputStream contentDecodedStream = contentEncoding.decode(transferDecodedStream, contentLength);
        return contentType.decode(new BufferedReader(new InputStreamReader(contentDecodedStream, contentType.getCharset())), contentLength);
    }
}
// http://www.google.com