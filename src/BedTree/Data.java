package BedTree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

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

	//misspells a given string by making a random set of insertions, deletions, or substitutions
	//limited to a size of 2 for the purposes of this project
	//50% chance of 1 error and 50% chance of 2
	public static String misspell(String q){
		char[] result = q.toCharArray();

		double rng_numOfErrors = Math.random();
		double rng_typeOfError_1 = Math.random();
		double rng_typeOfError_2 = Math.random();
		int [] index = new Random().ints(0,q.length()-1).distinct().limit(2).toArray();


		//insertion
		if(rng_typeOfError_1 < 0.3)
			result = insertion(result, index[0]);

		//deletion
		else if(rng_typeOfError_1 < 0.6)
			result = deletion(result, index[0]);

		//substitution
		else
			result[index[0]] = randomLetter();

		
		//two errors happen
		if(rng_numOfErrors < 0.5){

			//insertion
			if(rng_typeOfError_2 < 0.3)
				result = insertion(result, index[1]);

			//deletion
			else if(rng_typeOfError_2 < 0.6)
				result = deletion(result, index[1]);

			//substitution
			else
				result[index[1]] = randomLetter();
		}

		return convertToString(result);
	}

	//inserts a random letter into the array of chars at the given index
	public static char [] insertion(char [] word, int index){
		char [] temp = new char[word.length+1];

		for(int i=0; i<word.length+1; i++){
			if(i<index)
				temp[i] = word[i];
			else if(i == index)
				temp[i] = randomLetter();
			else
				temp[i] = word[i-1];
		}
		return temp;
	}

	//deletes an element from the array of chars at the given index
	public static char [] deletion(char [] word, int index){
		char [] temp = new char[word.length-1];

		for(int i=0; i<word.length-1; i++){
			if(i<index)
				temp[i] = word[i];
			else
				temp[i] = word[i+1];
		}
		return temp;
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
