package com.sun.spring.example.beans.basic;

import org.springframework.beans.factory.FactoryBean;

public class UserFactoryBean implements FactoryBean<User> {

	/**
	 * 返回bean的实例
	 */
	@Override
	public User getObject() throws Exception {
		User user = new User();
		user.setName("User7");
		user.setRealName("小胖子");
		user.setLevel(10);
		user.setDescription("小胖子人见人爱，花见花开");
		
		return user;
	}

	/**
	 * 返回bean的类型
	 */
	@Override
	public Class<?> getObjectType() {
		return User.class;
	}

	/**
	 * 返回bean是否是单例
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

}
