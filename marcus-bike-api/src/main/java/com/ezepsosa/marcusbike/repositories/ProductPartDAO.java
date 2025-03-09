package com.ezepsosa.marcusbike.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ezepsosa.marcusbike.config.HikariDatabaseConfig;
import com.ezepsosa.marcusbike.models.Product;
import com.ezepsosa.marcusbike.models.ProductPart;
import com.ezepsosa.marcusbike.models.ProductPartCategory;

public class ProductPartDAO {

    public List<ProductPart> getAll() {
        List<ProductPart> productParts = new ArrayList<ProductPart>();
        String SQL_GET_ALL_QUERY = "SELECT pp.*, p.id AS product_id, p.product_name, p.created_at AS created_at_product FROM product_part pp JOIN product p ON pp.product_id = p.id";
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                productParts.add(createProductPart(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productParts;
    }

    public ProductPart getById(Long id) {
        String SQL_GET_ID_QUERY = "SELECT pp.*, p.id AS product_id, p.product_name, p.created_at AS created_at_product FROM product_part pp JOIN product p ON pp.product_id = p.id WHERE pp.id = (?)";
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY);

            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                return createProductPart(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Long insert(ProductPart productPart) {
        String SQL_INSERT_QUERY = "INSERT INTO product_part(product_id, part_option, is_available, base_price, category) VALUES (?, ?, ?, ?, ?::product_part_category) RETURNING id";
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setLong(1, productPart.getProduct().getId());
            pst.setString(2, productPart.getPartOption());
            pst.setBoolean(3, productPart.getIsAvailable());
            pst.setDouble(4, productPart.getBasePrice());
            pst.setString(5, productPart.getCategory().name().toLowerCase());

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
        String SQL_DETELE_QUERY = "DELETE FROM product_part WHERE id = (?)";
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

    public Boolean update(ProductPart productPart) {
        String SQL_UPDATE_QUERY = "UPDATE product_part SET part_option = ?, is_available = ?, base_price = ?, category = ?::product_part_category WHERE id = ?";

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY);

            pst.setString(1, productPart.getPartOption());
            pst.setBoolean(2, productPart.getIsAvailable());
            pst.setDouble(3, productPart.getBasePrice());
            pst.setString(4, productPart.getCategory().name().toLowerCase());
            pst.setLong(5, productPart.getId());

            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    private ProductPart createProductPart(ResultSet rs) throws SQLException {
        return new ProductPart(rs.getLong("id"),
                new Product(rs.getLong("product_id"), rs.getString("product_name"),
                        rs.getTimestamp("created_at_product").toLocalDateTime()),
                rs.getString("part_option"),
                rs.getBoolean("is_available"),
                rs.getDouble("base_price"),
                ProductPartCategory.valueOf(rs.getString("category").toUpperCase()),
                rs.getTimestamp("created_at").toLocalDateTime());
    }
}
