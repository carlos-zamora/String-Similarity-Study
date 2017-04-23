package TrieSearch;

public class NodeTuple {

	public int i;
	public TrieNode n;
	
	public NodeTuple(TrieNode n, int i) {
		this.i = i;
		this.n = n;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeTuple other = (NodeTuple) obj;
		if (i != other.i)
			return false;
		if (n == null) {
			if (other.n != null)
				return false;
		} else if (!n.equals(other.n))
			return false;
		return true;
	}
}
