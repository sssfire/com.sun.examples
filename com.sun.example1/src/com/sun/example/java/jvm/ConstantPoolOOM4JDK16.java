package com.sun.example.java.jvm;

import java.util.ArrayList;

/**
 * ��֤Java������OOM�쳣��Only for JDK1.6�����°汾��
 * 1. ����ʱ�������Ƿ�������һ���֣�ͨ���������ӳ������г�����С���Ӷ�ʹ���ô��������
 * 2. ʹ��������������£�
 * -Xms10M -Xmx10M -XX:PermSize=5M -XX:MaxPermSize=5M
 * -XX:PermSize=5M ������С��������С
 * -XX:MaxPermSize=5M ������󷽷�����С
 * 3. ʹ��String.intern()�������÷����������أ���������ذ���һ�����ڴ��ַ����ĳ�����
 *    �򷵻ش��ַ��������ã��������������Ѹ��ַ������볣����
 * 4. ʹ������������в鿴�й�Heap��PS Perm Generation��Interned String��Ϣ������pid��ͨ��jps�����ѯ
 *    jmap -heap pid
 * 5. �ô����쳣��������JDK1.6�����°汾���֣�����JDK1.7�����ϰ汾������֡���ΪJDK1.6�����µĳ�������������
 *    ���У���1.7�ڷ������С���1.7�л�����������ȥ��
 * @author I068353
 *
 */
public class ConstantPoolOOM4JDK16 {

	public void putConstantPoolObject(){
		ArrayList<String> list = new ArrayList<String>();
		int i=1;
		while(true){
			String str = new String("test" + i).intern();
			list.add(str);
			if( i%100 == 0){
				System.out.println("list[75]" + list.get(75) + ", list size:" + list.size() + ", i:" + str);
			}
			i++;
		}
	}
	
	public static void main(String[] args) {
		
		ConstantPoolOOM4JDK16 oom = new ConstantPoolOOM4JDK16();
		oom.putConstantPoolObject();
	}

}
