package com.orders.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.orders.models.JsonOrder;
import com.orders.models.Order;

/**
 * @author PIYUSH GUPTA
 *
 */
public class JsonOrderConverter extends OrderConverter {

	/**
	 * Converts list of JsonOrder objects to list of Order objects
	 * 
	 * @param jsonOrders
	 * @param filename
	 * @return
	 */
	public List<Order> convert(List<JsonOrder> jsonOrders, String filename) {
		List<Order> orders = null;
		if (!CollectionUtils.isEmpty(jsonOrders)) {
			orders = new ArrayList<>(jsonOrders.size());
			long lineNumber = 0;
			for (JsonOrder jsonOrder : jsonOrders) {
				Order order = convert(jsonOrder);
				order.setLine(++lineNumber);
				order.setFilename(filename);
				evaluateAndSetResult(order);
				orders.add(order);
			}
		}
		return orders;
	}

	/**
	 * convert JsonOrder object to Order object
	 * 
	 * @param jsonOrder
	 * @return
	 */
	private Order convert(JsonOrder jsonOrder) {
		Order order = new Order();
		setOrderId(order, jsonOrder.getOrderId());
		setAmount(order, jsonOrder.getAmount());
		setCurrency(order, jsonOrder.getCurrency());
		setComment(order, jsonOrder.getComment());
		return order;
	}

}
