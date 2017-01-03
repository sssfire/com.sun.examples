package com.sun.example.java.generictype;


/**
 * 1. ��������ֻ���ڱ���׶ν��м�飬���������ֽ���ᡰ�����������͵�������Ϣ�������Ǳ����ͬ�Ļ������͡�
 * 2. ���Ϳ������ڽӿڣ���ͺ����Ĳ������棬��Ӧ�Ŀ��Է�Ϊ���ͽӿڣ�������ͷ��ͷ���
 * 3. ���ͷ����еķ��Ͳ������Դ��뷺�����͵�����
 * 4. ���ͷ�����ʹ��<? extends Food>����ͨ������޷��Ͳ������ò���ֻ�ܴ��뷺�����͵�����ͱ���
 * 5. ���ͷ�����ʹ��<? super Apple>����ͨ������޷��Ͳ������ò���ֻ�ܴ��뷺�����͵ĸ���ͱ���
 */
public class GenericTypeDemo {
	
	public static void main(String[] args) {
		GenericTypeDemo app = new GenericTypeDemo();
		
		Plate<Fruit> plate1 = app.new Plate<Fruit>(app.new Fruit());
		Plate<Meat>  plate2 = app.new Plate<Meat>(app.new Meat());
		Plate<Apple> plate3 = app.new Plate<Apple>(app.new Apple());	
		Plate<Food>  plate4 = app.new Plate<Food>(app.new Food());
		Plate<RedApple> plate5 = app.new Plate<RedApple>(app.new RedApple());
		
		//���ͷ���set���Դ��뷺�����͵�����
		plate1.set(app.new Apple());
		plate1.set(app.new RedApple());
		//plate1.set(app.new Meat());  //����ͨ��������ΪMeat����Fruit������
		
		//���ͱ��������������������Ϣ�������Ǳ��ԭʼ���ͣ���˱����ͬ�ķ��Ͷ����class����ͬ��
		System.out.println("Fruit class: " + plate1.getClass());
		System.out.println("Meat  class: " + plate2.getClass());

		//�����Ͻ�ͨ�����ֻ�ܴ���Fruit�������Furit
		Fruit f = app.getFruitInPlate(plate1);
		f = app.getFruitInPlate(plate3);
		//f = app.getFruitInPlate(plate2); //�������Meat������Furit������
		//f = app.getFruitInPlate(plate4); //�������Food��apple�ĸ���
		
		//�����½�ͨ�����ֻ�ܴ���Apple�ĸ����Apple
		Object o = app.getFoodInPlate(plate1);
		o = app.getFoodInPlate(plate3);
	    o = app.getFoodInPlate(plate4);
		//o = app.getFoodInPlate(plate5); //�������RedApple��Apple�����࣬���Ǹ���
		//o = app.getFoodInPlate(plate2); //�������Meat����Apple�ĸ���
		
		//�������ʽ����ͨ������޲�������ֵ
		//�������ʽ����ͨ������޲��ܵõ�ֵ
	}
	
	//����ͨ�������
	public Fruit getFruitInPlate(Plate<? extends Fruit> plate){
		return plate.get();
	}
	
	//����ͨ�������
	public Object getFoodInPlate(Plate<? super Apple> plate){
		return plate.get();
	}
	
	class Plate<T>{
		private T item;
		
		public Plate(T item){
			this.item = item;
		}
		
		public void set(T item){
			this.item = item;
		}
		
		public T get(){
			return item;
		}
		
	}
	
	
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
