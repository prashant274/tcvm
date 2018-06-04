package com.yash.ytcvm.config;

import java.io.FileNotFoundException;

import com.yash.ytcvm.enums.DrinkTypeEnum;

public abstract class AbstractDrinkConfigurer implements DrinkConfigurer{
	
	private int teaUse;
	private int coffeeUse;
	private int sugarUse;
	private int milkUse;
	private int waterUse;
	
	private int teaWaste;
	private int coffeeWaste;
	private int sugarWaste;
	private int milkWaste;
	private int waterWaste;
	private DrinkTypeEnum drinkType;
	private int rate;
	
	public AbstractDrinkConfigurer() throws FileNotFoundException{
		initConfig();
	}
	private void initConfig() throws FileNotFoundException {
		readConfigFile();
		configUse();
		configWaste();
		configDrinkType();
	}
	
	
	public int getTeaUse() {
		return teaUse;
	}
	public void setTeaUse(int teaUse) {
		this.teaUse = teaUse;
	}
	public int getCoffeeUse() {
		return coffeeUse;
	}
	public void setCoffeeUse(int coffeeUse) {
		this.coffeeUse = coffeeUse;
	}
	public int getSugarUse() {
		return sugarUse;
	}
	public void setSugarUse(int sugarUse) {
		this.sugarUse = sugarUse;
	}
	public int getMilkUse() {
		return milkUse;
	}
	public void setMilkUse(int milkUse) {
		this.milkUse = milkUse;
	}
	public int getWaterUse() {
		return waterUse;
	}
	public void setWaterUse(int waterUse) {
		this.waterUse = waterUse;
	}
	public int getTeaWaste() {
		return teaWaste;
	}
	public void setTeaWaste(int teaWaste) {
		this.teaWaste = teaWaste;
	}
	public int getCoffeeWaste() {
		return coffeeWaste;
	}
	public void setCoffeeWaste(int coffeeWaste) {
		this.coffeeWaste = coffeeWaste;
	}
	public int getSugarWaste() {
		return sugarWaste;
	}
	public void setSugarWaste(int sugarWaste) {
		this.sugarWaste = sugarWaste;
	}
	public int getMilkWaste() {
		return milkWaste;
	}
	public void setMilkWaste(int milkWaste) {
		this.milkWaste = milkWaste;
	}
	public int getWaterWaste() {
		return waterWaste;
	}
	public void setWaterWaste(int waterWaste) {
		this.waterWaste = waterWaste;
	}
	public DrinkTypeEnum getDrinkType() {
		return drinkType;
	}
	public void setDrinkType(DrinkTypeEnum drinkType) {
		this.drinkType = drinkType;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	

	

}
