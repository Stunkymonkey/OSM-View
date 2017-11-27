package main;

import java.util.ArrayList;

public class Geo_reloadet {
	public static void createGrid() {
		initGrid();
		createGeoTables();
	}

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

	public static int[] getGridPosition(double x, double y) {
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

	public static void createGeoTables() {
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

	public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	public static ArrayList<Integer> calcIntersection(int x_index, int y_index) {
		ArrayList<Integer> intersection = new ArrayList<>();
		ArrayList<Integer> line = Data.gridX[x_index];
		ArrayList<Integer> column = Data.gridY[y_index];
		// check ob null
		for (int i : line) {
			if (column.contains(i)) {
				intersection.add(i);
			}
			
		}return intersection;
		/*
		 * TODO: getDistance calcIntersection bound getPointsinBound getClosestPoint
		 */

	}
}
