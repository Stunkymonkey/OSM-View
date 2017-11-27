package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	
	static long startTime;

	public static void main(String[] args) {
		startTime = System.currentTimeMillis();
		String filename;
		 //filename = "toy.fmi";
		 filename = "MV.fmi";
		// filename = "stgtregbz.fmi";
		// filename = "bw.fmi";
		//filename = "germany.fmi";

		Data.fr = new Fileread(filename);
		NodesAndEdges.createArrays();
		System.out.println(System.currentTimeMillis() - Main.startTime + " ms");
		// Data.PrintEverything();
		Geo.createGrid();
		System.out.println(System.currentTimeMillis() - Main.startTime + " ms");
		List<Integer> path = Dijkstra.findWay(2,4);
		
		//System.out.println(Arrays.toString(Geo.getClosestPoint(49.02, 10.02, Geo.calcIntersection(Geo.getGridPosition(49.01, 10.01)[0], Geo.getGridPosition(49.01, 10.01)[1]))));
		System.out.println(System.currentTimeMillis() - Main.startTime + " ms");
		System.out.println(Arrays.toString(path.toArray()));
		// for (Integer integer : path) {
		// System.out.println(integer);
		// System.out.println(Data.x_buckets[integer]);
		// System.out.println(Data.y_buckets[integer]);
		// }
//		path = Dijkstra.findWay(67085, 68015);
//		System.out.println(System.currentTimeMillis() - startTime + " ms");
//		System.out.println(Arrays.toString(path.toArray()));
		// for (Integer integer : path) {
		// System.out.println(integer);
		// System.out.println(Data.x_buckets[integer]);
		// System.out.println(Data.y_buckets[integer]);
		// }
	}
}
