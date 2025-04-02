package com.ezepsosa.marcusbike.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.models.ProductPart;
import com.ezepsosa.marcusbike.models.ProductPartCondition;

// Handles database operations for product part conditions, including retrieval, insertion, updating, and deletion.
public class ProductPartConditionDAO {

    private static final Logger logger = LoggerFactory.getLogger(ProductPartConditionDAO.class);

    private final String SQL_GET_ALL_QUERY = "SELECT * FROM product_part_condition";
    private final String SQL_GET_ID_QUERY = "SELECT * FROM product_part_condition WHERE part_id = ? AND dependant_part_id = ?";
    private final String SQL_INSERT_QUERY = "INSERT INTO product_part_condition (part_id, dependant_part_id, price_adjustment, is_restriction) VALUES (?, ?, ?, ?)";
    private final String SQL_UPDATE_QUERY = "UPDATE product_part_condition SET price_adjustment = ?, is_restriction = ? WHERE part_id = ? AND dependant_part_id = ?";
    private final String SQL_DELETE_QUERY = "DELETE FROM product_part_condition WHERE part_id = ? AND dependant_part_id = ?";
    private final String SQL_GET_ALL_BY_PRODUCT_ID_QUERY = "SELECT * FROM product_part_condition WHERE part_id IN (%s)";

    public List<ProductPartCondition> getAll(Connection connection) {
        List<ProductPartCondition> conditions = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                conditions.add(createProductPartConditions(rs));
            }
        } catch (SQLException e) {
            logger.warn("Error fetching product part conditions. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return conditions;
    }

    // The method is not currently utilized, but it may become useful for future
    // development or feature enhancements.
    public ProductPartCondition getById(Connection connection, Long partId, Long dependantPartId) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY)) {
            pst.setLong(1, partId);
            pst.setLong(2, dependantPartId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return createProductPartConditions(rs);
                }
            }
        } catch (SQLException e) {
            logger.warn("Error fetching product part conditions by id. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return null;
    }

    public Boolean insert(Connection connection, ProductPartCondition productPartCondition) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY)) {
            pst.setLong(1, productPartCondition.getPartId().getId());
            pst.setLong(2, productPartCondition.getDependantPartId().getId());
            pst.setDouble(3, productPartCondition.getPriceAdjustment());
            pst.setBoolean(4, productPartCondition.getIsRestriction());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error inserting product part condition. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;
    }

    // The method is not currently utilized, but it may become useful for future
    // development or feature enhancements.
    public Boolean update(Connection connection, ProductPartCondition productPartCondition) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY)) {
            pst.setDouble(1, productPartCondition.getPriceAdjustment());
            pst.setBoolean(2, productPartCondition.getIsRestriction());
            pst.setLong(3, productPartCondition.getPartId().getId());
            pst.setLong(4, productPartCondition.getDependantPartId().getId());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error updating product part condition. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;
    }

    public Boolean delete(Connection connection, Long partId, Long dependantPartId) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_DELETE_QUERY)) {
            pst.setLong(1, partId);
            pst.setLong(2, dependantPartId);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error deleting product part condition. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;
    }

    public List<ProductPartCondition> getAllById(Connection connection, List<Long> productIds) {
        if (productIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<ProductPartCondition> conditions = new ArrayList<>();
        String placeholders = productIds.stream().map(id -> "?").collect(Collectors.joining(","));
        String query = String.format(SQL_GET_ALL_BY_PRODUCT_ID_QUERY, placeholders);

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            for (int i = 0; i < productIds.size(); i++) {
                pst.setLong(i + 1, productIds.get(i));
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                conditions.add(createProductPartConditions(rs));
            }
        } catch (SQLException e) {
            logger.warn("Error fetching product part conditions. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return conditions;
    }

    private ProductPartCondition createProductPartConditions(ResultSet rs) throws SQLException {
        ProductPart part = new ProductPart(rs.getLong("part_id"), null, null, null, null, null);
        ProductPart dependantPart = new ProductPart(rs.getLong("dependant_part_id"), null, null, null, null,
                null);
        return new ProductPartCondition(part, dependantPart, rs.getDouble("price_adjustment"),
                rs.getBoolean("is_restriction"), rs.getTimestamp("created_at").toLocalDateTime());
    }
}
