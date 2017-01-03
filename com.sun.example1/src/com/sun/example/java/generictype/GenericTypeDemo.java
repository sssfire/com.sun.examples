package com.sun.example.java.generictype;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 泛型类型只会在编译阶段进行检查，编译完后的字节码会“擦除”掉泛型的类型信息，把他们变成相同的基本类型。
 * 2. 泛型方法的动态调用可绕过编译器检查，但有可能在运行期出错，出现类型转换错误。
 * 3. 泛型类型不能是基本类型，例如double，float等，必须是对象类型
 * 4. 泛型对象在定义时不能是静态类型，泛型方法也不能是静态方法，否则编译出错
 * 5. 泛型可以用在接口，类和函数的参数上面，相应的可以分为泛型接口，泛型类和泛型方法
 * 6. 泛型方法中的泛型参数可以传入泛型类型的子类
 * 7. 泛型分为限定通配符和非限定通配符，使用<T>表示限定通配符，代表某一类具体类型，使用<?>表示非限定通配符，表示任意类型。
 * 8. 泛型方法中使用<? extends Food>定义上界通配符泛型参数，该参数只能传入泛型类型的子类和本身
 * 9. 泛型方法中使用<? super Apple>定义下界通配符泛型参数，该参数只能传入泛型类型的父类和本身
 * 10. 泛型对象定义时使用<? extends Food>定义上界通配符泛型参数，不能对泛型对象set值
 * 11. 泛型对象定义时使用<? super Food>定义下界通配符泛型参数，不能使用get方法取得值值
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
				
		//泛型方法set可以传入泛型类型的子类
		plate1.set(app.new Apple());
		plate1.set(app.new RedApple());
		//plate1.set(app.new Meat());  //编译通不过，因为Meat不是Fruit的子类
		//plate1.getClass().getMethod("set", Object.class).invoke(plate1, app.new Meat()); //编译可通过，运行出错，cast exception，说明使用方法动态调用可绕过编译器检查
		//plate1.add(app.new Meat()); //编译通不过，因为Meat不是Fruit的之类
		plate1.getClass().getMethod("add", Object.class).invoke(plate1, app.new Meat()); //编译运行均无错，但list中存储的是不同的元素，说明使用方法动态调用可绕过编译器检查。 
		
		//泛型编译后会擦除掉泛型类型信息，把他们变成原始类型，因此编译后不同的泛型对象的class是相同的
		System.out.println("Fruit class: " + plate1.getClass());
		System.out.println("Meat  class: " + plate2.getClass());

		//泛型参数上界通配符，只能传入Fruit的子类和Furit
		Fruit f = app.getFruitInPlate(plate1);
		f = app.getFruitInPlate(plate3);
		//f = app.getFruitInPlate(plate2); //编译错误，Meat不属于Furit的子类
		//f = app.getFruitInPlate(plate4); //编译错误，Food是apple的父类
		
		//泛型参数下界通配符，只能传入Apple的父类和Apple
		Object o = app.getFoodInPlate(plate1);
		o = app.getFoodInPlate(plate3);
	    o = app.getFoodInPlate(plate4);
		//o = app.getFoodInPlate(plate5); //编译错误，RedApple是Apple的子类，而非父类
		//o = app.getFoodInPlate(plate2); //编译错误，Meat不是Apple的父类
		
		//泛型对象上界通配符，对象不能set值，因为Plate内部元素定义为Fruit本身或子类，不能确定具体的存储类型。
	    //plate6.set(app.new RedApple()); //编译错误，
	    Fruit fruit = plate6.get(); //可以使用get方法把获得的对象泛化为基本类型fruit
		
	    //泛型对象下界通配符，对象不能get值
	    plate7.set(app.new RedApple()); //可以使用set方法设置值，内部存储的为Fruit本身或之类，但根据泛型参数定义传入的实参必须为Fruit的子类
	    //fruit = plate7.get(); //不能使用get方法设置值，内部存储的为Fruit的最顶级父类Object，屏蔽了父类型信息，取出时除了Object类不能确定是继承层次中的哪个父类
	    o = plate7.get(); //只能转化为Object类。
	    
	    //可以存放任意类型的泛型对象
	    plate8.set(app.new Apple());
	    plate8.set(app.new Meat());
	    
	}
	
	//定义通配符上界
	public Fruit getFruitInPlate(Plate<? extends Fruit> plate){
		//plate.set(new Apple()); //编译错误，泛型参数上界通配符，对象不能set值
		return plate.get();
	}
	
	//定义通配符下界
	public Object getFoodInPlate(Plate<? super Apple> plate){
		return plate.get(); //只能返回Object类型
	}
	
	//定义无界通配符，使用<?>通配符的对象只能get，不能set，除了null, 和extends同理
	public void addFoodInPlate(Plate<Object> plate, List<?> foods){
		//foods.add(new Object()); //Object对象也不能添加，因为Object是?的子类，不能加子类
		for (int i = 0; i < foods.size(); i++) {
			plate.add(foods.get(i));
		}
	}
	
	class Plate<T>{
		//private static T item; //错误，泛型类型定义的变量不能是静态类型。
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
		
		//不能定义静态泛型方法，因为静态方法不需要对象来调用，对象没有，因此没办法确定T的类型
		//public static void show(T item){}
	
	}
	
	/**
	 * 继承关系
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
