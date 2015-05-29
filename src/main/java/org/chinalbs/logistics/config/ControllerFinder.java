package org.chinalbs.logistics.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

public class ControllerFinder implements BeanPostProcessor {

	private final Set<Object> controllers = new HashSet<Object>(); 
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		Class<?> beanClass = bean.getClass();
		if (beanClass.isAnnotationPresent(RestController.class) || beanClass.isAnnotationPresent(Controller.class)) {
			controllers.add(bean);
		}
		
		return bean;
	}

	public Set<Object> getAllControllers() {
		return controllers;
	}
	
}
