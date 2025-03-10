package com.ezepsosa.marcusbike.utils;

import com.ezepsosa.marcusbike.models.User;

public class ValidatorUtils {

    public static Boolean validateUser(User user) {
        Boolean res = false;
        res = checkStringField(user.getEmail()) && checkStringField(user.getPasswordHash())
                && checkStringField(user.getUsername()) && user.getRole() != null;
        return res;
    }

    public static Boolean checkStringField(String field) {
        return field != null && !field.isEmpty();

    }

}
