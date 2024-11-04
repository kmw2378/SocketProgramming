package v3.core.cache;

import java.util.HashMap;
import java.util.Map;

// 싱글톤 패턴
public class CacheManager {
    private static CacheManager instance;

    public CacheManager() {
        values = new HashMap<>();
    }

    private final Map<String, Cache> values;
    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    public void put(String url, String eTag, String content, String lastModified, long maxAgeInSeconds) {
        long expirationTime = System.currentTimeMillis() + maxAgeInSeconds * 1000;
        values.put(url, new Cache(eTag, content, lastModified, expirationTime));
        System.out.println("caching success for key: " + url);
    }

    public boolean contains(final String url) {
        return values.containsKey(url);
    }

    public String getETag(String url) {
        Cache item = values.get(url);
        return (item != null && item.expirationTime > System.currentTimeMillis()) ? item.eTag : null;
    }

    public String getLastModified(String url) {
        Cache item = values.get(url);
        return item.lastModified;
    }

    public String getContent(String url) {
        System.out.println(values.keySet());

        Cache item = values.get(url);
        System.out.println("item = " + item);
        return item.content;
    }

    public void clearExpired() {
        long currentTime = System.currentTimeMillis();
        values.entrySet().removeIf(entry -> entry.getValue().expirationTime <= currentTime);
    }

    private static class Cache {
        String eTag;
        String content;
        String lastModified;
        long expirationTime;

        Cache(String eTag, String content, String lastModified, long expirationTime) {
            this.eTag = eTag;
            this.content = content;
            this.lastModified = lastModified;
            this.expirationTime = expirationTime;
        }

        @Override
        public String toString() {
            return "Cache{" +
                    "eTag='" + eTag + '\'' +
                    ", content='" + content + '\'' +
                    ", lastModified='" + lastModified + '\'' +
                    ", expirationTime=" + expirationTime +
                    '}';
        }
    }
}