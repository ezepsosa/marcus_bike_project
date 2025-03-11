package com.ezepsosa.marcusbike.routes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.config.DependencyInjection;

import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;

public class RouterConfig {

    private static final Logger logger = LoggerFactory.getLogger(RouterConfig.class);
    private static DependencyInjection dependencyInjection = new DependencyInjection();

    public static HttpHandler getRoutes() {
        logger.info("Inserting routes");

        RoutingHandler router = new RoutingHandler();

        List<RouteRegistrar> controllers = List.of(dependencyInjection.getUserController(),
                dependencyInjection.getProductController());

        controllers.forEach(controller -> controller.registerRoutes(router));

        logger.info("Returning routes");

        return router;
    }

}
