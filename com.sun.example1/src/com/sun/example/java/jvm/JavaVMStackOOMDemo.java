package com.sun.example.java.jvm;

/**
 * ����Java����ջ��Out of Memory�쳣
 * 1.�����̹߳��ർ���ڴ����
 * 2.ʹ��������������£� -Xms5M -Xmx5M -Xss128K
 * 3.����ʹ��JDK�Դ��Ĺ���Java VisualVM�鿴��Ӧ���ڴ���Ϣ�������thread�ᵼ��Heap����OOM����
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
