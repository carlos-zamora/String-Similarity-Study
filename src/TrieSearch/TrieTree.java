package TrieSearch;

import java.util.Collection;

public class TrieTree {
	
	public TrieNode root;
	
	public TrieTree() {
		root = new TrieNode(null);
	}
	
	public void addString(String s) {
		root.addString(s);
	}
	
	public void addStrings(Collection<String> strings) {
		for(String s : strings) {
			addString(s);
		}
	}
	
	public void addStrings(String[] strings) {
		for(String s : strings) {
			addString(s);
		}
	}
	
	public boolean contains(String s) {
		return root.contains(s);
	}
}
