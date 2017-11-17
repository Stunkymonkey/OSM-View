package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Data {

	public static Fileread fr;

	public static int AmountNodes;
	public static int AmountEdges;
	public static int[] source, target, weight;
	public static int[] OffsetTable;
	public static double[] x_buckets, y_buckets;
	public static double max_x = Double.MIN_VALUE;
	public static double max_y = Double.MIN_VALUE;
	public static double min_x = Double.MAX_VALUE;
	public static double min_y = Double.MAX_VALUE;

	public static int gridAmount;
	public static double gridSizeX;
	public static double gridSizeY;
	public static double gridStepSizeX;
	public static double gridStepSizeY;
	public static ArrayList<Integer>[] gridX ;
	public static ArrayList<Integer>[] gridY;
	
	public static void PrintEverything() {
		System.out.println("AmountNodes: " + AmountNodes);
		System.out.println("AmountEdges: " + AmountEdges);

		System.out.println("source:");
		for (int i = 0; i < source.length; i++) {
			System.out.println(i + ": " + source[i]);
		}
		System.out.println("target:");
		for (int i = 0; i < target.length; i++) {
			System.out.println(i + ": " + target[i]);
		}
		System.out.println("weight:");
		for (int i = 0; i < weight.length; i++) {
			System.out.println(i + ": " + weight[i]);
		}
		
		System.out.println("OffsetTable:");
		for (int i = 0; i < OffsetTable.length; i++) {
			System.out.println(i + ": " + OffsetTable[i]);
		}
		
		System.out.println("latitude:");
		for (int i = 0; i < x_buckets.length; i++) {
			System.out.println(i + ": " + x_buckets[i]);
		}
		System.out.println("longitude:");
		for (int i = 0; i < y_buckets.length; i++) {
			System.out.println(i + ": " + y_buckets[i]);
		}
		System.out.println("max_x: " + max_x);
		System.out.println("max_y: " + max_y);
		System.out.println("min_x: " + min_x);
		System.out.println("min_y: " + min_y);
		
		System.out.println("gridAmount: " + gridAmount);
		System.out.println("gridSizeX: " + gridSizeX);
		System.out.println("gridSizeY: " + gridSizeY);

		System.out.println("gridX:");
		for (int i = 0; i < gridX.length && gridX[i].size() > 0; i++) {
			System.out.println(i + ": " + Arrays.toString(gridX[i].toArray()));
		}
		System.out.println("gridY:");
		for (int i = 0; i < gridY.length && gridY[i].size() > 0; i++) {
			System.out.println(i + ": " + Arrays.toString(gridY[i].toArray()));
		}
	}
}
