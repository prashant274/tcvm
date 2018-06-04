package com.yash.ytcvm.builder;

import com.yash.ytcvm.config.CoffeConfig;
import com.yash.ytcvm.enums.ContainerEnum;
import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.exception.ContainerOverFlowException;
import com.yash.ytcvm.exception.ContainerUnderflowException;
import com.yash.ytcvm.model.Order;

public class CoffeDrinkBuilder extends AbstractDrinkBuilder{
	
	public CoffeDrinkBuilder() {
		setDrinkConfigurer(CoffeConfig.getDrinkConfigurer());
	}

	@Override
	public void process(Order order) throws ContainerUnderflowException{
		super.process(order);
	}
	
	public static DrinkBuilder getDrinkBuilder(){
		return new CoffeDrinkBuilder();
	}

}
