package com.sun.spring.example.beans.annotation.generic;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T> {
	
	/**
	 * ��������ע��
	 * --�ڸ�����ʹ���Զ�װ��
	 * --������ʱ���������ݼ̳е����ࣨ����ָUserService��RoleService����<T>ָ���Ĳ���
	 *   �Զ�ע���Ӧ���������ͣ�UserDao��RoleDao��
	 */
	@Autowired
	private BaseDao<T> dao;
	
	public void add(T entity){
		System.out.println("add by " + this);
		dao.save(entity);
	}
}
