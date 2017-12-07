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
		this.distance = Arrays.copyOf(Data.dijkstraDistances, Data.dijkstraDistances.length);
		this.visited = new boolean[Data.AmountNodes];
		this.unvisited = new PriorityQueue<Tuple>();
		this.parent = new int[Data.AmountNodes];

		this.unvisited.add(new Tuple(start, 0));
		this.distance[start] = 0;
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
			Helper.Print("Distance: " + distance[goal]);
			return reconstructPath(parent, start, goal);
		}
		int current;
		while (!unvisited.isEmpty()) {
			current = unvisited.remove().getNode();
			visited[current] = true;
			if (current == goal) {
				Helper.Print("Distance: " + distance[goal]);
				return reconstructPath(parent, start, goal);
			}
			for (int i = Data.OffsetTable[current]; Data.OffsetTable[current + 1] > i; i++) {
				int target = Data.target[i];
				if (!visited[target]) {
					int totalDistance = distance[current] + Data.weight[i];
					if (totalDistance < distance[target]) {
						distance[target] = totalDistance;
						parent[target] = i;
						unvisited.add(new Tuple(target, totalDistance));
					}
				}
			}
		}
		return new LinkedList<Integer>();
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
			u = Data.source[shortestPath[u]];
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
