package com.sun.example.algorithm.btree;

public interface BTreeNodeInterface {

	final public int threshold = 5;
	
	//插入节点,如果需要分裂，则返回true，否则返回false
	public boolean insert(String key);
	
	//删除节点,如果需要合并，则返回true，否则返回false
	public boolean delete();
	
	//查找节点，找到后返回true
	public boolean search(BTreeNodeInterface bTreeNode);
	
	
}
