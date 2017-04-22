
public class Data {
	private String data;
	
	public Data(){
		data = null;
	}
	
	public Data(String data){
		this.data = data;
	}
	
	
	public String toString(){
		return data.toString();
	}
	
	public String getData(){;
		return data;
	}
	
	
	// compares data to other using DictionaryOrder
	// OUTPUT: true  - data <= other
	//		   false - otherwise
	public boolean inOrder(Data other){
		return data.compareTo(other.getData()) <= 0; 
	}
	
}
