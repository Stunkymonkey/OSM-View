package main;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
	private static int[] distance;
	private static boolean[] visited;
	private static List<Integer> parent;
	private static List<Integer> shortestPath;
	private static PriorityQueue<Tuple> unvisited;

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
		/*
		 * find way from start to goal using Dijkstra
		 */
		if (start >= Data.AmountNodes || goal >= Data.AmountNodes ) {
			System.out.println(("Node not found"));
			return shortestPath;
		}
		initialize(start);
		int current;
		int[] neighbors;
		while (!unvisited.isEmpty()) {
			current = unvisited.remove().getNode();
			if (current == goal) {
				System.out.println("Distance: " + distance[goal]);
				// TODO
				// reconstruct path from parent
				// but the printed distance is correct i think
				return shortestPath;
			}
			neighbors = getNeighbors(current);
			for (int i : neighbors) {
				if (!visited[i]) {
					int totalDistance = distance[current] + Data.weight[i];
					if(totalDistance < distance[i]){
						distance[i] = totalDistance;
						visited[i] = true;
						unvisited.add(new Tuple(i, totalDistance));
					}
				}
			}
		}
		System.out.println("No Path found");
		return shortestPath;
	}

	public static void initialize(int start) {
		/*
		 * initialize every distance with infinity, except the start setting to 0
		 */
		distance = new int[Data.AmountNodes];
		visited = new boolean[Data.AmountNodes];
		unvisited = new PriorityQueue<Tuple>();
		shortestPath = new LinkedList<Integer>();
		parent = new LinkedList<Integer>();
		for (int i = 0; i < Data.AmountNodes; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		unvisited.add(new Tuple(start, 0));
		distance[start] = 0;
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

	private static int[] getNeighbors(int node) {
		/**
		 * return neighbors of node
		 */
		int start = Data.OffsetTable[node];
		int end = Data.OffsetTable[node + 1];
		return Arrays.copyOfRange(Data.target, start, end);
	}

	private static void addNeighborsToQueue(int node) {
		/**
		 * add neighbors to unvisited-queue, but reachable
		 */
		int[] neighbors = getNeighbors(node);
		for (int i : neighbors) {
			unvisited.offer(new Tuple(i, distance[node] + Data.weight[i]));
		}
		return;
	}
}
