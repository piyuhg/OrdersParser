package com.orders.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author PIYUSH GUPTA
 * POJO class for Order
 */
public class Order {
	
	private static Long idCounter = 0l;
	
	@JsonProperty
	private Long id;
	@JsonProperty
	private Long orderId;
	@JsonProperty
	private Double amount;
	@JsonProperty
	private String currency;
	@JsonProperty
	private String comment;
	@JsonProperty
	private String filename;
	@JsonProperty
	private Long line;
	@JsonProperty
	private String result;
	
	public Order() {
		incrementAndSetId(this);
	}
	
	@JsonIgnore
	private List<String> errors = new ArrayList<>(4);

	public Long getId() {
		return id;
	}

	/**
	 * synchronized method to increment and assign the id to every
	 * Order object.
	 * @param order
	 */
	private static synchronized void incrementAndSetId(Order order) {
		order.id = ++idCounter;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getLine() {
		return line;
	}

	public void setLine(Long line) {
		this.line = line;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
