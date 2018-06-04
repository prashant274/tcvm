package com.yash.ytcvm.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.yash.ytcvm.dao.OrderDAO;
import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.exception.EmptyFileException;
import com.yash.ytcvm.exception.FileNotExistException;
import com.yash.ytcvm.exception.NoValuePresentException;
import com.yash.ytcvm.model.Order;
import com.yash.ytcvm.service.ReportService;

public class ReportServiceImplTest {

	private ReportService reportService;
	private OrderDAO orderDAO;

	@Before
	public void init() {
		orderDAO = mock(OrderDAO.class);
		reportService = new ReportServiceImpl(orderDAO);
	}

	@Test(expected=FileNotExistException.class)
	public void getDrinkWiseTotalSaleReport_orderFileNotPresent_ThrowFileNotExistException() throws Exception {
		when(orderDAO.getAllOrders()).thenThrow(FileNotExistException.class);
		reportService.getDrinkWiseTotalSaleReport(DrinkTypeEnum.TEA);
	}
	
	@Test(expected=EmptyFileException.class)
	public void getDrinkWiseTotalSaleReport_OrderJsonFileEmpty_ThrowEmptyFIleException() throws Exception {
		when(orderDAO.getAllOrders()).thenThrow(EmptyFileException.class);
		reportService.getDrinkWiseTotalSaleReport(DrinkTypeEnum.TEA);
	}

	@Test(expected=NoValuePresentException.class)
	public void getDrinkWiseTotalSaleReport_NoOrderPresntForDrinkType_ThrowNoValuePresentException() throws Exception {
		when(orderDAO.getAllOrders()).thenReturn(Arrays.asList(new Order(5, DrinkTypeEnum.COFFEE),new Order(2, DrinkTypeEnum.TEA)));
		reportService.getDrinkWiseTotalSaleReport(DrinkTypeEnum.BLACK_COFFEE);
	}
	
	@Test
	public void getDrinkWiseTotalSaleReport_FiveCoffeOrderPresent_ShouldReturnTotalSaleReport() throws Exception {
		String expectedCoffeReport="COFFEE Report: Total quantity 5 cups and total earning 75 Rs.";
		when(orderDAO.getAllOrders()).thenReturn(Arrays.asList(new Order(5, DrinkTypeEnum.COFFEE)));
		String actualReport=reportService.getDrinkWiseTotalSaleReport(DrinkTypeEnum.COFFEE);
		assertEquals(expectedCoffeReport, actualReport);
	}
	
	
	@Test(expected=FileNotExistException.class)
	public void getTotalSaleReport_orderFileNotPresent_ThrowFileNotExistException() throws Exception {
		when(orderDAO.getAllOrders()).thenThrow(FileNotExistException.class);
		reportService.getTotalSaleReport();
	}
	
	@Test(expected=FileNotExistException.class)
	public void getTotalSaleReport_ConfigurationFileNotExist_ThrowFileNotExistException() throws Exception {
		when(orderDAO.getAllOrders()).thenThrow(FileNotExistException.class);
		reportService.getTotalSaleReport();
	}
	
	@Test(expected=EmptyFileException.class)
	public void getTotalSaleReport_OrderJsonFileEmpty_ThrowEmptyFIleException() throws Exception {
		when(orderDAO.getAllOrders()).thenThrow(EmptyFileException.class);
	    reportService.getTotalSaleReport();
	}

	@Test(expected=NoValuePresentException.class)
	public void getTotalSaleReport_NoOrderPresntForDrinkType_ThrowNoValuePresentException() throws Exception {
		when(orderDAO.getAllOrders()).thenReturn(new ArrayList<Order>());
		reportService.getTotalSaleReport();
	}

	@Test
	public void getTotalSaleReport_TotalFiveCoffeAsOrderPresent_ShouldReturnTotalSaleReport() throws Exception {
		Order fiveCoffeOrder=new Order(5, DrinkTypeEnum.COFFEE);
		when(orderDAO.getAllOrders()).thenReturn(Arrays.asList(fiveCoffeOrder));
		String expectedReport="Total Sale Report: Orders=5cups, Total Earning= Rs. 75";
		String actualReport=reportService.getTotalSaleReport();
		assertEquals(expectedReport, actualReport);
	}
}
