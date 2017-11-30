package main;

import java.util.Arrays;
import java.util.List;

public class Helper {

	public static void Print(int[] array) {
		System.out.println(Arrays.toString(array));
	}
	
	public static void Print(String s, int[] array) {
		System.out.println(s + ": " + Arrays.toString(array));
	}

	public static void Print(double[] array) {
		System.out.println(Arrays.toString(array));
	}

	public static void Print(List<Integer> list) {
		System.out.println(Arrays.toString(list.toArray()));
	}

	public static void Time(String s) {
		if (!s.equals("")) {
			System.out.println(s + ": " + (System.currentTimeMillis() - Data.startTime + " ms"));
		}
		Data.startTime = System.currentTimeMillis();
	}

	public static void PrintCoords(List<Integer> path) {
		for (Integer integer : path) {
			System.out.println("Node   : " + integer);
			System.out.println("Node[x]: " + Data.x_dim[integer]);
			System.out.println("Node[y]: " + Data.y_dim[integer]);
		}
	}
}
