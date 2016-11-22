package com.sun.spring.example.beans.aop;

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
 * AOP �� helloWorld
 * 1. ���� jar ��
 * com.springsource.org.aopalliance-1.0.0.jar
 * com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar
 * spring-aspects-4.0.0.RELEASE.jar
 * 
 * 2. �� Spring �������ļ��м��� aop �������ռ䡣 
 * 
 * 3. ����ע��ķ�ʽ��ʹ�� AOP
 * 3.1 �������ļ��������Զ�ɨ��İ�: <context:component-scan base-package="com.atguigu.spring.aop"></context:component-scan>
 * 3.2 ����ʹ AspjectJ ע�������õ�����: <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
 * Ϊƥ������Զ����ɶ�̬�������. 
 * 
 * 4. ��д������: 
 * 4.1 һ��һ��� Java ��
 * 4.2 ���������Ҫ����ʵ�ֵĹ���. 
 *
 * 5. ��������
 * 5.1 ��������� IOC �е� bean: ʵ������� @Component ע��
 * 5.2 ������һ������: ��� @Aspect
 * 5.3 ����֪ͨ: ��������빦�ܶ�Ӧ�ķ���. 
 * 5.3.1 ǰ��֪ͨ:@Before 
 * 5.3.2 ��֪ͨ�з�������ϸ��: ������֪ͨ��������� JoinPoint ���͵Ĳ���, ���п��Է��ʵ�������ǩ���ͷ����Ĳ���. 
 * 5.3.3 ����֪ͨ:@After
 * 5.3.4 ����֪ͨ:@AfterReturning
 * 5.3.5 �쳣֪ͨ:@AfterThrowing
 * 5.3.6 ����֪ͨ:@Around
 * 5.4 �����е�: @Pointcut ͨ������һ���е�����������������ط���������
 * 5.5 ������������ȼ�˳��: @Order
 */

@Order(2) //������������ȼ�������ԽС���ȼ�Խ��
@Aspect //��������Ϊһ������
@Component //��Ҫ�Ѹ�����뵽IOC�����н��й���
public class LoggingAspect {
	
	 //����һ������, ���������������ʽ. һ���, �÷������ٲ���Ҫ���������Ĵ���. 
	 //ʹ�� @Pointcut �������������ʽ. 
	 //���������ֱ֪ͨ��ʹ�÷����������õ�ǰ���������ʽ. 
	@Pointcut("execution(public int com.sun.spring.example.beans.aop.ArithmeticCalculatorImpl.*(int,int))")
	public void declearPointcutExpression1(){}
	//��һ�������ж������е�
	@Pointcut("execution(public double com.sun.spring.example.beans.aop.ArithmeticCalculatorImpl.*(int,int))")
	public void declearPointcutExpression2(){}
	
	//�����÷�����һ��ǰ��֪ͨ����Ŀ�귽��֮ǰִ��
	//���еı��ʽ���������ʽ������ʹ��ͨ������һ���е�
	//@Before("execution(public int com.sun.spring.example.beans.aop.ArithmeticCalculatorImpl.*(int,int))")
	@Before(value = "declearPointcutExpression1() || declearPointcutExpression2()")
	public void beforeMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		List<Object> args = Arrays.asList(joinPoint.getArgs());
		
		System.out.println("Before method '" + methodName + "' begin with " + args);
	}
	
	//�����÷�����һ������֪ͨ������֪ͨ���ʲ�������ֵ��
	//���۷����Ƿ����쳣����ִ�к���֪ͨ
	//@After("execution(public int com.sun.spring.example.beans.aop.ArithmeticCalculatorImpl.*(int,int))")
	@After(value = "declearPointcutExpression1() || declearPointcutExpression2()")
	public void afterMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("After method '" + methodName + "' ends.");
	}
	
	//�����÷�����һ������֪ͨ
	//����֪ͨ�Ǻ�����������ʱ���ʵ�֪ͨ���������ִ�г����쳣�򲻷��ʸ�֪ͨ
	//��֪ͨ�п��Է��ʷ������ص�ֵ
	//@AfterReturning(value="execution(public int com.sun.spring.example.beans.aop.ArithmeticCalculatorImpl.*(int,int))",	returning="result")
	@AfterReturning(value="declearPointcutExpression1() || declearPointcutExpression2()",	returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("AfterReturning method '" + methodName + "' ends with return value: " + result);
	}
	
	//�����÷�����һ���쳣֪ͨ
	//�쳣֪ͨ�ں����׳��쳣ʱִ��
	//�쳣֪ͨ���Բ����׳����쳣�����׳����쳣���ͱ���ͺ����׳����쳣��ƥ�����Ϊ������󣬷����쳣֪ͨ���񲻵���
	//������ĺ�����Exception, ArithmeticException���Ա����񵽣���NullPointerException���񲻵���
	//@AfterThrowing(value="execution(public int com.sun.spring.example.beans.aop.ArithmeticCalculatorImpl.*(int,int))",throwing="ex")
	@AfterThrowing(value="declearPointcutExpression1() || declearPointcutExpression2()", throwing="ex")
	public void afterThrowing(JoinPoint joinPoint,  ArithmeticException ex){
//	public void afterThrowing(JoinPoint joinPoint,  Exception ex){
//	public void afterThrowing(JoinPoint joinPoint,  NullPointerException ex){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("afterThrowing method '" + methodName + "' throw exceptions: " + ex.toString());
	}
	
	//����֪ͨ��Я��ProceedingJoinPoint���͵Ĳ���
	//����֪ͨ�����ڶ�̬�����ʵ�֣�ProceedingJoinPoint������Ƿ�ִ��Ŀ�귽��
	//����֪ͨ�����з���ֵ������ֵ��ΪĿ�귽���ķ���ֵ
	//@Around("execution(public int com.sun.spring.example.beans.aop.ArithmeticCalculatorImpl.*(int,int))")
	@Around(value="declearPointcutExpression2()")
	public Object aroundMethod(ProceedingJoinPoint pjp){
		Object result = null;
		
		String methodName = pjp.getSignature().getName();
		
		try{
			//ǰ��֪ͨ
			System.out.println("Aound method ==> The before method '" +methodName+ "' begin with " + Arrays.asList(pjp.getArgs()));
			
			//ִ�з���
			result = pjp.proceed();
			
			//����֪ͨ
			System.out.println("Aound method ==> The after method '" +methodName+ "' result: " + result);
		}catch(Throwable e){
			//�쳣֪ͨ
			System.out.println("Aound method ==> The after throwing method '" +methodName+ "' exception: " + e.toString());
		}
		
		//����֪ͨ
		System.out.println("Aound method ==> The returning method '" + methodName + "'");
		
		return result;
	}
	
	
}
