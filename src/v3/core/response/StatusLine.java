package v3.core.response;

// 응답 메시지 첫 번째 줄 (상태 코드 줄)에 대한 정보를 저장하는 클래스
public class StatusLine {
    private final String version;
    private final int status;
    private final String code;

    public StatusLine(final String line) {
        final String[] arr = line.split(" "); // ["HTTP/1.1", "200", "OK"]
        this.version = arr[0];
        this.status = Integer.parseInt(arr[1]); // Integer.parseInt(): 파이썬의 int() 함수
        this.code = arr[2];
    }

    public boolean is304() {
        return status == 304;
    }
}
