package BedTree;

import java.util.ArrayList;

public abstract class Node {
	protected ArrayList<Data> data;
	protected Node parent;
	protected int maxsize;
	
	abstract Node insert(Data data);
	abstract boolean search(Data x);
	
	Node(int maxsize){
		parent = null;	
		data = new ArrayList<Data>();
		this.maxsize = maxsize;
	}
	
	
	public boolean isLeafNode(){
		return this.getClass().getName().trim().equals("BedTree.LeafNode");
	}
	
	// checks if the Node is full
	protected boolean isFull() {
		return data.size() == maxsize-1;
	}
	
	//returns data at specific index
	public Data getDataAt(int index) {
		return (Data) data.get(index);
	}
	
	// returns size of Node
	public int size(){
		return data.size();
	}
	
	// returns string version of data in the following format:
	// "s1 s2 s3"
	public String toString(){
		String s = "";
		for(int i=0; i < data.size(); i++) {
			if(i==data.size()-1)
				s += ((Data) data.get(i)).toString();
			else
				s += ((Data) data.get(i)).toString() + " ";
		}
		return s;
	}

	// returns the Node that is the root of the tree
	protected Node findRoot(){
		Node node = this;

		while(node.parent != null)
			node = node.parent;

		return node;
	}

	// sets the parent of this node to newparent
	protected void setParent(Node newparent){
		parent = newparent;
	}
	
	protected void propogate(Data dnode, Node right){
		
		if(parent == null){
			TreeNode newparent = new TreeNode(maxsize);
			
			newparent.data.add(dnode);
			newparent.pointer.add(this);
			newparent.pointer.add(right);
			
			setParent(newparent);
			right.setParent(newparent);
		}
		
		else{
			if(!parent.isFull()){
				boolean dnode_inserted = false;
				for(int i=0; !dnode_inserted && i<parent.data.size(); i++){
					Data parent_data = parent.data.get(i);
					if(parent_data.inOrder(dnode)){
						parent.data.add(i, dnode);
						((TreeNode) parent).pointer.add(i+1, right);
						dnode_inserted = true;
					}
				}
				
				if(!dnode_inserted){
					parent.data.add(dnode);
					((TreeNode)parent).pointer.add(right);
				}
				
				right.setParent(parent);
			}
			
			else
				((TreeNode)parent).split(dnode, this, right);
		}
	}
}
