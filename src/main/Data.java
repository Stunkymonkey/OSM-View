package main;

import java.util.ArrayList;

public class Data {
	static long startTime;

	public static Fileread fr;

	public static int AmountNodes;
	public static int AmountEdges;
	public static int[] source, target, weight;
	public static int[] OffsetTable;
	public static double[] x_dim, y_dim;
	public static double max_x = Double.MIN_VALUE;
	public static double max_y = Double.MIN_VALUE;
	public static double min_x = Double.MAX_VALUE;
	public static double min_y = Double.MAX_VALUE;

	public static int gridAmount;
	public static double gridSizeX;
	public static double gridSizeY;
	public static double gridStepSizeX;
	public static double gridStepSizeY;
	public static ArrayList<Integer>[] gridX;
	public static ArrayList<Integer>[] gridY;
	
	public static int[] dijkstraDistances;
}
