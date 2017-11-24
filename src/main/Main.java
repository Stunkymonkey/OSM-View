package main;

import java.util.Arrays;
import java.util.List;

public class Main {
	
	static long startTime;

	public static void main(String[] args) {
		startTime = System.currentTimeMillis();
		String filename;
		// filename = "toy.fmi";
		// filename = "MV.fmi";
		// filename = "stgtregbz.fmi";
		// filename = "bw.fmi";
		filename = "germany.fmi";

		Data.fr = new Fileread(filename);
		NodesAndEdges.createArrays();
		System.out.println(System.currentTimeMillis() - Main.startTime + " ms");
		// Data.PrintEverything();
		Geo.createGrid();
		System.out.println(System.currentTimeMillis() - Main.startTime + " ms");
		List<Integer> path = Dijkstra.findWay(8371826, 16743651);
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
