package com.yash.ytcvm.daoimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.yash.ytcvm.config.FilePathLiterals;
import com.yash.ytcvm.dao.OrderDAO;
import com.yash.ytcvm.exception.FileNotExistException;
import com.yash.ytcvm.exception.NullValueException;
import com.yash.ytcvm.model.Order;
import com.yash.ytcvm.util.FileHelper;

public class OrderDAOImpl implements OrderDAO {

	private FileHelper fileHelper;
	
	private static Logger logger=Logger.getLogger(OrderDAOImpl.class);
	
	
	public OrderDAOImpl(FileHelper fileHelper) {
		this.fileHelper=fileHelper;
	}

	public int insertOrder(Order order) throws FileNotExistException {
		logger.info("insertOrder started");
		checkForNullObject(order);
		List<Order> ordersInJsonFile=this.getAllOrders();
		ordersInJsonFile.add(order);
		Gson gson=new Gson();
		String updatedOrdersJson=gson.toJson(ordersInJsonFile);
		int result=fileHelper.writeJsonStringToFile(FilePathLiterals.ORDER_JSON_FILEPATH, updatedOrdersJson);
		logger.info("insertOrder ended");
		return result;
	}

	public List<Order> getAllOrders() throws FileNotExistException {
		logger.info("getAllOrders started");
		String orderJson=fileHelper.readJsonFile(FilePathLiterals.ORDER_JSON_FILEPATH);
		Gson gson=new Gson();
		Order[]orders=gson.fromJson(orderJson, Order[].class);
		List<Order> orderList=new ArrayList<Order>(Arrays.asList(orders));
		logger.info("getAllOrders ended");
		return orderList;
	}
	
	
	private void checkForNullObject(Order order) {
		if(isNullObject(order)){
			throw new NullValueException("order is null");
		}
	}

	private boolean isNullObject(Object order) {
		return null==order;
	}



}
