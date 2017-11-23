package main;

import java.util.PriorityQueue;

public class Dijkstra {
	
	public static int preNode;
	public static int startNode = 0;
	public static int[] allNodes = new int[Data.AmountNodes];
	public static int newDistance;
	public static int minNode;
	public static PriorityQueue<Integer> minDis;
	
	public static int algo() {
		initialize();
		   while (allNodes != null) {
			   	minDis = new PriorityQueue<Integer>();
			   	for (int i = 0; i <= allNodes.length;i++)
			   		minDis.offer(Data.weight[i]);
			   	// if (vom startknoten target = minDis.poll())
			   	// do: return minNode & delete minNode aus allNodes
			   		
		   }
		distance();
		return minNode;
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
