package com.ezepsosa.marcusbike.routes;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

public class CorsHandler implements HttpHandler {
    private final HttpHandler next;

    public CorsHandler(HttpHandler next) {
        this.next = next;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "http://localhost:5173");
        exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Methods"), "GET, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Headers"),
                "Content-Type, Authorization");

        if (exchange.getRequestMethod().equals(new HttpString("OPTIONS"))) {
            exchange.setStatusCode(204);
            exchange.endExchange();
            return;
        }
        next.handleRequest(exchange);
    }
}
