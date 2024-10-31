import java.util.HashMap;
import java.util.Map;

public class CookieManager {
    private final Map<String, String> cookies = new HashMap<>();

    public void addCookie(String domain, String cookie) {
        cookies.put(domain, cookie);
    }

    public String getCookies(String domain) {
        return cookies.getOrDefault(domain, "");
    }
}