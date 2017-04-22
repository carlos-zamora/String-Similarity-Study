package TrieSearch;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class EditDistanceQuery {
	
	public static List<String> findWithinEditDistance(TrieTree t, String s, int e) {
		List<String> ret = new LinkedList<String>();
		HashSet<NodeTuple> h = new HashSet<NodeTuple>();

		h.add(new NodeTuple(t.root, 0));
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
			tempH.addAll(FindMatch(s, e.n, e.i, list));
		}
		return tempH;
	}
	
	//returns a hashtable of the substring matches between s at i and the given trie node
	public static HashSet<NodeTuple> FindMatch(String s, TrieNode n, Integer i, List<String> list) {
		if(n.isLeaf && i == s.length() - 1) {
			if(!list.contains(n.getString()))
				list.add(n.getString());
		}
		
		HashSet<NodeTuple> h = new HashSet<NodeTuple>();
		TrieNode x = n;
		int temp = i;
		while(temp < s.length() && x.children.containsKey(s.charAt(temp))) {
			for(Entry<Character, TrieNode> e : x.children.entrySet()) {
				if(e.getKey().equals(s.charAt(temp))) {
					h.add(new NodeTuple(e.getValue(), temp));
					x = e.getValue();
					if(x.isLeaf && temp == s.length() - 1) {
						if(!list.contains(x.getString()))
							list.add(x.getString());
					}
					temp++;
					break;
				}
			}
		}
		return h;
	}
	
	public static HashSet<NodeTuple> Extension(HashSet<NodeTuple> h, HashSet<NodeTuple> checker) {
		HashSet<NodeTuple> ret = new HashSet<NodeTuple>();
		
		for(NodeTuple n : h) {
			for(TrieNode x : n.n.children.values()) {
				if(!checker.contains(new NodeTuple(x, n.i+1)))
					ret.add(new NodeTuple(x, n.i+1));
				if(!checker.contains(new NodeTuple(x, n.i)))
					ret.add(new NodeTuple(x, n.i));
			}
			if(!checker.contains(new NodeTuple(n.n, n.i+1)))
				ret.add(new NodeTuple(n.n, n.i+1));
		}
		
		return ret;
	}
	
    public static void main(String[] args) {
    	TrieTree t = new TrieTree();
    	String[] s = {"cat", "bar", "car", "far", "lark", "people", "luke", "run", "qwerqwer", "cattle"};
    	t.addStrings(s);
    	
    	List<String> list = findWithinEditDistance(t, "pillow", 5);
		for(String e : list) {
			System.out.println(e);
		}
    }
}
