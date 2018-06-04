package com.yash.ytcvm.service;

import com.yash.ytcvm.enums.ContainerEnum;
import com.yash.ytcvm.exception.ContainerOverFlowException;

public interface ContainerService {
	boolean refillContainer(ContainerEnum containerType,int quantity) throws ContainerOverFlowException;
}
