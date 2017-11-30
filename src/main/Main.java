package main;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		String filename;
		// filename = "toy.fmi";
		// filename = "MV.fmi";
		filename = "stgtregbz.fmi";
		// filename = "bw.fmi";
		// filename = "germany.fmi";
		Main.init(filename);

		List<Integer> path;
		Dijkstra d = new Dijkstra(2);

		path = d.findWay(17402);
		Helper.Time("Find Way");
		Helper.Print(path);

		// Helper.PrintCoords(path);

//		Helper.Print(Geo.getClosestPoint(49.02, 10.02,
//				Geo.calcIntersection(Geo.getGridPosition(49.01, 10.01)[0], Geo.getGridPosition(49.01, 10.01)[1])));
	}

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
