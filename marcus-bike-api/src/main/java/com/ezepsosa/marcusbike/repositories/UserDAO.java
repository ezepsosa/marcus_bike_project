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
import com.ezepsosa.marcusbike.models.User;
import com.ezepsosa.marcusbike.models.UserRole;

public class UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    private final String SQL_GET_ALL_QUERY = "SELECT * FROM app_user";
    private final String SQL_GET_ID_QUERY = "SELECT * FROM app_user WHERE id = (?)";
    private final String SQL_INSERT_QUERY = "INSERT INTO app_user (username, email, password_hash, user_role) VALUES (?, ?, ?, ?::user_role) RETURNING id";
    private final String SQL_UPDATE_QUERY = "UPDATE app_user  SET username = ?, email = ?, password_hash = ?, user_role = ?::user_role   WHERE id = ?";
    private final String SQL_DELETE_QUERY = "DELETE FROM app_user WHERE id = (?)";

    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                User userResult = createUser(rs);
                users.add(userResult);
            }
        } catch (SQLException e) {
            logger.warn("Error fetching users. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return users;
    }

    public User getById(Long id) {
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY);

            pst.setLong(1, id);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return createUser(rs);

            }
        } catch (SQLException e) {
            logger.warn("Error fetching users by id. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return null;

    }

    public Long insert(User user) {

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setString(1, user.getUsername());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPasswordHash());
            pst.setString(4, user.getRole().name().toLowerCase());

            Integer affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedValues = pst.getGeneratedKeys()) {
                    if (generatedValues.next()) {
                        return generatedValues.getLong(1);
                    }
                }
            }

        } catch (SQLException e) {
            logger.warn("Error inserting users. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return null;

    }

    public Boolean update(User user) {

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY);

            pst.setString(1, user.getUsername());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPasswordHash());
            pst.setString(4, user.getRole().name().toLowerCase());
            pst.setLong(5, user.getId());

            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.warn("Error updating users. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;

    }

    public Boolean delete(Long id) {

        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_DELETE_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setLong(1, id);

            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.warn("Error deleting users. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;

    }

    private User createUser(ResultSet rs) throws SQLException {
        return new User(rs.getLong("id"), rs.getString("username"), rs.getString("email"),
                rs.getString("password_hash"), UserRole.valueOf(rs.getString("user_role").toUpperCase()),
                rs.getTimestamp("created_at").toLocalDateTime());
    }

}
