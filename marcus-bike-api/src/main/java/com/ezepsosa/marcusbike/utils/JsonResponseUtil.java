package com.ezepsosa.marcusbike.utils;

import java.io.IOException;
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
import io.undertow.util.StatusCodes;

// Utility class for handling JSON responses.  
// Provides methods for serializing objects to JSON, sending responses, and handling errors.
public class JsonResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonResponseUtil.class);

    // Configures a custom date-time formatter for JSON serialization.
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Jackson ObjectMapper configured to handle Java 17
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule().addSerializer(java.time.LocalDateTime.class,
                    new LocalDateTimeSerializer(dateTimeFormatter)))
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // Sends a JSON response with an HTTP 200 OK status.
    public static void sendJsonResponse(HttpServerExchange exchange, Object response) {
        sendJsonResponse(exchange, response, StatusCodes.OK);
    }

    // Serializes an object into JSON and sends it as an HTTP response with the
    // given status code.
    public static void sendJsonResponse(HttpServerExchange exchange, Object response, int statusCode) {
        try {
            String jsonResponse = objectMapper.writeValueAsString(response);

            exchange.setStatusCode(statusCode);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
            exchange.getResponseSender().send(jsonResponse, StandardCharsets.UTF_8);
            exchange.endExchange();

        } catch (Exception e) {
            logger.error("Error serializing JSON response", e);
            sendErrorResponse(exchange, StatusCodes.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    // Sends a standardized JSON error response with a given status code and error
    // message.
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

    // Parses a JSON byte array into a Java object of the specified class.
    public static <T> T parseJson(byte[] message, Class<T> classToParse)
            throws IOException {
        return objectMapper.readValue(message, classToParse);
    }

}
