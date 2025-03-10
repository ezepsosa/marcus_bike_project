package com.ezepsosa.marcusbike.routes;

import io.undertow.server.RoutingHandler;

public interface RouteRegistrar {
    void registerRoutes(RoutingHandler router);

}
