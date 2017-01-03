package com.sun.example.java.generictype;


/**
 * 1. 泛型类型只会在编译阶段进行检查，编译完后的字节码会“擦除”掉泛型的类型信息，把他们变成相同的基本类型。
 * 2. 泛型可以用在接口，类和函数的参数上面，相应的可以分为泛型接口，泛型类和泛型方法
 * 3. 泛型方法中的泛型参数可以传入泛型类型的子类
 * 4. 泛型方法中使用<? extends Food>定义通配符上限泛型参数，该参数只能传入泛型类型的子类和本身
 * 5. 泛型方法中使用<? super Apple>定义通配符下限泛型参数，该参数只能传入泛型类型的父类和本身
 */
public class GenericTypeDemo {
	
	public static void main(String[] args) {
		GenericTypeDemo app = new GenericTypeDemo();
		
		Plate<Fruit> plate1 = app.new Plate<Fruit>(app.new Fruit());
		Plate<Meat>  plate2 = app.new Plate<Meat>(app.new Meat());
		Plate<Apple> plate3 = app.new Plate<Apple>(app.new Apple());	
		Plate<Food>  plate4 = app.new Plate<Food>(app.new Food());
		Plate<RedApple> plate5 = app.new Plate<RedApple>(app.new RedApple());
		
		//泛型方法set可以传入泛型类型的子类
		plate1.set(app.new Apple());
		plate1.set(app.new RedApple());
		//plate1.set(app.new Meat());  //编译通不过，因为Meat不是Fruit的子类
		
		//泛型编译后会擦除掉泛型类型信息，把他们变成原始类型，因此编译后不同的泛型对象的class是相同的
		System.out.println("Fruit class: " + plate1.getClass());
		System.out.println("Meat  class: " + plate2.getClass());

		//泛型上界通配符，只能传入Fruit的子类和Furit
		Fruit f = app.getFruitInPlate(plate1);
		f = app.getFruitInPlate(plate3);
		//f = app.getFruitInPlate(plate2); //编译错误，Meat不属于Furit的子类
		//f = app.getFruitInPlate(plate4); //编译错误，Food是apple的父类
		
		//泛型下界通配符，只能传入Apple的父类和Apple
		Object o = app.getFoodInPlate(plate1);
		o = app.getFoodInPlate(plate3);
	    o = app.getFoodInPlate(plate4);
		//o = app.getFoodInPlate(plate5); //编译错误，RedApple是Apple的子类，而非父类
		//o = app.getFoodInPlate(plate2); //编译错误，Meat不是Apple的父类
		
		//下面的形式定义通配符上限不能设置值
		//下面的形式定义通配符下限不能得到值
	}
	
	//定义通配符上限
	public Fruit getFruitInPlate(Plate<? extends Fruit> plate){
		return plate.get();
	}
	
	//定义通配符下限
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
