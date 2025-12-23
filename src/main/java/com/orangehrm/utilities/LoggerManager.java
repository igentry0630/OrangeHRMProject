package com.orangehrm.utilities;

import java.lang.System.Logger;

import org.apache.logging.log4j.LogManager;



public class LoggerManager {

	// This method returns a Logger instance for the provided class
	public static Logger getLogger(Class<?> clazz) {
		return (Logger) LogManager.getLogger();
	}
}
