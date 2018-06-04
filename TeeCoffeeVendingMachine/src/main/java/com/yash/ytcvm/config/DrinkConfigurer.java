package com.yash.ytcvm.config;

import java.io.FileNotFoundException;

public interface DrinkConfigurer {
	void readConfigFile() throws FileNotFoundException;
	void configUse();
	void configWaste();
	void configDrinkType();
	void configDrinkRate();
}
