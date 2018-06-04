package com.yash.ytcvm.dao;

import java.util.List;

import com.yash.ytcvm.exception.FileNotExistException;
import com.yash.ytcvm.model.Order;

public interface OrderDAO {

	public int insertOrder(Order order) throws FileNotExistException;

	public List<Order> getAllOrders() throws FileNotExistException;

}
