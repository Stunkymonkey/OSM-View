package main;

import java.util.ArrayList;

public class Geo {

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
		if (x_result >= Data.max_x) {
			x_result--;
		}
		if (y_result >= Data.max_y) {
			y_result--;
		}
		return new int[] { x_result, y_result };
	}
	public static void getPointsAroundClick(double x_click, double y_click) {
		//berechne in welchem viertel von Grid click ist
		//ist in diesem Kästchen ein punkt? 
		//Ja: 
		//nehme die 3 umliegenden kästchen
		//packe alle punkte aus diesen grids in array -> getclosestpoint
		
		//Nein: 
		// nehme die 8 umliegenden kästchen
		// immer noch nix? weiter nach aussen
	}

	public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	// kriegt array von umliegenden punkten und berechnet min distanz
	public static double[] getClosestPoint(double[] x_pointsAroundClick, double[] y_pointsAroundClick) {
		double cp_x = x_pointsAroundClick[0];
		double cp_y = y_pointsAroundClick[0];
		double min_distance = Double.MAX_VALUE;
		for (int i = 1; i < x_pointsAroundClick.length; i++) {
			double tmp_distance = getDistance(x_pointsAroundClick[i - 1], y_pointsAroundClick[i - 1], x_pointsAroundClick[i],
					y_pointsAroundClick[i]);
			if (tmp_distance <= min_distance) {

				cp_x = x_pointsAroundClick[i - 1];
				cp_y = y_pointsAroundClick[i - 1];
				min_distance = tmp_distance;
			} 
		}

		return new double[] { cp_x, cp_y };
	}

	public static void createGeoTables() {
		int[] tmp = new int[2];
		for (int i = 0; i < Data.AmountNodes; i++) {
			tmp = getGridPosition(Data.x_buckets[i], Data.y_buckets[i]);
			if (Data.gridX[tmp[0]] == null) {
				Data.gridX[tmp[0]] = new ArrayList<Integer>();
				;
			}
			if (Data.gridY[tmp[1]] == null) {
				Data.gridY[tmp[1]] = new ArrayList<Integer>();
				;
			}
			Data.gridX[tmp[0]].add(i);
			Data.gridY[tmp[1]].add(i);
		}
	}
}
