package main;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
	private static int[] distance;
	private static boolean[] visited;
	private static int[] parent;
	private static PriorityQueue<Tuple> unvisited;

	/**
	 * this class is needed for PriorityQueue: with comparable we are able to sort
	 * the nodes by weight
	 */
	static class Tuple implements Comparable<Tuple> {
		int weight;
		int node;

		public Tuple(int node, int weight) {
			this.weight = weight;
			this.node = node;
		}

		public String toString() {
			return "[" + weight + ", " + node + "]";
		}

		public int getNode() {
			return node;
		}

		@Override
		public int compareTo(Tuple tup) {
			return this.weight - tup.weight;
		}
	}

	public static List<Integer> findWay(int start, int goal) {
		/**
		 * find way from start to goal using Dijkstra
		 */
		if (start >= Data.AmountNodes || goal >= Data.AmountNodes) {
			System.out.println(("Node not found"));
			return new LinkedList<Integer>();
		}
		initialize(start);
		int current;
		int[] neighbors;
		while (!unvisited.isEmpty()) {
			current = unvisited.remove().getNode();
			if (current == goal) {
				System.out.println("Distance: " + distance[goal]);
				return reconstructPath(parent, start, goal);
			}
			neighbors = getNeighbors(current);
			// System.out.println("Array: " + Arrays.toString(neighbors));
			for (int h = 0; neighbors.length > h; h++) {
				int i = neighbors[h];
				int j = Data.OffsetTable[current] + h;
				// System.out.println("source: " + Data.source[j] + " target: " + Data.target[j]
				// + " weight: " + Data.weight[j]);
				if (!visited[i]) {
					int totalDistance = distance[current] + Data.weight[j];
					if (totalDistance < distance[i]) {
						distance[i] = totalDistance;
						visited[i] = true;
						parent[i] = current;
						unvisited.add(new Tuple(i, totalDistance));
					}
				}
			}
		}
		System.out.println("No Path found");
		return new LinkedList<Integer>();
	}

	private static void initialize(int start) {
		/**
		 * initialize every distance with infinity, except the start setting to 0
		 */
		distance = Arrays.copyOf(Data.dijkstraDistances, Data.dijkstraDistances.length);
		visited = new boolean[Data.AmountNodes];
		unvisited = new PriorityQueue<Tuple>();
		parent = new int[Data.AmountNodes];
		unvisited.add(new Tuple(start, 0));
		distance[start] = 0;
	}

	private static int[] getNeighbors(int node) {
		/**
		 * return neighbors of node
		 */
		int start = Data.OffsetTable[node];
		int end = Data.OffsetTable[node + 1];
		return Arrays.copyOfRange(Data.target, start, end);
	}
	
	private static List<Integer> reconstructPath(int[] shortestPath, int start, int goal) {
		List<Integer> result = new LinkedList<Integer>();
		int u = goal;
		while (true) {
			result.add(0, u);
			if (u == start) {
				break;
			}
			u = shortestPath[u];
		}
		return result;
	}
	
	public static void initDistanceTable() {
		Data.dijkstraDistances = new int[Data.AmountNodes];
		for (int i = 0; i < Data.dijkstraDistances.length; i++) {
			Data.dijkstraDistances[i] = Integer.MAX_VALUE;
		}
	}
}
