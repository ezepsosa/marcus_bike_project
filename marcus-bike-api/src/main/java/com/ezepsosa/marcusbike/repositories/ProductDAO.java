package com.ezepsosa.marcusbike.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ezepsosa.marcusbike.config.HikariDatabaseConfig;
import com.ezepsosa.marcusbike.models.Product;

public class ProductDAO {

    public List<Product> getAll() {
        List<Product> products = new ArrayList<Product>();
        String SQL_GET_ALL_QUERY = "SELECT * FROM product";
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getLong("id"), rs.getString("product_name"),
                        rs.getTimestamp("created_at").toLocalDateTime());
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getById(Long id) {
        String SQL_GET_ID_QUERY = "SELECT * FROM product WHERE id = (?)";
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY);

            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                return new Product(rs.getLong("id"), rs.getString("product_name"),
                        rs.getTimestamp("created_at").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Long insert(Product product) {
        String SQL_INSERT_QUERY = "INSERT INTO product(product_name) VALUES (?) RETURNING id";
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setString(1, product.getProductName());

            Integer affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedValues = pst.getGeneratedKeys()) {
                    if (generatedValues.next()) {
                        return generatedValues.getLong(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Boolean delete(Long id) {
        String SQL_DETELE_QUERY = "DELETE FROM product WHERE id = (?)";
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_DETELE_QUERY);
            pst.setLong(1, id);
            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean update(Product product) {
        String SQL_UPDATE_QUERY = "UPDATE product SET product_name = ? WHERE id = ?";

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY);

            pst.setString(1, product.getProductName());
            pst.setLong(2, product.getId());

            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
}
