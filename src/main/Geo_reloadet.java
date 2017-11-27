package main;

import java.util.ArrayList;
import java.util.LinkedList;

public class Geo_reloadet {
	/**
	 * 
	 */
	public static void createGrid() {
		initGrid();
		createGeoTables();
	}

	/**
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
	 * @param x_index
	 * @param y_index
	 * @return
	 */
	private static ArrayList<Integer> calcIntersection(int x_index, int y_index) {
		ArrayList<Integer> first = Data.gridX[x_index];
		ArrayList<Integer> second = Data.gridY[y_index];
		first.retainAll(second);
		return first;
	}

	/**
	 * 
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
	 * test this it might have a off by one error
	 * 
	 * @param x
	 * @param y
	 * @param range
	 * @return
	 */
	private static ArrayList<Integer> getPointsInBound(int x, int y, int range) {
		ArrayList<Integer> points = new ArrayList<Integer>();
		while (points.isEmpty()) {
			for (int i = 0; i < range; i++) {
				if (i % (range - 1) == 0) {
					for (int j = 0; j < range; j++) {
						points.addAll(calcIntersection(x - range, y - range + i));
					}
				} else {
					points.addAll(calcIntersection(x - range + i, y - range + i));
					points.addAll(calcIntersection(x - range - i, y - range + i));
				}
			}
			range += 2;
		}
		// vielleicht muessen wir hier noch einmal alles aussenrum nehmen und mit
		// berechnen, da es moeglcherweise doch naeher sein koennte
		return points;
	}

	/**
	 * ich kapier hier nicht wie du ohne for schleifen irgendwie alles abgreifen
	 * willst. für mich sieht das nachm eindimensionalen einsammeln aus
	 * 
	 * @param x
	 * @param y
	 * @param r
	 * @return
	 */
	public static int[][] getBounds(double x, double y, int r) {
		int[] grid = getGridPosition(x, y); // ist das nicht dumm das hier zu machen? dann wird die methode ja bei jedem
											// rekursiven aufruf aufgerufen? waers nicht besser der methode direkt nen
											// array von getgridpos zu geben?
		int[] x_bounds = { grid[0] - r, grid[0] + r };
		int[] y_bounds = { grid[1] - r, grid[1] + r };
		System.out.println(r);
		if (getPointsInBounds(x_bounds[0], x_bounds[1], "x") == null
				| getPointsInBounds(y_bounds[0], y_bounds[1], "y") == null) {
			getBounds(x, y, r + 1);
		}
		return new int[][] { x_bounds, y_bounds };
	}

	/**
	 * ja an sich ganz ok, allerdings die sache mit der dimension find ich nicht so
	 * geil, auch wenn die Idee wahrscheinlich von mir kam.
	 * 
	 * @param low
	 * @param up
	 * @param dim
	 * @return
	 */
	private static LinkedList<Integer> getPointsInBounds(int low, int up, String dim) {
		LinkedList<Integer> pointsInBounds = new LinkedList<>();
		switch (dim) {
		case "x":
			for (int i = low; i <= up; i++) {
				pointsInBounds.addAll(Data.gridX[i]);
			}
			break;
		case "y":
			for (int i = low; i <= up; i++) {
				pointsInBounds.addAll(Data.gridY[i]);
			}
			break;
		default:
			System.out.println("No proper dimension");
		}

		return pointsInBounds;
	}
}
