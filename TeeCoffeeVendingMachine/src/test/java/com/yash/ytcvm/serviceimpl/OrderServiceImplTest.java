package com.yash.ytcvm.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.yash.ytcvm.builder.DrinkBuilder;
import com.yash.ytcvm.dao.OrderDAO;
import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.exception.ContainerUnderflowException;
import com.yash.ytcvm.exception.FileNotExistException;
import com.yash.ytcvm.exception.NullValueException;
import com.yash.ytcvm.model.Container;
import com.yash.ytcvm.model.Order;
import com.yash.ytcvm.service.OrderService;

public class OrderServiceImplTest {

	private OrderService orderService;
	private DrinkBuilder drinkBuilder;
	private OrderDAO orderDAO;

	@Before
	public void init() {
		orderDAO = mock(OrderDAO.class);
		orderService = new OrderServiceImpl(orderDAO);
		drinkBuilder = mock(DrinkBuilder.class);
	}

	@Test(expected = NullValueException.class)
	public void processOrder_NullOrderPresent_ThrowNullValueException()
			throws ContainerUnderflowException, FileNotExistException {
		Order nullOrder = null;
		int rowsAffected = orderService.processOrder(nullOrder);
	}

	@Test(expected = ContainerUnderflowException.class)
	public void processOrder_OrderQuantityMoreThanCapacityGiven_ThrowContainerUnderFlowException()
			throws ContainerUnderflowException, FileNotExistException {
		Order order = new Order();
		order.setQuantity(5);
		order.setDrinkTypeEnum(DrinkTypeEnum.TEA);
		order.setStatus(false);
		doThrow(new ContainerUnderflowException("Tea is insufficient")).when(drinkBuilder).process(order);
		orderService.processOrder(order);
	}

	@Test
	public void processOrder_TeaOrderQuantityLessThanCapacityGiven_ShouldNotUpdateCoffeContainer() throws Exception {
		Order order = new Order();
		order.setQuantity(5);
		order.setDrinkTypeEnum(DrinkTypeEnum.TEA);
		orderService.processOrder(order);
		assertEquals(2000, Container.getContainer().getCoffee());
	}

	@Test
	public void processOrder_OrderQuantityLessThanCapacityGiven_AllContainerShouldBeUpdated() throws Exception {
		Order order = new Order();
		order.setQuantity(5);
		order.setDrinkTypeEnum(DrinkTypeEnum.TEA);
		orderService.processOrder(order);
		assertEquals(1970, Container.getContainer().getTea());
		assertEquals(14675, Container.getContainer().getWater());
		assertEquals(9780, Container.getContainer().getMilk());
		assertEquals(7915, Container.getContainer().getSugar());
	}

	@Test
	public void processOrder_ValidOrderGiven_OrderSuccessfullShouldChangeOrderStatus() throws Exception {
		Order order = new Order();
		order.setQuantity(5);
		order.setDrinkTypeEnum(DrinkTypeEnum.TEA);
		orderService.processOrder(order);
		assertTrue(order.getStatus());
	}

	@Test
	public void processOrder_ValidOrderGiven_OrderSuccessfullShouldReturnOne() throws Exception {
		Order order = new Order();
		order.setQuantity(5);
		order.setDrinkTypeEnum(DrinkTypeEnum.TEA);
		when(orderDAO.insertOrder(order)).thenReturn(1);
		int rowsAffected = orderService.processOrder(order);
		assertEquals(1, rowsAffected);
	}
}
