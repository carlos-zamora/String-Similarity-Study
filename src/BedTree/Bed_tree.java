package BedTree;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class Bed_tree {
	private static Node tree;
	private static int degree;

	public Bed_tree(int x){
		degree = x;
		tree = new LeafNode(degree);
	}
	
	public Bed_tree(int x, String[] data_set){
		degree = x;
		tree = new LeafNode(degree);
		for(String s: data_set)
			insert(s);
	}

	public void insert(String x){
		Data dnode = new Data(x);
		insertIntoTree(dnode);
	}

	private static void insertIntoTree(Data dnode){
		tree = tree.insert(dnode);
	}

	public static boolean searchTree(String x){
		if(tree.search(new Data(x)))
			return true;
		return false;
	}
	
	public Node getRoot(){
		return tree;
	}

	public LinkedList<String> RangeQuery(String q, Node N, int threshold, String min, String max){

		LinkedList <String> result = new LinkedList<String>();

		if(N.isLeafNode()){
			for(Data data: N.data){
				if(verifyED(q, data.getData(), threshold))
					result.add(data.getData());
			}
		}

		else{
			String s1 = N.getDataAt(0).getData();
			if(lower_bound(q, min, s1) <= threshold)
				result.addAll(RangeQuery(q, ((TreeNode) N).getPointerAt(0), threshold, min, s1));

			for(int j=1; j<N.size(); j++){
				String sj1 = N.getDataAt(j-1).getData();
				String sj2 = N.getDataAt(j).getData();

				if(lower_bound(q, sj1, sj2) <= threshold)
					result.addAll(RangeQuery(q, ((TreeNode) N).getPointerAt(j), threshold, sj1, sj2));
			}

			String s_m = N.getDataAt(N.size()).getData();
			if(lower_bound(q, s_m, max) <= threshold)
				result.addAll(RangeQuery(q, ((TreeNode) N).getPointerAt(N.size()), threshold, s_m, max));
		}

		return result;
	}

	//transforms the tree into a level-order traversal of the tree
	public String toString(){
		LinkedList<Node> nodeList = new LinkedList<Node>();
		String result = "";

		nodeList.add(tree);

		boolean done = false;
		while(!done){

			LinkedList<Node> nextLevelList = new LinkedList<Node>();
			String toprint = "";

			for(int i=0; i<nodeList.size(); i++){
				Node node = nodeList.get(i);

				if(i == nodeList.size()-1)
					toprint += "(" + node.toString() + ")";
				else
					toprint += "(" + node.toString() + ") ";

				if(node.isLeafNode())
					done = true;
				else{
					for(int j=0; j<node.size()+1; j++)
						nextLevelList.add(((TreeNode)node).getPointerAt(j));
				}
			}
			result += toprint + '\n';
			nodeList = nextLevelList;
		}
		return result;
	}

	public boolean verifyED(String s1, String s2, int threshold){
		if(Math.abs(s1.length()-s2.length()) > threshold)
			return false;
		
		int ED = calculateED(s1, s2);
		
		if(ED <= threshold)
			return true;
		return false;
	}

	public int calculateED(String s1, String s2){
		//Construct a table T of 2 rows and |s2|+1 columns
		int [][] table = new int[s1.length()+1][s2.length()+1];

		for(int i=0; i<s1.length()+1; i++){
			for(int j=0; j<s2.length()+1; j++){
				if(i == 0)
					table[0][j] = j;
				else if(j==0)
					table[i][0] = i;
				else
					table[i][j] = 0;
			}
		}

		for(int i=1; i<s1.length()+1; i++){
			for(int j=1; j<s2.length()+1; j++){

				int d1 = table[i-1][j-1];
				if(s1.charAt(i-1) != s2.charAt(j-1))
					d1 += 1;

				int d2 = table[i-1][j] + 1;
				int d3 = table[i][j-1] + 1;

				table[i][j] = min(d1,min(d2,d3));
			}
		}
		
		return table[s1.length()][s2.length()];
	} 

	private static int min(int val1, int val2){
		if(val1 <= val2)
			return val1;
		return val2;
	}


	private int lower_bound(String q, String min, String max){
		String LCP = LCP(min,max);
		int ED = calculateED(q,LCP);
		
		return ED;
	}

	private String LCP(String s1, String s2){
		int bound = min(s1.length(), s2.length());
		for(int i=0; i<bound; i++){
			if(s1.charAt(i) != s2.charAt(i))
				return s1.substring(0, i);
		}
		if(s1.length() < s2.length())
			return s1;
		return s2;
	}


	private void print(int[][] t){
		for(int i=0; i<t.length; i++){
			for(int j=0; j<t[i].length; j++)
				System.out.print(t[i][j] + " ");
			System.out.println();
		}
	}
}
