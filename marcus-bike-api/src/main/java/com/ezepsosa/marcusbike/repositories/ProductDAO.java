package com.ezepsosa.marcusbike.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.config.HikariDatabaseConfig;
import com.ezepsosa.marcusbike.models.Product;

public class ProductDAO {

    private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);

    private final String SQL_GET_ALL_QUERY = "SELECT * FROM product";
    private final String SQL_GET_ID_QUERY = "SELECT * FROM product WHERE id = (?)";
    private final String SQL_INSERT_QUERY = "INSERT INTO product(product_name) VALUES (?) RETURNING id";
    private final String SQL_UPDATE_QUERY = "UPDATE product SET product_name = ? WHERE id = ?";
    private final String SQL_DELETE_QUERY = "DELETE FROM product WHERE id = (?)";

    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = HikariDatabaseConfig.getConnection();
                PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                products.add(createProduct(rs));
            }
        } catch (SQLException e) {
            logger.warn("Error fetching products. SQL returned error {}, Error Code: {}", e.getSQLState(),
                    e.getErrorCode());
        }
        return products;
    }

    public Product getById(Long id) {
        try (Connection connection = HikariDatabaseConfig.getConnection();
                PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return createProduct(rs);
                }
            }
        } catch (SQLException e) {
            logger.warn("Error fetching product by ID. SQL returned error {}, Error Code: {}", e.getSQLState(),
                    e.getErrorCode());
        }
        return null;
    }

    public Long insert(Product product) {
        try (Connection connection = HikariDatabaseConfig.getConnection();
                PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, product.getProductName());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedValues = pst.getGeneratedKeys()) {
                    if (generatedValues.next()) {
                        return generatedValues.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            logger.warn("Error inserting product. SQL returned error {}, Error Code: {}", e.getSQLState(),
                    e.getErrorCode());
        }
        return null;
    }

    public Boolean update(Product product, Long id) {
        try (Connection connection = HikariDatabaseConfig.getConnection();
                PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY)) {
            pst.setString(1, product.getProductName());
            pst.setLong(2, id);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error updating product. SQL returned error {}, Error Code: {}", e.getSQLState(),
                    e.getErrorCode());
        }
        return false;
    }

    public Boolean delete(Long id) {
        try (Connection connection = HikariDatabaseConfig.getConnection();
                PreparedStatement pst = connection.prepareStatement(SQL_DELETE_QUERY)) {
            pst.setLong(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error deleting product. SQL returned error {}, Error Code: {}", e.getSQLState(),
                    e.getErrorCode());
        }
        return false;
    }

    private Product createProduct(ResultSet rs) throws SQLException {
        return new Product(rs.getLong("id"), rs.getString("product_name"), new ArrayList<>(),
                rs.getTimestamp("created_at").toLocalDateTime());
    }
}