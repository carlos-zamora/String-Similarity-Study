package Experiment;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import BedTree.*;
import Qgram.*;
import TrieSearch.EditDistanceQuery;

public class RunExperiment{

	public static void main(String args[]){

		String q = "Jeff Goldblum";
		
		List<String> lines = new ArrayList<String>();;
		Path file = Paths.get("output.txt");
		
		//4-dim experimental results
		// dim	-	type								 -	size
		//	1	-	 dataset_size (250, 500, ..., 5000)	 -	20
		//	2	-	 % of misspellings (10%, 40%, 70%)	 -	3
		//	3	-	 threshold (1,2)					 -	2
		//	4	-	 method (BedTree, TrieSearch, Qgram) -	3
		double [][][][] results = new double[20][3][2][3];


		// 2-dim data
		// dim	-	type								-	size
		//	1	-	 dataset_size (250, 500, ..., 5000)	-	20
		//	2	-	 % of misspellings (10%, 40%, 70%)	-	3
		//	3	-	 String data						-	<determined by dim 1>
		String [][][] data_set = populate(q);


		for(int i=0; i<results.length; i++){
			for(int j=0; j<results[i].length; j++){
				for(int k=0; k<results[i][j].length; k++){
					
					int ED = k+1;
					
					double BedTree_time = testEfficiency_BedTree(ED, data_set[i][j], q);
					double TrieSearch_time = testEfficiency_TrieSearch(ED, data_set[i][j]);
					double Qgram_time = testEfficiency_Qgram(ED, data_set[i][j], q);

					BedTree_time /= (double)1000000000;
					TrieSearch_time /= (double)1000000000;
					Qgram_time /= (double)1000000000;

					results[i][j][k][0] = BedTree_time;
					results[i][j][k][1] = TrieSearch_time; 
					results[i][j][k][2] = Qgram_time;
				}
			}
		}

		print(results, lines);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//fills the data_set with a variety of dataset sizes and misspellings
	public static String[][][] populate(String q){
		String [][][] data_set = new String[20][3][];

		for(int i=0; i<data_set.length; i++){
			for(int j=0; j<data_set[i].length; j++){
				int size = (i+1)*250;
				int numOfMisspellings;

				if(j==0)
					numOfMisspellings = (int) (size*0.1);
				else if(j==1)
					numOfMisspellings = (int) (size*0.4);
				else
					numOfMisspellings = (int) (size*0.7);

				data_set[i][j] = createDataSet(size, numOfMisspellings, q); 
			}
		}

		return data_set;
	}

	public static String[] createDataSet(int size, int numOfMisspellings, String q){
		String[] s = Data.getDataSet("IMDB_dataset.csv", size);

		int [] index = new Random().ints(0,size).distinct().limit(numOfMisspellings).toArray();

		for(int i=0; i<index.length; i++)
			s[index[i]] = Data.misspell(q);
		return s;
	}

	public static void print(double [][][][] results, List <String> list){


		for(int i=0; i<3; i++){
			for(int j=0; j<2; j++){
				for(int method = 0; method<3; method++){					
					
					String s = ""; 
					
					if(method == 0)
						s += "BED Tree - " + "threshold " + (j+1) + " - ";
					if(method == 1)
						s += "Trie Search - " + "threshold " + (j+1) + " - ";
					if(method == 2)
						s += "Qgram - " + "threshold " + (j+1) + " - ";
					
					
					if(i==0)
						s += "10%";
					if(i==1)
						s += "40%";
					if(i==2)
						s += "70%";
					
					list.add(s);
					
					
					for(int data_size=0; data_size<20; data_size++){
						double data = results[data_size][i][j][method];
						Double wrap = new Double(data);
						list.add(wrap.toString());
					}
				}
				list.add("");
			}
		}
	}


	public static long testEfficiency_BedTree(int ED, String [] data_set, String q){

		long start_time = System.nanoTime();

		for(int i=0; i<100; i++){
			int order = (int) (Math.sqrt(data_set.length)) + 1;
			Bed_tree tree = new Bed_tree(order, data_set);
			LinkedList<String> results = tree.RangeQuery(q, tree.getRoot(), ED, "A", "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");	
		}

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

		for(int i=0; i<100; i++){
			Qgram g = new Qgram(ED, q, data_set);
			g.outputReturn();
		}

		long end_time = System.nanoTime();

		return end_time - start_time;
	}
}
