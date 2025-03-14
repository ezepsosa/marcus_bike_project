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

import com.ezepsosa.marcusbike.config.HikariDatabaseConfig;
import com.ezepsosa.marcusbike.models.ProductPart;
import com.ezepsosa.marcusbike.models.ProductPartCategory;

public class ProductPartDAO {

    private static final Logger logger = LoggerFactory.getLogger(ProductPartDAO.class);

    private final String SQL_GET_ALL_QUERY = "SELECT pp.*, p.id AS product_id, p.product_name, p.created_at AS created_at_product FROM product_part pp JOIN product p ON pp.product_id = p.id WHERE pp.id IN (%s)";
    private final String SQL_GET_ID_QUERY = "SELECT pp.*, p.id AS product_id, p.product_name, p.created_at AS created_at_product FROM product_part pp JOIN product p ON pp.product_id = p.id WHERE pp.id = (?)";
    private final String SQL_INSERT_QUERY = "INSERT INTO product_part(part_option, is_available, base_price, category) VALUES (?, ?, ?, ?::product_part_category) RETURNING id";
    private final String SQL_UPDATE_QUERY = "UPDATE product_part SET part_option = ?, is_available = ?, base_price = ?, category = ?::product_part_category WHERE id = ?";
    private final String SQL_DETELE_QUERY = "DELETE FROM product_part WHERE id = (?)";
    private final String SQL_GET_ALL_BY_QUERY = "SELECT * FROM product_part WHERE id IN (%s)";

    public List<ProductPart> getAll() {
        List<ProductPart> productParts = new ArrayList<>();
        try (Connection connection = HikariDatabaseConfig.getConnection();
                PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                productParts.add(createProductPart(rs));
            }
        } catch (SQLException e) {
            logger.warn("Error fetching product parts. SQL returned error {}, Error Code: {}", e.getSQLState(),
                    e.getErrorCode());
        }
        return productParts;
    }

    public List<ProductPart> getAllPartPriceById(List<Long> productIds) {
        if (productIds.isEmpty()) {
            return Collections.emptyList();
        }
        String placeholders = productIds.stream().map(id -> "?").collect(Collectors.joining(","));
        String query = String.format(SQL_GET_ALL_BY_QUERY, placeholders);
        List<ProductPart> productParts = new ArrayList<>();
        try (Connection connection = HikariDatabaseConfig.getConnection();
                PreparedStatement pst = connection.prepareStatement(query)) {
            for (int i = 0; i < productIds.size(); i++) {
                pst.setLong(i + 1, productIds.get(i));
            }
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    productParts.add(createProductPart(rs));
                }
            }
        } catch (SQLException e) {
            logger.warn("Error fetching product parts. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }

        return productParts;
    }

    public ProductPart getById(Long id) {
        try (Connection connection = HikariDatabaseConfig.getConnection();
                PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return createProductPart(rs);
                }
            }
        } catch (SQLException e) {
            logger.warn("Error fetching product part by ID. SQL returned error {}, Error Code: {}", e.getSQLState(),
                    e.getErrorCode());
        }
        return null;
    }

    public Long insert(ProductPart productPart) {
        try (Connection connection = HikariDatabaseConfig.getConnection();
                PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, productPart.getPartOption());
            pst.setBoolean(2, productPart.getIsAvailable());
            pst.setDouble(3, productPart.getBasePrice());
            pst.setString(4, productPart.getCategory().name().toLowerCase());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedValues = pst.getGeneratedKeys()) {
                    if (generatedValues.next()) {
                        return generatedValues.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            logger.warn("Error inserting product part. SQL returned error {}, Error Code: {}", e.getSQLState(),
                    e.getErrorCode());
        }
        return null;
    }

    public Boolean update(ProductPart productPart) {
        try (Connection connection = HikariDatabaseConfig.getConnection();
                PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY)) {
            pst.setString(1, productPart.getPartOption());
            pst.setBoolean(2, productPart.getIsAvailable());
            pst.setDouble(3, productPart.getBasePrice());
            pst.setString(4, productPart.getCategory().name().toLowerCase());
            pst.setLong(5, productPart.getId());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error updating product part. SQL returned error {}, Error Code: {}", e.getSQLState(),
                    e.getErrorCode());
        }
        return false;
    }

    public Boolean delete(Long id) {
        try (Connection connection = HikariDatabaseConfig.getConnection();
                PreparedStatement pst = connection.prepareStatement(SQL_DETELE_QUERY)) {
            pst.setLong(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error deleting product part. SQL returned error {}, Error Code: {}", e.getSQLState(),
                    e.getErrorCode());
        }
        return false;
    }

    private ProductPart createProductPart(ResultSet rs) throws SQLException {
        return new ProductPart(rs.getLong("id"),
                rs.getString("part_option"),
                rs.getBoolean("is_available"),
                rs.getDouble("base_price"),
                ProductPartCategory.valueOf(rs.getString("category").toUpperCase()),
                rs.getTimestamp("created_at").toLocalDateTime());
    }

}