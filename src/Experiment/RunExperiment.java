package Experiment;

import java.util.LinkedList;
import java.util.Random;

import BedTree.*;
import Qgram.*;
import TrieSearch.EditDistanceQuery;

public class RunExperiment{

	public static void main(String args[]){
		String q = "Jeff Goldblum";
		String [] data_set_1 = createDataSet(5000, 10, q);



		float timeSum = 0;
		timeSum += testEfficiency_TrieSearch(2, data_set_1);

		timeSum /= (float)1000000000;

		System.out.println(timeSum + "s");

	}

	public static String[] createDataSet(int size, int numOfMisspellings, String q){
		String[] s = Data.getDataSet("IMDB_dataset.csv", size);

		int [] index = new Random().ints(0,size).distinct().limit(numOfMisspellings).toArray();

		for(int i=0; i<index.length; i++)
			s[index[i]] = Data.misspell(q);
		return s;
	}

	public static long testEfficiency_BedTree(String [] data_set, String q){

		long start_time = System.nanoTime();

		int order = (int) (Math.sqrt(data_set.length)) + 1;
		Bed_tree tree = new Bed_tree(order, data_set);
		
		
		LinkedList<String> results = tree.RangeQuery(q, tree.getRoot(), 5, "A", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		long end_time = System.nanoTime();

		return end_time - start_time;
	}

	public static long testEfficiency_TrieSearch(int ED, String[] q){ 
		long start_time = System.nanoTime();

		EditDistanceQuery.test(ED, q, 100);

		long end_time = System.nanoTime();

		return end_time - start_time;
	}

	public static long testEfficiency_Qgram(int ED, String[] data_set, String q) {

		long start_time = System.nanoTime();

		Qgram g = new Qgram(ED, q, data_set);

		long end_time = System.nanoTime();

		return end_time - start_time;
	}
}
