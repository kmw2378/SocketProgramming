
package v3.core.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

// Socket 객체를 캡슐화
public class HttpSocketManager {
    private final Socket socket;

    public HttpSocketManager(final String host, final int port) throws IOException {
        this.socket = new Socket(host, port);
    }

    // 요청 보낼 때 필요한 파이프라인
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    // 응답을 받을 때 필요한 파이프라인
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (Exception e) {
            System.err.println("소켓 닫기 중 오류 발생: " + e.getMessage());
        }
    }
}