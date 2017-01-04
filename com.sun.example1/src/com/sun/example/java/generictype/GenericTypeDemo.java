package com.sun.example.java.generictype;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. ��������ֻ���ڱ���׶ν��м�飬���������ֽ���ᡰ�����������͵�������Ϣ�������Ǳ����ͬ�Ļ������͡�
 * 2. ���ͷ����Ķ�̬���ÿ��ƹ���������飬���п����������ڳ�����������ת������
 * 3. �������Ͳ����ǻ������ͣ�����double��float�ȣ������Ƕ�������
 * 4. ���Ͷ����ڶ���ʱ�����Ǿ�̬���ͣ����ͷ���Ҳ�����Ǿ�̬����������������
 * 5. ���Ϳ������ڽӿڣ���ͺ����Ĳ������棬��Ӧ�Ŀ��Է�Ϊ���ͽӿڣ�������ͷ��ͷ���
 * 6. ���ͷ����еķ��Ͳ������Դ��뷺�����͵�����
 * 7. ���ͷ�Ϊ�޶�ͨ����ͷ��޶�ͨ�����ʹ��<T>��ʾ�޶�ͨ���������ĳһ��������ͣ�ʹ��<?>��ʾ���޶�ͨ�������ʾ�������͡�
 * 8. ���ͷ�����ʹ��<? extends Food>�����Ͻ�ͨ������Ͳ������ò���ֻ�ܴ��뷺�����͵�����ͱ���
 * 9. ���ͷ�����ʹ��<? super Apple>�����½�ͨ������Ͳ������ò���ֻ�ܴ��뷺�����͵ĸ���ͱ���
 * 10. ���Ͷ�����ʱʹ��<? extends Food>�����Ͻ�ͨ������Ͳ��������ܶԷ��Ͷ���setֵ
 * 11. ���Ͷ�����ʱʹ��<? super Food>�����½�ͨ������Ͳ���������ʹ��get����ȡ��ֵֵ
 * 
 */
public class GenericTypeDemo {
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		GenericTypeDemo app = new GenericTypeDemo();
		
		Plate<Fruit> plate1 = app.new Plate<Fruit>(app.new Fruit());
		Plate<Meat>  plate2 = app.new Plate<Meat>(app.new Meat());
		Plate<Apple> plate3 = app.new Plate<Apple>(app.new Apple());	
		Plate<Food>  plate4 = app.new Plate<Food>(app.new Food());
		Plate<RedApple> plate5 = app.new Plate<RedApple>(app.new RedApple());
		Plate<? extends Fruit> plate6= app.new Plate<Fruit>(app.new Apple());
		Plate<? super Fruit> plate7= app.new Plate<Fruit>(app.new Apple());
		Plate<Object> plate8 = app.new Plate<Object>();
				
		//���ͷ���set���Դ��뷺�����͵�����
		plate1.set(app.new Apple());
		plate1.set(app.new RedApple());
		//plate1.set(app.new Meat());  //����ͨ��������ΪMeat����Fruit������
		//plate1.getClass().getMethod("set", Object.class).invoke(plate1, app.new Meat()); //�����ͨ�������г���cast exception��˵��ʹ�÷�����̬���ÿ��ƹ����������
		//plate1.add(app.new Meat()); //����ͨ��������ΪMeat����Fruit��֮��
		plate1.getClass().getMethod("add", Object.class).invoke(plate1, app.new Meat()); //�������о��޴���list�д洢���ǲ�ͬ��Ԫ�أ�˵��ʹ�÷�����̬���ÿ��ƹ���������顣 
		
		//���ͱ��������������������Ϣ�������Ǳ��ԭʼ���ͣ���˱����ͬ�ķ��Ͷ����class����ͬ��
		System.out.println("Fruit class: " + plate1.getClass());
		System.out.println("Meat  class: " + plate2.getClass());

		//���Ͳ����Ͻ�ͨ�����ֻ�ܴ���Fruit�������Furit
		Fruit f = app.getFruitInPlate(plate1);
		f = app.getFruitInPlate(plate3);
		//f = app.getFruitInPlate(plate2); //�������Meat������Furit������
		//f = app.getFruitInPlate(plate4); //�������Food��apple�ĸ���
		
		//���Ͳ����½�ͨ�����ֻ�ܴ���Apple�ĸ����Apple
		Object o = app.getFoodInPlate(plate1);
		o = app.getFoodInPlate(plate3);
	    o = app.getFoodInPlate(plate4);
		//o = app.getFoodInPlate(plate5); //�������RedApple��Apple�����࣬���Ǹ���
		//o = app.getFoodInPlate(plate2); //�������Meat����Apple�ĸ���
		
		//���Ͷ����Ͻ�ͨ�����������setֵ����ΪPlate�ڲ�Ԫ�ض���ΪFruit��������࣬����ȷ������Ĵ洢���͡�
	    //plate6.set(app.new RedApple()); //�������
	    Fruit fruit = plate6.get(); //����ʹ��get�����ѻ�õĶ��󷺻�Ϊ��������fruit
		
	    //���Ͷ����½�ͨ�����������getֵ
	    plate7.set(app.new RedApple()); //����ʹ��set��������ֵ���ڲ��洢��ΪFruit�����֮�࣬�����ݷ��Ͳ������崫���ʵ�α���ΪFruit������
	    //fruit = plate7.get(); //����ʹ��get��������ֵ���ڲ��洢��ΪFruit���������Object�������˸�������Ϣ��ȡ��ʱ����Object�಻��ȷ���Ǽ̳в���е��ĸ�����
	    o = plate7.get(); //ֻ��ת��ΪObject�ࡣ
	    
	    //���Դ���������͵ķ��Ͷ���
	    plate8.set(app.new Apple());
	    plate8.set(app.new Meat());
	    
	}
	
	//����ͨ����Ͻ�
	public Fruit getFruitInPlate(Plate<? extends Fruit> plate){
		//plate.set(new Apple()); //������󣬷��Ͳ����Ͻ�ͨ�����������setֵ
		return plate.get();
	}
	
	//����ͨ����½�
	public Object getFoodInPlate(Plate<? super Apple> plate){
		return plate.get(); //ֻ�ܷ���Object����
	}
	
	//�����޽�ͨ�����ʹ��<?>ͨ����Ķ���ֻ��get������set������null, ��extendsͬ��
	public void addFoodInPlate(Plate<Object> plate, List<?> foods){
		//foods.add(new Object()); //Object����Ҳ������ӣ���ΪObject��?�����࣬���ܼ�����
		for (int i = 0; i < foods.size(); i++) {
			plate.add(foods.get(i));
		}
	}
	
	class Plate<T>{
		//private static T item; //���󣬷������Ͷ���ı��������Ǿ�̬���͡�
		private T item;
		private List<T> items = new ArrayList<T>();
		
		public Plate(){
		}
		
		public Plate(T item){
			this.item = item;
		}
		
		public void set(T item){
			this.item = item;
		}
		
		public T get(){
			return item;
		}
		
		public void add(T item){
			items.add(item);
		}
		
		//���ܶ��徲̬���ͷ�������Ϊ��̬��������Ҫ���������ã�����û�У����û�취ȷ��T������
		//public static void show(T item){}
	
	}
	
	/**
	 * �̳й�ϵ
	 * Food <= Fruit <= Apple <= RedApple
	 * 						  <= GreenApple
	 *               <= Banana
	 *      <= Meat  <= Pork
	 *               <= Beef
	 * @author sssfire
	 *
	 */
	
	class Food{}
	
	class Fruit extends Food{}
	
	class Apple extends Fruit{}
	
	class RedApple extends Apple{}
	
	class GreenApple extends Apple{}
	
	class Banana extends Fruit{}
	
	class Meat extends Food{}
	
	class Pork extends Meat{}
	
	class Beef extends Meat{}

}
