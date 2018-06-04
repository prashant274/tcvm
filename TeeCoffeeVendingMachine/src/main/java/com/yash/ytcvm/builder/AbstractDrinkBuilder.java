package com.yash.ytcvm.builder;

import com.yash.ytcvm.config.AbstractDrinkConfigurer;
import com.yash.ytcvm.config.DrinkConfigurer;
import com.yash.ytcvm.enums.ContainerEnum;
import com.yash.ytcvm.exception.ContainerUnderflowException;
import com.yash.ytcvm.model.Container;
import com.yash.ytcvm.model.Order;

public abstract class AbstractDrinkBuilder implements DrinkBuilder {
	Container container;
	DrinkConfigurer dConfigurer;

	int teaRequired;
	int milkRequired;
	int waterRequired;
	int sugarRequired;
	int coffeeRequired;

	public AbstractDrinkBuilder() {
		container = Container.getContainer();
	}

	public void setDrinkConfigurer(DrinkConfigurer drinkConfigurer) {
		this.dConfigurer = drinkConfigurer;
	}

	public void process(Order order) throws ContainerUnderflowException {
		checkUnderFlow(order);
		updateContainer();
		order.setStatus(true);
	}
	
	private void checkUnderFlow(Order order) throws ContainerUnderflowException {

		AbstractDrinkConfigurer drinkConfigurer = (AbstractDrinkConfigurer) dConfigurer;
		teaRequired = order.getQuantity() * (drinkConfigurer.getTeaUse() + drinkConfigurer.getTeaWaste());
		checkForLessTeaInContainer();
		milkRequired = order.getQuantity() * (drinkConfigurer.getMilkUse() + drinkConfigurer.getMilkWaste());
		checkForLessMilkInContainer();
		waterRequired = order.getQuantity() * (drinkConfigurer.getWaterUse() + drinkConfigurer.getWaterWaste());
		checkForLessWaterInContainer();
		sugarRequired = order.getQuantity() * (drinkConfigurer.getSugarUse() + drinkConfigurer.getSugarWaste());
		checkForLessSugarInContainer();
		coffeeRequired = order.getQuantity() * (drinkConfigurer.getCoffeeUse() + drinkConfigurer.getCoffeeWaste());
		checkForLessCoffeInContainer();

	}

	private void checkForLessTeaInContainer() throws ContainerUnderflowException {
		if (isLessTeaInContainer()) {
			throw new ContainerUnderflowException(ContainerEnum.TEA + "Insufficient");
		}
	}

	private void checkForLessMilkInContainer() throws ContainerUnderflowException {
		if (isLessMilkInContainer()) {
			throw new ContainerUnderflowException(ContainerEnum.MILK + " Insufficient");
		}
	}

	private void checkForLessWaterInContainer() throws ContainerUnderflowException {
		if (isLessWaterInContainer()) {
			throw new ContainerUnderflowException(ContainerEnum.WATER + " Insufficient");
		}
	}

	private void checkForLessSugarInContainer() throws ContainerUnderflowException {
		if (isLessSugarInContainer()) {
			throw new ContainerUnderflowException(ContainerEnum.SUGAR + " Insufficient");
		}
	}

	private void checkForLessCoffeInContainer() throws ContainerUnderflowException {
		if (isLessCoffeInContainer()) {
			throw new ContainerUnderflowException(ContainerEnum.COFFEE + " Insufficient");
		}
	}

	private boolean isLessCoffeInContainer() {
		return container.getCoffee() < coffeeRequired;
	}

	private boolean isLessSugarInContainer() {
		return container.getSugar() < sugarRequired;
	}

	private boolean isLessWaterInContainer() {
		return container.getWater() < waterRequired;
	}

	private boolean isLessMilkInContainer() {
		return container.getMilk() < milkRequired;
	}

	private boolean isLessTeaInContainer() {
		return container.getTea() < teaRequired;
	}

	public Container getContainer() {

		return this.container;
	}

	public void updateContainer() {
		container.setCoffee(container.getCoffee() - coffeeRequired);
		container.setMilk(container.getMilk() - milkRequired);
		container.setSugar(container.getSugar() - sugarRequired);
		container.setTea(container.getTea() - teaRequired);
		container.setWater(container.getWater() - waterRequired);

	}

}
