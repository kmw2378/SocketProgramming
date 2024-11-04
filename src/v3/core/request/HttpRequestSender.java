package v3.core.request;

import v3.core.socket.HttpSocketManager;

import java.io.PrintWriter;

public class HttpRequestSender {
    private final HttpRequestBuilder requestBuilder;
    private final HttpSocketManager socketManager;

    public HttpRequestSender(HttpRequestBuilder requestBuilder, HttpSocketManager socketManager) {
        this.requestBuilder = requestBuilder;
        this.socketManager = socketManager;
    }

    public void sendRequest() {
        try {
            PrintWriter writer = new PrintWriter(socketManager.getOutputStream(), true);
            requestBuilder.writeRequest(writer);
        } catch (Exception e) {
            System.err.println("요청 전송 중 오류 발생: " + e.getMessage());
        }
    }
}