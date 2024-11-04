package v3.decoding;

import java.io.BufferedReader;
import java.io.IOException;

@FunctionalInterface
public interface ContentDecoder {
    String decode(BufferedReader reader, int contentLength) throws IOException;
}