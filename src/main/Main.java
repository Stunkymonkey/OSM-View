package main;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		String filename;
//		filename = "toy.fmi";
		filename = "MV.fmi";
//		filename = "stgtregbz.fmi";
//		filename = "bw.fmi";

		Data.fr = new Fileread(filename);
		NodesAndEdges.createArrays();
//		Data.PrintEverything();
		Geo.createGrid();
		System.out.println(Arrays.toString(Data.gridX));
		//System.out.println(Geo.calcIntersection(2, 2));
	}
}
