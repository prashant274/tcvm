package com.yash.ytcvm.config;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.util.FileHelper;

public class BlackCoffeConfig extends AbstractDrinkConfigurer{
	
	private static Properties blackCoffeConfigurationProperties;	
	private static DrinkConfigurer drinkConfigurer;
	private static Logger logger=Logger.getLogger(BlackCoffeConfig.class);
	
	static {
		try {
			drinkConfigurer = new BlackCoffeConfig();
		} catch (FileNotFoundException fileNotFoundException) {
			logger.error("error while initializing :"+fileNotFoundException.getMessage());
		}
	}
	
	private BlackCoffeConfig() throws FileNotFoundException{
		super();
	}
	
	public void readConfigFile() throws FileNotFoundException {
		FileHelper fileHelper = new FileHelper();
		blackCoffeConfigurationProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.BLACK_COFFEE_CONFIG_FILEPATH);
	}

	public void configUse() {
		setCoffeeUse(parseStringToInt(blackCoffeConfigurationProperties.getProperty("COFFE_USE")));
		setSugarUse(parseStringToInt(blackCoffeConfigurationProperties.getProperty("SUGAR_USE")));
		setWaterUse(parseStringToInt(blackCoffeConfigurationProperties.getProperty("WATER_USE")));
	}

	public void configWaste() {
		setCoffeeWaste(parseStringToInt(blackCoffeConfigurationProperties.getProperty("COFFE_WASTE")));
		setSugarWaste(parseStringToInt(blackCoffeConfigurationProperties.getProperty("SUGAR_WASTE")));
		setWaterWaste(parseStringToInt(blackCoffeConfigurationProperties.getProperty("WATER_WASTE")));
	}

	public void configDrinkType() {
		setDrinkType(DrinkTypeEnum.TEA);
	}

	public void configDrinkRate() {
		setRate(parseStringToInt(blackCoffeConfigurationProperties.getProperty("RATE")));
	}
	
	public static DrinkConfigurer getDrinkConfigurer() {
		return drinkConfigurer;
	}

	private int parseStringToInt(String stringToParse) {
		return Integer.parseInt(stringToParse);
	}
}
