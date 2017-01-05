package com.sun.example.java.referencetype;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Random;

/**
 * 有问题！！在取得EM数据的时候，应该随机生成，并且id的范围要大于内存能够容纳的范围。
 * 修改get方法以随机在某个ID范围取得数据，然后插入缓存
 * @author I068353
 *
 */
public class SoftReferenceDemo {
	
	Hashtable<String, EmployeeReference> employeeCache = new Hashtable<String, EmployeeReference>();
	ReferenceQueue<Employee> refQueue = new  ReferenceQueue<Employee>();
	
	//模拟数据库
	Hashtable<String, Employee> emDatabase = new Hashtable<String, Employee>();
	final static int employNum = 1000;
	
	public static void main(String[] args) {
		SoftReferenceDemo refDemo = new SoftReferenceDemo();
		
		while(true){
			Random rand = new Random();
			String key = (rand.nextInt(employNum) + 1) + "";
			Employee em = refDemo.get(key);
			System.out.println(em.getName());
			//System.out.println(em);
//			if( i%(employNum/10) == 0 ){
//				System.out.println("System GC...");
//				System.gc();
//			}
		}
	}
	
	public SoftReferenceDemo() {
		initalEmployeeData(employNum);
		System.out.println("Employee初始化完毕");
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
	
	
	//模拟数据库操作
	private Employee getEmployeeFromDatabase(String employeeID){
		return emDatabase.get(employeeID);
	}
	
	private void cleanCache(){
		EmployeeReference empRef = null;
		
		while((empRef=(EmployeeReference)refQueue.poll())!=null){
			employeeCache.remove(empRef.getKey());
			System.out.println("清理缓存：" + empRef.getKey());
		}
	}
	
	//创建模拟数据
	private void initalEmployeeData(int number){
		Random random = new Random();
		for(int i=1;i<number+1;i++){
			int age = random.nextInt(22) + 30; 
			Employee em = new Employee(""+i, "name-"+i, age);
			emDatabase.put(em.getId(), em);
		}
	}

	class EmployeeReference extends WeakReference<Employee>{

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
