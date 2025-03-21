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

import com.ezepsosa.marcusbike.models.OrderLine;
import com.ezepsosa.marcusbike.models.Product;

// Manages database operations for orders, including retrieval, insertion, updating, and deletion.
public class OrderLineDAO {

	private static final Logger logger = LoggerFactory.getLogger(OrderLineDAO.class);

	private final String SQL_GET_ALL_QUERY = "SELECT ol.*, p.product_name, p.brand, p.category, p.material, p.image_url, p.created_at AS product_created_at FROM order_line ol JOIN product p ON ol.product_id = p.id";
	private final String SQL_GET_ID_QUERY = "SELECT ol.*, p.product_name, p.brand, p.category, p.material, p.image_url, p.created_at AS product_created_at FROM order_line ol JOIN product p ON ol.product_id = p.id WHERE ol.id = (?)";
	private final String SQL_INSERT_QUERY = "INSERT INTO order_line(app_order_id, product_id, quantity) VALUES (?, ?, ?)";
	private final String SQL_UPDATE_QUERY = "UPDATE order_line SET app_order_id = ?, product_id = ?, quantity = ? WHERE id = ?";
	private final String SQL_DELETE_QUERY = "DELETE FROM order_line WHERE id = (?)";
	private final String SQL_GET_BY_ORDER_ID = "SELECT ol.*,  p.product_name, p.brand, p.category, p.material, p.image_url, p.created_at AS product_created_at FROM order_line ol JOIN product p ON ol.product_id = p.id WHERE ol.app_order_id = ?";

	public Map<Long, List<OrderLine>> getAllGroupedByOrder(Connection connection) {
		Map<Long, List<OrderLine>> orderLines = new HashMap<>();
		try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ALL_QUERY);
				ResultSet rs = pst.executeQuery()) {
			while (rs.next()) {
				Long orderId = rs.getLong("app_order_id");
				orderLines.computeIfAbsent(orderId, k -> new ArrayList<>()).add(createOrderLine(rs));
			}
		} catch (SQLException e) {
			logger.warn("Error fetching order lines grouped by order. SQL returned error {}, Error Code: {}",
					e.getSQLState(), e.getErrorCode());
		}
		return orderLines;
	}

	public OrderLine getById(Connection connection, Long id) {
		try (PreparedStatement pst = connection.prepareStatement(SQL_GET_ID_QUERY)) {
			pst.setLong(1, id);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return createOrderLine(rs);
				}
			}
		} catch (SQLException e) {
			logger.warn("Error fetching order line by id. SQL returned error {}, Error Code: {}", e.getSQLState(),
					e.getErrorCode());
		}
		return null;
	}

	public Long insert(Connection connection, OrderLine orderLine, Long orderId) {
		try (PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
				PreparedStatement.RETURN_GENERATED_KEYS)) {
			pst.setLong(1, orderId);
			pst.setLong(2, orderLine.getProduct().getId());
			pst.setInt(3, orderLine.getQuantity());

			int affectedRows = pst.executeUpdate();
			if (affectedRows > 0) {
				try (ResultSet generatedValues = pst.getGeneratedKeys()) {
					if (generatedValues.next()) {
						return generatedValues.getLong(1);
					}
				}
			}
		} catch (SQLException e) {
			logger.warn("Error inserting order line. SQL returned error {}, Error Code: {}", e.getSQLState(),
					e.getErrorCode());
		}
		return null;
	}

	public Boolean update(Connection connection, OrderLine order, Long orderId) {
		try (PreparedStatement pst = connection.prepareStatement(SQL_UPDATE_QUERY)) {
			pst.setLong(1, orderId);
			pst.setLong(2, order.getProduct().getId());
			pst.setInt(3, order.getQuantity());
			pst.setLong(4, order.getId());

			return pst.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.warn("Error updating order line. SQL returned error {}, Error Code: {}", e.getSQLState(),
					e.getErrorCode());
		}
		return false;
	}

	public Boolean delete(Connection connection, Long id) {
		try (PreparedStatement pst = connection.prepareStatement(SQL_DELETE_QUERY)) {
			pst.setLong(1, id);
			return pst.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.warn("Error deleting order line. SQL returned error {}, Error Code: {}", e.getSQLState(),
					e.getErrorCode());
		}
		return false;
	}

	public List<OrderLine> getByOrderId(Connection connection, Long orderId) {
		List<OrderLine> ordersLines = new ArrayList<>();
		try (PreparedStatement pst = connection.prepareStatement(SQL_GET_BY_ORDER_ID)) {
			pst.setLong(1, orderId);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					ordersLines.add(createOrderLine(rs));
				}
			}
		} catch (SQLException e) {
			logger.warn("Error fetching order line by order Id. SQL returned error {}, Error Code: {}", e.getSQLState(),
					e.getErrorCode());
		}
		return ordersLines;
	}

	public List<Long> insertAll(Connection connection, List<OrderLine> orderLines, Long orderId) {
		List<Long> res = new ArrayList<>();

		try (PreparedStatement pst = connection.prepareStatement(SQL_INSERT_QUERY,
				PreparedStatement.RETURN_GENERATED_KEYS)) {

			for (OrderLine orderLine : orderLines) {
				pst.setLong(1, orderId);
				pst.setLong(2, orderLine.getProduct().getId());
				pst.setInt(3, orderLine.getQuantity());
				pst.addBatch();
			}

			pst.executeBatch();

			try (ResultSet generatedValues = pst.getGeneratedKeys()) {
				while (generatedValues.next()) {
					res.add(generatedValues.getLong(1));
				}
			}

		} catch (SQLException e) {
			logger.warn("Error inserting order lines. SQL returned error {}, Error Code: {}",
					e.getSQLState(), e.getErrorCode());
		}

		return res;
	}

	private OrderLine createOrderLine(ResultSet rs) throws SQLException {
		Product product = new Product(rs.getLong("product_id"), rs.getString("product_name"), rs.getString("brand"),
				rs.getString("category"), rs.getString("material"), rs.getString("image_url"), new ArrayList<>(),
				rs.getTimestamp("product_created_at").toLocalDateTime());

		return new OrderLine(rs.getLong("id"), product, rs.getInt("quantity"),
				rs.getTimestamp("created_at").toLocalDateTime());
	}
}
