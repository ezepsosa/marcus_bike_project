package com.ezepsosa.marcusbike.routes;

import io.undertow.server.RoutingHandler;

// Interface for registering routes in Undertow.  
// Implemented by controllers to define their respective API endpoints.
public interface RouteRegistrar {
    void registerRoutes(RoutingHandler router);

}
