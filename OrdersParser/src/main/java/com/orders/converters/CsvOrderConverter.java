package com.orders.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.orders.models.CsvOrder;
import com.orders.models.Order;

/**
 * @author PIYUSH GUPTA
 */
public class CsvOrderConverter extends OrderConverter {

	/**
	 * Converts list of CsvOrder objects to list of Order objects
	 * 
	 * @param csvOrders
	 * @param filename
	 * @return
	 */
	public List<Order> convert(List<CsvOrder> csvOrders, String filename) {
		List<Order> orders = null;
		if (!CollectionUtils.isEmpty(csvOrders)) {
			orders = new ArrayList<>(csvOrders.size());
			long lineNumber = 0;
			for (CsvOrder csvOrder : csvOrders) {
				Order order = convert(csvOrder);
				order.setLine(++lineNumber);
				order.setFilename(filename);
				evaluateAndSetResult(order);
				orders.add(order);
			}
		}
		return orders;
	}

	/**
	 * convert CsvOrder object to Order object
	 * 
	 * @param csvOrder
	 * @return
	 */
	private Order convert(CsvOrder csvOrder) {
		Order order = new Order();
		setOrderId(order, csvOrder.getOrderId());
		setAmount(order, csvOrder.getAmount());
		setCurrency(order, csvOrder.getCurrency());
		setComment(order, csvOrder.getComment());
		return order;
	}

}
