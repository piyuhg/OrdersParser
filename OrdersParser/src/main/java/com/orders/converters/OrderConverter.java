package com.orders.converters;

import org.springframework.util.StringUtils;

import com.orders.models.Order;

public abstract class OrderConverter {

	private static final String RESULT_OK = "OK";
	private static final String RESULT_ERROR = "ERROR";
	private static final String ERROR_MISSING = "Missing required Field: ";
	private static final String ERROR_INVALID = "Invalid value for ";

	protected void setOrderId(Order order, String orderId) {
		if (StringUtils.hasText(orderId)) {
			try {
				order.setOrderId(Long.parseLong(orderId));
			} catch (NumberFormatException e) {
				order.getErrors().add(ERROR_INVALID + "orderId: " + orderId);
			}
		} else {
			order.getErrors().add(ERROR_MISSING + " orderId");
		}
	}

	protected void setAmount(Order order, String amount) {
		if (StringUtils.hasText(amount)) {
			try {
				order.setAmount(Double.parseDouble(amount));
			} catch (NumberFormatException e) {
				order.getErrors().add(ERROR_INVALID + "amount: " + amount);
			}
		} else {
			order.getErrors().add(ERROR_MISSING + " amount");
		}
	}

	protected void setCurrency(Order order, String currency) {
		if (StringUtils.hasText(currency)) {
			order.setCurrency(currency);
		} else {
			order.getErrors().add(ERROR_MISSING + " currency");
		}
	}

	protected void setComment(Order order, String comment) {
		if (StringUtils.hasText(comment)) {
			order.setComment(comment);
		} else {
			order.getErrors().add(ERROR_MISSING + " comment");
		}
	}

	protected void evaluateAndSetResult(Order order) {
		if (order.getErrors().isEmpty()) {
			order.setResult(RESULT_OK);
		} else {
			order.setResult(RESULT_ERROR + ": " + order.getErrors());
		}
	}

}
