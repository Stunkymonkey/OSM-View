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
	private boolean validStart;

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
			System.out.println("Start not found");
			return;
		}
		this.validStart = true;
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
		// check if goal is in range
		if (goal >= Data.AmountNodes || goal < -1) {
			System.out.println("Goal not found");
			return new LinkedList<Integer>();
		}
		// check if start is defined
		else if (!validStart){
			System.out.println("Start not defined");
			return new LinkedList<Integer>();
		}
		// check if goal was already visited
		else if (goal != -1 && visited[goal]) {
			return reconstructPath(parent, start, goal);
		}
		// else do Dijkstra
		int current;
		while (!unvisited.isEmpty()) {
			current = unvisited.remove().getNode();
			visited[current] = true;
			if (current == goal) {
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
	 * reconstruct the edges of the shortest path 
	 * @param shortestPath
	 * @param start
	 * @param goal
	 * @return
	 */
	private List<Integer> reconstructPath(int[] shortestPath, int start, int goal) {
		List<Integer> result = new LinkedList<Integer>();
		int node = goal;
		int edge = Data.source[goal];
		while (node != start) {
			edge = shortestPath[node];
			node = Data.source[edge];
			result.add(0, edge);
		}
		return result;
	}
	
	/**
	 * convert edges to nodes
	 * @param edges
	 * @return
	 */
	public static List<Integer> edgesToNodes(List<Integer> edges) {
		if (edges.size() == 0) {
			return new LinkedList<Integer>();
		}
		List<Integer> result = new LinkedList<Integer>();
		result.add(Data.source[edges.get(0)]);
		for (Integer i : edges) {
			result.add(Data.target[i]);
		}
		return result;
	}
	
	/**
	 * get distance if goal was visited
	 * @param edges
	 * @return
	 */
	public int getDistance(int goal) {
		if (goal <= Data.AmountNodes && goal >= 0 && visited[goal]) {
			return distance[goal];
		}
		System.out.println("Not visited yet");
		return Integer.MAX_VALUE;
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
