package com.ezepsosa.marcusbike.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.routes.RouteRegistrar;

import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.util.Headers;
import io.undertow.util.Methods;

public class RouterConfig {

    private static final Logger logger = LoggerFactory.getLogger(RouterConfig.class);

    public static HttpHandler getRoutes() {
        logger.info("Inserting routes");

        RoutingHandler router = new RoutingHandler();
        router.add(Methods.GET, "/hello", exchange -> {
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
            exchange.getResponseSender().send("{'message': 'Hello world'}");
        });
        // TODO: Add here the controllers as the app grows and delete the previous
        // insertion

        List<RouteRegistrar> controllers = List.of();

        controllers.forEach(controller -> controller.registerRoutes(router));
        logger.info("Returning routes");

        return router;
    }

}
