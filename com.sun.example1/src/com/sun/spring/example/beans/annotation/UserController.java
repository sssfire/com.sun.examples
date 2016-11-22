package com.sun.spring.example.beans.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.sun.spring.example.beans.annotation.UserRepository;

@Controller
public class UserController {

	/**
	 * @Authowired ע���Զ�װ����м������͵ĵ��� Bean����
	     --������, ��ͨ�ֶ�(��ʹ�Ƿ� public), һ�о��в����ķ���������Ӧ��@Authwired ע��
	     --Ĭ�������, ����ʹ�� @Authwired ע������Զ���Ҫ������. �� Spring �Ҳ���ƥ��� Bean װ������ʱ, ���׳��쳣, ��ĳһ��������������, �������� @Authwired ע��� required ����Ϊ false
	     --Ĭ�������, �� IOC ��������ڶ�����ͼ��ݵ� Bean ʱ, ͨ�����͵��Զ�װ�佫�޷�����. ��ʱ������ @Qualifier ע�����ṩ Bean ������. Spring ����Է�������α�ע @Qualifiter ��ָ��ע�� Bean ������
	     --@Authwired ע��Ҳ����Ӧ�����������͵�������, ��ʱ Spring ���������ƥ��� Bean �����Զ�װ��.
	     --@Authwired ע��Ҳ����Ӧ���ڼ���������, ��ʱ Spring ��ȡ�ü��ϵ�������Ϣ, Ȼ���Զ�װ��������֮���ݵ� Bean. 
	     --@Authwired ע������ java.util.Map ��ʱ, ���� Map �ļ�ֵΪ String, ��ô Spring ���Զ�װ����֮ Map ֵ���ͼ��ݵ� Bean, ��ʱ Bean ��������Ϊ��ֵ
	 */
	
	/**
	 * @Autowire���Է���private��public�ֶ����棬��ʹû��set����Ҳ���Թ��� (���Գ��԰������set����ɾ��)
	 */
	@Autowired
	@Qualifier("userRepository")  //���װ���bean������������ͬ�����Զ���Ҫװ���beanʵ�������ֽ���ָ����ʹ��Qulifier����ָ��
	private UserRepository userRepository;
	
	public void execute(){
		System.out.println("UserController Execute...");
		userRepository.save();
	}
	
	/**
	 * @Autowired���Է���set��������
	 */
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
//	public void setUserRepository(@Qualifier("userRepository") UserRepository userRepository) { //QualifierҲ����ֱ�ӷ��������
		this.userRepository = userRepository;
	}
	
}
