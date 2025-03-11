package com.ezepsosa.marcusbike.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.config.HikariDatabaseConfig;
import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.models.Product;

public class OrderLineDAO {

    private static final Logger logger = LoggerFactory.getLogger(OrderLineDAO.class);

    private final String SQL_GET_ALL_QUERY = "SELECT ol.*, p.id, p.product_name, p.created_at AS product_created_at FROM order_line ol JOIN product p ON ol.product_id = p.id";
    private final String SQL_GET_ID_QUERY = "SELECT ol.*, p.id, p.product_name, p.created_at AS product_created_at FROM order_line ol JOIN product p ON ol.product_id = p.id WHERE ol.id = (?)";
    private final String SQL_INSERT_QUERY = "INSERT INTO order_line(app_order_id, product_id, quantity) VALUES (?, ?, ?) RETURNING id";
    private final String SQL_UPDATE_QUERY = "UPDATE order_line SET app_order_id = ?, product_id = ?, quantity = ? WHERE id = ?";
    private final String SQL_DETELE_QUERY = "DELETE FROM order_line WHERE id = (?)";
    private final String SQL_GET_BY_ORDER_ID = "SELECT ol.id AS order_line_id, ol.product_id, ol.quantity, ol.created_at, p.id AS product_id, p.product_name FROM order_line ol JOIN product p ON ol.product_id = p.id WHERE ol.app_order_id = ?";

    @SuppressWarnings("CallToPrintStackTrace")
    public List<OrderLine> getAll() {
        List<OrderLine> orderLines = new ArrayList<>();
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                orderLines.add(createOrderLine(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderLines;
    }

    public Map<Long, List<OrderLine>> getAllGroupedByOrder() {
        Map<Long, List<OrderLine>> orderLines = new HashMap<>();
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Long orderId = rs.getLong("app_order_id");
                if (orderLines.keySet().contains(orderId)) {
                    orderLines.get(orderId).add(createOrderLine(rs));

                } else {
                    orderLines.put(orderId, new ArrayList<>());
                    orderLines.get(orderId).add(createOrderLine(rs));
                }
            }
        } catch (SQLException e) {
            logger.warn("Error fetching order lines grouped by order. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());

        }
        return orderLines;
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
            logger.warn("Error fetching order line by id. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return null;
    }

    public Long insert(OrderLine orderLine, Long orderId) {
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setLong(1, orderId);
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
            logger.warn("Error inserting order line. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return null;

    }

    public Boolean update(OrderLine order, Long orderId) {

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY);

            pst.setLong(1, orderId);
            pst.setLong(2, order.getProduct().getId());
            pst.setInt(3, order.getQuantity());
            pst.setLong(4, order.getId());

            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.warn("Error updating order line. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
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
            logger.warn("Error deleting order line. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;
    }

    public List<OrderLine> getByOrderId(Long orderId) {
        List<OrderLine> ordersLines = new ArrayList<>();

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_BY_ORDER_ID);
            pst.setLong(1, orderId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ordersLines.add(createOrderLine(rs));
            }
            return ordersLines;
        } catch (SQLException e) {
            logger.warn("Error fetching order line by order Id. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return ordersLines;
    }

    private OrderLine createOrderLine(ResultSet rs) throws SQLException {

        Product product = new Product(
                rs.getLong("product_id"),
                rs.getString("product_name"),
                rs.getTimestamp("product_created_at").toLocalDateTime());

        return new OrderLine(
                rs.getLong("id"),
                product,
                rs.getInt("quantity"),
                rs.getTimestamp("created_at").toLocalDateTime());
    }

}
