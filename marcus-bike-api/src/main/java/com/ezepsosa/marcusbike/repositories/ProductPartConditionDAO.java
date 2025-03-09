package com.ezepsosa.marcusbike.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ezepsosa.marcusbike.config.HikariDatabaseConfig;
import com.ezepsosa.marcusbike.models.ProductPart;
import com.ezepsosa.marcusbike.models.ProductPartCondition;

public class ProductPartConditionDAO {

    public List<ProductPartCondition> getAll() {
        String SQL_GET_ALL_QUERY = "SELECT * FROM product_part_condition";
        List<ProductPartCondition> orders = new ArrayList<ProductPartCondition>();
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ProductPartCondition order = createProductPartConditions(rs);
                orders.add(order);

            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public ProductPartCondition getById(Long part_id, Long dependant_part_id) {
        String SQL_GET_ID_QUERY = "SELECT * FROM product_part_condition WHERE part_id = ? AND dependant_part_id = ?";

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY);

            pst.setLong(1, part_id);
            pst.setLong(2, dependant_part_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                return createProductPartConditions(rs);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Boolean insert(ProductPartCondition productPartCondition) {

        String SQL_INSERT_QUERY = "INSERT INTO product_part_condition (part_id, dependant_part_id, price_adjustment, is_restriction) VALUES (?, ?, ?, ?) RETURNING part_id";

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setLong(1, productPartCondition.getPartId().getId());
            pst.setLong(2, productPartCondition.getDependantPartId().getId());
            pst.setDouble(3, productPartCondition.getPriceAdjustment());
            pst.setBoolean(4, productPartCondition.getIsRestriction());

            Integer affectedRows = pst.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public Boolean update(ProductPartCondition productPartCondition) {
        String SQL_UPDATE_QUERY = "UPDATE product_part_condition SET price_adjustment = ?, is_restriction = ? WHERE part_id = ? AND dependant_part_id = ?";

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY);

            pst.setDouble(1, productPartCondition.getPriceAdjustment());
            pst.setBoolean(2, productPartCondition.getIsRestriction());
            pst.setLong(3, productPartCondition.getPartId().getId());
            pst.setLong(4, productPartCondition.getDependantPartId().getId());

            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public Boolean delete(Long part_id, Long dependat_part_id) {

        String SQL_DELETE_QUERY = "DELETE FROM product_part_condition WHERE part_id = ? AND dependant_part_id = ?";

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_DELETE_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setLong(1, part_id);
            pst.setLong(2, dependat_part_id);
            Integer affectedRows = pst.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    private ProductPartCondition createProductPartConditions(ResultSet rs) throws SQLException {
        ProductPart pp = new ProductPart(rs.getLong("part_id"), null, null, null, null, null, null);
        ProductPart ppd = new ProductPart(rs.getLong("dependant_part_id"), null, null, null, null, null, null);
        return new ProductPartCondition(pp, ppd, rs.getDouble("price_adjustment"), rs.getBoolean("is_restriction"),
                rs.getTimestamp("created_at").toLocalDateTime());
    }
}
