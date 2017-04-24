package TrieSearch;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import BedTree.Data;

public class EditDistanceQuery {
	
	//returns a list of strings in t that are within edit distance e of s
	public static List<String> findWithinEditDistance(TrieTree t, String s, int e) {
		List<String> ret = new LinkedList<String>();
		HashSet<NodeTuple> h = new HashSet<NodeTuple>();

		//add the base root node
		h.add(new NodeTuple(t.root, -1));
		
		//find reachable through insertions/deletions/substitutions, then find matches again
		HashSet<NodeTuple> temp = FindMatches(s, h, ret);
		h.addAll(temp);
		int x = 0;
		while (x < e) {
			temp = Extension(temp, h);
			temp = FindMatches(s, temp, ret);
			h.addAll(temp);
			x++;
		}
		return ret;
	}
	
	//finds all matches between the given substrings in h and their trie nodes
	public static HashSet<NodeTuple> FindMatches(String s, HashSet<NodeTuple> h, List<String> list) {
		HashSet<NodeTuple> tempH = new HashSet<NodeTuple>(h);
		for(NodeTuple e : h) {
			tempH.addAll(FindMatch(s, e.n, e.i+1, list));
		}
		return tempH;
	}
	
	//returns a hashtable of the substring matches between s at i and the given trie node
	public static HashSet<NodeTuple> FindMatch(String s, TrieNode n, Integer i, List<String> list) {
		//if its a leaf and at the end of the given string, add it to the list
		if(n.isLeaf && i >= s.length() - 1) {
			if(!list.contains(n.getString()))
				list.add(n.getString());
		}
		
		HashSet<NodeTuple> h = new HashSet<NodeTuple>();
		TrieNode x = n;
		int temp = i;
		//loops through every character as long as theres still matches
		while(temp < s.length() && x.children.containsKey(s.charAt(temp))) {
			x = x.children.get(s.charAt(temp));
			h.add(new NodeTuple(x, temp));
			//as above, add it to the list
			if(x.isLeaf && temp >= s.length() - 1) {
				if(!list.contains(x.getString()))
					list.add(x.getString());
			}
			temp++;
		}
		return h;
	}
	
	//adds values to the hashset according to insertion, substitution, and deletion
	public static HashSet<NodeTuple> Extension(HashSet<NodeTuple> h, HashSet<NodeTuple> checker) {
		HashSet<NodeTuple> ret = new HashSet<NodeTuple>();
		
		for(NodeTuple n : h) {
			for(TrieNode x : n.n.children.values()) {
				if(!checker.contains(new NodeTuple(x, n.i+2)))
					ret.add(new NodeTuple(x, n.i+1));
				if(!checker.contains(new NodeTuple(x, n.i+1)))
					ret.add(new NodeTuple(x, n.i));
			}
			if(!checker.contains(new NodeTuple(n.n, n.i+2)))
				ret.add(new NodeTuple(n.n, n.i+1));
		}
		
		return ret;
	}
	
    public static void main(String[] args) {
    	TrieTree t = new TrieTree();
    	String[] s = Data.getDataSet("IMDB_dataset.csv", 397);
    	t.addStrings(s);
    	
    	int numIters = 100;
    	int editDistance = 3;
    	for(int i = 0; i < numIters; i++) {
    		String data = Data.misspell(s[(int)(Math.random() * s.length)]);
	    	List<String> list = findWithinEditDistance(t, data, editDistance);
			for(String e : list) {
				System.out.println(e);
			}
			System.out.println("End Iteration " + i + "\n");
    	}
    }
}
