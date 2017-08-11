package com.sun.example.java.jvm;

/**
 * 测试Java本地栈的Out of Memory异常
 * 1.创建线程过多导致内存溢出
 * 2.使用虚拟机参数如下： -Xms5M -Xmx5M -Xss128K
 * 3.可以使用JDK自带的工具Java VisualVM查看相应的内存信息，过多的thread会导致Heap产生OOM错误。
 * @author I068353
 *
 */
public class JavaVMStackOOMDemo {

	private void runFovever() {
		while (true) {
		}
	}

	public void stackLeakByThread() throws InterruptedException {
		while (true) {
			Thread thread = new Thread(new Runnable() {
				
				private byte[] bigObject = new byte[1024*50];
				
				@Override
				public void run() {
					runFovever();
				}
			});
			thread.start();
			Thread.sleep(0);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		JavaVMStackOOMDemo oom = new JavaVMStackOOMDemo();
		oom.stackLeakByThread();
	}

}
