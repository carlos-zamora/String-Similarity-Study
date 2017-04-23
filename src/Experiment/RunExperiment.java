package Experiment;

import java.util.LinkedList;
import java.util.Random;

import BedTree.*;

public class RunExperiment{
	
	public static void main(String args[]){
		String q = "Jeff Goldblum";
		String [] data_set_1 = createDataSet(100, 10, q);
		
		System.out.println(testAccuracy_BedTree(data_set_1, q));
		
	}
	
	public static String[] createDataSet(int size, int numOfMisspellings, String q){
		String[] s = Data.getDataSet("IMDB_dataset.csv", size);
		
		int [] index = new Random().ints(0,size).distinct().limit(numOfMisspellings).toArray();
		
		for(int i=0; i<index.length; i++)
			s[index[i]] = Data.misspell(q);
		return s;
	}
	
	public static int testAccuracy_BedTree(String [] data_set, String q){
		int order = (int) (Math.sqrt(data_set.length)) + 1;
		Bed_tree tree = new Bed_tree(order, data_set);
		
		LinkedList<String> results = tree.RangeQuery(q, tree.getRoot(), 5, "A", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		return results.size();
	}
	
	//public static int testAccuracy_Qgram(String [] data_set){}
	//public static int testAccuracy_TrieSearch(String [] data_set){}
	
}