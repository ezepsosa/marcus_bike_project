package com.ezepsosa.marcusbike.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ezepsosa.marcusbike.config.HikariDatabaseConfig;
import com.ezepsosa.marcusbike.models.Order;
import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.models.ProductPartCondition;
import com.ezepsosa.marcusbike.models.User;
import com.ezepsosa.marcusbike.models.UserRole;

public class OrderDao {

    public List<Order> getAll() {
        String SQL_GET_ALL_QUERY = "SELECT appor.*, u.id AS user_id, u.username, u.email, u.password_hash, u.user_role, u.created_at as user_created_at FROM app_order appor JOIN app_user u ON appor.app_user_id = u.id ";
        List<Order> orders = new ArrayList<Order>();
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Order order = createOrder(rs);
                orders.add(order);

            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order getById(Long id) {
        String SQL_GET_ID_QUERY = "SELECT appor.*, u.id AS user_id, u.username, u.email, u.password_hash, u.user_role, u.created_at as user_created_at FROM app_order appor JOIN app_user u ON appor.app_user_id = u.id WHERE appor.id = (?)";

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY);

            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                return createOrder(rs);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Long insert(Order order) {

        String SQL_INSERT_QUERY = "INSERT INTO app_order (app_user_id, final_price) VALUES (?, ?) RETURNING id";

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setLong(1, order.getUser().getId());
            pst.setDouble(2, order.getFinalPrice());

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

    public Boolean update(Order order) {
        String SQL_UPDATE_QUERY = "UPDATE app_order SET app_user_id = ?, final_price = ? WHERE id = ?";

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY);

            pst.setLong(1, order.getUser().getId());
            pst.setDouble(2, order.getFinalPrice());
            pst.setLong(3, order.getId());

            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public Boolean delete(Long id) {

        String SQL_DELETE_QUERY = "DELETE FROM app_order WHERE id = ?";

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_DELETE_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setLong(1, id);
            Integer affectedRows = pst.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    private Order createOrder(ResultSet rs) throws SQLException {
        return new Order(rs.getLong("id"),
                new User(rs.getLong("user_id"), rs.getString("username"), rs.getString("email"),
                        rs.getString("password_hash"),
                        UserRole.valueOf(rs.getString("user_role").toUpperCase()),
                        rs.getTimestamp("user_created_at").toLocalDateTime()),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getDouble("final_price"), new ArrayList<OrderLine>());
    }
}
