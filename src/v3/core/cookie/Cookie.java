package v3.core.cookie;

import java.time.ZonedDateTime;

public class Cookie {
    private final String name;
    private final String value;
    private final String domain;
    private final String path;
    private final boolean isSecure;
    private final boolean isHttpOnly;
    private final ZonedDateTime expires;

    private Cookie(CookieBuilder builder) {
        this.name = builder.name;
        this.value = builder.value;
        this.domain = builder.domain;
        this.path = builder.path;
        this.isSecure = builder.isSecure;
        this.isHttpOnly = builder.isHttpOnly;
        this.expires = builder.expires;
    }

    public static Cookie parse(String setCookieHeader, String domain) {
        String[] parts = setCookieHeader.split(";");
        String[] nameValuePair = parts[0].trim().split("=", 2);
        String name = nameValuePair[0].trim();
        String value = nameValuePair.length > 1 ? nameValuePair[1].trim() : "";

        Cookie.CookieBuilder builder = new Cookie.CookieBuilder(name, value, domain);

        for (int i = 1; i < parts.length; i++) {
            String[] pair = parts[i].trim().split("=", 2);
            String key = pair[0].trim().toLowerCase();
            CookieAttribute attribute = CookieAttribute.fromKey(key);

            if (attribute != null) {
                attribute.parse(pair, builder);
            }
        }

        return builder.build();
    }

    public boolean isExpired() {
        return expires != null && ZonedDateTime.now().isAfter(expires);
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }

    public static class CookieBuilder {
        private final String name;
        private final String value;
        private final String domain;
        private String path = "/";
        private boolean isSecure = false;
        private boolean isHttpOnly = false;
        private ZonedDateTime expires;

        public CookieBuilder(String name, String value, String domain) {
            this.name = name;
            this.value = value;
            this.domain = domain;
        }

        public CookieBuilder path(String path) {
            this.path = path;
            return this;
        }

        public CookieBuilder secure(boolean isSecure) {
            this.isSecure = isSecure;
            return this;
        }

        public CookieBuilder httpOnly(boolean isHttpOnly) {
            this.isHttpOnly = isHttpOnly;
            return this;
        }

        public CookieBuilder expires(ZonedDateTime expires) {
            this.expires = expires;
            return this;
        }

        public Cookie build() {
            return new Cookie(this);
        }
    }
}