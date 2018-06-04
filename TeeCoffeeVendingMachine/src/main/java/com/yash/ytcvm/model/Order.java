package com.yash.ytcvm.model;

import java.util.Date;

import com.yash.ytcvm.enums.DrinkTypeEnum;



public class Order {

	private int quantity;
	private DrinkTypeEnum drinkTypeEnum;
	private Date orderDate = new Date();
	private Boolean status;

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(int quantity, DrinkTypeEnum drinkTypeEnum) {
		super();
		this.quantity = quantity;
		this.drinkTypeEnum = drinkTypeEnum;
	}

	public Order(int quantity, DrinkTypeEnum drinkTypeEnum, Date orderDate, Boolean status) {
		super();
		this.quantity = quantity;
		this.drinkTypeEnum = drinkTypeEnum;
		this.orderDate = orderDate;
		this.status = status;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public DrinkTypeEnum getDrinkTypeEnum() {
		return drinkTypeEnum;
	}

	public void setDrinkTypeEnum(DrinkTypeEnum drinkTypeEnum) {
		this.drinkTypeEnum = drinkTypeEnum;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Order{ \r\n");
		sb.append("Qty : " + quantity + "\r\n");
		sb.append("Type : " + drinkTypeEnum + "\r\n");
		sb.append("Date : " + orderDate + "\r\n");
		sb.append("Status : " + status + "\r\n");
		sb.append("}");
		return sb.toString();
	}

}