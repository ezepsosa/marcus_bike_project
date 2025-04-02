package com.ezepsosa.marcusbike.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.models.Order;
import com.ezepsosa.marcusbike.models.User;
import com.ezepsosa.marcusbike.models.UserRole;

// Handles database operations for orders, including retrieval, insertion, updating, and deletion.
public class OrderDAO {

    private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

    private final String SQL_GET_ALL_QUERY = "SELECT appor.*, u.id AS user_id, u.username, u.email, u.password_hash, u.user_role, u.created_at as user_created_at FROM app_order appor JOIN app_user u ON appor.app_user_id = u.id ";
    private final String SQL_GET_ID_QUERY = "SELECT appor.*, u.id AS user_id, u.username, u.email, u.password_hash, u.user_role, u.created_at as user_created_at FROM app_order appor JOIN app_user u ON appor.app_user_id = u.id WHERE appor.id = (?)";
    private final String SQL_INSERT_QUERY = "INSERT INTO app_order (app_user_id, final_price) VALUES (?, ?) RETURNING id";
    private final String SQL_UPDATE_QUERY = "UPDATE app_order SET app_user_id = ?, final_price = ? WHERE id = ?";
    private final String SQL_DELETE_QUERY = "DELETE FROM app_order WHERE id = ?";
    private final String SQL_GET_ALL_BY_USER_QUERY = "SELECT appor.*, u.id AS user_id, u.username, u.email, u.password_hash, u.user_role, u.created_at as user_created_at FROM app_order appor JOIN app_user u ON appor.app_user_id = u.id WHERE appor.app_user_id = ?";

    public List<Order> getAll(Connection connection) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                orders.add(createOrder(rs));
            }
        } catch (SQLException e) {
            logger.warn("Error fetching orders. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return orders;
    }

    public List<Order> getAllByUser(Connection connection, Long id) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_BY_USER_QUERY)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orders.add(createOrder(rs));
                }
            }
        } catch (SQLException e) {
            logger.warn("Error fetching orders by user. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return orders;
    }

    public Order getById(Connection connection, Long id) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return createOrder(rs);
                }
            }
        } catch (SQLException e) {
            logger.warn("Error fetching orders by Id. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return null;
    }

    public Long insert(Connection connection, Order order) {
        try {
            PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setLong(1, order.getUser().getId());
            pst.setDouble(2, order.getFinalPrice());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedValues = pst.getGeneratedKeys()) {
                    if (generatedValues.next()) {
                        return generatedValues.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            logger.warn("Error inserting order. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return null;
    }

    public Boolean update(Connection connection, Order order) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY)) {
            pst.setLong(1, order.getUser().getId());
            pst.setDouble(2, order.getFinalPrice());
            pst.setLong(3, order.getId());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error updating order. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;
    }

    public Boolean delete(Connection connection, Long id) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_DELETE_QUERY)) {
            pst.setLong(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error deleting order. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;
    }

    private Order createOrder(ResultSet rs) throws SQLException {
        return new Order(
                rs.getLong("id"),
                new User(
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        UserRole.valueOf(rs.getString("user_role").toUpperCase()),
                        rs.getTimestamp("user_created_at").toLocalDateTime()),
                rs.getDouble("final_price"),
                new ArrayList<>(),
                rs.getTimestamp("created_at").toLocalDateTime());
    }
}
