package v3.core;

import v3.core.request.HttpRequestSender;
import v3.core.response.HttpResponseReceiver;
import v3.core.socket.HttpSocketManager;

public class HttpClient {
    private final HttpRequestSender requestSender;
    private final HttpResponseReceiver responseReceiver;
    private final HttpSocketManager socketManager;

    public HttpClient(final HttpRequestSender requestSender, final HttpResponseReceiver responseReceiver, final HttpSocketManager socketManager) {
        this.requestSender = requestSender;
        this.responseReceiver = responseReceiver;
        this.socketManager = socketManager;
    }

    public void executeRequest() {
        // 요청 전송
        requestSender.sendRequest();

        // 응답 수신 및 파싱 후 렌더링
        responseReceiver.receiveResponse();

        // 커넥션 종료
        closeConnection();
    }

    public void closeConnection() {
        socketManager.closeConnection();
    }
}