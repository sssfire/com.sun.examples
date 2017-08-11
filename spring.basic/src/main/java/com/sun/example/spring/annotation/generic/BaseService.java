package com.sun.example.spring.annotation.generic;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<T> {
	
	/**
	 * 泛型依赖注入
	 * --在父类中使用自动装配
	 * --在运行时，父类会根据继承的子类（这里指UserService或RoleService）的<T>指定的参数
	 *   自动注入对应的子类类型（UserDao或RoleDao）
	 */
	@Autowired
	private BaseDao<T> dao;
	
	public void add(T entity){
		System.out.println("add by " + this);
		dao.save(entity);
	}
}
