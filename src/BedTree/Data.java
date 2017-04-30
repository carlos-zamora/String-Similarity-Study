package BedTree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
	
	public static String [] getDataSet(String fileName, int size){
		BufferedReader reader = null;
		String [] data_set = new String[size];
		
		try {
			reader = new BufferedReader(new FileReader(fileName));
			
			String line = reader.readLine();
			int index = 0;
			
			while(line != null && index < size){
				
				//CSV files treat entries with ',' character differently
				//Instead of the pure entry, the entry is wrapped in ""
				//this removes that from the entry
				if(line.charAt(0) == '"')
					line = line.substring(1, line.length()-1);
				
				data_set[index] = line;
				
				line = reader.readLine();
				index++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally{
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return data_set;
	}

	public static String misspell(String q){
		double r1 = Math.random();
		char[] result = q.toCharArray();
		int index = (int) (Math.random()*result.length);
		
		if(r1 < 0.15){
			result[index] = randomLetter();
		}
		if(r1 < 0.3){
			index = (int) (Math.random()*result.length);
			result[index] = randomLetter();
		}
		if(r1 < 0.45){
			index = (int) (Math.random()*result.length);
			result[index] = randomLetter();
		}
		if(r1 < 0.6){
			index = (int) (Math.random()*result.length);
			result[index] = randomLetter();
		}
		if(r1 < 0.8){
			index = (int) (Math.random()*result.length);
			result[index] = randomLetter();
		}
		
		return convertToString(result);
	}
	
	//returns a random letter (uppercase and lowercase)
	public static char randomLetter(){
		//char A-Z
		int r1 = (int) (Math.random()*26+65);
		
		//char a-z
		int r2 = (int) (Math.random()*26+97);
		
		//choose one of the two
		if(Math.random() < 0.5)
			return (char) r1;
		return (char) r2;
	}

	public static String convertToString(char [] arr){
		String s = "";
		for(int i=0; i<arr.length; i++)
			s+=arr[i];
		return s;
	}
	
}
