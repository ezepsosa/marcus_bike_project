package com.ezepsosa.marcusbike.utils;

import java.util.Deque;

import io.undertow.server.HttpServerExchange;

// Utility class for extracting and parsing request parameters from HTTP exchanges.
public class RequestUtils {

    // Retrieves a request parameter from the URL query string and converts it to a
    // Long.
    // Returns null if the parameter is missing or not a valid number.
    public static Long getRequestParam(HttpServerExchange exchange, String paramToRetrieve) {
        Deque<String> idParam = exchange.getQueryParameters().get(paramToRetrieve);
        if (idParam == null || idParam.isEmpty()) {
            return null;
        }
        try {
            return Long.valueOf(idParam.getFirst());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
