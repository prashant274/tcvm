package com.yash.ytcvm.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.yash.ytcvm.enums.ContainerEnum;
import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.exception.ContainerOverFlowException;
import com.yash.ytcvm.exception.InvalidInputException;
import com.yash.ytcvm.model.Container;
import com.yash.ytcvm.service.ContainerService;

public class ContainerServiceImplTest {

	private ContainerService containerService;

	@Before
	public void init() {
		containerService = new ContainerServiceImpl();
	}

	@Test(expected = InvalidInputException.class)
	public void refillContainer_quantityZeroOrLessGiven_ThrowInvalidInputException() throws ContainerOverFlowException {
		containerService.refillContainer(ContainerEnum.COFFEE, -90);
	}

	@Test(expected = ContainerOverFlowException.class)
	public void refillContainer_quantityMoreThanCapacityGiven_ThrowContainOverFlowException()
			throws ContainerOverFlowException {
		ContainerEnum containerToFilled=ContainerEnum.COFFEE;
		int quantityTobeFilled=3000;
		containerService.refillContainer(containerToFilled, quantityTobeFilled);
	}

	@Test
	public void refillContainer_validQunatityGiven_ShouldUpdateContainerWithGivenQuantityAndReturnTrue()
			throws ContainerOverFlowException {
		int currentCoffeContainerCapacity = 1000;
		Container container = Container.getContainer();
		container.setCoffee(currentCoffeContainerCapacity);
		int coffeeQuantityToAdd = 500;
		boolean isUpdated = containerService.refillContainer(ContainerEnum.COFFEE, coffeeQuantityToAdd);
		assertEquals(1500, container.getCoffee());
		assertTrue(isUpdated);
	}

}
