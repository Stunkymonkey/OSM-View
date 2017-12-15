package main;

import java.util.ArrayList;
import java.util.LinkedList;

public class Grid {
	/**
	 * prepare grid
	 */
	public static void createGrid() {
		initGrid();
		createGeoTables();
	}

	/**
	 * initialize all arrays
	 */
	@SuppressWarnings("unchecked")
	private static void initGrid() {
		if (Data.AmountNodes < 10) {
			Data.gridAmount = Data.AmountNodes;
		} else {
			Data.gridAmount = Data.AmountNodes / 4;
		}
		Data.gridX = (ArrayList<Integer>[]) new ArrayList[Data.gridAmount];
		Data.gridY = (ArrayList<Integer>[]) new ArrayList[Data.gridAmount];
		Data.gridSizeX = Data.max_x - Data.min_x;
		Data.gridSizeY = Data.max_y - Data.min_y;
		Data.gridStepSizeX = Data.gridSizeX / Data.gridAmount;
		Data.gridStepSizeY = Data.gridSizeY / Data.gridAmount;
	}

	/**
	 * calculates position of node in grid
	 * @param x
	 * @param y
	 * @return
	 */
	private static int[] getGridPosition(double x, double y) {
		int x_result, y_result;
		x_result = (int) ((x - Data.min_x) / Data.gridStepSizeX);
		y_result = (int) ((y - Data.min_y) / Data.gridStepSizeY);
		if (x_result >= Data.gridAmount) {
			x_result = Data.gridAmount - 1;
		}
		if (y_result >= Data.gridAmount) {
			y_result = Data.gridAmount - 1;
		}
		return new int[] { x_result, y_result };
	}

	/**
	 * add all nodes to grid
	 */
	private static void createGeoTables() {
		int[] tmp = new int[2];
		for (int i = 0; i < Data.AmountNodes; i++) {
			tmp = getGridPosition(Data.x_dim[i], Data.y_dim[i]);
			if (Data.gridX[tmp[0]] == null) {
				Data.gridX[tmp[0]] = new ArrayList<Integer>();
			}
			if (Data.gridY[tmp[1]] == null) {
				Data.gridY[tmp[1]] = new ArrayList<Integer>();
			}
			Data.gridX[tmp[0]].add(i);
			Data.gridY[tmp[1]].add(i);
		}
	}

	/**
	 * calculates distance of two points
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	/**
	 * returns integer, that are in both lists
	 * @param x_index
	 * @param y_index
	 * @return
	 */
	private static ArrayList<Integer> calcIntersection(ArrayList<Integer> first, ArrayList<Integer> second) {
		first.retainAll(second);
		return first;
	}

	/**
	 * get nearest node to coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	public static int getNearestNeighbor(double x, double y) {
		int[] gridPos = getGridPosition(x, y);
		ArrayList<Integer> points = getPointsInBound(gridPos[0], gridPos[1], 1);
		double dist = Double.MAX_VALUE;
		int id = -1;
		double tmp;
		for (int point : points) {
			tmp = getDistance(x, y, Data.x_dim[point], Data.x_dim[point]);
			if (tmp < dist) {
				dist = tmp;
				id = point;
			}
		}
		return id;
	}

	/**
	 * return points near to grid position
	 * @param x
	 * @param y
	 * @param range
	 * @return
	 */
	private static ArrayList<Integer> getPointsInBound(int x, int y, int range) {
		if (range % 2 != 1) {
			System.out.println("wrong range");
			return new ArrayList<Integer>();
		}
		ArrayList<Integer> points = new ArrayList<Integer>();
		ArrayList<Integer> points_x = new ArrayList<Integer>();
		ArrayList<Integer> points_y = new ArrayList<Integer>();
		while (points.isEmpty()) {
			for (int i = 0; i < range; i++) {
				for (int index: Data.gridX[x - range + i]) {
					points_x.add(index);
				}
				for (int index: Data.gridY[x - range + i]) {
					points_y.add(index);
				}
			}
			points = calcIntersection(points_x, points_y);
			range += 2;
		}
		// maybe we should add more grids around them
		return points;
	}
}
