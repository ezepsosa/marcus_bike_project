package com.ezepsosa.marcusbike.utils;

import com.ezepsosa.marcusbike.models.Product;
import com.ezepsosa.marcusbike.models.User;

public class ValidatorUtils {

    // User validations
    public static Boolean validateUser(User user) {
        return checkStringField(user.getEmail()) && checkStringField(user.getPasswordHash())
                && checkStringField(user.getUsername()) && user.getRole() != null;

    }

    public Boolean sameUser(User user, User userToCompare) {
        return user.getEmail().equals(userToCompare.getEmail())
                && user.getPasswordHash().equals(userToCompare.getPasswordHash())
                && user.getRole().equals(userToCompare.getRole())
                && user.getUsername().equals(userToCompare.getUsername());
    }

    public static boolean validateProduct(Product product) {
        return checkStringField(product.getProductName());
    }

    // AUX methods
    public static Boolean checkStringField(String field) {
        return field != null && !field.isEmpty();

    }

}
