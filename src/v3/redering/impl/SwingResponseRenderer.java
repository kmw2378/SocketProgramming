package v3.redering.impl;

import v3.redering.ResponseRenderer;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SwingResponseRenderer implements ResponseRenderer {
    @Override
    public void render(String responseBody) {
        try {
            // 임시 파일 생성
            File htmlFile = File.createTempFile("response", ".html");
            htmlFile.deleteOnExit(); // JVM 종료 시 파일 삭제

            // HTML 내용을 파일에 쓰기
            try (FileWriter writer = new FileWriter(htmlFile)) {
                writer.write(responseBody);
            }

            // 기본 웹 브라우저로 파일 열기
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
            } else {
                System.err.println("Desktop API를 지원하지 않습니다. 브라우저를 열 수 없습니다.");
            }
        } catch (IOException e) {
            System.err.println("응답을 브라우저로 렌더링하는 중 오류 발생: " + e.getMessage());
        }
    }
}