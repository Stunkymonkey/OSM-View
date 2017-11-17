package main;

public class Data {

	public static Fileread fr;

	public static int AmountNodes;
	public static int AmountEdges;
	public static int[] source, target, weight;
	public static int[] OffsetTable;
	public static double[] latitude, longitude;
	public static double max_lat = Double.MIN_VALUE;
	public static double max_lon = Double.MIN_VALUE;
	public static double min_lat = Double.MAX_VALUE;
	public static double min_lon = Double.MAX_VALUE;

}
