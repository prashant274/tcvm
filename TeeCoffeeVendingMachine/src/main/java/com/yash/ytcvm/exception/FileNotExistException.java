package com.yash.ytcvm.exception;

import java.io.FileNotFoundException;

public class FileNotExistException extends FileNotFoundException {

	public FileNotExistException(String errorMessage) {
		super(errorMessage);
	}
}
