package v3.core.util;

import java.net.MalformedURLException;
import java.net.URL;

// http://www.naver.com/index.html
// getHost(): www.naver.com 반환
// getPort(): 80 반환
// getPath(): /index.html
// getUrl(): http://www.naver.com/index.html 반환
public class URLParser {
    private final URL url;

    public URLParser(String urlString) throws MalformedURLException {
        this.url = new URL(urlString);
    }

    public String getUrl() {
        return url.toString();
    }

    public String getHost() {
        return url.getHost();
    }

    public int getPort() {
        return (url.getPort() == -1) ? url.getDefaultPort() : url.getPort();
    }

    public String getPath() {
        if (url.getPath() == null || url.getPath().isEmpty()) {
            return "/";
        }

        if (url.getQuery() == null || url.getQuery().isEmpty()) {
            return url.getPath();
        }

        return url.getPath() + "?" + url.getQuery();
    }
}