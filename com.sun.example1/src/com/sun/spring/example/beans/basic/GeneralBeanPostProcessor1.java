package com.sun.spring.example.beans.basic;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class GeneralBeanPostProcessor1 implements BeanPostProcessor {

	/**
	 * ��init-method����ǰ������
	 * bean: ������õ�bean
	 * beanName: ������õ�bean������
	 * 
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(beanName.equalsIgnoreCase("user3")){
			User tempUser = (User)bean;
			System.out.println("Before init-method: User "+ tempUser.getName() + " is reset its description... ");
			tempUser.setDescription("Bean: " + beanName);
		}
		
		return bean;
	}

	/**
	 * ��init-method�����󱻵���
	 * bean: ������õ�bean
	 * beanName: ������õ�bean������
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(beanName.equalsIgnoreCase("user3")){
			System.out.println("After init-method: User "+ ((User)bean).getName());
		}
		return bean;
	}

}
