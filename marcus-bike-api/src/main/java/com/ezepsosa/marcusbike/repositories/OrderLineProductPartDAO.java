package com.ezepsosa.marcusbike.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ezepsosa.marcusbike.models.OrderLineProductPart;
import com.ezepsosa.marcusbike.models.ProductPart;
import com.ezepsosa.marcusbike.models.ProductPartCategory;

// Handles database operations for order line product parts, including retrieval, insertion, updating, and deletion.
public class OrderLineProductPartDAO {

    private static final Logger logger = LoggerFactory.getLogger(OrderLineProductPartDAO.class);

    private final String SQL_GET_ALL_QUERY = "SELECT olpp.*, pp.id AS product_part_id, pp.part_option, pp.is_available, pp.base_price, pp.category, pp.created_at AS product_part_created_at, p.id AS product_id, p.product_name, p.created_at AS product_created_at FROM order_line_product_part olpp JOIN product_part pp ON olpp.product_part_id = pp.id JOIN product p ON pp.product_id = p.id";
    private final String SQL_GET_ID_QUERY = "SELECT olpp.*, pp.id AS product_part_id, pp.part_option, pp.is_available, pp.base_price, pp.category, pp.created_at AS product_part_created_at, p.id AS product_id, p.product_name, p.created_at AS product_created_at FROM order_line_product_part olpp JOIN product_part pp ON olpp.product_part_id = pp.id JOIN product p ON pp.product_id = p.id WHERE olpp.order_line_id = ? AND olpp.product_part_id = ?";
    private final String SQL_INSERT_QUERY = "INSERT INTO order_line_product_part(order_line_id, product_part_id, final_price) VALUES (?, ?, ?) RETURNING order_line_id AND";
    private final String SQL_UPDATE_QUERY = "UPDATE order_line_product_part final_price = ? WHERE order_line_id = ? AND product_part_id = ?";
    private final String SQL_DETELE_QUERY = "DELETE FROM order_line_product_part WHERE order_line_id = ? AND product_part_id = ?";
    private final String SQL_GET_ALL_BY_PRODUCT_ID_QUERY = "SELECT olpp.*, pp.id AS product_part_id, pp.part_option, pp.is_available, pp.base_price, pp.category, pp.created_at AS product_part_created_at, p.id AS product_id, p.product_name, p.created_at AS product_created_at FROM order_line_product_part olpp JOIN product_part pp ON olpp.product_part_id = pp.id JOIN product p ON pp.product_id = p.id where olpp.order_line_id = ?";

    // The method is not currently utilized, but it may become useful for future
    // development or feature enhancements.
    public List<OrderLineProductPart> getAll(Connection connection) {
        List<OrderLineProductPart> orderLines = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                orderLines.add(createOrderLineProductPart(rs));
            }
        } catch (SQLException e) {
            logger.warn("Error fetching order line product parts. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return orderLines;
    }

    // The method is not currently utilized, but it may become useful for future
    // development or feature enhancements.
    public OrderLineProductPart getById(Connection connection, Long orderLineId, Long productPartId) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY)) {
            pst.setLong(1, orderLineId);
            pst.setLong(2, productPartId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return createOrderLineProductPart(rs);
                }
            }
        } catch (SQLException e) {
            logger.warn("Error fetching order line product part by IDs. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return null;
    }

    // The method is not currently utilized, but it may become useful for future
    // development or feature enhancements.
    public Boolean insert(Connection connection, OrderLineProductPart orderLineProductPart) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY)) {
            pst.setLong(1, orderLineProductPart.getOrderLine().getId());
            pst.setLong(2, orderLineProductPart.getProductPart().getId());
            pst.setDouble(3, orderLineProductPart.getFinalPrice());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error inserting order line product part. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;
    }

    // The method is not currently utilized, but it may become useful for future
    // development or feature enhancements.
    public Boolean update(Connection connection, OrderLineProductPart orderLineProductPart) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY)) {
            pst.setDouble(1, orderLineProductPart.getFinalPrice());
            pst.setLong(2, orderLineProductPart.getOrderLine().getId());
            pst.setLong(3, orderLineProductPart.getProductPart().getId());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error updating order line product part. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;
    }

    // The method is not currently utilized, but it may become useful for future
    // development or feature enhancements.
    public Boolean delete(Connection connection, Long orderLineId, Long productPartId) {
        try (PreparedStatement pst = connection.prepareStatement(SQL_DETELE_QUERY)) {
            pst.setLong(1, orderLineId);
            pst.setLong(2, productPartId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Error deleting order line product part. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;
    }

    public List<OrderLineProductPart> getByOrderLineId(Connection connection, Long orderLineId) {
        List<OrderLineProductPart> orderLines = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_BY_PRODUCT_ID_QUERY)) {
            pst.setLong(1, orderLineId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    orderLines.add(createOrderLineProductPart(rs));
                }
            }
        } catch (SQLException e) {
            logger.warn(
                    "Error fetching order line product parts by order line ID. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return orderLines;
    }

    public List<Long> insertAll(Connection connection, List<OrderLineProductPart> orderLineProductParts,
            Long orderLineId) {
        List<Long> res = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                PreparedStatement.RETURN_GENERATED_KEYS)) {

            for (OrderLineProductPart orderLineProductPart : orderLineProductParts) {
                pst.setLong(1, orderLineId);
                pst.setLong(2, orderLineProductPart.getProductPart().getId());
                pst.setDouble(3, orderLineProductPart.getFinalPrice());
                pst.addBatch();
            }
            pst.executeBatch();
            try (ResultSet generatedValues = pst.getGeneratedKeys()) {
                while (generatedValues.next()) {
                    res.add(generatedValues.getLong(1));
                }
            }

        } catch (SQLException e) {
            logger.warn("Error inserting order line product parts. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return res;
    }

    private OrderLineProductPart createOrderLineProductPart(ResultSet rs) throws SQLException {
        ProductPart productPart = new ProductPart(
                rs.getLong("product_part_id"),
                rs.getString("part_option"),
                rs.getBoolean("is_available"),
                rs.getDouble("base_price"),
                ProductPartCategory.valueOf(rs.getString("category").toUpperCase()),
                rs.getTimestamp("product_part_created_at").toLocalDateTime());

        return new OrderLineProductPart(
                null,
                productPart,
                rs.getDouble("final_price"),
                rs.getTimestamp("created_at").toLocalDateTime());
    }
}