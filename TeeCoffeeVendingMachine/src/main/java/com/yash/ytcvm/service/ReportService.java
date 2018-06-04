package com.yash.ytcvm.service;

import java.io.FileNotFoundException;

import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.exception.FileNotExistException;

public interface ReportService {

	void getContainerStatus();

	String getDrinkWiseTotalSaleReport(DrinkTypeEnum drinkType) throws FileNotExistException, FileNotFoundException;

	String getTotalSaleReport() throws FileNotExistException, FileNotFoundException;

}
