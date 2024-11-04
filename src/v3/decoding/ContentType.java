package v3.decoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ContentType {
    TEXT(ContentType::readContent, StandardCharsets.UTF_8),
    JSON(ContentType::readContent, StandardCharsets.UTF_8);

    private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=([^;]+)");

    private final ContentDecoder decoder;
    private Charset charset;

    ContentType(ContentDecoder decoder, Charset charset) {
        this.decoder = decoder;
        this.charset = charset;
    }

    public String decode(BufferedReader reader, int contentLength) throws IOException {
        return decoder.decode(reader, contentLength);
    }

    public Charset getCharset() {
        return charset;
    }

    private static String readContent(BufferedReader reader, int contentLength) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            bodyBuilder.append(line).append("\n");
        }
        return bodyBuilder.toString();
    }

    public static ContentType fromHeader(String contentTypeHeader) {
        if (contentTypeHeader == null) {
            throw new IllegalArgumentException("Content-Type이 없습니다.");
        }

        ContentType contentType;
        if (contentTypeHeader.startsWith("text/")) {
            contentType = TEXT;
        } else if (contentTypeHeader.contains("json")) {
            contentType = JSON;
        } else {
            throw new IllegalArgumentException("지원하지 않는 Content-Type: " + contentTypeHeader);
        }

        // charset 추출
        Matcher matcher = CHARSET_PATTERN.matcher(contentTypeHeader);
        if (matcher.find()) {
            contentType.charset = Charset.forName(matcher.group(1).trim());
        }

        return contentType;
    }
}