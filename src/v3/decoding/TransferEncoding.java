package v3.decoding;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public enum TransferEncoding {
    CHUNKED((reader, contentLength) -> {
        StringBuilder bodyBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            int chunkSize = Integer.parseInt(line.trim(), 16); // 청크 크기 (16진수)

            if (chunkSize == 0) {
                break; // 청크 크기가 0이면 종료
            }

            char[] chunkData = new char[chunkSize];
            int charsRead = reader.read(chunkData, 0, chunkSize); // 청크 데이터 읽기
            while (charsRead < chunkSize) { // 청크 크기에 도달할 때까지 읽기
                int additionalChars = reader.read(chunkData, charsRead, chunkSize - charsRead);
                if (additionalChars == -1) {
                    break; // 스트림 끝
                }
                charsRead += additionalChars;
            }
            bodyBuilder.append(chunkData, 0, charsRead);

            reader.readLine(); // 청크의 끝에 있는 CRLF 건너뛰기
        }

        return new ByteArrayInputStream(bodyBuilder.toString().getBytes());
    }),
    IDENTITY((reader, contentLength) -> {
        StringBuilder bodyBuilder = new StringBuilder();
        char[] buffer = new char[contentLength > 0 ? contentLength : 8192];
        int charsRead;
        int remaining = contentLength;
        while (remaining > 0 && (charsRead = reader.read(buffer)) != -1) {
            bodyBuilder.append(buffer, 0, charsRead);
            remaining -= contentLength;
        }

        return new ByteArrayInputStream(bodyBuilder.toString().getBytes());
    });

    private final ContentDecoder decoder;

    TransferEncoding(ContentDecoder decoder) {
        this.decoder = decoder;
    }

    public InputStream decode(BufferedReader reader, Integer contentLength) throws IOException {
        return decoder.decode(reader, contentLength);
    }

    public static TransferEncoding fromHeader(String encoding) {
        return "chunked".equalsIgnoreCase(encoding) ? CHUNKED : IDENTITY;
    }

    @FunctionalInterface
    interface ContentDecoder {
        InputStream decode(BufferedReader reader, Integer contentLength) throws IOException;
    }
}