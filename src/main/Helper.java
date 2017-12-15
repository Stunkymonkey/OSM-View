package main;

import java.util.Arrays;
import java.util.List;

/**
 * this is a helper class to better debugging
 */
public class Helper {
	
	private static boolean DEBUG = true;
	
	public static void Print(String string) {
		if (DEBUG) {
			System.out.println(string);
		}
	}
	
	public static void Print(String[] array) {
		if (DEBUG) {
			System.out.println(Arrays.toString(array));
		}
	}

	public static void Print(int[] array) {
		if (DEBUG) {
			System.out.println(Arrays.toString(array));
		}
	}
	
	public static void Print(String s, int[] array) {
		if (DEBUG) {
			System.out.println(s + ": " + Arrays.toString(array));
		}
	}

	public static void Print(double[] array) {
		if (DEBUG) {
			System.out.println(Arrays.toString(array));
		}
	}

	public static void Print(List<Integer> list) {
		if (DEBUG) {
			System.out.println(Arrays.toString(list.toArray()));
		}
	}
	
	public static void Print(String s, List<Integer> list) {
		if (DEBUG) {
			System.out.println(s + ": " + Arrays.toString(list.toArray()));
		}
	}

	public static void Time(String s) {
		if (!s.equals("") && DEBUG) {
			System.out.println(s + ": " + (System.currentTimeMillis() - Data.startTime + " ms"));
		}
		Data.startTime = System.currentTimeMillis();
	}

	public static void PrintCoords(List<Integer> path) {
		if (DEBUG) {
			for (Integer integer : path) {
				System.out.println("Node   : " + integer);
				System.out.println("Node[x]: " + Data.x_dim[integer]);
				System.out.println("Node[y]: " + Data.y_dim[integer]);
			}
		}
	}
}
