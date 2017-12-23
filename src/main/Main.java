package main;

import java.util.List;
import java.util.Scanner;

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
		int start = 0;
		int goal = -1;
		
		// initialize and set start point
		d = new Dijkstra(start);
		Scanner user_input = new Scanner( System.in );
		while (true) {
			System.out.print("Set start: ");
			start = user_input.nextInt();
			System.out.print("Set goal (-1 for whole graph): ");
			goal = user_input.nextInt();
			Helper.Time("time for input");
			if (!d.setStart(start)) {
				continue;
			}
			edges = d.findWay(goal);
			Helper.Time("executed dijkstra");
			if (goal != -1) {
				Helper.Print("Distance: " + d.getDistance(goal));
				Helper.Print("Edges", edges);
				nodes = Dijkstra.edgesToNodes(edges);
				Helper.Print("Nodes", nodes);
				Helper.Time("calculated path");
			}
			System.out.print("quit? [y/n]");
			if (user_input.next().toLowerCase().equals("y")) {
				break;
			}
		}
		user_input.close();
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
