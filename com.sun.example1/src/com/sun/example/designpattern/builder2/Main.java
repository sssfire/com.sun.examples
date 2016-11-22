package com.sun.example.designpattern.builder2;

public class Main {

	/**
	 * ����ģʽ�Ƕ���Ĵ���ģʽ������ģʽ���Խ�һ����Ʒ���ڲ�����internal representation��
	 * ���Ʒ���������̷ָ�����Ӷ�����ʹһ������������ɾ��в�ͬ���ڲ�����Ĳ�Ʒ����
	 * 
	 */
	
    public static void main(String[]args){
        //�����ߣ��������������Ĵ���
    	Builder builder = new ConcreteBuilder();
    	
    	//��װ�ߣ���֯���װ�����
        Director director = new Director(builder);
        director.construct();
        
        //ͨ��������ȡ������װ��Ĳ�Ʒ
        Product product = builder.retrieveResult();
        
        
        System.out.println(product.getPart1());
        System.out.println(product.getPart2());
    }

}
