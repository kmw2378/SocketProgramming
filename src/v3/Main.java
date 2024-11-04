package v3;

import v3.config.AppConfig;
import v3.core.HttpClient;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            HttpClient client = null;
            System.out.print("URL을 입력하세요 ('exit' 입력 시 종료)\n>> ");
            String urlString = scanner.nextLine();

            if (urlString.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                AppConfig config = new AppConfig(urlString);
                client = config.getHttpClient();
                client.executeRequest();
            } catch (Exception e) {
                System.err.println("HTTP 요청 중 오류 발생: " + e.getMessage());
            } finally {
                if (client != null) {
                    client.closeConnection();
                }
            }
        }
    }
}