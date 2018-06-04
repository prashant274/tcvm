package com.yash.ytcvm.exception;

public class ContainerUnderflowException extends Exception {

	public ContainerUnderflowException(){
		
	}
	
	public ContainerUnderflowException(String errMsg){
		super(errMsg);
		
	}
}
