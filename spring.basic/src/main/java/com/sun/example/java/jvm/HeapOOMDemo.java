package com.sun.example.java.jvm;

import java.util.ArrayList;

/**
 * 1.验证堆内存溢出问题
 * 2.使用虚拟机参数如下：
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError
 * -Xms20M 设置最小堆内存
 * -Xmx20M 设置最大堆内存
 * -Xmn10M 设置年轻代内存
 * -XX:+PrintGCDetails 当有GC发生时打印相应GC信息
 * -XX:SurvivorRatio=8 设置两个Survivor区和Eden区的比，8表示两个Survivor区：Eden为2：8，即每个Survivor区占年轻代的1/10
 * -XX:+HeapDumpOnOutOfMemoryError 发生OOM时转储堆内存快照
 * 3.执行完后会生成hprof堆内存转储快照文件，可以通过控制台查看文件信息
 * 4.使用Eclipse Memory Analyzer进行分析，可以在Eclipse Marketplace中搜索安装
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
