## 실행 결과
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
