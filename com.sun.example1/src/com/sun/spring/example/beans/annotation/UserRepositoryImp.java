package com.sun.spring.example.beans.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sun.spring.example.beans.basic.User;

/**
 * ��ʾ�ṩbean�����ƣ��������value����bean������Ϊ����ĸСд��class���ơ�
 */
//@Repository(value="userRepository")
@Repository("userRepository")
public class UserRepositoryImp implements UserRepository{

	/**
	 * @Autowired(required=false) 
	 * ���required=false����ָ�����IOC�������ж�����װ�䣬û���򲻱��Զ�װ�䣻
	 * ���required=true��������Ա������װ�䣬���IOC�������Ҳ�����Ӧ������ᱨ��
	 * �Զ�װ��Ĺ���Ϊ��
	 * ���ȸ��ݱ���/���Ե�������IOC������Ѱ��ƥ���bean�����û����������ͽ���װ�䡣
	 * ���ͬһ���͵�bean�ж��ʵ�������Զ�װ�����
	 */
	@Autowired(required=false)
	private User user;
	
	@Override
	public void save() {
		System.out.println("UserReportory Save...");
		System.out.println("Save User:" + user);
	}
	
	

}
