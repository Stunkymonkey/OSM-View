package main;

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

	}
}
