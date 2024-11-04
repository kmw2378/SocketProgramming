package v3.core.header;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private final Map<String, String> headers;

    // 응답 헤더에 사용되는 생성자
    public HttpHeaders() {
        headers = new HashMap<>();
    }

    // 요청 헤더에 사용되는 생성자
    public HttpHeaders(String host) {
        headers = new HashMap<>();

        // Host: www.naver.com
        // User-Agent: JavaSocketClient/1.0
        // Accept: text/html,application/json
        // Connection: keep-alive
        headers.put("Host", host);
        headers.put("User-Agent", "JavaSocketClient/1.0");  // 별 의미 없는 값
        headers.put("Accept", "text/html,application/json");
        headers.put("Connection", "keep-alive");    // TTL
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.getOrDefault(key, null);
    }

    public boolean containsHeader(String key) {
        return headers.containsKey(key);
    }

    public Map<String, String> getAllHeaders() {
        return headers;
    }
}