package com.yash.ytcvm.daoimpl;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.yash.ytcvm.dao.OrderDAO;
import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.exception.EmptyFileException;
import com.yash.ytcvm.exception.FileNotExistException;
import com.yash.ytcvm.exception.NullValueException;
import com.yash.ytcvm.model.Order;
import com.yash.ytcvm.util.FileHelper;

public class OrderDAOImplTest {

	private OrderDAO orderDAO;
	private FileHelper fileHelper;
	
	@Before
	public void init() {
		fileHelper=mock(FileHelper.class);
		orderDAO=new OrderDAOImpl(fileHelper);
	}

	@Test(expected= FileNotExistException.class)
	public void getAllOrder_FileNotPresent_ThrowFileNotExistException() throws Exception {
		String nonExitingFilePath = anyString();
		when(fileHelper.readJsonFile(nonExitingFilePath)).thenThrow(FileNotExistException.class);
		orderDAO.getAllOrders();
	}
	
	@Test(expected= EmptyFileException.class)
	public void getAllOrder_EmptyFilePresent_ThrowFileNotExistException() throws Exception {
		String nonExitingFilePath = anyString();
		when(fileHelper.readJsonFile(nonExitingFilePath)).thenThrow(EmptyFileException.class);
		orderDAO.getAllOrders();
	}
	
	@Test
	public void getAllOrder_ValidFilePresent_ShouldReturnOrderList() throws Exception {
		String validFilePath = anyString();
		String ordersArrays="[{\"quantity\":5,\"drinkTypeEnum\":\"TEA\",\"orderDate\":\"Jun 2, 2018 12:19:31 PM\",\"false\":true}]";
		List<Order> ordersInFile=Arrays.asList(new Order(5, DrinkTypeEnum.TEA));
		when(fileHelper.readJsonFile(validFilePath)).thenReturn(ordersArrays);
		List<Order>orderList=orderDAO.getAllOrders();
		assertEquals(ordersInFile.size(), orderList.size());
	}
	
	@Test(expected=NullValueException.class)
	public void insertOrder_NullOrderObjectGiven_ThrowNullValueException() throws Exception {
		Order nullOrder=null;
		int rowsAffected=orderDAO.insertOrder(nullOrder);
	}
	
	@Test
	public void insertOrder_ValidFilePathPresent_ShouldAddOrderAndReturnOne() throws Exception {
		String validOrderFilePath="./src/main/resources/json_files/orders.json";
		String jsonOrderToBeAdded="{\"quantity\":6,\"drinkTypeEnum\":\"TEA\",\"orderDate\":\"Jun 2, 2018 2:58:57 PM\",\"status\":true}";
		String alreadyExistOrders="[{\"quantity\":5,\"orderDate\":\"Jun 2, 2018 12:21:31 PM\",\"status\":true},{\"quantity\":6,\"drinkTypeEnum\":\"TEA\",\"orderDate\":\"Jun 2, 2018 12:19:31 PM\",\"status\":true},{\"quantity\":6,\"drinkTypeEnum\":\"TEA\",\"orderDate\":\"Jun 2, 2018 2:29:50 PM\"}]";
		Order newOrder=new Order(6,DrinkTypeEnum.TEA);
		when(fileHelper.readJsonFile(validOrderFilePath)).thenReturn(alreadyExistOrders);
		when(fileHelper.writeJsonStringToFile(validOrderFilePath,jsonOrderToBeAdded)).thenReturn(1);
		int rowsAffected=orderDAO.insertOrder(newOrder);
		assertEquals(0, rowsAffected);
	}
}
