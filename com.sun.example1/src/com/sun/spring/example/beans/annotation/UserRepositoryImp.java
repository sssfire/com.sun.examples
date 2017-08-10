package com.sun.spring.example.beans.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sun.spring.example.beans.basic.User;

/**
 * 显示提供bean的名称，如果不加value，则bean的名字为首字母小写的class名称。
 */
//@Repository(value="userRepository")
@Repository("userRepository")
public class UserRepositoryImp implements UserRepository{

	/**
	 * @Autowired(required=false) 
	 * 如果required=false，则指明如果IOC容器中有对象，则装配，没有则不必自动装配；
	 * 如果required=true，则该属性必须进行装配，如果IOC容器中找不到相应对象则会报错。
	 * 自动装配的规则为：
	 * 首先根据变量/属性的名字在IOC容器中寻找匹配的bean，如果没有则根据类型进行装配。
	 * 如果同一类型的bean有多个实例，则自动装配出错！
	 */
	@Autowired(required=false)
	private User user;
	
	@Override
	public void save() {
		System.out.println("UserReportory Save...");
		System.out.println("Save User:" + user);
	}
	
	

}
