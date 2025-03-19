package com.ezepsosa.marcusbike.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.routes.RouterConfig;

import io.undertow.Undertow;

// Configures and manages the Undertow HTTP server.
public class ServerConfig {

    private static final Logger logger = LoggerFactory.getLogger(ServerConfig.class);
    private final Undertow server;

    public ServerConfig() {
        logger.info("Initializing server on port 8080");
        server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(RouterConfig.getRoutes()).build();
    }

    public void start() {
        server.start();
        logger.info("Server successfully initialized on port 8080");

    }

    public void stop() {
        server.stop();
        logger.info("Server stopped successfully");

    }

}
