package main;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
	private int[] distance;
	private boolean[] visited;
	private int[] parent;
	private PriorityQueue<Tuple> unvisited;
	private int start;

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
	
	/**
	 * set the starting point for Dijkstra
	 * @param start
	 */
	public Dijkstra(int start) {
		if (start >= Data.AmountNodes || start < 0) {
			System.out.println(("Start not found"));
			return;
		}
		this.start = start;
		initialize(start);
	}

	/**
	 * find goal from given start using Dijkstra
	 * @param goal set it to a node-index OR set it to -1 to run it on full graph
	 * @return
	 */
	public List<Integer> findWay(int goal) {
		if (goal < -1 || goal >= Data.AmountNodes) {
			System.out.println(("Goal not found"));
			return new LinkedList<Integer>();
		} else if (goal != -1 && visited[goal]) {
			return reconstructPath(parent, start, goal);
		}
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
		return new LinkedList<Integer>();
	}

	/**
	 * initialize every distance with infinity, except the start setting to 0
	 * @param start
	 */
	private void initialize(int start) {
		distance = Arrays.copyOf(Data.dijkstraDistances, Data.dijkstraDistances.length);
		visited = new boolean[Data.AmountNodes];
		unvisited = new PriorityQueue<Tuple>();
		parent = new int[Data.AmountNodes];

		unvisited.add(new Tuple(start, 0));
		distance[start] = 0;
	}

	/**
	 * get all neighbors of node
	 * @param node
	 * @return
	 */
	private int[] getNeighbors(int node) {
		int start = Data.OffsetTable[node];
		int end = Data.OffsetTable[node + 1];
		return Arrays.copyOfRange(Data.target, start, end);
	}
	
	/**
	 * reconstruct the shortest path 
	 * @param shortestPath
	 * @param start
	 * @param goal
	 * @return
	 */
	private List<Integer> reconstructPath(int[] shortestPath, int start, int goal) {
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
	
	/**
	 * set all distances to "infinity"
	 */
	public static void initDistanceTable() {
		Data.dijkstraDistances = new int[Data.AmountNodes];
		for (int i = 0; i < Data.dijkstraDistances.length; i++) {
			Data.dijkstraDistances[i] = Integer.MAX_VALUE;
		}
	}
}
