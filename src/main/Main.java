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
		
		// initialize and set start point
		d = new Dijkstra(8371830);

		// set goal to -1 if Dijkstra should run without goal
		d.findWay(-1);
		Helper.Time("Dijkstra running on full graph");

		edges = d.findWay(16743651);
		Helper.Time("Find Way to node");

		Helper.Print("Distance: " + d.getDistance(16743651));
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
		//Geo.createGrid();
		//Helper.Time("Create-Grid");
		Dijkstra.initDistanceTable();
		//Helper.Time("Initialize Dijkstra-Tables");
	}
	
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
