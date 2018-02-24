package backend;

import java.util.ArrayList;

/**
 * for storing all the data
 */
public class Data {
	// for timing output
	static long startTime;

	// Data from file
	public static int AmountNodes;
	public static int AmountEdges;
	public static int[] source, target, weight;
	public static int[] OffsetTable;
	public static double[] x_dim, y_dim;
	public static double max_x = Double.MIN_VALUE;
	public static double max_y = Double.MIN_VALUE;
	public static double min_x = Double.MAX_VALUE;
	public static double min_y = Double.MAX_VALUE;

	// for the grid
	public static int gridAmount;
	public static double gridSizeX;
	public static double gridSizeY;
	public static double gridStepSizeX;
	public static double gridStepSizeY;
	public static ArrayList<Integer>[] gridX;
	public static ArrayList<Integer>[] gridY;
	
	// distances will get set to infinity
	public static int[] dijkstraDistances;
}
