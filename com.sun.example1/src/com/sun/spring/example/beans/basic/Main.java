package com.sun.spring.example.beans.basic;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		//1. ����Spring��IOC����
		//ApplicationContext:����IOC�������ڴ���Beanʵ��֮ǰ�������ȳ�ʼ��IOC����
		//ClassPathXMLApplicationContext:��ApplicaitonContext��ʵ���࣬���Դ���·���¼��������ļ�
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-basic.xml");
		
		//2. ��IOC������ȡbeanʵ��
		//   ����id��λ��IOC�����е�Bean
		System.out.println();
		System.out.println("����ID��ȡIOC�����е�Bean");
		HelloWorld helloWorld = (HelloWorld) context.getBean("helloWorld");
		
		//�������ͷ���IOC�����е�bean����Ҫ��IOC�����б���ֻ����һ�������͵�bean
		//HelloWorld helloWorld = context.getBean(HelloWorld.class);
		
		//3. ����hello����
		helloWorld.helloWorld();
		
		//4. ���Թ��캯���Ĳ�ͬ��ʽ����ȡCarʵ������ϸ������ʽ��μ������ļ�
		System.out.println();
		System.out.println("���Թ��캯�����õĲ�ͬ��ʽ");
		Car car1 = (Car)context.getBean("car1");
		System.out.println(car1);
		
		Car car2 = (Car)context.getBean("car2");
		System.out.println(car2);
		
		Car car3 = (Car)context.getBean("car3");
		System.out.println(car3);
		
		Car car4 = (Car)context.getBean("car4");
		System.out.println(car4);
		
		//5. ����ref����, �������õ�carʹ��ref��ʽ���е���
		System.out.println();
		System.out.println("����ref����");
		Person person1 = (Person) context.getBean("person1");
		System.out.println(person1);
		
		//6. �����ڲ�bean���������õ�carʹ���ڲ�bean�ķ�ʽ���е���
		System.out.println();
		System.out.println("�����ڲ�Bean");
		Person person2 = (Person) context.getBean("person2");
		System.out.println(person2);
		
		//7. ���Լ������ԣ�Ϊ��ַ����ʾ�ļ������͸�ֵ
		System.out.println();
		System.out.println("���Լ�������");
		Person person3 = (Person) context.getBean("person3");
		System.out.println(person3);
		
		//8. ���Լ������͵�bean���������ʹ�ü������͵�bean
		//   �������ʹ��p����
		//   �������ʹ��bean�ļ̳�
		System.out.println();
		System.out.println("���Խ�����bean\n����P����\n�������ʹ��bean�ļ̳�");
		Person person4 = (Person) context.getBean("person4");
		System.out.println(person4);
		
		//9. ʵ����һ��bean��template
		//   ����ʵ������������
		//Address addressTemplate = (Address) context.getBean("addressTemplate");
		//System.out.println(addressTemplate);
		
		//10. ���Լ�������
		System.out.println();
		System.out.println("���ü�������");
		Person person5 = (Person) context.getBean("person5");
		System.out.println(person5);
		
		//11. �����ӳټ���
		System.out.println();
		System.out.println("�����ӳټ��غ�������Car8��Car7�ȳ�ʼ��");
		Car car7 = (Car) context.getBean("car7");
		System.out.println(car7);
		
		//12. �����Զ�װ��
		System.out.println();
		System.out.println("�����Զ�װ��");
		Person person7 = (Person) context.getBean("person7");
		System.out.println(person7);
		
		//13. ��������bean��������
		//����Singleton���͵�bean
		System.out.println();
		System.out.println("����Singleton���͵�bean");
		Car car8_1 = (Car) context.getBean("car8");
		Car car8_2 = (Car) context.getBean("car8");
		System.out.println("Scope: Singleton beans comparsion result: car8_1 " + (car8_1==car8_2?"=":"!=") + " car8_2");
		Car car9_1 = (Car) context.getBean("car9");
		Car car9_2 = (Car) context.getBean("car9");
		System.out.println("Scope: Prototype beans comparsion result: car9_1 " + (car9_1==car9_2?"=":"!=") + " car9_2");

		//14. �����ⲿ�����ļ�
		//�ⲿ�����ļ���database.properties
		System.out.println();
		System.out.println("�����ⲿ�����ļ�");
		DataSource dataSource = (DataSource) context.getBean("datasource");
		System.out.println(dataSource);
		
		//15. ����SpEL
		System.out.println();
		System.out.println("����SpEL");
		User user1 = (User) context.getBean("user1");
		System.out.println(user1);
		
		//16. ����bean����������(init-method and destroy-method)
		//ע��user�и��������ĵ���˳��
		System.out.println();
		System.out.println("����bean����������(init-method and destroy-method)");
		User user2 = (User) context.getBean("user2");
		System.out.println(user2);
		
		//17. ����bean����������(postProcessBeforeInitialzation and postProcessAfterInitialzation)
		//ע��user�и��������ĵ���˳��
		System.out.println();
		System.out.println("����bean����������(postProcessBeforeInitialzation and postProcessAfterInitialzation)");
		User user3 = (User) context.getBean("user3");
		System.out.println(user3);
		
		//18. ����bean�Ĺ�������
		System.out.println();
		System.out.println("����bean�Ĺ�������");
		User user5 = (User)context.getBean("user5");
		System.out.println(user5);
		User user6 = (User)context.getBean("user6");
		System.out.println(user6);
		
		//19. ����FactoryBean���÷�
		System.out.println();
		System.out.println("����FactoryBean���÷�");
		User user7 = (User) context.getBean("user7");
		System.out.println(user7);
		
		//����IOC�����е�beanʵ�����ر�IOC����
		System.out.println();
		System.out.println("����Bean");
		((ClassPathXmlApplicationContext)context).close();
	}
	
}
