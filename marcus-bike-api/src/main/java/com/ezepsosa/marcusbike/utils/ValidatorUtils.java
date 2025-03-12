package com.ezepsosa.marcusbike.utils;

import com.ezepsosa.marcusbike.dto.UserDTO;
import com.ezepsosa.marcusbike.dto.UserInsertDTO;
import com.ezepsosa.marcusbike.models.Product;

public class ValidatorUtils {

    // User validations
    public static Boolean validateUser(UserInsertDTO user) {
        return checkStringField(user.email()) && checkStringField(user.password())
                && checkStringField(user.username());

    }

    public Boolean sameUser(UserDTO user, UserDTO userToCompare) {
        return user.email().equals(userToCompare.email())
                && user.role().equals(userToCompare.role())
                && user.username().equals(userToCompare.username());
    }

    public static boolean validateProduct(Product product) {
        return checkStringField(product.getProductName());
    }

    // AUX methods
    public static Boolean checkStringField(String field) {
        return field != null && !field.isEmpty();

    }

}
