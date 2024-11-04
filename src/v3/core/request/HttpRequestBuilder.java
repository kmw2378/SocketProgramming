package v3.core.request;

import v3.core.header.HttpHeaders;
import v3.core.util.URLParser;
import v3.core.cache.CacheManager;
import v3.core.cookie.CookieManager;

import java.io.PrintWriter;

public class HttpRequestBuilder {
    private static final String SUFFIX_CHARS = "\r\n";

    private final URLParser urlParser;
    private final HttpHeaders requestHeaders;
    private final CacheManager cacheManager;
    private final CookieManager cookieManager;

    public HttpRequestBuilder(URLParser urlParser, HttpHeaders requestHeaders, final CacheManager cacheManager, final CookieManager cookieManager) {
        this.urlParser = urlParser;
        this.requestHeaders = requestHeaders;
        this.cacheManager = cacheManager;
        this.cookieManager = cookieManager;
    }

    public void writeRequest(final PrintWriter writer) {
        StringBuilder requestBuilder = new StringBuilder();

        // 요청 라인 추가
        final String path = urlParser.getPath();
        requestBuilder.append("GET ")
                .append(path)
                .append(" HTTP/1.1")
                .append(SUFFIX_CHARS);

        // 헤더 추가
        requestHeaders.getAllHeaders()
                .forEach(
                        (key, value) -> requestBuilder.append(key)
                        .append(": ")
                        .append(value)
                        .append(SUFFIX_CHARS)
                );

        // 쿠키 추가
        // 쿠키: Host
        final String host = urlParser.getHost();
        if (cookieManager.containsCookie(host)) {
            requestBuilder.append("Cookie: ")
                    .append(cookieManager.getCookiesForRequest(host))
                    .append(SUFFIX_CHARS);
        }

        // 캐시: URL
        final String url = urlParser.getUrl();
        if (cacheManager.contains(url)) {
            requestBuilder.append("If-None-Match: ")
                    .append(cacheManager.getETag(url))
                    .append(SUFFIX_CHARS)
                    .append("If-Modified-Since: ")
                    .append(cacheManager.getLastModified(url))
                    .append(SUFFIX_CHARS);
        }

        // 요청 끝을 알리는 빈 줄 추가
        requestBuilder.append(SUFFIX_CHARS);

        System.out.println("[HTTP 요청 메시지]");
        System.out.println(requestBuilder);

        writer.print(requestBuilder);
        writer.flush();
    }
}