package TrieSearch;

import java.util.Hashtable;

public class TrieNode {
	
	public TrieNode parent;
	public Character c;
	public boolean isLeaf;
	public Hashtable<Character, TrieNode> children;

	public TrieNode(TrieNode parent) {
		this.parent = parent;
		children = new Hashtable<Character, TrieNode>();
	}
	
	public void addString(String s) {
		if(s.equals("")) {
			this.isLeaf = true;
			return;
		}
		Character x = s.charAt(0);
		if(children.containsKey(x)) {
			children.get(x).addString(s.substring(1));
		}
		else {
			TrieNode n = new TrieNode(this);
			n.c = x;
			children.put(x, n);
			n.addString(s.substring(1));
		}
	}
	
	public String getString() {
		return parent == null ? "" : parent.getString() + c;
	}

	public boolean contains(String s) {
		if(s.equals("")) {
			return true;
		}
		Character x = s.charAt(0);
		if(!children.containsKey(x)) {
			return false;
		}
		else {
			return children.get(x).contains(s.substring(1));
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		return result;
	}
}
