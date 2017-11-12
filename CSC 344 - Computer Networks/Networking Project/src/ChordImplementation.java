import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ChordImplementation {
	
	private static int nodeSpace;

	public static void main(String[] args) {
		
		System.out.println("Welcome to my small faux Chord implementation.\n");
		System.out.println("The value B can be defined as the circular ID space where 2^B node spaces "
						 + "are possible and 5 <= B <= 10.");

		// get the B value
		int b = 5;
		while (true) {
			System.out.print("Please enter a value for B: ");
			try {
				Scanner s = new Scanner(System.in);
				b = s.nextInt();
				if (b >= 5 && b <= 10) {
					break;
				} else {
					System.out.print("That number is not in the valid range. ");
				}
			} catch (InputMismatchException e) {
				System.out.print("That was not a valid entry. ");
			}
		}
		
		System.out.println("The value N can be defined as the number of nodes involved in this Chord session "
						 + "where 5 <= N <= 15.");
		
		// get the N value
		int n = 5;
		while (true) {
			System.out.print("Please enter a value for N: ");
			try {
				Scanner s = new Scanner(System.in);
				n = s.nextInt();
				if (n >= 5 && n <= 15) {
					s.close();
					break;
				} else {
					System.out.print("That number is not in the valid range. ");
					s.close();
				}
			} catch (InputMismatchException e) {
				System.out.print("That was not a valid entry. ");
			}
		}
		
		nodeSpace = (int) Math.pow(2, b);
		
		// create an array the size of the node space
		int[] nodeIds = new int[nodeSpace];
		
		// fill the array with an impossible node value
		for (int c=0; c<nodeSpace; c++) {
			nodeIds[c] = nodeSpace;
		}
		
		// generate random node values and add them to the array using bucket sort
		for (int x=0; x<n; x++) {
			int randomNode = (int) (Math.random() * nodeSpace);
			
			if (nodeIds[randomNode] == randomNode) {
				// if the node id has already been randomly generated, generate a new one
				x--;
			} else {
				nodeIds[randomNode] = randomNode;
			}
		}
		
		// generate a random key value to search for
		int randomKey = (int) (Math.random() * nodeSpace);
		
		System.out.println("\nLookup key:\tK" + randomKey);
		
		// set the starting node to the second lowest node
		int nStart = 0;
		int a = 0;
		for (int node: nodeIds) {
			if (node != nodeSpace) {
				a++;
			}
			if (a == 2) {
				nStart = node;
				break;
			}
		}
		
		// pull the node ids out into a sorted array
		int[] nodeIdArray = new int[n];
		int nodeIdIndex = 0;
		for (int node: nodeIds) {
			if (node != nodeSpace) {
				nodeIdArray[nodeIdIndex] = node;
				nodeIdIndex++;
			}
		}
		
		// create all the node objects out of the node id array
		Node[] nodeList = new Node[n];
		nodeIdIndex = 0;
		for (int nodeId: nodeIdArray) {
			
			// create a new node with the node id
			Node node = new Node(nodeId);
			nodeList[nodeIdIndex] = node;

			// if first node, set predecessor to last node
			if (nodeIdIndex == 0) {
				node.setPredecessor(nodeIdArray[nodeIdArray.length-1]);
			} else {
				node.setPredecessor(nodeIdArray[nodeIdIndex-1]);
			}
			
			// if last node, set successor to first node
			if (nodeIdIndex == nodeIdArray.length-1) {
				node.setSuccessor(nodeIdArray[0]);
			} else {
				node.setSuccessor(nodeIdArray[nodeIdIndex+1]);
			}
			
			nodeIdIndex++;
		}
		
		System.out.println("Starting node:\tN" + nStart);
		
		/*
		// print nodes and key location if necessary
		for (Node node: nodeList) {
			System.out.println(node + ", " + node.hasKey(randomKey));
		}
		*/
		
		// generate the finger tables for each node
		for (Node node: nodeList) {
			int[][] fingerTable = new int[b][2];
			
			// for each entry in finger table
			for (int x=0; x<b; x++) {
				
				// node id plus a power of two
				int lookup = node.getId() + (int) Math.pow(2, x);
				fingerTable[x][0] = lookup;
				
				// if lookup value is greater than the node space,
				// wrap back around to the front of the node space
				if (lookup > nodeSpace - 1) {
					lookup = lookup - nodeSpace;
				}
				
				// search the node ids for the next highest node
				for (int y=lookup; y<=nodeSpace; y++) {
					if (y == nodeSpace) {
						y=0;
					}
					
					if (nodeIds[y] != nodeSpace) {
						fingerTable[x][1] = nodeIds[y];
						break;
					}
				}
			}
			
			node.setFingerTable(fingerTable);	
		}
		
		/*
		// print all finger tables
		for (Node node: nodeList) {
			node.printFingerTable();
		}
		*/
		
		ArrayList<Node> visitedNodes = new ArrayList<Node>();
		
		// add the starting node to the visited nodes list
		Node currentNode = nodeList[1];
		visitedNodes.add(currentNode);
		
		// hop from node to node until the key is found
		while (!currentNode.hasKey(randomKey)) {
			int successor = currentNode.getSuccessor();
			Node nextNode = currentNode;
			
			// set the successor as the next node
			for (Node node: nodeList) {
				if (node.getId() == successor) {
					nextNode = node;
				}
			}
			
			// check if they successor has the key
			if (nextNode.hasKey(randomKey)) {
				currentNode = nextNode;
			} else {
				
				// if not, look up next hop
				int nextNodeId = currentNode.lookup(randomKey);
				for (Node node: nodeList) {
					if (node.getId() == nextNodeId) {
						currentNode = node;
					}
				}
			}
			
			// add next hop to visited nodes list
			visitedNodes.add(currentNode);
		}
		
		// print the visiting order
		System.out.println("\nVisited Nodes:");
		for (Node node: visitedNodes) {
			System.out.println(node);
		}
		
		// print the visited nodes' finger tables
		System.out.println();
		for (Node node: visitedNodes) {
			node.printFingerTable();
		}

	}

}
