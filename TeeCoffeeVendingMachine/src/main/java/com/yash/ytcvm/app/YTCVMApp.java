package com.yash.ytcvm.app;

import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.yash.ytcvm.config.FilePathLiterals;
import com.yash.ytcvm.dao.OrderDAO;
import com.yash.ytcvm.daoimpl.OrderDAOImpl;
import com.yash.ytcvm.enums.ContainerEnum;
import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.exception.ContainerOverFlowException;
import com.yash.ytcvm.exception.ContainerUnderflowException;
import com.yash.ytcvm.exception.EmptyFileException;
import com.yash.ytcvm.exception.FileNotExistException;
import com.yash.ytcvm.exception.NoValuePresentException;
import com.yash.ytcvm.model.Order;
import com.yash.ytcvm.service.ContainerService;
import com.yash.ytcvm.service.OrderService;
import com.yash.ytcvm.service.ReportService;
import com.yash.ytcvm.serviceimpl.ContainerServiceImpl;
import com.yash.ytcvm.serviceimpl.OrderServiceImpl;
import com.yash.ytcvm.serviceimpl.ReportServiceImpl;
import com.yash.ytcvm.util.FileHelper;

public class YTCVMApp {

	private OrderService orderService;

	private OrderDAO orderDAO;

	private FileHelper fileHelper;

	private static Logger logger = Logger.getLogger(YTCVMApp.class);

	private ContainerService containerService;

	private ReportService reportService;
	

	public YTCVMApp() {
		fileHelper = new FileHelper();
		orderDAO = new OrderDAOImpl(fileHelper);
		orderService = new OrderServiceImpl(orderDAO);
		reportService = new ReportServiceImpl(orderDAO);
		containerService = new ContainerServiceImpl();
	}

	public void launchApp() {
		logger.info("launchApp started");
		boolean isContinue = true;
		String userInput = null;
		Scanner scanner = new Scanner(System.in);
		int validInput = -1;
		try {
			String menus = fileHelper.readFile(FilePathLiterals.MENU_FILE);
			while (isContinue) {
				System.out.println(menus);
				userInput = scanner.nextLine();
				try {
					validInput = Integer.parseInt(userInput);
					switch (validInput) {
					case 0:
						isContinue = false;
						break;
					case 1:
						processOrderBasedOnInput(scanner, DrinkTypeEnum.COFFEE);
						isContinue = isUserWantsToContinue(scanner);
						break;
					case 2:
						processOrderBasedOnInput(scanner, DrinkTypeEnum.TEA);
						isContinue = isUserWantsToContinue(scanner);
						break;
					case 3:
						processOrderBasedOnInput(scanner, DrinkTypeEnum.BLACK_TEA);
						isContinue = isUserWantsToContinue(scanner);
						break;
					case 4:
						processOrderBasedOnInput(scanner, DrinkTypeEnum.BLACK_COFFEE);
						isContinue = isUserWantsToContinue(scanner);
						break;
					case 5:
						processContainerRefill(scanner);
						isContinue = isUserWantsToContinue(scanner);
						break;
					case 6:
						isContinue = processReportRequest(scanner);
						break;
					case 7:
						reportService.getContainerStatus();
						isContinue = isUserWantsToContinue(scanner);
						break;
					default:
						System.out.println("Please enter valid input");
						break;
					}

				} catch (NumberFormatException numberFormatException) {
					System.out.println("Please enter valid input");
					continue;
				} catch (ContainerUnderflowException containerUnderflowException) {
					logger.error(containerUnderflowException.getMessage());
					System.out.println(containerUnderflowException.getMessage() + " please refill the containers");
					continue;
				} catch (EmptyFileException emptyFileException) {
					logger.error(emptyFileException.getMessage());
					System.out.println(emptyFileException.getMessage());
				} catch (ContainerOverFlowException containerOverFlowException) {
					logger.error(containerOverFlowException.getMessage());
					System.out.println(containerOverFlowException.getMessage());
				} catch (FileNotFoundException fileNotFoundException) {
					logger.error(fileNotFoundException.getMessage());
					System.out.println(fileNotFoundException.getMessage());
				} catch (NoValuePresentException noValuePresentException) {
					logger.error(noValuePresentException.getMessage());
					System.out.println(noValuePresentException.getMessage());
				}
			}
		} catch (FileNotExistException fileNotExistException) {
			System.out.println(fileNotExistException.getMessage());
			logger.error(fileNotExistException.getMessage());
		}
		logger.info("launchApp ended");
	}

	private boolean processReportRequest(Scanner scanner) throws FileNotExistException, FileNotFoundException {
		logger.info("processReportRequest started");
		boolean isContinue = false;
		System.out.println(fileHelper.readFile(FilePathLiterals.REPORTS_MENU_FILE));
		int validInput = Integer.parseInt(scanner.nextLine());
		switch (validInput) {
		case 1:
			String totalSaleReport=reportService.getTotalSaleReport();
			System.out.println("---------------------------------------------------------------");
			System.out.println(totalSaleReport);
			System.out.println("---------------------------------------------------------------");
			isContinue = isUserWantsToContinue(scanner);
			break;
		case 2:
			System.out.println(fileHelper.readFile(FilePathLiterals.DRINK_WISE_SALE_REPORT_FILE));
			generateDrinkWiseReport(scanner);
			isContinue = isUserWantsToContinue(scanner);
			break;
		default:
			System.out.println("Invalid Input, try again");
			processReportRequest(scanner);
		}
		logger.info("processReportRequest ended");
		return isContinue;
	}

	private void generateDrinkWiseReport(Scanner scanner) throws FileNotExistException, FileNotFoundException {
		logger.info("generateDrinkWiseReport started");
		int validInput = Integer.parseInt(scanner.nextLine());
		String drinkWiseReport=null;
		switch (validInput) {
		case 1:
			drinkWiseReport=reportService.getDrinkWiseTotalSaleReport(DrinkTypeEnum.COFFEE);
			break;
		case 2:
			drinkWiseReport=reportService.getDrinkWiseTotalSaleReport(DrinkTypeEnum.TEA);
			break;
		case 3:
			drinkWiseReport=reportService.getDrinkWiseTotalSaleReport(DrinkTypeEnum.BLACK_TEA);
			break;
		case 4:
			drinkWiseReport=reportService.getDrinkWiseTotalSaleReport(DrinkTypeEnum.BLACK_COFFEE);
			break;
		default:
			System.out.println("Invalid Input, Try Again");
			generateDrinkWiseReport(scanner);
			break;
		}
		System.out.println("-------------------------------------------------------------");
		System.out.println(drinkWiseReport);
		System.out.println("-------------------------------------------------------------");
		logger.info("generateDrinkWiseReport ended");
	}

	private void processContainerRefill(Scanner scanner) throws ContainerOverFlowException, FileNotExistException {
		logger.info("generateDrinkWiseReport started");
		boolean isRefilled = false;
		int validInput = 0, quantityToFilled = 0;
		System.out.println(fileHelper.readFile(FilePathLiterals.CONTAINER_REFILL_FILE));
		
		try {
			validInput = Integer.parseInt(scanner.nextLine());
			System.out.println("Enter quantity to be filled in (ml/gr) :");
			quantityToFilled = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException numberFormatException) {
			logger.error("ivalid input given for processContainerRefill's container no.");
			System.out.println("Invalid input, try again");
			processContainerRefill(scanner);
		}
		
		switch (validInput) {
		case 1:
			isRefilled = containerService.refillContainer(ContainerEnum.COFFEE, quantityToFilled);
			break;
		case 2:
			isRefilled = containerService.refillContainer(ContainerEnum.TEA, quantityToFilled);
			break;
		case 3:
			isRefilled = containerService.refillContainer(ContainerEnum.WATER, quantityToFilled);
			break;
		case 4:
			isRefilled = containerService.refillContainer(ContainerEnum.SUGAR, quantityToFilled);
			break;
		case 5:
			isRefilled = containerService.refillContainer(ContainerEnum.MILK, quantityToFilled);
			break;
		default:
			System.out.println("Invalid input, enter valid container no.");
			processContainerRefill(scanner);
			break;
		}
		
		if (isRefilled) {
			System.out.println("Container refilled successfully.");
		}
		logger.info("generateDrinkWiseReport ended");
	}

	private boolean isUserWantsToContinue(Scanner scanner) {
		boolean isContinue = true;
		System.out.println("\nDo you want to continue (y/n)");
		String userInput = scanner.nextLine();
		if (userInput.equalsIgnoreCase("N")) {
			isContinue = false;
		} else if (!userInput.equalsIgnoreCase("Y")) {
			System.out.println("Please enter valid input");
			isUserWantsToContinue(scanner);
		}
		return isContinue;

	}

	private void processOrderBasedOnInput(Scanner scanner, DrinkTypeEnum drinkType)
			throws ContainerUnderflowException, FileNotExistException {
		Order order;
		System.out.println("Enter no. of cups");
		int noOfCups = Integer.parseInt(scanner.nextLine());
		if(noOfCups<=0){
			System.out.println("Invalid input, try again");
			processOrderBasedOnInput(scanner,drinkType);
		}
		order = new Order(noOfCups, drinkType);
		int rowsAffected = orderService.processOrder(order);
		if (isOrderProcessed(rowsAffected)) {
			System.out.println("Your order is ready!");
		}
	}

	private boolean isOrderProcessed(int rowsAffected) {
		return rowsAffected > 0;
	}

}
