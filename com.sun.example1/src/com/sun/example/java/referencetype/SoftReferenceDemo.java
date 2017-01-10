package com.sun.example.java.referencetype;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;
import java.util.Random;

/**
 * 1. Java�����������͵����ã�StrongReference��SoftReference��WeakReference��PhantomReference��
 *    ��JVM������������ʱ��ͬ�����û��ղ��Ը�����ͬ
 * - StrongReference������һ������ã�����������StrongReference�����������JVM��������ոö���
 * - SoftReference�������ã�JVM��GCʱ���Heap�ռ䲻�����Ż���������õĶ��󣬷��򲻻���ա�
 * - WeakReference�������ã�JVM��GCʱ���������ö���ᱻ���գ�����Heap�ռ乻������
 * - PhantomReference, �����ã��൱��û�����ã�JVM��GCʱ����ո����ö��������ÿ�׷�ٶ�����������
 * 2. �����ÿ�����һЩϵͳ����Ĵ���
 * 3. ʹ��ReferenceQueue���Ը��ٱ����յĶ���. ��Reference�����ʼ��ʱ����������а���ReferenceQueue��
 *    ����󱻻���ʱ����¼����Queue�С�ʹ��poll�����ɵ���Reference����
 * 3. �����Demo�У��Ѵ����ݿ��в���������ݷ���cache�У�cache�д�������ö����Ա��ڴ治��ʱJVM���Ի��ա�
 *    �������Լ��ٲ�ѯЧ�ʶ��ֲ��ص����ڴ治������OOM���⡣
 * 4. ����������������£���СHeap�ڴ���Ը�������֤��
 *    -Xms10m -Xmx10m
 * 5. Ҳ���԰�SoftReference��ΪWeakReference�Կ��������Ե�Ч��
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
	
	
	//ģ�����ݿ�������������һ��Employee
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
			System.out.println("�����棺" + empRef.getKey());
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
