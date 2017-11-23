package main;

import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		String filename;
		filename = "toy.fmi";
//		filename = "MV.fmi";
//		filename = "stgtregbz.fmi";
//		filename = "bw.fmi";

		Data.fr = new Fileread(filename);
		NodesAndEdges.createArrays();
//		Data.PrintEverything();
		Geo.createGrid();
		List<Integer> path = Dijkstra.findWay(0, 3);
//		List<Integer> path = Dijkstra.findWay(20, 40);
		System.out.println(Arrays.toString(path.toArray()));
		for (Integer integer : path) {
			System.out.println(integer);
			System.out.println(Data.x_buckets[integer]);
			System.out.println(Data.y_buckets[integer]);
		}
	}
}
