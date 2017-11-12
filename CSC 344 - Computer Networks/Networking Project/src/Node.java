
/**
 * This is a class that represents a node that is participating in Chord peer-to-peer look-up. 
 * @author Phil Smith
 *
 */
public class Node {
	private int successor, predecessor, id;
	private int[][] fingerTable;
	
	/**
	 * This constructor creates a new node given a node id.
	 * @param nodeId The id of the node.
	 */
	public Node(int nodeId) {
		this.id = nodeId;
	}

	/**
	 * Check if this node has the key id in question. This can only be done
	 * if the successor and predecessor have been set.
	 * @param keyID The id of the key.
	 * @return True if this node has the key - otherwise, false.
	 */
	public boolean hasKey(int keyID) {
		// the normal behavior if this node is not the first node
		if (predecessor < id) {
			if (keyID > predecessor && keyID <= id) {
				return true;
			}
		}
		
		// modified behavior in case this node is the first node
		if (predecessor > id) {
			if ((keyID >= 0 && keyID <= id) || (keyID > predecessor)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Look up the next hop.
	 * @param keyID The key id that Chord should look for.
	 * @return The id of the node that is the next hop. 
	 */
	public int lookup(int keyID) {
		int nextNodeId = fingerTable[fingerTable.length-1][1];
		int leastDifference = Math.abs(keyID - nextNodeId);
		for (int[] entry: fingerTable) {
			int difference = (keyID - entry[1]);
			if (entry[1] <= keyID && (difference < leastDifference && difference > 0)) {
				leastDifference = difference;
				nextNodeId = entry[1];
			}
		}
		
		return nextNodeId;
	}
	
	/**
	 * Get the id of this node.
	 * @return This node's id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the successor of this node.
	 * @return The id of this node's successor.
	 */
	public int getSuccessor() {
		return successor;
	}
	
	/**
	 * Set the predecessor of this node.
	 * @param predecessor The id of this node's predecessor.
	 */
	public void setPredecessor(int predecessor) {
		this.predecessor = predecessor;
	}
	
	/**
	 * Set the successor of this node. 
	 * @param successor The id of this node's successor. 
	 */
	public void setSuccessor(int successor) {
		this.successor = successor;
	}
	
	/**
	 * Set the finger table of this node.
	 * @param fingerTable A 2D array that represents this node's finger table.
	 */
	public void setFingerTable(int[][] fingerTable) {
		this.fingerTable = fingerTable;
	}
	
	/**
	 * Print this node's finger table.
	 */
	public void printFingerTable() {
		System.out.println("N" + id + " Finger");
		
		for (int[] entry: fingerTable) {
			System.out.println(entry[0] + " | " + entry[1]);
		}
		
		System.out.println();
	}
	
	/**
	 * Return a textual representation of this node including its id, predecessor, and successor.
	 */
	@Override
	public String toString() {
		return "N" + id + ": " + "predecessor - " + predecessor + ", successor - " + successor;
	}

}
