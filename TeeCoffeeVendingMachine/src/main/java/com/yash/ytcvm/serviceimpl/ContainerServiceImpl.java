package com.yash.ytcvm.serviceimpl;

import com.yash.ytcvm.enums.ContainerEnum;
import com.yash.ytcvm.exception.ContainerOverFlowException;
import com.yash.ytcvm.exception.InvalidInputException;
import com.yash.ytcvm.model.Container;
import com.yash.ytcvm.service.ContainerService;

public class ContainerServiceImpl implements ContainerService {

	private Container container;

	public ContainerServiceImpl() {
		container = Container.getContainer();
	}

	public boolean refillContainer(ContainerEnum containerType, int quantity) throws ContainerOverFlowException {
		checkForInvalidQuantity(quantity);
		checkForOverFlow(containerType, quantity);
		int remainingQuantity=updateContainer(containerType, quantity);
		System.out.println(containerType+" has filled with: "+quantity+" and remaining capacity is :"+remainingQuantity);
		return true;
	}

	private void checkForInvalidQuantity(int quantity) {
		if (quantity <= 0) {
			throw new InvalidInputException("passed quantity :" + quantity + " is an invalid to refill");
		}
	}

	private int updateContainer(ContainerEnum containerType, int quantity) {
		int remaining=0;
		switch (containerType) {
		case TEA:
			container.setTea(container.getTea() + quantity);
			remaining= Container.TEA_CONTAINER_MAX_CAPACITY-container.getTea();
			break;
		case SUGAR:
			container.setSugar(container.getSugar() + quantity);
			remaining= Container.SUGAR_CONTAINER_MAX_CAPACITY-container.getSugar();
			break;
		case COFFEE:
			container.setCoffee(container.getCoffee() + quantity);
			remaining=Container.COFFEE_CONTAINER_MAX_CAPACITY-container.getCoffee();
			break;
		case WATER:
			container.setWater(container.getWater() + quantity);
			remaining=Container.WATER_CONTAINER_MAX_CAPACITY-container.getWater();
			break;
		case MILK:
			container.setMilk(container.getMilk() + quantity);
			remaining=Container.MILK_CONTAINER_MAX_CAPACITY-container.getMilk();
			break;
		default:
			break;
		}
		return remaining;
	}

	private void checkForOverFlow(ContainerEnum containerType, int quantity) throws ContainerOverFlowException {
		switch (containerType) {
		case TEA:
			int remainingTeaQuantity=Container.TEA_CONTAINER_MAX_CAPACITY - container.getTea();
			if (quantity > remainingTeaQuantity)
				throw new ContainerOverFlowException("Quantity to be fill is more than tea container capacity, required :"+remainingTeaQuantity);
			break;
		case SUGAR:
			int remainingSugarQuantity=Container.TEA_CONTAINER_MAX_CAPACITY - container.getTea();
			if (quantity > remainingSugarQuantity)
				throw new ContainerOverFlowException("Quantity to be fill is more than sugar container capacity, required :"+remainingSugarQuantity);
			break;
		case COFFEE:
			int remainingCoffeQuantity=Container.TEA_CONTAINER_MAX_CAPACITY - container.getTea();
			if (quantity > (Container.COFFEE_CONTAINER_MAX_CAPACITY - container.getCoffee()))
				throw new ContainerOverFlowException("Quantity to be fill is more than coffee container capacity, required :"+remainingCoffeQuantity);
			break;
		case WATER:
			int remainingWaterQuantity=Container.WATER_CONTAINER_MAX_CAPACITY - container.getWater();
			if (quantity > remainingWaterQuantity)
				throw new ContainerOverFlowException("Quantity to be fill is more than water container capacity, required :"+remainingWaterQuantity);
			break;
		case MILK:
			int remainingMilkQuantity=Container.MILK_CONTAINER_MAX_CAPACITY - container.getMilk();
			if (quantity >remainingMilkQuantity )
				throw new ContainerOverFlowException("Quantity to be fill is more than milk container capacity, required :"+remainingMilkQuantity);
			break;
		default:
			break;
		}
	}

}
