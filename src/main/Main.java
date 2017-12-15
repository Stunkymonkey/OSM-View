package main;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		String filename = getFilename(args);
		Main.init(filename);
		Helper.Time("done reading");

		// for saving results
		List<Integer> edges;
		List<Integer> nodes;
		Dijkstra d;
		
		/*
		 * ENTER BELOW HERE FOR 
		 */
		int start = 8371830;
		int goal = 16743651;
		
		// initialize and set start point
		d = new Dijkstra(start);

		// set goal to -1 if Dijkstra should run without goal
		d.findWay(-1);
		Helper.Time("Dijkstra running on full graph");

		edges = d.findWay(goal);
		Helper.Time("Find Way to goal");

		Helper.Print("Distance: " + d.getDistance(goal));
		Helper.Print("Edges", edges);
		nodes = Dijkstra.edgesToNodes(edges);
		Helper.Print("Nodes", nodes);
	}

	/**
	 * prepare everything of the graph to be able to execute Dijkstra
	 * @param filename
	 */
	public static void init(String filename) {
		Helper.Time("");
		new Fileread(filename);
		NodesAndEdges.createArrays();
		//Helper.Time("Offset-Array");
		//Grid.createGrid();
		//Helper.Time("Create-Grid");
		Dijkstra.initDistanceTable();
		//Helper.Time("Initialize Dijkstra-Tables");
	}
	
	/**
	 * return the filename we are using
	 */
	public static String getFilename(String[] args) {
		String filename;
		if (args.length == 0) {
			// filename = "toy.fmi";
			// filename = "MV.fmi";
			// filename = "stgtregbz.fmi";
			// filename = "bw.fmi";
			filename = "germany.fmi";
		} else {
			filename = args[0];
		}
		return filename;
	}
}
