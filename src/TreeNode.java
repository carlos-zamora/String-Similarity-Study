import java.util.ArrayList;

public class TreeNode extends Node{
	
	protected ArrayList<Node> pointer;
	
	TreeNode(int maxsize){
		super(maxsize);
		pointer = new ArrayList<Node>();
	}
	
	// finds the correct pointer to the next lowest
	//	level of the tree where x should be found
	public Node getPointerTo(Data x){
		
		for(int i=0; i < data.size(); i++){
			
			Data val = (Data)data.get(i); 
			
			if(val.inOrder(x)){
				return (Node) pointer.get(i);
			}
		}
		
		return (Node) pointer.get(pointer.size());
	}

	public Node getPointerAt(int index){
		return (Node) pointer.get(index);
	}

	boolean search(Data data){
		Node next = getPointerTo(data);
		
		return next.search(data);
		
	}

	Node insert(Data data){
		Node next = getPointerTo(data);
		
		return next.insert(data);
	}

	
	protected void split(Data dnode, Node left, Node right){
		
		// calculate split point
		int split_location, insert_location = 0;
		if(maxsize%2==0)
			split_location = maxsize/2;
		else
			split_location = (maxsize+1)/2 - 1;
		
		
		//insert dnode into the vector (it will now have overflow)
		boolean data_inserted = false;
		for(int i=0; !data_inserted && i<data.size(); i++){
			Data current_node = data.get(i);
			
			if(current_node.inOrder(dnode)){
				data.add(i, dnode);
				pointer.remove(i);
				pointer.add(i, left);
				pointer.add(i+1, right);
				data_inserted = true;
				
				//set location of insert
				insert_location = i;
			}
			
			if(!data_inserted){
				insert_location = data.size();
				data.add(i, dnode);
				pointer.remove(data.size()-1);
				pointer.add(i, left);
				pointer.add(i+1, right);
				data_inserted = true;
			}
		}
		
		
		// get middle data
		Data mid = data.remove(split_location);
		
		//create new tree node to accomodate for split
		TreeNode newright = new TreeNode(maxsize);
		
		//populate data and pointers to new right node
		for(int i=data.size()-split_location; i>0; i--){
			newright.data.add(data.remove(split_location));
			newright.pointer.add(pointer.remove(split_location+1));
		}
		newright.pointer.add(pointer.remove(split_location+1));
		
		
		// set parents of right and left
		// if the item was inserted before the split point, both nodes are children of the left
		if(insert_location < split_location){
			left.setParent(this);
			right.setParent(this);
		}
		// if the item was inserted at the split_point
		//	 the nodes have different parents this and right
		else if(insert_location == split_location){
			left.setParent(this);
			right.setParent(newright);
		}
		// if the item was was inserted past the split_point 
		//	the nodes are children of right
		else{
			left.setParent(newright);
			right.setParent(newright);
		}
		
		//propogate the node up
		propogate(mid, newright);
	}
}
