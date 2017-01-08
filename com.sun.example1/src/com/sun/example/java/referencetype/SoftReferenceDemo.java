package com.sun.example.java.referencetype;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Random;

/**
 * �����⣡����ȡ��EM���ݵ�ʱ��Ӧ��������ɣ�����id�ķ�ΧҪ�����ڴ��ܹ����ɵķ�Χ��
 * �޸�get�����������ĳ��ID��Χȡ�����ݣ�Ȼ����뻺��
 * @author I068353
 *
 */
public class SoftReferenceDemo {
	
	Hashtable<String, EmployeeReference> employeeCache = new Hashtable<String, EmployeeReference>();
	ReferenceQueue<Employee> refQueue = new  ReferenceQueue<Employee>();
	
	//ģ�����ݿ�
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
		System.out.println("Employee��ʼ�����");
	}
	
	public void put(Employee em){
		EmployeeReference empRef = new EmployeeReference(em, refQueue);
		employeeCache.put(em.getId(), empRef);
		//System.out.println("Put ID: " + em.getId());
	}
	
	public Employee get(String employeeID){
		
		EmployeeReference empRef = employeeCache.get(employeeID);
		if(empRef!=null){ //�����������
			Employee em = empRef.get();
			if(em!=null){ //employeeδ������
				return em;
			}else{
				//������û���
				System.out.println("׼���������");
				cleanCache();
			}
		}
		
		//δ����������򻺴����employee�ѱ�����,���»���
		Employee em = getEmployeeFromDatabase(employeeID);
		put(em);
		
		return em;
	}
	
	
	//ģ�����ݿ����
	private Employee getEmployeeFromDatabase(String employeeID){
		return emDatabase.get(employeeID);
	}
	
	private void cleanCache(){
		EmployeeReference empRef = null;
		
		while((empRef=(EmployeeReference)refQueue.poll())!=null){
			employeeCache.remove(empRef.getKey());
			System.out.println("�����棺" + empRef.getKey());
		}
	}
	
	//����ģ������
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
