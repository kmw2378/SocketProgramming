package v3.decoding;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public enum ContentEncoding {
    GZIP((inputStream, contentLength) -> {
        // GZIPInputStream을 사용하여 압축 해제된 InputStream 반환
        return new GZIPInputStream(inputStream);
    }),
    IDENTITY((inputStream, contentLength) -> inputStream);  // 압축이 없는 경우는 그대로 반환

    private final ContentDecoder decoder;

    ContentEncoding(ContentDecoder decoder) {
        this.decoder = decoder;
    }

    public InputStream decode(InputStream inputStream, int contentLength) throws IOException {
        return decoder.decode(inputStream, contentLength);
    }

    private static final Map<String, ContentEncoding> encodingMap = new HashMap<>();

    static {
        encodingMap.put("gzip", GZIP);
        encodingMap.put("identity", IDENTITY);
    }

    public static ContentEncoding fromHeader(String encoding) {
        return encodingMap.getOrDefault(encoding != null ? encoding.toLowerCase() : "identity", IDENTITY);
    }

    @FunctionalInterface
    interface ContentDecoder {
        InputStream decode(InputStream inputStream, int contentLength) throws IOException;
    }
}