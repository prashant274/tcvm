package com.yash.ytcvm.builder;

import com.yash.ytcvm.config.DrinkConfigurer;
import com.yash.ytcvm.enums.ContainerEnum;
import com.yash.ytcvm.exception.ContainerOverFlowException;
import com.yash.ytcvm.exception.ContainerUnderflowException;
import com.yash.ytcvm.model.Container;
import com.yash.ytcvm.model.Order;

public interface DrinkBuilder {
	void setDrinkConfigurer(DrinkConfigurer drinkConfigurer);
	void process(Order order) throws ContainerUnderflowException;
	void updateContainer();
	Container getContainer();
}
