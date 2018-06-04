package com.yash.ytcvm.util;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.yash.ytcvm.exception.EmptyFileException;
import com.yash.ytcvm.exception.FileNotExistException;


public class FileHelperTest {

	private FileHelper fileUtil;
	
	@Before
	public void init() {
		fileUtil=new FileHelper();
	}
	
	@Test(expected=FileNotExistException.class)
	public void readJsonFile_NonExistFilePathGiven_FileNotExistException() throws FileNotExistException {
		String nonExistFilePath="./src/test/resources/jsonfiles/nonExistingFile.json";
		fileUtil.readJsonFile(nonExistFilePath);
	}

	@Test(expected=EmptyFileException.class)
	public void readJsonFile_NonExistFilePathGiven_EmptyFileException() throws FileNotExistException {
		String emptyFilePath="./src/test/resources/jsonfiles/EmpyFile.json";
		fileUtil.readJsonFile(emptyFilePath);
	}

	@Test
	public void readJsonFile_ValidFilePresent_ShouldReturnJsonString() throws Exception {
		String validFilePath="./src/test/resources/jsonfiles/validJsonFile.json";
		String jsonString=fileUtil.readJsonFile(validFilePath);
		String fileContent="[{\"id\":101,\"screenName\":\"Audi1\"},{\"id\":102,\"screenName\":\"Audi2\"}]";
		assertEquals(fileContent, jsonString);
	}
	
	@Test
	public void writeJsonStringToFile_NonNullJsonStringGiven_ShouldReturnOne() throws Exception {
		String validFilePath="./src/test/resources/jsonfiles/validJsonFile.json";
		String jsonToStore="[{\"id\":101,\"screenName\":\"Audi1\"},{\"id\":102,\"screenName\":\"Audi2\"}]";
		fileUtil.writeJsonStringToFile(validFilePath, jsonToStore);		
	}
	
	@Test(expected=FileNotExistException.class)
	public void getPropertiesFromFilee_NonExistFilePathGiven_ThrowFileNotExistException() throws FileNotFoundException {
		String nonExistFilePath="./src/test/resources/jsonfiles/nonExistingFile.property";
		fileUtil.getPropertiesFromFile(nonExistFilePath);
	}
	
	@Test(expected=EmptyFileException.class)
	public void getPropertiesFromFilee_EmptyPropertyFilePathGiven_ThrowFileNotExistException() throws FileNotFoundException {
		String emptyPropertyFilePath="./src/test/resources/propertiesfiles/emptyPropertyFile.properties";
		fileUtil.getPropertiesFromFile(emptyPropertyFilePath);
	}
	
	@Test
	public void getPropertiesFromFilee_ValidPropertyFilePathGiven_shouldReturnPropertyReference() throws FileNotFoundException {
		String validConfigPropertyFilePath="./src/test/resources/propertiesfiles/teaConfiguration";
		Properties properties=fileUtil.getPropertiesFromFile(validConfigPropertyFilePath);
		String milkUseToMakeTea = "40";
		assertEquals(milkUseToMakeTea, properties.getProperty("MILK_USE"));
	}
}
