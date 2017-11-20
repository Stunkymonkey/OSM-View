package main;

public class Dijkstra {
	
	public static int preNode;
	public static int startNode = 0;
	public static int[] allNodes = new int[Data.AmountNodes];
	public static int newDistance;
	public static int minNode;
	
	public static int algo() {
		initialize();
		 //while (allNodes != null) {
			// minNode = node mit kleinstem abstand vom startknoten aus
			//delete minNode aus allNodes
			//if (Data.target = Data.source[minNode]) {
				distance();
			//}
		//}
		return preNode;
	}
	
	
	public static void initialize () {
		for (int i=0; i < Data.source.length; i++) {
			Data.weight[i] = Integer.MAX_VALUE;
			Data.target[i] = 0;
		}
		Data.weight[startNode] = 0;
	}
	
	public static void distance () {
		 // newDistance = abstand von minNode + abstand zwischen startNode und minNode
		 // if newDistance < weight startNode
		 // weight[startNode] = newDistance;
		 // preNode [starNode] = minNode
	}
}
