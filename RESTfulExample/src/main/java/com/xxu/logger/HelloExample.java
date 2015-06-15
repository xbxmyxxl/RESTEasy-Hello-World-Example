package com.xxu.logger;

import org.apache.log4j.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Handler;

public class HelloExample{
 
	final static Logger logger = Logger.getLogger(HelloExample.class);
	//Handler handler = new FileHandler("test.log");
 
	public static void main(String[] args) {
 
		HelloExample obj = new HelloExample();
		obj.runMe("mkyong");
 
	}
 
	private void runMe(String parameter){
 
		if(logger.isDebugEnabled()){
			logger.debug("This is debug : " + parameter);
		}
 
		if(logger.isInfoEnabled()){
			logger.info("This is info : " + parameter);
		}
 
		logger.warn("This is warn : " + parameter);
		logger.error("This is error : " + parameter);
		logger.fatal("This is fatal : " + parameter);
 
	}
 
}
