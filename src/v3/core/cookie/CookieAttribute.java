package v3.core.cookie;

import java.util.function.BiConsumer;

public enum CookieAttribute {
    PATH((pair, builder) -> builder.path(pair.length > 1 ? pair[1].trim() : "/")),
    EXPIRES((pair, builder) -> {
        if (pair.length > 1) {
            builder.expires(CookieDateParser.parse(pair[1].trim()));
        }
    }),
    SECURE((pair, builder) -> builder.secure(true)),
    HTTPONLY((pair, builder) -> builder.httpOnly(true));

    private final BiConsumer<String[], Cookie.CookieBuilder> parser;

    CookieAttribute(BiConsumer<String[], Cookie.CookieBuilder> parser) {
        this.parser = parser;
    }

    public void parse(String[] pair, Cookie.CookieBuilder builder) {
        parser.accept(pair, builder);
    }

    public static CookieAttribute fromKey(String key) {
        switch (key.toLowerCase()) {
            case "path": return PATH;
            case "expires": return EXPIRES;
            case "secure": return SECURE;
            case "httponly": return HTTPONLY;
            default: return null;
        }
    }
}