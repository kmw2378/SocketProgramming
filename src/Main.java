import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            CookieManager cookieManager = new CookieManager();

            while (true) {
                System.out.print("URL을 입력하세요 (종료하려면 'exit'): ");
                String urlString = scanner.nextLine();
                if (urlString.equalsIgnoreCase("exit")) {
                    break;
                }

                // URL 파싱
                URLParser urlParser = new URLParser(urlString);
                System.out.println("URL 파싱 완료: " + urlParser.getHost() + ", 경로: " + urlParser.getPath());

                // HttpClient로 요청 전송 및 응답 렌더링
                HttpClient client = new HttpClient(urlParser, cookieManager);
                client.sendRequest();
            }
        } catch (Exception e) {
            System.err.println("오류 발생: " + e.getMessage());
        }
    }
}