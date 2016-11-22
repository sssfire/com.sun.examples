package com.sun.spring.example.beans.basic;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class GeneralBeanPostProcessor2 implements BeanPostProcessor {

	/**
	 * ��init-method����ǰ������
	 * bean: ������õ�bean
	 * beanName: ������õ�bean������
	 * 
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(beanName.equalsIgnoreCase("user4")){
			User tempUser = (User)bean;
			System.out.println("Before init-method: User "+ tempUser.getName() + " is reset its description... ");
			tempUser.setDescription("Bean: " + beanName);
		}
		
		if(beanName.equalsIgnoreCase("user7")){
			System.out.println("Before init-method: UserBeanFactory: user7");
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
		if(beanName.equalsIgnoreCase("user4")){
			System.out.println("After init-method: User " + ((User)bean).getName());
		}
		
		if(beanName.equalsIgnoreCase("user7")){
			System.out.println("After init-method: UserBeanFactory: user7");
		}
		return bean;
	}

}
