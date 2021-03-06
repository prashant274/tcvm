package com.yash.ytcvm.builder;

import com.yash.ytcvm.config.BlackCoffeConfig;
import com.yash.ytcvm.exception.ContainerUnderflowException;
import com.yash.ytcvm.model.Order;

public class BlackCoffeDrinkBuilder extends AbstractDrinkBuilder{
	
	public BlackCoffeDrinkBuilder() {
		setDrinkConfigurer(BlackCoffeConfig.getDrinkConfigurer());
	}

	@Override
	public void process(Order order) throws ContainerUnderflowException{
		super.process(order);
	}
	
	public static DrinkBuilder getDrinkBuilder(){
		return new BlackCoffeDrinkBuilder();
	}

}
