package main;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
	public static int[] distance = new int[Data.AmountNodes];
	public static boolean[] visited = new boolean[Data.AmountNodes];
    private List<Integer> shortestPath = new LinkedList<Integer>();
	public static PriorityQueue<Integer> unvisited;

	public static int algo(int start, int goal) {
		initialize(start);
		int newDistance;
		int minNode = 0;
		while (distance != null) {
			for (int i = 0; i <= distance.length; i++)
				unvisited.offer(Data.weight[i]);
			// if (vom startknoten target = minDis.poll())
			// do: return minNode & delete minNode aus allNodes
		}
		distance();
		return minNode;
	}

	public static void initialize(int start) {
		unvisited = new PriorityQueue<Integer>();
		for (int i = 0; i < Data.source.length; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		distance[start] = 0;
	}

	public static void distance() {
		// newDistance = abstand von minNode + abstand zwischen startNode und minNode
		// if newDistance < weight startNode
		// weight[startNode] = newDistance;
		// preNode [starNode] = minNode
	}

	private static int getMinPathNode(int node) {
		/**
		 * method to return minimum node connected to current do not delete it, might be
		 * useful
		 */
		int min = Integer.MAX_VALUE;
		int index = 0;
		for (int j = Data.OffsetTable[node]; j < Data.OffsetTable[node + 1]; j++) {
			if (Data.weight[j] < min) {
				min = Data.weight[j];
				index = j;
			}
		}
		return Data.target[index];
	}
}
