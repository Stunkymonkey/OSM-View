package main;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		String filename;
		// filename = "toy.fmi";
		// filename = "MV.fmi";
		// filename = "stgtregbz.fmi";
		// filename = "bw.fmi";
		filename = "germany.fmi";
		Main.init(filename);

		List<Integer> path;
		Dijkstra d;
		d = new Dijkstra(2);

		d.findWay(-1);
		Helper.Time("Dijkstra all");
		path = d.findWay(1);
		Helper.Time("Find Way");
		Helper.Print(path);

		// Helper.PrintCoords(path);

//		Helper.Print(Geo.getClosestPoint(49.02, 10.02,
//				Geo.calcIntersection(Geo.getGridPosition(49.01, 10.01)[0], Geo.getGridPosition(49.01, 10.01)[1])));
	}

	/**
	 * prepare everything of the graph to be able to execute Dijkstra
	 * @param filename
	 */
	public static void init(String filename) {
		Helper.Time("");
		Data.fr = new Fileread(filename);
		NodesAndEdges.createArrays();
		Helper.Time("Offset-Array");
		Geo.createGrid();
		Helper.Time("Create-Grid");
		Dijkstra.initDistanceTable();
		Helper.Time("Init Dijkstra-Tables");
	}
}
