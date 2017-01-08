package com.sun.example.java.jvm;

import java.util.ArrayList;

/**
 * 1.��֤���ڴ��������
 * 2.ʹ��������������£�
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError
 * -Xms20M ������С���ڴ�
 * -Xmx20M ���������ڴ�
 * -Xmn10M ����������ڴ�
 * -XX:+PrintGCDetails ����GC����ʱ��ӡ��ӦGC��Ϣ
 * -XX:SurvivorRatio=8 ��������Survivor����Eden���ıȣ�8��ʾ����Survivor����EdenΪ2��8����ÿ��Survivor��ռ�������1/10
 * -XX:+HeapDumpOnOutOfMemoryError ����OOMʱת�����ڴ����
 * 3.ִ����������hprof���ڴ�ת�������ļ�������ͨ������̨�鿴�ļ���Ϣ
 * 4.ʹ��Eclipse Memory Analyzer���з�����������Eclipse Marketplace��������װ
 * @author I068353
 *
 */
public class HeapOOMDemo {
	public static void main(String[] args) throws InterruptedException {
		ArrayList<OOMObject> list = new ArrayList<OOMObject>();
		
		while(true){
			list.add(new OOMObject());
			if(list.size()>10000){
				list.clear();
			}
			Thread.sleep(1);
		}
	}
	
	static class OOMObject{
		
	}
}
