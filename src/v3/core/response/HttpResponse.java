package v3.core.response;

import v3.core.header.HttpHeaders;

public class HttpResponse {
    private final StatusLine statusLine;
    private final HttpHeaders headers;
    private final String body;

    public HttpResponse(StatusLine statusLine, HttpHeaders headers, String body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
