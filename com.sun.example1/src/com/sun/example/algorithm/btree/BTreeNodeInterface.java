package com.sun.example.algorithm.btree;

public interface BTreeNodeInterface {

	final public int threshold = 5;
	
	//����ڵ�,�����Ҫ���ѣ��򷵻�true�����򷵻�false
	public boolean insert(String key);
	
	//ɾ���ڵ�,�����Ҫ�ϲ����򷵻�true�����򷵻�false
	public boolean delete();
	
	//���ҽڵ㣬�ҵ��󷵻�true
	public boolean search(BTreeNodeInterface bTreeNode);
	
	
}
