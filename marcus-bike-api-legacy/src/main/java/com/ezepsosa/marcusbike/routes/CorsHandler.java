package com.ezepsosa.marcusbike.routes;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import io.undertow.util.StatusCodes;

// Middleware that handles Cross-Origin Resource Sharing (CORS) by allowing requests from a specific origin.  
// Adds necessary headers to enable client-server communication across different origins.
public class CorsHandler implements HttpHandler {
    private final HttpHandler next;

    public CorsHandler(HttpHandler next) {
        this.next = next;
    }

    @Override
    // Adds CORS headers to the response and handles preflight (OPTIONS) requests.
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Origin"), "http://localhost:5173");
        exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Methods"), "GET, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().put(new HttpString("Access-Control-Allow-Headers"),
                "Content-Type, Authorization");
        // Handles preflight requests by responding with 204 No Content and terminating
        // the exchange.
        if (exchange.getRequestMethod().equals(new HttpString("OPTIONS"))) {
            exchange.setStatusCode(StatusCodes.NO_CONTENT);
            exchange.endExchange();
            return;
        }
        next.handleRequest(exchange);
    }
}
