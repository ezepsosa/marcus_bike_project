package com.ezepsosa.marcusbike.utils;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class JsonResponseUtil {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule().addSerializer(java.time.LocalDateTime.class,
                    new LocalDateTimeSerializer(dateTimeFormatter)))
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private static final Logger logger = LoggerFactory.getLogger(JsonResponseUtil.class);

    public static void sendJsonResponse(HttpServerExchange exchange, Object response) {
        sendJsonResponse(exchange, response, 200);
    }

    public static void sendJsonResponse(HttpServerExchange exchange, Object response, int statusCode) {
        try {
            String jsonResponse = objectMapper.writeValueAsString(response);

            exchange.setStatusCode(statusCode);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
            exchange.getResponseSender().send(jsonResponse, StandardCharsets.UTF_8);
            exchange.endExchange();

        } catch (Exception e) {
            logger.error("Error serializing JSON response", e);
            sendErrorResponse(exchange, 500, "Internal Server Error");
        }
    }

    public static void sendErrorResponse(HttpServerExchange exchange, Integer statusCode, String message) {
        try {
            String jsonResponse = objectMapper.writeValueAsString(Map.of("error", message));
            exchange.setStatusCode(statusCode);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
            exchange.getResponseSender().send(jsonResponse, StandardCharsets.UTF_8);
            exchange.endExchange();

        } catch (Exception e) {
            logger.error("Critical error serializing error response", e);
        }

    }

}
