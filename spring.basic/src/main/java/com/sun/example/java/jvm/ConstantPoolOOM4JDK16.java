package com.sun.example.java.jvm;

import java.util.ArrayList;

/**
 * 验证Java常量池OOM异常（Only for JDK1.6及以下版本）
 * 1. 运行时常量池是方法区的一部分，通过不断增加常量池中常量大小，从而使永久代产生溢出
 * 2. 使用虚拟机参数如下：
 * -Xms10M -Xmx10M -XX:PermSize=5M -XX:MaxPermSize=5M
 * -XX:PermSize=5M 设置最小方法区大小
 * -XX:MaxPermSize=5M 设置最大方法区大小
 * 3. 使用String.intern()可以引用方法区常量池，如果常量池包含一个等于此字符串的常量，
 *    则返回此字符串的引用；如果不包含，则把该字符串加入常量池
 * 4. 使用下面的命令行查看有关Heap，PS Perm Generation，Interned String信息，其中pid可通过jps命令查询
 *    jmap -heap pid
 * 5. 该代码异常仅仅会在JDK1.6及以下版本出现，对于JDK1.7及以上版本不会出现。因为JDK1.6及以下的常量区是在永久
 *    代中，而1.7在方法区中。在1.7中会永久运行下去。
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
