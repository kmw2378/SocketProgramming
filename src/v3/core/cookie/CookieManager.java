package v3.core.cookie;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// 싱글톤 패턴
public class CookieManager {
    private static CookieManager instance;
    private final Map<String, Map<String, Cookie>> domainCookies;

    private CookieManager() {
        this.domainCookies = new HashMap<>();
    }

    // 싱글톤 인스턴스를 반환
    public static CookieManager getInstance() {
        if (instance == null) {
            instance = new CookieManager();
        }
        return instance;
    }

    // 응답의 Set-Cookie 헤더로부터 쿠키 추가
    public void addCookie(String domain, String setCookieHeader) {
        Cookie cookie = Cookie.parse(setCookieHeader, domain);
        domainCookies.computeIfAbsent(domain, k -> new HashMap<>()).put(cookie.toString(), cookie);
    }

    public boolean containsCookie(String domain) {
        return domainCookies.containsKey(domain);
    }

    // 특정 도메인에 대한 요청의 Cookie 헤더로 사용할 문자열 반환
    public String getCookiesForRequest(String domain) {
        Map<String, Cookie> cookies = domainCookies.get(domain);
        if (cookies == null || cookies.isEmpty()) {
            return "";
        }
        
        StringBuilder cookieHeader = new StringBuilder();
        Iterator<Map.Entry<String, Cookie>> iterator = cookies.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, Cookie> entry = iterator.next();
            Cookie cookie = entry.getValue();
            if (cookie.isExpired()) {
                iterator.remove(); // 만료된 쿠키 삭제
            } else {
                cookieHeader.append(cookie).append("; ");
            }
        }

        if (!cookieHeader.isEmpty()) {
            cookieHeader.setLength(cookieHeader.length() - 2); // 마지막 "; " 제거
        }
        return cookieHeader.toString();
    }

    // 저장된 쿠키들을 문자열로 출력
    @Override
    public String toString() {
        return "v0.CookieManager{" +
                "domainCookies=" + domainCookies +
                '}';
    }
}