# Java Socket HTTP Browser

이 프로젝트는 Java Socket을 이용해 간단한 HTTP 브라우저를 구현한 예제입니다. 사용자가 입력한 URL에 HTTP 요청을 보내고, 서버 응답을 콘솔에 출력하여 헤더, 쿠키, 본문을 확인할 수 있습니다. 요청 시 필수 헤더를 자동으로 설정하고, 쿠키를 관리하여 동일 도메인에 재요청할 때 쿠키를 포함합니다.

## 주요 기능
- HTTP 요청 전송: 사용자가 입력한 URL에 GET 요청을 보냅니다.
- 자동 헤더 설정: Host, User-Agent, Connection 등의 필수 요청 헤더를 자동으로 추가합니다.
- 쿠키 관리: 응답에서 Set-Cookie 헤더를 읽어 쿠키를 저장하고, 같은 도메인으로의 요청 시 자동으로 쿠키를 추가합니다.
- 응답 렌더링: 서버로부터 받은 응답 헤더와 본문을 콘솔에 출력합니다.
- URL 입력 처리: 사용자가 원하는 URL을 입력하여 요청을 보낼 수 있도록 지원합니다.

## 요구 사항
- Java 11 이상 (확장을 통해 HTTP/2를 사용하려는 경우 HttpClient 클래스 사용 가능)

## 사용 방법
1.  git 저장소를 `clone`합니다.
```
git clone https://github.com/yourusername/java-socket-http-browser.git
cd java-socket-http-browser
```

2.	프로젝트를 컴파일합니다.
```
javac Main.java
```

3.	애플리케이션을 실행합니다.
```
java Main
```

4.	요청할 URL을 입력합니다 (예: `http://example.com`). 프로그램이 해당 URL로 GET 요청을 보내고, 서버 응답을 출력합니다.

## 프로젝트 구조
- `Main.java`: 애플리케이션의 메인 진입점으로, 사용자로부터 URL을 입력받아 요청을 전송하고 응답을 처리합니다.
- `HttpClient.java`: 소켓을 통해 서버와 통신하고 요청을 전송, 응답을 수신하여 처리하는 역할을 담당합니다.
- `CookieManager.java`: 서버로부터 받은 쿠키를 저장하고, 동일 도메인 요청 시 쿠키를 추가하여 세션을 유지합니다.
- `RequestBuilder.java`: HTTP 요청을 구성하는 클래스로, URL과 헤더를 기반으로 요청 문자열을 작성합니다.
- `ResponseHandler.java`: 응답을 처리하고, Set-Cookie 헤더를 통해 받은 쿠키를 CookieManager에 저장합니다.

## 기능
1.	`http://example.com` 등 다양한 URL을 입력하여 서버 응답을 확인할 수 있습니다.
2.	`http://httpbin.org/cookies/set?name=value` 와 같은 URL을 통해 쿠키를 설정하고 관리되는지 확인할 수 있습니다.

## 예시
### 정상
```
URL을 입력하세요 (종료하려면 'exit'): http://example.com
URL 파싱 완료: example.com, 경로: /
===== HTTP 응답 =====
HTTP/1.1 200 OK
Age: 574191
Cache-Control: max-age=604800
Content-Type: text/html; charset=UTF-8
Date: Thu, 31 Oct 2024 10:14:22 GMT
Etag: "3147526947+gzip+ident"
Expires: Thu, 07 Nov 2024 10:14:22 GMT
Last-Modified: Thu, 17 Oct 2019 07:18:26 GMT
Server: ECAcc (sed/5912)
Vary: Accept-Encoding
X-Cache: HIT
Content-Length: 1256
Connection: close

===== 응답 본문 =====

<!doctype html>
<html>
<head>
    <title>Example Domain</title>

    <meta charset="utf-8" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <style type="text/css">
    body {
        background-color: #f0f0f2;
        margin: 0;
        padding: 0;
        font-family: -apple-system, system-ui, BlinkMacSystemFont, "Segoe UI", "Open Sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
        
    }
    div {
        width: 600px;
        margin: 5em auto;
        padding: 2em;
        background-color: #fdfdff;
        border-radius: 0.5em;
        box-shadow: 2px 3px 7px 2px rgba(0,0,0,0.02);
    }
    a:link, a:visited {
        color: #38488f;
        text-decoration: none;
    }
    @media (max-width: 700px) {
        div {
            margin: 0 auto;
            width: auto;
        }
    }
    </style>    
</head>

<body>
<div>
    <h1>Example Domain</h1>
    <p>This domain is for use in illustrative examples in documents. You may use this
    domain in literature without prior coordination or asking for permission.</p>
    <p><a href="https://www.iana.org/domains/example">More information...</a></p>
</div>
</body>
</html>
```

### 오류 (URL이 잘못된 경우)

```
URL을 입력하세요 (종료하려면 'exit'): http://example/com
URL 파싱 완료: example, 경로: /com
요청 전송 중 오류 발생: example
```
