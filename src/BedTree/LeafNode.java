package BedTree;

public class LeafNode extends Node{
	private LeafNode nextNode;
	
	LeafNode(int maxsize){
		super(maxsize);
		
		nextNode = null;
	}

	private void setNextNode(LeafNode next){
		nextNode = next;
	}
	
	protected LeafNode getNextNode(){
		return nextNode;
	}

	public boolean search(Data x){
		for(Data node : data){
			
			String saved_string = node.getData();
			String searched_string = x.getData();
			
			if(saved_string.equals(searched_string))
				return true;
		}
		return false;
	}

	protected void split(Data dnode){
		
		// insert dnode into vector (it will experience overflow)
		boolean dnode_inserted = false;
		
		for(int i=0; !dnode_inserted && i<data.size(); i++){
			Data current_node = data.get(i);
			
			if(current_node.inOrder(dnode)){
				data.add(i, dnode);
				dnode_inserted = true;
			}
		}
		
		if(!dnode_inserted)
			data.add(data.size(), dnode);
		
		
		// calculate split point
		int split_location;
		if(maxsize%2==0)
			split_location = maxsize/2;
		else
			split_location = (maxsize+1)/2;
		
		
		// create new LeafNode
		LeafNode right = new LeafNode(maxsize);
		
		for(int i = data.size()-split_location; i>0; i--)
			right.data.add(data.remove(split_location));
		
		
		//link the two together
		right.setNextNode(this.getNextNode());
		this.setNextNode(right);
		
		//get the middle item's data
		Data mid = data.get(data.size()-1);
		
		//propogate the data and pointers into the parent node
		this.propogate(mid, right);
		
	}
	
	public Node insert(Data dnode){
		
		if(data.size() < maxsize-1){
			for(int i=0; i<data.size(); i++){
				Data current_node = data.get(i);
				if(current_node.inOrder(dnode)){
					data.add(i,dnode);
					return findRoot();
				}
			}
			data.add(dnode);
		}
		
		//if the leaf is full, split
		else
			split(dnode);
		
		return findRoot();
	}
}
