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
import com.ezepsosa.marcusbike.models.ProductPartCategory;

public class ProductPartDAO {

    private static final Logger logger = LoggerFactory.getLogger(ProductPartDAO.class);

    private final String SQL_GET_ALL_QUERY = "SELECT * FROM product_part ";
    private final String SQL_GET_ID_QUERY = "SELECT * FROM product_part WHERE id = ?";
    private final String SQL_INSERT_QUERY = "INSERT INTO product_part(part_option, is_available, base_price, category) VALUES (?, ?, ?, ?::product_part_category) RETURNING id";
    private final String SQL_UPDATE_QUERY = "UPDATE product_part SET part_option = ?, is_available = ?, base_price = ?, category = ?::product_part_category WHERE id = ?";
    private final String SQL_DETELE_QUERY = "DELETE FROM product_part WHERE id = (?)";
    private final String SQL_DETELE_FROM_PRODUCT_QUERY = "delete from product_product_part where product_id = ? AND product_part_id = ?";
    private final String SQL_GET_ALL_BY_QUERY = "SELECT * FROM product_part WHERE id IN (%s)";
    private final String SQL_GET_ALL_BY_PRODUCT_QUERY = "select pp.* from product_part pp join product_product_part ppp on ppp.product_part_id = pp.id where ppp.product_id = ?";

    public List<ProductPart> getAll(Connection connection) {
        List<ProductPart> productParts = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
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

    public List<ProductPart> getAllByProductId(Connection connection, Long productPartId) {
        List<ProductPart> productParts = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_BY_PRODUCT_QUERY)) {
            pst.setLong(1, productPartId);
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

    public List<ProductPart> getAllByProductPartId(Connection connection, List<Long> productPartIds) {
        if (productPartIds.isEmpty()) {
            return Collections.emptyList();
        }
        String placeholders = productPartIds.stream().map(id -> "?").collect(Collectors.joining(","));
        String query = String.format(SQL_GET_ALL_BY_QUERY, placeholders);
        List<ProductPart> productParts = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            for (int i = 0; i < productPartIds.size(); i++) {
                pst.setLong(i + 1, productPartIds.get(i));
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

    public ProductPart getById(Connection connection, Long id) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY)) {
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

    public Long insert(Connection connection, ProductPart productPart) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
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

    public Boolean update(Connection connection, ProductPart productPart) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY)) {
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

    public Boolean delete(Connection connection, Long id) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_DETELE_QUERY)) {
            pst.setLong(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error deleting product part. SQL returned error {}, Error Code: {}", e.getSQLState(),
                    e.getErrorCode());
        }
        return false;
    }

    public Boolean deleteFromProduct(Connection connection, Long productId, Long productPartId) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_DETELE_FROM_PRODUCT_QUERY)) {
            pst.setLong(1, productId);
            pst.setLong(2, productPartId);
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