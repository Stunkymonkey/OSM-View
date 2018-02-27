package backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
			Data.gridAmount = 3;
		} else {
			Data.gridAmount = Data.AmountNodes / (int) (Data.AmountNodes * 0.00001);
		}
		// System.out.println("Data.gridAmount: " + Data.gridAmount);
		// System.out.println("Data.AmountNodes: " + Data.AmountNodes);
		Data.gridX = (ArrayList<Integer>[]) new ArrayList[Data.gridAmount];
		Data.gridY = (ArrayList<Integer>[]) new ArrayList[Data.gridAmount];
		for (int i = 0; i < Data.gridAmount; i++) {
			Data.gridX[i] = new ArrayList<Integer>();
			Data.gridY[i] = new ArrayList<Integer>();
		}
		Data.gridSizeX = Data.max_x - Data.min_x;
		Data.gridSizeY = Data.max_y - Data.min_y;
		Data.gridStepSizeX = Data.gridSizeX / Data.gridAmount;
		Data.gridStepSizeY = Data.gridSizeY / Data.gridAmount;
	}

	/**
	 * calculates position of node in grid
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private static int[] getGridPosition(double x, double y) {
		int x_result, y_result;
		x_result = (int) ((x - Data.min_x) / Data.gridStepSizeX);
		y_result = (int) ((y - Data.min_y) / Data.gridStepSizeY);
		// edge cases are handled below
		if (x_result >= Data.gridAmount) {
			x_result = Data.gridAmount - 1;
		} else if (x_result < 0) {
			x_result = 0;
		}
		if (y_result >= Data.gridAmount) {
			y_result = Data.gridAmount - 1;
		} else if (y_result < 0) {
			y_result = 0;
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
			Data.gridX[tmp[0]].add(i);
			Data.gridY[tmp[1]].add(i);
		}
	}

	/**
	 * calculates distance of two points
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
	}

	/**
	 * returns integer, that are in both lists
	 * 
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
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static int getNearestNeighbor(double x, double y) {
		int[] gridPos = getGridPosition(x, y);
		System.out.println("grid_pos_x: " + gridPos[0]);
		System.out.println("grid_pos_y: " + gridPos[1]);
		ArrayList<Integer> points = getPointsInBound(gridPos[0], gridPos[1]);
		double dist = Double.MAX_VALUE;
		int id = -1;
		double tmp;
		for (int point : points) {
			tmp = getDistance(x, y, Data.x_dim[point], Data.y_dim[point]);
			if (tmp < dist) {
				dist = tmp;
				id = point;
			}
		}
		return id;
	}

	/**
	 * return set of points near to grid position
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private static ArrayList<Integer> getPointsInBound(int x, int y) {
		int range = 0;
		ArrayList<Integer> points = new ArrayList<Integer>();
		ArrayList<Integer> points_x = new ArrayList<Integer>();
		ArrayList<Integer> points_y = new ArrayList<Integer>();
		while (points.isEmpty()) {
			// System.out.println("range: " + range);
			if (range == 0) {
				points_x.addAll(Data.gridX[x]);
				points_y.addAll(Data.gridY[y]);
			} else {
				if (x - range < Data.gridAmount && x - range >= 0) {
					points_x.addAll(Data.gridX[x - range]);
				}
				if (x + range < Data.gridAmount && x + range >= 0) {
					points_x.addAll(Data.gridX[x + range]);
				}
				if (y - range < Data.gridAmount && y - range >= 0) {
					points_y.addAll(Data.gridY[y - range]);
				}
				if (y + range < Data.gridAmount && y + range >= 0) {
					points_y.addAll(Data.gridY[y + range]);
				}
			}
			points = calcIntersection(points_x, points_y);
			range++;
		}
		return points;
	}

	/**
	 * return positions of nodes
	 * 
	 * @return
	 */
	public static LinkedList<Double[]> getCoordsOfPoints(List<Integer> nodes) {
		LinkedList<Double[]> result = new LinkedList<Double[]>();
		for (Integer i : nodes) {
			Double[] d = new Double[2];
			d[0] = Data.y_dim[i];
			d[1] = Data.x_dim[i];
			result.add(d);
		}
		return result;
	}

	/**
	 * stupid version, which calculates distance to all nodes
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static int getNearestNeighborNaive(double x, double y) {
		double dist = Double.MAX_VALUE;
		int id = -1;
		double tmp;
		for (int i = 0; i < Data.AmountNodes; i++) {
			tmp = getDistance(x, y, Data.x_dim[i], Data.y_dim[i]);
			if (tmp < dist) {
				dist = tmp;
				id = i;
			}
		}
		return id;
	}
}
