package com.sun.example.java.jvm;


/**
 * ����Java����ջ��Stack Overflow�쳣
 * 1.ʹ��������������£�
 * -Xms20M -Xmx20M -Xss128K
 * -Xss128K ����ջ�ڴ�����������ԽС��ջ���ԽС
 * @author I068353
 *
 */
public class JavaVMStackSOFDemo {
	
	private int stackLength = 1;
	
	public void stackLeak(){
		stackLength++;
		stackLeak();
	}
	
	public static void main(String[] args) {
		JavaVMStackSOFDemo sof = new JavaVMStackSOFDemo();
		try{
			sof.stackLeak();
		}finally{
			System.out.println("stack length:" + sof.stackLength);
		}
	}
	
	
}
