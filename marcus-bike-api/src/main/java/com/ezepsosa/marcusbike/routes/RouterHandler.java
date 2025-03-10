package com.ezepsosa.marcusbike.routes;

import java.util.HashMap;
import java.util.Map;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class RouterHandler implements HttpHandler {

    private Map<String, HttpHandler> handlerMap = new HashMap<>();

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String uri = exchange.getRequestURI();

        HttpHandler handler = this.handlerMap.get(uri);

        if (handler != null) {
            handler.handleRequest(exchange);
        }
    }

}
