package com.sun.example.spring.aop;

import java.util.Arrays;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * AOP 的 helloWorld
 * 1. 加入 jar 包
 * com.springsource.org.aopalliance-1.0.0.jar
 * com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar
 * spring-aspects-4.0.0.RELEASE.jar
 * 
 * 2. 在 Spring 的配置文件中加入 aop 的命名空间。 
 * 
 * 3. 基于注解的方式来使用 AOP
 * 3.1 在配置文件中配置自动扫描的包: <context:component-scan base-package="com.atguigu.spring.aop"></context:component-scan>
 * 3.2 加入使 AspjectJ 注解起作用的配置: <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
 * 为匹配的类自动生成动态代理对象. 
 * 
 * 4. 编写切面类: 
 * 4.1 一个一般的 Java 类
 * 4.2 在其中添加要额外实现的功能. 
 *
 * 5. 配置切面
 * 5.1 切面必须是 IOC 中的 bean: 实际添加了 @Component 注解
 * 5.2 声明是一个切面: 添加 @Aspect
 * 5.3 声明通知: 即额外加入功能对应的方法. 
 * 5.3.1 前置通知:@Before 
 * 5.3.2 在通知中访问连接细节: 可以在通知方法中添加 JoinPoint 类型的参数, 从中可以访问到方法的签名和方法的参数. 
 * 5.3.3 后置通知:@After
 * 5.3.4 返回通知:@AfterReturning
 * 5.3.5 异常通知:@AfterThrowing
 * 5.3.6 环绕通知:@Around
 * 5.4 声明切点: @Pointcut 通过定义一个切点变量，可以在其它地方进行引用
 * 5.5 定义切面的优先级顺序: @Order
 */

@Order(2) //定义切面的优先级，数字越小优先级越高
@Aspect //声明该类为一个切面
@Component //需要把该类放入到IOC容器中进行管理
public class LoggingAspect {
	
	 //定义一个方法, 用于声明切入点表达式. 一般地, 该方法中再不需要添入其他的代码. 
	 //使用 @Pointcut 来声明切入点表达式. 
	 //后面的其他通知直接使用方法名来引用当前的切入点表达式. 
	@Pointcut("execution(public int com.sun.example.spring.aop.ArithmeticCalculatorImpl.*(int,int))")
	public void declearPointcutExpression1(){}
	//在一个切面中定义多个切点
	@Pointcut("execution(public double com.sun.example.spring.aop.ArithmeticCalculatorImpl.*(int,int))")
	public void declearPointcutExpression2(){}
	
	//声明该方法是一个前置通知：在目标方法之前执行
	//其中的表达式是切入点表达式，可以使用通配符表达一类切点
	//@Before("execution(public int com.sun.example.spring.aop.ArithmeticCalculatorImpl.*(int,int))")
	@Before(value = "declearPointcutExpression1() || declearPointcutExpression2()")
	public void beforeMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		List<Object> args = Arrays.asList(joinPoint.getArgs());
		
		System.out.println("Before method '" + methodName + "' begin with " + args);
	}
	
	//声明该方法是一个后置通知（后置通知访问不到返回值）
	//无论方法是否发生异常都会执行后置通知
	//@After("execution(public int com.sun.example.spring.aop.ArithmeticCalculatorImpl.*(int,int))")
	@After(value = "declearPointcutExpression1() || declearPointcutExpression2()")
	public void afterMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("After method '" + methodName + "' ends.");
	}
	
	//声明该方法是一个返回通知
	//返回通知是函数正常结束时访问的通知，如果函数执行出现异常则不访问该通知
	//在通知中可以访问方法返回的值
	//@AfterReturning(value="execution(public int com.sun.example.spring.aop.ArithmeticCalculatorImpl.*(int,int))",	returning="result")
	@AfterReturning(value="declearPointcutExpression1() || declearPointcutExpression2()",	returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("AfterReturning method '" + methodName + "' ends with return value: " + result);
	}
	
	//声明该方法是一个异常通知
	//异常通知在函数抛出异常时执行
	//异常通知可以捕获抛出的异常。但抛出的异常类型必须和函数抛出的异常相匹配或者为父类对象，否则异常通知捕获不到。
	//在下面的函数中Exception, ArithmeticException可以被捕获到，但NullPointerException捕获不到。
	//@AfterThrowing(value="execution(public int com.sun.example.spring.aop.ArithmeticCalculatorImpl.*(int,int))",throwing="ex")
	@AfterThrowing(value="declearPointcutExpression1() || declearPointcutExpression2()", throwing="ex")
	public void afterThrowing(JoinPoint joinPoint,  ArithmeticException ex){
//	public void afterThrowing(JoinPoint joinPoint,  Exception ex){
//	public void afterThrowing(JoinPoint joinPoint,  NullPointerException ex){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("afterThrowing method '" + methodName + "' throw exceptions: " + ex.toString());
	}
	
	//环绕通知须携带ProceedingJoinPoint类型的参数
	//环绕通知类似于动态代理的实现：ProceedingJoinPoint会决定是否执行目标方法
	//环绕通知必须有返回值，返回值即为目标方法的返回值
	//@Around("execution(public int com.sun.example.spring.aop.ArithmeticCalculatorImpl.*(int,int))")
	@Around(value="declearPointcutExpression2()")
	public Object aroundMethod(ProceedingJoinPoint pjp){
		Object result = null;
		
		String methodName = pjp.getSignature().getName();
		
		try{
			//前置通知
			System.out.println("Aound method ==> The before method '" +methodName+ "' begin with " + Arrays.asList(pjp.getArgs()));
			
			//执行方法
			result = pjp.proceed();
			
			//后置通知
			System.out.println("Aound method ==> The after method '" +methodName+ "' result: " + result);
		}catch(Throwable e){
			//异常通知
			System.out.println("Aound method ==> The after throwing method '" +methodName+ "' exception: " + e.toString());
		}
		
		//返回通知
		System.out.println("Aound method ==> The returning method '" + methodName + "'");
		
		return result;
	}
	
	
}
