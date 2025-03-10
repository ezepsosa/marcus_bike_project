package com.ezepsosa.marcusbike.utils;

import java.util.Deque;

import io.undertow.server.HttpServerExchange;

public class RequestUtils {

    public static Long getRequestParam(HttpServerExchange exchange, String paramToRetrieve) {
        Deque<String> idParam = exchange.getQueryParameters().get(paramToRetrieve);
        if (idParam == null || idParam.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(idParam.getFirst());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
