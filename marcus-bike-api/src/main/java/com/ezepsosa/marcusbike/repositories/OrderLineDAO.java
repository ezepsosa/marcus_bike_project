package com.ezepsosa.marcusbike.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ezepsosa.marcusbike.config.HikariDatabaseConfig;
import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.models.Product;

public class OrderLineDAO {

    private final String SQL_GET_ALL_QUERY = "SELECT ol.*, p.id, p.product_name, p.created_at AS product_created_at FROM order_line ol JOIN product p ON ol.product_id = p.id";
    private final String SQL_GET_ID_QUERY = "SELECT ol.*, p.id, p.product_name, p.created_at AS product_created_at FROM order_line ol JOIN product p ON ol.product_id = p.id WHERE ol.id = (?)";
    private final String SQL_INSERT_QUERY = "INSERT INTO order_line(app_order_id, product_id, quantity) VALUES (?, ?, ?) RETURNING id";
    private final String SQL_UPDATE_QUERY = "UPDATE order_line SET app_order_id = ?, product_id = ?, quantity = ? WHERE id = ?";
    private final String SQL_DETELE_QUERY = "DELETE FROM order_line WHERE id = (?)";

    public List<OrderLine> getAll() {
        List<OrderLine> OrderLines = new ArrayList<OrderLine>();
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OrderLines.add(createOrderLine(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return OrderLines;
    }

    public OrderLine getById(Long id) {
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY);

            pst.setLong(1, id);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return createOrderLine(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long insert(OrderLine orderLine) {
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setLong(1, orderLine.getOrder().getId());
            pst.setLong(2, orderLine.getProduct().getId());
            pst.setInt(3, orderLine.getQuantity());

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

    public Boolean update(OrderLine order) {

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY);

            pst.setLong(1, order.getOrder().getId());
            pst.setLong(2, order.getProduct().getId());
            pst.setInt(3, order.getQuantity());
            pst.setLong(4, order.getId());

            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public Boolean delete(Long id) {
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

    private OrderLine createOrderLine(ResultSet rs) throws SQLException {

        Product product = new Product(
                rs.getLong("product_id"),
                rs.getString("product_name"),
                rs.getTimestamp("product_created_at").toLocalDateTime());

        return new OrderLine(
                rs.getLong("id"),
                null,
                product,
                rs.getInt("quantity"),
                rs.getTimestamp("created_at").toLocalDateTime());
    }

}
