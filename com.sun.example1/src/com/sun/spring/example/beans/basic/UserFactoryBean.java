package com.sun.spring.example.beans.basic;

import org.springframework.beans.factory.FactoryBean;

public class UserFactoryBean implements FactoryBean<User> {

	/**
	 * ����bean��ʵ��
	 */
	@Override
	public User getObject() throws Exception {
		User user = new User();
		user.setName("User7");
		user.setRealName("С����");
		user.setLevel(10);
		user.setDescription("С�����˼��˰�����������");
		
		return user;
	}

	/**
	 * ����bean������
	 */
	@Override
	public Class<?> getObjectType() {
		return User.class;
	}

	/**
	 * ����bean�Ƿ��ǵ���
	 */
	@Override
	public boolean isSingleton() {
		return true;
	}

}
