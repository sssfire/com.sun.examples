package com.sun.example.designpattern.builder2;

public class Main {

	/**
	 * 建造模式是对象的创建模式。建造模式可以将一个产品的内部表象（internal representation）
	 * 与产品的生产过程分割开来，从而可以使一个建造过程生成具有不同的内部表象的产品对象。
	 * 
	 */
	
    public static void main(String[]args){
        //建造者：负责各部分零件的创建
    	Builder builder = new ConcreteBuilder();
    	
    	//组装者：组织如何装配零件
        Director director = new Director(builder);
        director.construct();
        
        //通过建造者取得最终装配的产品
        Product product = builder.retrieveResult();
        
        
        System.out.println(product.getPart1());
        System.out.println(product.getPart2());
    }

}
