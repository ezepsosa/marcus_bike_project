package com.ezepsosa.marcusbike.utils;

import com.ezepsosa.marcusbike.dto.ProductInsertDTO;
import com.ezepsosa.marcusbike.dto.UserInsertDTO;

public class ValidatorUtils {

    // Validates a UserInsertDTO by checking that required fields are present.
    public static Boolean validateUser(UserInsertDTO user) {
        return checkStringField(user.email()) && checkStringField(user.password())
                && checkStringField(user.username());

    }

    // Validates a ProductInsertDTO by ensuring the product name is not empty.
    public static boolean validateProduct(ProductInsertDTO product) {
        return checkStringField(product.productName());
    }

    // AUX methods
    public static Boolean checkStringField(String field) {
        return field != null && !field.isEmpty();

    }

}
