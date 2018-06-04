package com.yash.ytcvm.builder;

import com.yash.ytcvm.config.BlackTeaConfig;
import com.yash.ytcvm.enums.ContainerEnum;
import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.exception.ContainerOverFlowException;
import com.yash.ytcvm.exception.ContainerUnderflowException;
import com.yash.ytcvm.model.Order;

public class BlackTeaDrinkBuilder extends AbstractDrinkBuilder{
	
	public BlackTeaDrinkBuilder() {
		setDrinkConfigurer(BlackTeaConfig.getDrinkConfigurer());
	}

	@Override
	public void process(Order order) throws ContainerUnderflowException{
		super.process(order);
	}
	
	public static DrinkBuilder getDrinkBuilder(){
		return new BlackTeaDrinkBuilder();
	}

}
