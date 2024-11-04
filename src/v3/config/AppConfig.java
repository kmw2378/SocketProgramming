package v3.config;

import v3.core.cache.CacheManager;
import v3.core.cookie.CookieManager;
import v3.core.HttpClient;
import v3.core.header.HttpHeaders;
import v3.core.request.HttpRequestBuilder;
import v3.core.request.HttpRequestSender;
import v3.core.response.HttpResponseParser;
import v3.core.response.HttpResponseReceiver;
import v3.core.socket.HttpSocketManager;
import v3.redering.ResponseRenderer;
import v3.redering.impl.ConsoleResponseRenderer;
import v3.redering.impl.SwingResponseRenderer;
import v3.core.util.URLParser;

public class AppConfig {
    private final CookieManager cookieManager;
    private final CacheManager cacheManager;
    private final URLParser urlParser;
    private final HttpHeaders requestHeaders;
    private final HttpSocketManager socketManager;
    private final HttpRequestBuilder httpRequestBuilder;
    private final HttpRequestSender httpRequestSender;
    private final HttpResponseParser httpResponseParser;
    private final ResponseRenderer responseRenderer;
    private final HttpResponseReceiver httpResponseReceiver;
    private final HttpClient httpClient;

    public AppConfig(String urlString) throws Exception {
        this.cookieManager = CookieManager.getInstance();   // 싱글톤 패턴
        this.cacheManager = CacheManager.getInstance();     // 싱글톤 패턴
        this.urlParser = new URLParser(urlString);
        this.requestHeaders = new HttpHeaders(urlParser.getHost());
        this.socketManager = new HttpSocketManager(urlParser.getHost(), urlParser.getPort());
        this.httpRequestBuilder = new HttpRequestBuilder(urlParser, requestHeaders, cacheManager, cookieManager);
        this.httpRequestSender = new HttpRequestSender(httpRequestBuilder, socketManager);
        this.httpResponseParser = new HttpResponseParser(urlParser, cacheManager, cookieManager);
        this.responseRenderer = new SwingResponseRenderer();
        this.httpResponseReceiver = new HttpResponseReceiver(httpResponseParser, socketManager, responseRenderer);
        this.httpClient = new HttpClient(httpRequestSender, httpResponseReceiver, socketManager);
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public URLParser getUrlParser() {
        return urlParser;
    }

    public HttpHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public HttpSocketManager getSocketManager() {
        return socketManager;
    }

    public HttpRequestBuilder getHttpRequestBuilder() {
        return httpRequestBuilder;
    }

    public HttpRequestSender getHttpRequestSender() {
        return httpRequestSender;
    }

    public ResponseRenderer getResponseRenderer() {
        return responseRenderer;
    }

    public HttpResponseReceiver getHttpResponseReceiver() {
        return httpResponseReceiver;
    }
}