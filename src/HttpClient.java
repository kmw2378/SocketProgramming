import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClient {
    private final String host;
    private final int port;
    private final String path;
    private final CookieManager cookieManager;

    public HttpClient(String host, int port, String path, CookieManager cookieManager) {
        this.host = host;
        this.port = port;
        this.path = path;
        this.cookieManager = cookieManager;
    }

    public void sendRequest() {
        try (Socket socket = new Socket(host, port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // 쿠키 가져오기
            String cookieHeader = cookieManager.getCookies(host);

            // 1. HTTP 요청 작성
            writer.println("GET " + path + " HTTP/1.1");
            writer.println("Host: " + host);
            writer.println("User-Agent: JavaSocketClient/1.0");
            writer.println("Accept: */*");
            if (!cookieHeader.isEmpty()) {
                writer.println("Cookie: " + cookieHeader);
            }
            writer.println("Connection: close");
            writer.println();

            // 2. HTTP 응답 수신
            String responseLine;
            boolean isHeader = true;
            System.out.println("===== HTTP 응답 =====");

            // 쿠키 추출용 패턴
            Pattern cookiePattern = Pattern.compile("Set-Cookie: (.*?);");

            while ((responseLine = reader.readLine()) != null) {
                if (isHeader) {
                    // Set-Cookie 헤더에서 쿠키 추출
                    Matcher matcher = cookiePattern.matcher(responseLine);
                    if (matcher.find()) {
                        String cookie = matcher.group(1);
                        cookieManager.addCookie(host, cookie); // 쿠키 저장
                    }
                    // 헤더의 끝을 알리는 빈 줄 확인
                    if (responseLine.isEmpty()) {
                        isHeader = false;
                        System.out.println("\n===== 응답 본문 =====");
                    }
                }
                System.out.println(responseLine);
            }

        } catch (IOException e) {
            System.err.println("요청 전송 중 오류 발생: " + e.getMessage());
        }
    }
}