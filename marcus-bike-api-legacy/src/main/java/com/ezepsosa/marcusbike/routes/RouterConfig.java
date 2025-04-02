package com.ezepsosa.marcusbike.routes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.config.DependencyInjection;

import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.PathHandler;

// Configures and registers all API routes, applying CORS middleware and structuring endpoint handling.
public class RouterConfig {

    private static final Logger logger = LoggerFactory.getLogger(RouterConfig.class);
    private static final DependencyInjection dependencyInjection = new DependencyInjection();

    // Retrieves and initializes all controllers, registering their respective
    // routes.
    public static HttpHandler getRoutes() {
        logger.info("Inserting routes");

        RoutingHandler router = new RoutingHandler();

        // Registers routes from all controllers.
        List<RouteRegistrar> controllers = List.of(
                dependencyInjection.getUserController(),
                dependencyInjection.getProductController(),
                dependencyInjection.getOrderController(),
                dependencyInjection.getOrderLineController(),
                dependencyInjection.getProductPartController(),
                dependencyInjection.getProductPartConditionController(),
                dependencyInjection.getAuthController());

        controllers.forEach(controller -> controller.registerRoutes(router));

        // Applies CORS handling to all routes.
        CorsHandler corsHanlder = new CorsHandler(router);

        // Prefixes all API routes under "/api".
        PathHandler apiRouter = new PathHandler()
                .addPrefixPath("/api", corsHanlder);

        logger.info("Returning routes");

        return apiRouter;
    }

}
