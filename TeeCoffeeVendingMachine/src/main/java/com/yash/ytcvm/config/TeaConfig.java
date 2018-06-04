package com.yash.ytcvm.config;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.yash.ytcvm.enums.DrinkTypeEnum;
import com.yash.ytcvm.util.FileHelper;

public class TeaConfig extends AbstractDrinkConfigurer {

	private static DrinkConfigurer drinkConfigurer;
	private static Properties teaConfigurationProperties;
	private static Logger logger = Logger.getLogger(TeaConfig.class);

	static {
		try {
			drinkConfigurer = new TeaConfig();
		} catch (FileNotFoundException fileNotFoundException) {
			logger.error("error while initializing :" + fileNotFoundException.getMessage());
		}
	}

	private TeaConfig() throws FileNotFoundException {
		super();
	}

	public void readConfigFile() throws FileNotFoundException {
		FileHelper fileHelper = new FileHelper();
		teaConfigurationProperties = fileHelper.getPropertiesFromFile(FilePathLiterals.TEA_CONFIG_FILEPATH);
	}

	public void configUse() {
		setTeaUse(parseStringToInt(teaConfigurationProperties.getProperty("TEA_USE")));
		setSugarUse(parseStringToInt(teaConfigurationProperties.getProperty("SUGAR_USE")));
		setMilkUse(parseStringToInt(teaConfigurationProperties.getProperty("MILK_USE")));
		setWaterUse(parseStringToInt(teaConfigurationProperties.getProperty("WATER_USE")));
	}

	public void configWaste() {
		setTeaWaste(parseStringToInt(teaConfigurationProperties.getProperty("TEA_WASTE")));
		setSugarWaste(parseStringToInt(teaConfigurationProperties.getProperty("SUGAR_WASTE")));
		setMilkWaste(parseStringToInt(teaConfigurationProperties.getProperty("MILK_WASTE")));
		setWaterWaste(parseStringToInt(teaConfigurationProperties.getProperty("WATER_WASTE")));
	}

	public void configDrinkType() {
		setDrinkType(DrinkTypeEnum.TEA);
	}

	public void configDrinkRate() {
		setRate(parseStringToInt(teaConfigurationProperties.getProperty("RATE")));
	}

	public static DrinkConfigurer getDrinkConfigurer() {
		return drinkConfigurer;
	}

	private int parseStringToInt(String stringToParse) {
		return Integer.parseInt(stringToParse);
	}
}
