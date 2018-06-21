package com.leotech.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public final class SpringContextBase 
{     
	private static ApplicationContext context;  
	public static ApplicationContext getSpringContext(){
		if(context == null){
			context = new ClassPathXmlApplicationContext("spring-config/spring-common.xml");
		}
		return context;
	}
	
} 
