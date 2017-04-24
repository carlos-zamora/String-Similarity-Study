package Experiment;

import java.util.LinkedList;
import java.util.Random;

import BedTree.*;
import TrieSearch.EditDistanceQuery;

public class RunExperiment{
	
	public static void main(String args[]){
		String q = "Jeff Goldblum";
		String [] data_set_1 = createDataSet(700, 10, q);
		
		System.out.println(testAccuracy_BedTree(data_set_1, q));
		
		/*
		float timeSum = 0;
		for(int i = 0; i < 100; i++)
			timeSum += testEfficiency_BedTree(data_set_1, q);
		
		timeSum /= (float)1000000000;
		*/
		
		//System.out.println(timeSum + "s");
		
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
		
		System.out.println(tree);
		
		LinkedList<String> results = tree.RangeQuery(q, tree.getRoot(), 5, "A", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		return results.size();
	}
	
	//public static int testAccuracy_Qgram(String [] data_set, String q){}
	public static long testAccuracy_TrieSearch(String[] q){
		return (EditDistanceQuery.test(5, q)).size();
	}
	
	
	public static long testEfficiency_BedTree(String [] data_set, String q){
		
		long start_time = System.nanoTime();
		
		int order = (int) (Math.sqrt(data_set.length)) + 1;
		Bed_tree tree = new Bed_tree(order, data_set);
		
		LinkedList<String> results = tree.RangeQuery(q, tree.getRoot(), 5, "A", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		long end_time = System.nanoTime();
		
		return end_time - start_time;
	}
	
	//public static int testEfficiency_Qgram(String [] data_set, String q){}
	public static long testEfficiency_TrieSearch(String[] q){	
		long start_time = System.nanoTime();
		
		EditDistanceQuery.test(2, q);
		
		long end_time = System.nanoTime();
		
		return end_time - start_time;
	}
}