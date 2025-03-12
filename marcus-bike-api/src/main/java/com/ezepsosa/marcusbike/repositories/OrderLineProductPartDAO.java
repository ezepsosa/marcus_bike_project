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
import com.ezepsosa.marcusbike.models.OrderLineProductPart;
import com.ezepsosa.marcusbike.models.Product;
import com.ezepsosa.marcusbike.models.ProductPart;
import com.ezepsosa.marcusbike.models.ProductPartCategory;

public class OrderLineProductPartDAO {

    private static final Logger logger = LoggerFactory.getLogger(OrderLineProductPartDAO.class);

    private final String SQL_GET_ALL_QUERY = "SELECT olpp.*, pp.id AS product_part_id, pp.part_option, pp.is_available, pp.base_price, pp.category, pp.created_at AS product_part_created_at, p.id AS product_id, p.product_name, p.created_at AS product_created_at FROM order_line_product_part olpp JOIN product_part pp ON olpp.product_part_id = pp.id JOIN product p ON pp.product_id = p.id";
    private final String SQL_GET_ID_QUERY = "SELECT olpp.*, pp.id AS product_part_id, pp.part_option, pp.is_available, pp.base_price, pp.category, pp.created_at AS product_part_created_at, p.id AS product_id, p.product_name, p.created_at AS product_created_at FROM order_line_product_part olpp JOIN product_part pp ON olpp.product_part_id = pp.id JOIN product p ON pp.product_id = p.id WHERE olpp.order_line_id = ? AND olpp.product_part_id = ?";
    private final String SQL_INSERT_QUERY = "INSERT INTO order_line_product_part(order_line_id, product_part_id, quantity, final_price) VALUES (?, ?, ?, ?) RETURNING order_line_id AND";
    private final String SQL_UPDATE_QUERY = "UPDATE order_line_product_part SET quantity = ?, final_price = ? WHERE order_line_id = ? AND product_part_id = ?";
    private final String SQL_DETELE_QUERY = "DELETE FROM order_line_product_part WHERE order_line_id = ? AND product_part_id = ?";

    public List<OrderLineProductPart> getAll() {
        List<OrderLineProductPart> OrderLines = new ArrayList<>();
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                OrderLines.add(createOrderLineProductPart(rs));
            }
        } catch (SQLException e) {
            logger.warn("Error fetching order line product parts. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return OrderLines;
    }

    public OrderLineProductPart getById(Long order_line_id, Long product_part_id) {
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY);
            pst.setLong(1, order_line_id);
            pst.setLong(2, product_part_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return createOrderLineProductPart(rs);
            }
        } catch (SQLException e) {
            logger.warn("Error fetching order line product parts by Ids. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return null;
    }

    public Boolean insert(OrderLineProductPart orderLineProductPart) {
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
                    PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setLong(1, orderLineProductPart.getOrderLine().getId());
            pst.setLong(2, orderLineProductPart.getProductPart().getId());
            pst.setInt(3, orderLineProductPart.getQuantity());
            pst.setDouble(4, orderLineProductPart.getFinalPrice());

            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.warn("Error inserting order line product parts by Ids. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;

    }

    public Boolean update(OrderLineProductPart orderLineProductPart) {
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY);

            pst.setInt(1, orderLineProductPart.getQuantity());
            pst.setDouble(2, orderLineProductPart.getFinalPrice());
            pst.setLong(3, orderLineProductPart.getOrderLine().getId());
            pst.setLong(4, orderLineProductPart.getProductPart().getId());

            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.warn("Error updating order line product parts by Ids. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;

    }

    public Boolean delete(Long order_line_id, Long product_part_id) {
        try (Connection connection = HikariDatabaseConfig.getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SQL_DETELE_QUERY);

            pst.setLong(1, order_line_id);
            pst.setLong(2, product_part_id);

            Integer affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.warn("Error deleting order line product parts by Ids. SQL returned error {}, Error Code: {}",
                    e.getSQLState(), e.getErrorCode());
        }
        return false;
    }

    private OrderLineProductPart createOrderLineProductPart(ResultSet rs) throws SQLException {

        Product product = new Product(
                rs.getLong("product_id"),
                rs.getString("product_name"),
                rs.getTimestamp("product_created_at").toLocalDateTime());

        ProductPart productPart = new ProductPart(
                rs.getLong("product_part_id"),
                product,
                rs.getString("part_option"),
                rs.getBoolean("is_available"),
                rs.getDouble("base_price"),
                ProductPartCategory.valueOf(rs.getString("category").toUpperCase()),
                rs.getTimestamp("product_part_created_at").toLocalDateTime());

        return new OrderLineProductPart(
                null,
                productPart,
                rs.getInt("quantity"),
                rs.getDouble("final_price"),
                rs.getTimestamp("created_at").toLocalDateTime());
    }

}