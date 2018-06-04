package com.yash.ytcvm.serviceimpl;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.yash.ytcvm.config.FilePathLiterals;
import com.yash.ytcvm.dao.OrderDAO;
import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.exception.NoValuePresentException;
import com.yash.ytcvm.model.Container;
import com.yash.ytcvm.model.Order;
import com.yash.ytcvm.service.ReportService;
import com.yash.ytcvm.util.FileHelper;

public class ReportServiceImpl implements ReportService {

	private OrderDAO orderDAO;

	private Container container;

	private Logger logger = Logger.getLogger(ReportServiceImpl.class);

	public ReportServiceImpl(OrderDAO orderDAO) {
		container = Container.getContainer();
		this.orderDAO = orderDAO;
	}

	public void getContainerStatus() {
		logger.info("getContainerStatus started");
		System.out.println("Milk Container contains :" + container.getMilk() + " ml out of "
				+ Container.MILK_CONTAINER_MAX_CAPACITY);
		System.out.println("Water Container contains :" + container.getWater() + " ml out of "
				+ Container.WATER_CONTAINER_MAX_CAPACITY);
		System.out.println("Sugar Container contains :" + container.getSugar() + " gm out of "
				+ Container.SUGAR_CONTAINER_MAX_CAPACITY);
		System.out.println("Coffee Container contains :" + container.getCoffee() + " gm out of "
				+ Container.COFFEE_CONTAINER_MAX_CAPACITY);
		System.out.println(
				"Tea Container contains :" + container.getTea() + "gm out of " + Container.TEA_CONTAINER_MAX_CAPACITY);
		logger.info("getContainerStatus ended");
	}

	public String getDrinkWiseTotalSaleReport(DrinkTypeEnum drinkType) throws FileNotFoundException {
		logger.info("getDrinkWiseTotalSaleReport started");
		List<Order> allOrder = orderDAO.getAllOrders();
		int totalDrinkTypeOrder = getTotalDrinkTypeOrders(drinkType, allOrder);
		checkNoOrderPresentForDrinkType(drinkType, totalDrinkTypeOrder);
		FileHelper fileHelper = new FileHelper();
		Properties drinkTypeProperties = getDrinkTypeConfguration(drinkType, fileHelper);
		int totalEarning = totalDrinkTypeOrder * Integer.parseInt(drinkTypeProperties.getProperty("RATE"));
		String drinkWiseReport=drinkType + " Report: Total quantity " + totalDrinkTypeOrder + " cups and total earning "
				+ totalEarning + " Rs.";
		logger.info("getDrinkWiseTotalSaleReport ended");
		return drinkWiseReport;
	}

	public String getTotalSaleReport() throws FileNotFoundException {
		
		logger.info("getTotalSaleReport started");
		List<Order> allOrder = orderDAO.getAllOrders();
		checkForOrderNotPresent(allOrder);
		int totalOrders = 0, totalEarning = 0, teaRate = 0, blackTeaRate = 0, coffeeRate = 0, blackCoffeeRate = 0;
		FileHelper fileHelper = new FileHelper();
		Properties drinkTypeProperties = null;
		
		for (DrinkTypeEnum drinkType : DrinkTypeEnum.values()) {
			switch (drinkType) {
			case TEA:
				drinkTypeProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.TEA_CONFIG_FILEPATH);
				teaRate = Integer.parseInt(drinkTypeProperties.getProperty("RATE"));
				break;
			case BLACK_TEA:
				drinkTypeProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.BLACK_TEA_CONFIG_FILEPATH);
				blackTeaRate = Integer.parseInt(drinkTypeProperties.getProperty("RATE"));
				break;
			case COFFEE:
				drinkTypeProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.COFFEE_CONFIG_FILEPATH);
				coffeeRate = Integer.parseInt(drinkTypeProperties.getProperty("RATE"));
				break;
			case BLACK_COFFEE:
				drinkTypeProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.BLACK_COFFEE_CONFIG_FILEPATH);
				blackCoffeeRate = Integer.parseInt(drinkTypeProperties.getProperty("RATE"));
				break;
			default:
				break;
			}
		}
		String totalSaleReport = generateReport(allOrder, totalOrders, totalEarning, teaRate, blackTeaRate, coffeeRate,
				blackCoffeeRate);
		logger.info("getTotalSaleReport ended");
		return totalSaleReport;
	}

	private String generateReport(List<Order> allOrder, int totalOrders, int totalEarning, int teaRate,
			int blackTeaRate, int coffeeRate, int blackCoffeeRate) {
		int orderQuantity = 0;
		for (Order order : allOrder) {
			DrinkTypeEnum orderType = order.getDrinkTypeEnum();
			switch (orderType) {
			case TEA:
				orderQuantity = order.getQuantity();
				totalOrders += orderQuantity;
				totalEarning += teaRate * orderQuantity;
				break;
			case COFFEE:
				orderQuantity = order.getQuantity();
				totalOrders += orderQuantity;
				totalEarning += coffeeRate * orderQuantity;
				break;
			case BLACK_COFFEE:
				orderQuantity = order.getQuantity();
				totalOrders += orderQuantity;
				totalEarning += blackCoffeeRate * orderQuantity;
				break;
			case BLACK_TEA:
				orderQuantity = order.getQuantity();
				totalOrders += orderQuantity;
				totalEarning += blackTeaRate * orderQuantity;
				break;
			default:
				break;
			}
		}
		String totalSaleReport = "Total Sale Report: Orders=" + totalOrders + "cups, Total Earning= Rs. "
				+ totalEarning;
		return totalSaleReport;
	}

	private Properties getDrinkTypeConfguration(DrinkTypeEnum drinkType, FileHelper fileHelper)
			throws FileNotFoundException {
		Properties drinkTypeProperties = null;
		switch (drinkType) {
		case TEA:
			drinkTypeProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.TEA_CONFIG_FILEPATH);
			break;
		case BLACK_TEA:
			drinkTypeProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.BLACK_TEA_CONFIG_FILEPATH);
			break;
		case COFFEE:
			drinkTypeProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.COFFEE_CONFIG_FILEPATH);
			break;
		case BLACK_COFFEE:
			drinkTypeProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.BLACK_COFFEE_CONFIG_FILEPATH);
			break;

		default:
			break;
		}
		return drinkTypeProperties;
	}

	private void checkNoOrderPresentForDrinkType(DrinkTypeEnum drinkType, int totalDrinkTypeOrder) {
		if (isNoOrderPresent(totalDrinkTypeOrder)) {
			throw new NoValuePresentException("No order has been placed for " + drinkType);
		}
	}

	private int getTotalDrinkTypeOrders(DrinkTypeEnum drinkType, List<Order> allOrder) {
		int totalDrinkTypeOrder = 0;
		for (Order order : allOrder) {
			DrinkTypeEnum orderType = order.getDrinkTypeEnum();
			if (isOrderTypeEqualsReportType(drinkType, orderType)) {
				totalDrinkTypeOrder += order.getQuantity();
			}
		}
		return totalDrinkTypeOrder;
	}

	private boolean isOrderTypeEqualsReportType(DrinkTypeEnum drinkType, DrinkTypeEnum orderType) {
		return orderType == drinkType;
	}

	private boolean isNoOrderPresent(int totalDrinkTypeOrder) {
		return totalDrinkTypeOrder == 0;
	}

	private boolean isOrderPresent(List<Order> allOrder) {
		return allOrder.size() == 0;
	}

	private void checkForOrderNotPresent(List<Order> allOrder) {
		if (isOrderPresent(allOrder)) {
			throw new NoValuePresentException("No order present!");
		}
	}

}
