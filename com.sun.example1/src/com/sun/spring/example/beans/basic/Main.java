package com.sun.spring.example.beans.basic;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		//1. 创建Spring的IOC容器
		//ApplicationContext:代表IOC容器，在创建Bean实例之前，必须先初始化IOC容器
		//ClassPathXMLApplicationContext:是ApplicaitonContext的实现类，可以从类路径下加载配置文件
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-basic.xml");
		
		//2. 从IOC容器获取bean实例
		//   利用id定位到IOC容器中的Bean
		System.out.println();
		System.out.println("利用ID获取IOC容器中的Bean");
		HelloWorld helloWorld = (HelloWorld) context.getBean("helloWorld");
		
		//利用类型返回IOC容器中的bean，但要求IOC容器中必须只能有一个该类型的bean
		//HelloWorld helloWorld = context.getBean(HelloWorld.class);
		
		//3. 调用hello方法
		helloWorld.helloWorld();
		
		//4. 测试构造函数的不同形式，获取Car实例，详细配置形式请参见配置文件
		System.out.println();
		System.out.println("测试构造函数调用的不同形式");
		Car car1 = (Car)context.getBean("car1");
		System.out.println(car1);
		
		Car car2 = (Car)context.getBean("car2");
		System.out.println(car2);
		
		Car car3 = (Car)context.getBean("car3");
		System.out.println(car3);
		
		Car car4 = (Car)context.getBean("car4");
		System.out.println(car4);
		
		//5. 测试ref属性, 其中引用的car使用ref方式进行调用
		System.out.println();
		System.out.println("测试ref属性");
		Person person1 = (Person) context.getBean("person1");
		System.out.println(person1);
		
		//6. 测试内部bean，其中引用的car使用内部bean的方式进行调用
		System.out.println();
		System.out.println("测试内部Bean");
		Person person2 = (Person) context.getBean("person2");
		System.out.println(person2);
		
		//7. 测试集合属性，为地址所表示的集合类型赋值
		System.out.println();
		System.out.println("测试集合属性");
		Person person3 = (Person) context.getBean("person3");
		System.out.println(person3);
		
		//8. 测试集合类型的bean，测试如何使用集合类型的bean
		//   测试如何使用p属性
		//   测试如何使用bean的继承
		System.out.println();
		System.out.println("测试结合类的bean\n测试P属性\n测试如何使用bean的继承");
		Person person4 = (Person) context.getBean("person4");
		System.out.println(person4);
		
		//9. 实例化一个bean的template
		//   不能实例化，报错！！
		//Address addressTemplate = (Address) context.getBean("addressTemplate");
		//System.out.println(addressTemplate);
		
		//10. 测试级联属性
		System.out.println();
		System.out.println("设置级联属性");
		Person person5 = (Person) context.getBean("person5");
		System.out.println(person5);
		
		//11. 测试延迟加载
		System.out.println();
		System.out.println("测试延迟加载和依赖，Car8比Car7先初始化");
		Car car7 = (Car) context.getBean("car7");
		System.out.println(car7);
		
		//12. 测试自动装配
		System.out.println();
		System.out.println("测试自动装配");
		Person person7 = (Person) context.getBean("person7");
		System.out.println(person7);
		
		//13. 测试配置bean的作用域
		//测试Singleton类型的bean
		System.out.println();
		System.out.println("测试Singleton类型的bean");
		Car car8_1 = (Car) context.getBean("car8");
		Car car8_2 = (Car) context.getBean("car8");
		System.out.println("Scope: Singleton beans comparsion result: car8_1 " + (car8_1==car8_2?"=":"!=") + " car8_2");
		Car car9_1 = (Car) context.getBean("car9");
		Car car9_2 = (Car) context.getBean("car9");
		System.out.println("Scope: Prototype beans comparsion result: car9_1 " + (car9_1==car9_2?"=":"!=") + " car9_2");

		//14. 测试外部属性文件
		//外部属性文件：database.properties
		System.out.println();
		System.out.println("测试外部属性文件");
		DataSource dataSource = (DataSource) context.getBean("datasource");
		System.out.println(dataSource);
		
		//15. 测试SpEL
		System.out.println();
		System.out.println("测试SpEL");
		User user1 = (User) context.getBean("user1");
		System.out.println(user1);
		
		//16. 测试bean的生命周期(init-method and destroy-method)
		//注意user中各个方法的调用顺序
		System.out.println();
		System.out.println("测试bean的生命周期(init-method and destroy-method)");
		User user2 = (User) context.getBean("user2");
		System.out.println(user2);
		
		//17. 测试bean的生命周期(postProcessBeforeInitialzation and postProcessAfterInitialzation)
		//注意user中各个方法的调用顺序
		System.out.println();
		System.out.println("测试bean的生命周期(postProcessBeforeInitialzation and postProcessAfterInitialzation)");
		User user3 = (User) context.getBean("user3");
		System.out.println(user3);
		
		//18. 测试bean的工厂方法
		System.out.println();
		System.out.println("测试bean的工厂方法");
		User user5 = (User)context.getBean("user5");
		System.out.println(user5);
		User user6 = (User)context.getBean("user6");
		System.out.println(user6);
		
		//19. 测试FactoryBean的用法
		System.out.println();
		System.out.println("测试FactoryBean的用法");
		User user7 = (User) context.getBean("user7");
		System.out.println(user7);
		
		//销毁IOC容器中的bean实例并关闭IOC容器
		System.out.println();
		System.out.println("销毁Bean");
		((ClassPathXmlApplicationContext)context).close();
	}
	
}
