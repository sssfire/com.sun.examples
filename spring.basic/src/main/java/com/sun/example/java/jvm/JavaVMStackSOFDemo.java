package com.sun.example.java.jvm;


/**
 * 测试Java本地栈的Stack Overflow异常
 * 1.使用虚拟机参数如下：
 * -Xms20M -Xmx20M -Xss128K
 * -Xss128K 设置栈内存容量，容量越小，栈深度越小
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
