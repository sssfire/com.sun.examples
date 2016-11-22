package com.sun.example.designpattern.builder2;

public interface Builder {
    public void buildPart1();
    public void buildPart2();
    public Product retrieveResult();
}