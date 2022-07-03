package com.orders.models;

import com.opencsv.bean.CsvBindByPosition;

/**
 * @author PIYUSH GUPTA
 * POJO class to map incoming CSV strings
 * assuming all fields to be of string type
 * to handle cast exceptions while converting later.
 * Mapping columns by their position
 */
public class CsvOrder {
	
	@CsvBindByPosition(position = 0)
	private String orderId;
	@CsvBindByPosition(position = 1)
	private String amount;
	@CsvBindByPosition(position = 2)
	private String currency;
	@CsvBindByPosition(position = 3)
	private String comment;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
