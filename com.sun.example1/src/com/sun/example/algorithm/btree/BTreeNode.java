package com.sun.example.algorithm.btree;

import java.util.ArrayList;

public class BTreeNode{

	private class BTreeObject{
		private String key;
		private BTreeNode leftNode = null;
		private BTreeNode rightNode = null;
		private String value = "";
		public String getKey() {
			return key;
		}
		
		public void setKey(String key) {
			this.key = key;
		}

		public BTreeNode getLeftNode() {
			return leftNode;
		}
		
		public void setLeftNode(BTreeNode leftNode) {
			this.leftNode = leftNode;
		}
		
		public BTreeNode getRightNode() {
			return rightNode;
		}
		
		public void setRightNode(BTreeNode rightNode) {
			this.rightNode = rightNode;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	
	}
	
	private boolean isRoot;
	private int n;
	private BTreeNode parentNode;
	private ArrayList<BTreeObject> nodes = new ArrayList<BTreeObject>();
	
	public boolean insert(String key) {
		BTreeObject obj = new BTreeObject();
		
		obj.setKey(key);
		obj.setValue(key);
		
		for(int i = 0; i<nodes.size();i++){
			if( key.compareToIgnoreCase(nodes.get(i).getKey()) < 0){
				nodes.add(i, obj);
			}
		}
		
		return false;
	}
	
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean search(BTreeNode bTreeNode) {
		return false;
	}

	public boolean isRoot(){
		if(parentNode == null){
			isRoot = true;
		}else{
			isRoot = false;
		}
		
		return isRoot;
	}
	
	public BTreeNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(BTreeNode parentNode) {
		this.parentNode = parentNode;
	}
	
}
