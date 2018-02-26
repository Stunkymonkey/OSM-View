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
			Data.gridAmount = Data.AmountNodes;
		} else {
			Data.gridAmount = Data.AmountNodes / 100;
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
		return Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
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
	 * return points near to grid position
	 * @param x
	 * @param y
	 * @return
	 */
	private static ArrayList<Integer> getPointsInBound(int x, int y) {
		int range = 1;
		ArrayList<Integer> points = new ArrayList<Integer>();
		ArrayList<Integer> points_x = new ArrayList<Integer>();
		ArrayList<Integer> points_y = new ArrayList<Integer>();
		int x_index;
		int y_index;
		//System.out.println("amount: " + Data.gridAmount);
		while (points.isEmpty()) {
			System.out.println("range: " + range);
			for (int i = 0; i < range; i++) {
				x_index = x + range/2 - i;
				if (x_index >= 0 && x_index < Data.gridAmount) {
					//System.out.println("x_i: " + x_index);
					//System.out.println("array_size_x: " + Data.gridY[x_index].size());
					for (int index: Data.gridX[x_index]) {
						points_x.add(index);
					}
				}
				y_index = y + range/2 - i;
				if (y_index >= 0 && y_index < Data.gridAmount) {
					//System.out.println("y_i: " + y_index);
					//System.out.println("array_size_y: " + Data.gridY[y_index].size());
					for (int index: Data.gridY[y_index]) {
						points_y.add(index);
					}
				}
				//System.out.println("indexes appended");
			}
			System.out.println("points: " + calcIntersection(points_x, points_y));
			points = calcIntersection(points_x, points_y);
			range += 2;
		}
		// maybe we should add more grids around them
		return points;
	}
	
	/**
	 * return positions of nodes
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
