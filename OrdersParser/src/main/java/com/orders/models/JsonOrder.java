package com.orders.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author PIYUSH GUPTA
 * POJO class to map incoming JSON strings
 * assuming all fields to be of string type
 * to handle cast exceptions while converting later
 */
public class JsonOrder {
	
	@JsonProperty
	private String orderId;
	@JsonProperty
	private String amount;
	@JsonProperty
	private String currency;
	@JsonProperty
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
