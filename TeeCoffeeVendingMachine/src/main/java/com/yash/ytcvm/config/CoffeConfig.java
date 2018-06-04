package com.yash.ytcvm.config;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.util.FileHelper;

public class CoffeConfig extends AbstractDrinkConfigurer {

	private static Properties coffeConfigurationProperties;

	private static DrinkConfigurer drinkConfigurer;
	
	private static Logger logger = Logger.getLogger(CoffeConfig.class);

	static {
		try {
			drinkConfigurer = new CoffeConfig();
		} catch (FileNotFoundException fileNotFoundException) {
			logger.error("error while initializing :" + fileNotFoundException.getMessage());
		}
	}

	private CoffeConfig() throws FileNotFoundException {
		super();
	}

	public void readConfigFile() throws FileNotFoundException {
		FileHelper fileHelper = new FileHelper();
		coffeConfigurationProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.COFFEE_CONFIG_FILEPATH);
	}

	public void configUse() {
		setCoffeeUse(parseStringToInt(coffeConfigurationProperties.getProperty("COFFE_USE")));
		setSugarUse(parseStringToInt(coffeConfigurationProperties.getProperty("SUGAR_USE")));
		setMilkUse(parseStringToInt(coffeConfigurationProperties.getProperty("MILK_USE")));
		setWaterUse(parseStringToInt(coffeConfigurationProperties.getProperty("WATER_USE")));
	}

	public void configWaste() {
		setCoffeeWaste(parseStringToInt(coffeConfigurationProperties.getProperty("COFFE_WASTE")));
		setSugarWaste(parseStringToInt(coffeConfigurationProperties.getProperty("SUGAR_WASTE")));
		setMilkWaste(parseStringToInt(coffeConfigurationProperties.getProperty("MILK_WASTE")));
		setWaterWaste(parseStringToInt(coffeConfigurationProperties.getProperty("WATER_WASTE")));
	}

	public void configDrinkType() {
		setDrinkType(DrinkTypeEnum.TEA);
	}

	public void configDrinkRate() {
		setRate(parseStringToInt(coffeConfigurationProperties.getProperty("RATE")));

	}

	public static DrinkConfigurer getDrinkConfigurer() {
		return drinkConfigurer;
	}

	private int parseStringToInt(String stringToParse) {
		return Integer.parseInt(stringToParse);
	}

}
