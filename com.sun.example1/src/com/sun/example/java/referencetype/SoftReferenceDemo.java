package com.sun.example.java.referencetype;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;
import java.util.Random;

/**
 * 1. Java存在四种类型的引用，StrongReference，SoftReference，WeakReference和PhantomReference，
 *    在JVM进行垃圾回收时不同的引用回收策略各不相同
 * - StrongReference，就是一般的引用，如果对象存在StrongReference，则无论如何JVM都不会回收该对象
 * - SoftReference，软引用，JVM在GC时如果Heap空间不够，才会回收软引用的对象，否则不会回收。
 * - WeakReference，弱引用，JVM在GC时所有弱引用对象会被回收，不管Heap空间够不够。
 * - PhantomReference, 虚引用，相当于没有引用，JVM在GC时会回收该引用对象，虚引用可追踪对象回收情况。
 * 2. 软引用可用于一些系统缓存的处理
 * 3. 使用ReferenceQueue可以跟踪被回收的对象. 当Reference对象初始化时，构造参数中包含ReferenceQueue，
 *    则对象被回收时将记录到该Queue中。使用poll方法可弹出Reference对象。
 * 3. 下面的Demo中，把从数据库中查出来的数据放入cache中，cache中存放软引用对象以便内存不足时JVM可以回收。
 *    这样可以加速查询效率而又不必担心内存不足引起OOM问题。
 * 4. 虚拟机参数设置如下，减小Heap内存可以更容易验证：
 *    -Xms10m -Xmx10m
 * 5. 也可以把SoftReference改为WeakReference以看到更明显的效果
 * @author I068353
 *
 */
public class SoftReferenceDemo {
	
	Hashtable<String, EmployeeReference> employeeCache = new Hashtable<String, EmployeeReference>();
	ReferenceQueue<Employee> refQueue = new  ReferenceQueue<Employee>();

	final static int employNum = 100000;
	
	public static void main(String[] args) {
		SoftReferenceDemo refDemo = new SoftReferenceDemo();
		
		while(true){
			Random rand = new Random();
			String key = rand.nextInt(employNum) + "";
			Employee em = refDemo.get(key);
			//System.out.println(em.getName());
			//System.out.println(em);
//			if( i%(employNum/10) == 0 ){
//				System.out.println("System GC...");
//				System.gc();
//			}
		}
	}

	public void put(Employee em){
		EmployeeReference empRef = new EmployeeReference(em, refQueue);
		employeeCache.put(em.getId(), empRef);
		//System.out.println("Put ID: " + em.getId());
	}
	
	public Employee get(String employeeID){
		
		EmployeeReference empRef = employeeCache.get(employeeID);
		if(empRef!=null){ //曾经被缓存过
			Employee em = empRef.get();
			if(em!=null){ //employee未被回收
				return em;
			}else{
				//清除无用缓存
				System.out.println("准备清除缓存");
				cleanCache();
			}
		}
		
		//未被缓存过，或缓存过但employee已被回收,重新缓存
		Employee em = getEmployeeFromDatabase(employeeID);
		put(em);
		
		return em;
	}
	
	
	//模拟数据库操作，随机生成一个Employee
	private Employee getEmployeeFromDatabase(String employeeID){
		Random rand = new Random();
		String id = String.valueOf(rand.nextInt(employNum));
		Employee em = new Employee(""+id, "name-"+id, rand.nextInt(22)+30);
		return em;
	}
	
	private void cleanCache(){
		EmployeeReference empRef = null;
		
		while((empRef=(EmployeeReference)refQueue.poll())!=null){
			employeeCache.remove(empRef.getKey());
			System.out.println("清理缓存：" + empRef.getKey());
		}
	}
	
	class EmployeeReference extends SoftReference<Employee>{

		private String key;
		
		public EmployeeReference(Employee em, ReferenceQueue<Employee> q) {
			super(em, q);
			this.key = em.getId();
		}			
		
		public String getKey(){
			return key;
		}
		
	}	
	
	class Employee{
		String id;
		String name;
		int age;
		
		public Employee(String id, String name, int age) {
			super();
			this.id = id;
			this.name = name;
			this.age = age;
		}
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public int getAge() {
			return age;
		}
		
		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "Employee [id=" + id + ", name=" + name + ", age=" + age + "]";
		}
		
		
	}
}
