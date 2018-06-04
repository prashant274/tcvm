package com.yash.ytcvm.service;

import com.yash.ytcvm.exception.ContainerUnderflowException;
import com.yash.ytcvm.exception.FileNotExistException;
import com.yash.ytcvm.model.Order;

public interface OrderService {

	int processOrder(Order order) throws ContainerUnderflowException, FileNotExistException;



}
