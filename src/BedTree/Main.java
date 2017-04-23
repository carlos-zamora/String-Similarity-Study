package BedTree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		/*
		String [] imdb_data = getDataSet("IMDB_dataset.csv", 100);
		
		for(int i=0; i<imdb_data.length; i++){
			System.out.println(imdb_data[i]);
		}
		*/
		
		int x = 1000000;
		String arr[] = new String[x];
		String s = "misspell";
		int [] data = new int[6];
		
		for(int i=0; i<x; i++)
			arr[i] = Data.misspell("misspell");
		
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
}
