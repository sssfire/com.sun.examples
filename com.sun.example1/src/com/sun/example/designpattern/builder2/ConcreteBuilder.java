package com.sun.example.designpattern.builder2;

public class ConcreteBuilder implements Builder {

    private Product product = new Product();
    /**
     * ��Ʒ������췽��1
     */
    @Override
    public void buildPart1() {
        //������Ʒ�ĵ�һ�����
    	product.setPart1("��ţ�9527");
    }
    /**
     * ��Ʒ������췽��2
     */
    @Override
    public void buildPart2() {
        //������Ʒ�ĵڶ������
    	product.setPart2("���ƣ�XXX");
    }
    /**
     * ��Ʒ��������
     */
    @Override
    public Product retrieveResult() {
        return product;
    }

}
