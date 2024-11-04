package v3.core.response;

import v3.core.socket.HttpSocketManager;
import v3.redering.ResponseRenderer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

// 1. 소켓의 OutputStream 을 통해 응답 값을 가져온다
// 2. HttpResponseParser 를 통해 응답 메시지 파싱
// 3. 응답 메시지를 ResponseRenderer 에게 전달하여 렌더링
public class HttpResponseReceiver {
    private final HttpResponseParser responseParser;
    private final HttpSocketManager socketManager;
    private final ResponseRenderer renderer;

    public HttpResponseReceiver(HttpResponseParser responseParser, HttpSocketManager socketManager, ResponseRenderer renderer) {
        this.responseParser = responseParser;
        this.socketManager = socketManager;
        this.renderer = renderer;
    }

    public void receiveResponse() {
        try {
            // 1. 소켓의 OutputStream 을 통해 응답 값을 가져온다
            BufferedReader reader = new BufferedReader(new InputStreamReader(socketManager.getInputStream()));

            // 2. 응답을 파싱하여 응답 Body를 가져옴
            String responseBody = responseParser.parseResponse(reader)
                    .getBody();

            // 3. 응답 Body를 지정된 방식으로 렌더링
            renderer.render(responseBody);
        } catch (Exception e) {
            System.err.println("응답 수신 중 오류 발생: " + e.getMessage());
        }
    }
}