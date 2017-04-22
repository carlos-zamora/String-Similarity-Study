import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		/*
		String [][] imdb_data = getDataSet("IMDB_dataset.csv", 100);
		
		for(int i=0; i<imdb_data.length; i++){
			System.out.println(imdb_data[i][0]);
		}
		*/
		
		int x = 1000000;
		String arr[] = new String[x];
		String s = "misspell";
		int [] data = new int[6];
		
		for(int i=0; i<x; i++)
			arr[i] = misspell("misspell");
		
		int count = 0;
		for(int i=0; i<x; i++){
			for(int j=0; j<arr[i].length(); j++){
				if(arr[i].charAt(j) != s.charAt(j))
					count++;
			}
			data[count]++;
			count=0;
		}
		
		for(int i=0; i<data.length; i++)
			System.out.println(i + ": " + data[i]);
		
		/*
		Bed_tree tree = new Bed_tree(4);
		
		//boolean b = tree.verifyED("Jim Grey", "Jim Grey", 0);
		
		//System.out.println(b);
		
		
		tree.insert("a");
		tree.insert("b");
		tree.insert("c");
		tree.insert("d");
		tree.insert("e");
		tree.insert("f");
		tree.insert("g");
		tree.insert("h");
		tree.insert("i");
		tree.insert("j");
		tree.insert("k");
		System.out.print(tree);
		*/
	}
	
	
	private static String [][] getDataSet(String fileName, int size){
		BufferedReader reader = null;
		String [][] data_set = new String[size][3];
		
		try {
			reader = new BufferedReader(new FileReader(fileName));
			
			String line = reader.readLine();
			int index = 0;
			
			while(line != null && index < size){
				
				String [] line_split = line.split(",");
				
				for(int i=0; i<line_split.length; i++)
					data_set[index][i] = line_split[i];
				
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


	private static String misspell(String q){
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
	private static char randomLetter(){
		//char A-Z
		int r1 = (int) (Math.random()*26+65);
		
		//char a-z
		int r2 = (int) (Math.random()*26+97);
		
		//choose one of the two
		if(Math.random() < 0.5)
			return (char) r1;
		return (char) r2;
	}

	private static String convertToString(char [] arr){
		String s = "";
		for(int i=0; i<arr.length; i++)
			s+=arr[i];
		return s;
	}
}
