package com.yash.ytcvm.serviceimpl;

import org.apache.log4j.Logger;

import com.yash.ytcvm.builder.BlackCoffeDrinkBuilder;
import com.yash.ytcvm.builder.BlackTeaDrinkBuilder;
import com.yash.ytcvm.builder.CoffeDrinkBuilder;
import com.yash.ytcvm.builder.DrinkBuilder;
import com.yash.ytcvm.builder.TeaDrinkBuilder;
import com.yash.ytcvm.dao.OrderDAO;
import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.exception.ContainerUnderflowException;
import com.yash.ytcvm.exception.FileNotExistException;
import com.yash.ytcvm.exception.NullValueException;
import com.yash.ytcvm.model.Container;
import com.yash.ytcvm.model.Order;
import com.yash.ytcvm.service.OrderService;

public class OrderServiceImpl implements OrderService {

	private static Logger logger=Logger.getLogger(OrderServiceImpl.class);
	
	private DrinkBuilder drinkBuilder;
	
	private OrderDAO orderDAO;
	
	public OrderServiceImpl(OrderDAO orderDAO) {
		this.orderDAO=orderDAO;
	}
	
	
	public void setDrinkBuilderByOrderType(Order order){
		DrinkTypeEnum drinkTypeEnum=order.getDrinkTypeEnum();
		switch(drinkTypeEnum){
		case TEA:
			drinkBuilder=TeaDrinkBuilder.getDrinkBuilder();
			break;
		case COFFEE:
			drinkBuilder=CoffeDrinkBuilder.getDrinkBuilder();
			break;
		case BLACK_TEA:
			drinkBuilder=BlackTeaDrinkBuilder.getDrinkBuilder();
			break;
		case BLACK_COFFEE:
			drinkBuilder=BlackCoffeDrinkBuilder.getDrinkBuilder();
			break;
		}
	}
	
	public int processOrder(Order order) throws ContainerUnderflowException, FileNotExistException {
		logger.info("processOrder started");
		checkForNullOrder(order);
		setDrinkBuilderByOrderType(order);
		drinkBuilder.process(order);
		int result=orderDAO.insertOrder(order);
		logger.info("processOrder ends");
		return result;
	}

	private void checkForNullOrder(Order order) {
		if(checkForNullObject(order)){
			logger.error("Order is null");
			throw new NullValueException("Order is null");
		}
	}

	private boolean checkForNullObject(Object nullableObject) {
		return nullableObject==null;
	}


}
