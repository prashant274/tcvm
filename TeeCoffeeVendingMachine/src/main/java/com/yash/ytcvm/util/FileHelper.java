package com.yash.ytcvm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.yash.ytcvm.exception.EmptyFileException;
import com.yash.ytcvm.exception.FileNotExistException;

public class FileHelper {

	private static final Logger logger = Logger.getLogger(FileHelper.class);

	public String readJsonFile(String filePath) throws FileNotExistException {
		logger.info("readJsonFile started");
		File jsonFile = new File(filePath);
		checkIfFileExist(jsonFile);
		checkForEmptyFile(jsonFile);
		StringBuilder jsonBuilder = new StringBuilder();
		BufferedReader bufferedReader = generateBufferReaderFromjsonFile(jsonFile);
		readFromBufferedReader(jsonFile, jsonBuilder, bufferedReader);
		logger.info("readJsonFile ends");
		return jsonBuilder.toString();

	}

	public Properties getPropertiesFromFile(String filePath) throws FileNotFoundException {
		logger.info("getPropertiesFromFile started");
		File propertyFile = new File(filePath);
		checkIfFileExist(propertyFile);
		checkForEmptyFile(propertyFile);
		Properties properties = new Properties();
		loadPropertiesFromFileStream(propertyFile, properties);
		logger.info("getPropertiesFromFile ended");
		return properties;
	}
	
	public String readFile(String filePath) throws FileNotExistException {
		File jsonFile = new File(filePath);
		checkIfFileExist(jsonFile);
		checkForEmptyFile(jsonFile);
		StringBuilder jsonBuilder = new StringBuilder();
		BufferedReader bufferedReader = generateBufferReaderFromjsonFile(jsonFile);
		readFromBufferedReaderWithFormat(jsonFile, jsonBuilder, bufferedReader);
		return jsonBuilder.toString();
	}
	
	
	public int writeJsonStringToFile(String filePath, String jsonString) {
		File jsonFile = new File(filePath);
		createFileIfNotExist(jsonFile);
		BufferedWriter bufferedWriter = generateBuffereWriterFromJsonFile(jsonFile);
		try {
			bufferedWriter.write(jsonString);
		} catch (IOException e) {
			System.out.println("error while writing to file");
		} finally {
			closeResources(bufferedWriter);
		}
		return 1;
	}

	private void loadPropertiesFromFileStream(File propertyFile, Properties properties) throws FileNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(propertyFile);
		try {
			properties.load(fileInputStream);
		} catch (IOException ioException) {
			logger.error("error while reading property file " + ioException.getMessage());
		}finally{
			try {
				fileInputStream.close();
			} catch (IOException e) {
				logger.error("error while closing file Stream");
			}
		}
		logger.info("getPropertiesFromFile ends");
	}

	private void readFromBufferedReader(File jsonFile, StringBuilder jsonBuilder, BufferedReader bufferedReader) {
		String nextReadLine;
		try {
			nextReadLine = bufferedReader.readLine();
			while (isEndOfFile(nextReadLine)) {
				jsonBuilder.append(nextReadLine);
				nextReadLine = bufferedReader.readLine();
			}
		} catch (IOException ioException) {
			System.out.println(ioException.getMessage());
		}
	}

	private void readFromBufferedReaderWithFormat(File jsonFile, StringBuilder jsonBuilder,
			BufferedReader bufferedReader) {
		String nextReadLine;
		try {
			nextReadLine = bufferedReader.readLine();
			while (isEndOfFile(nextReadLine)) {
				jsonBuilder.append(nextReadLine + "\n");
				nextReadLine = bufferedReader.readLine();
			}
		} catch (IOException ioException) {
			System.out.println(ioException.getMessage());
		}
	}

	private boolean isEndOfFile(String readLine) {
		return readLine != null;
	}

	private void checkForEmptyFile(File jsonFile) {
		if (isFileEmpty(jsonFile)) {
			throw new EmptyFileException(jsonFile.getPath() + " file is empty.");
		}
	}

	private boolean isFileEmpty(File jsonFile) {
		return jsonFile.length() == 0;
	}

	private BufferedReader generateBufferReaderFromjsonFile(File jsonFile) throws FileNotExistException {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(jsonFile));
			return bufferedReader;
		} catch (FileNotFoundException fileNotFoundException) {
			throw new FileNotExistException(jsonFile.getPath() + " File not exist");
		}
	}

	private void checkIfFileExist(File jsonFile) throws FileNotExistException {
		if (isFileNotExistAtGivenPath(jsonFile)) {
			throw new FileNotExistException(jsonFile + " not exist");
		}
	}

	private boolean isFileNotExistAtGivenPath(File file) {
		return !file.exists();
	}

	private void closeResources(BufferedWriter bufferedWriter) {
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error while closing connection");
		}
	}

	private BufferedWriter generateBuffereWriterFromJsonFile(File jsonFile) {
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(jsonFile));
		} catch (IOException e) {
			System.out.println("error occured while writing json file " + jsonFile.getPath());
		}
		return bufferedWriter;

	}

	private void createFileIfNotExist(File jsonFile) {
		if (isFileNotExistAtGivenPath(jsonFile)) {
			try {
				jsonFile.createNewFile();
			} catch (IOException e) {
				System.out.println("create file if not exist");
			}
		}
	}

}
