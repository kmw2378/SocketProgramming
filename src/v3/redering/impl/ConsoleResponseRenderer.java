package v3.redering.impl;

import v3.redering.ResponseRenderer;

public class ConsoleResponseRenderer implements ResponseRenderer {
    @Override
    public void render(String responseBody) {
        System.out.println("[Response Body]");
        System.out.println(responseBody);
    }
}