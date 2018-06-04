package com.yash.ytcvm.config;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.util.FileHelper;

public class BlackTeaConfig extends AbstractDrinkConfigurer {

	private static Properties blackTeaConfigurationProperties;

	private static DrinkConfigurer drinkConfigurer;

	private static Logger logger = Logger.getLogger(BlackTeaConfig.class);

	static {
		try {
			drinkConfigurer = new BlackTeaConfig();
		} catch (FileNotFoundException fileNotFoundException) {
			logger.error("error while initializing :" + fileNotFoundException.getMessage());
		}
	}

	private BlackTeaConfig() throws FileNotFoundException {
		super();
	}

	public void readConfigFile() throws FileNotFoundException {
		FileHelper fileHelper = new FileHelper();
		blackTeaConfigurationProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.BLACK_TEA_CONFIG_FILEPATH);
	}

	
	public void configUse() {
		setTeaUse(parseStringToInt(blackTeaConfigurationProperties.getProperty("TEA_USE")));
		setSugarUse(parseStringToInt(blackTeaConfigurationProperties.getProperty("SUGAR_USE")));
		setWaterUse(parseStringToInt(blackTeaConfigurationProperties.getProperty("WATER_USE")));
	}

	public void configWaste() {
		setTeaWaste(parseStringToInt(blackTeaConfigurationProperties.getProperty("TEA_WASTE")));
		setSugarWaste(parseStringToInt(blackTeaConfigurationProperties.getProperty("SUGAR_WASTE")));
		setWaterWaste(parseStringToInt(blackTeaConfigurationProperties.getProperty("WATER_WASTE")));
	}

	public void configDrinkType() {
		setDrinkType(DrinkTypeEnum.TEA);

	}

	public void configDrinkRate() {
		setRate(parseStringToInt(blackTeaConfigurationProperties.getProperty("RATE")));

	}

	public static DrinkConfigurer getDrinkConfigurer() {
		return drinkConfigurer;
	}

	private int parseStringToInt(String stringToParse) {
		return Integer.parseInt(stringToParse);
	}

}
