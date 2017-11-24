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
		if (x_result >= Data.gridAmount) {
			x_result = Data.gridAmount - 1;
		}
		if (y_result >= Data.gridAmount) {
			y_result = Data.gridAmount - 1;
		}
		return new int[] { x_result, y_result};
	}
//kreigt 2 listen (mit allen k�stchen aus x / y schon davor zusammengef�gt)
	public static ArrayList<Integer> calcIntersection(int x_index, int y_index) {
		ArrayList<Integer> intersection = new ArrayList<>();
		ArrayList<Integer> line = Data.gridX[x_index];
		ArrayList<Integer> column = Data.gridY[y_index];
		for (int i : line) {
			if (column.contains(i)) {
				intersection.add(i);
			}
		}
		return intersection;
	}

	// mit getGridpos x und y werte holen, damit die arraylisten holen damit die
	// indize schnittmenge holen
	public static int getQuarter(double x_click, double y_click, int[] grid) {
		int quarter = 0;
		if (grid[0] + Data.gridStepSizeX / 2 >= x_click) {
			if (grid[1] + Data.gridStepSizeY / 2 >= y_click) {
				quarter = 1;
			} else if (grid[1] + Data.gridStepSizeY / 2 <= y_click) {
				quarter = 3;
			}

		} else if (grid[0] + Data.gridStepSizeX / 2 <= x_click) {
			if (grid[1] + Data.gridStepSizeY / 2 >= y_click) {
				quarter = 2;
			} else if (grid[1] + Data.gridStepSizeY / 2 <= y_click) {
				quarter = 4;
			}
		}
		return quarter;
	}
	//TODO: getboundsmethode(x,y,r) : typ array[[x1,x2][y1,y2]]
	//TODO: getPointsinBounds(low,up,dim) : typ linkedliste
	public static ArrayList<Integer> getPointsAroundClick(double x_click, double y_click) {

		// berechne in welchem viertel von Grid click ist
		// ist in diesem K�stchen ein punkt?
		// Ja:
		// nehme die 3 umliegenden k�stchen
		// packe alle punkte aus diesen grids in array -> getclosestpoint

		// Nein:
		// nehme die 8 umliegenden k�stchen
		// immer noch nix? weiter nach aussen
		ArrayList<Integer> pointsAroundClick = null;
		int[] grid = getGridPosition(x_click, y_click); // das muss das grid sein in dem der klick ist
		int x_lower_bound = 0;
		int x_upper_bound = 0;
		int y_lower_bound = 0;
		int y_upper_bound = 0;

		switch (getQuarter(x_click, y_click, grid)) {
		default:
			x_lower_bound = -1;
			x_upper_bound = 1;
			y_lower_bound = -1;
			y_upper_bound = 1;

			break;
		case 1:
			// oben links
			x_lower_bound = -1;
			x_upper_bound = 0;
			y_lower_bound = 0;
			y_upper_bound = 1;

			break;

		case 2:
			// oben rechts
			x_lower_bound = 0;
			x_upper_bound = 1;
			y_lower_bound = 0;
			y_upper_bound = 1;

			break;

		case 3:
			// unten links
			x_lower_bound = -1;
			x_upper_bound = 0;
			y_lower_bound = -1;
			y_upper_bound = 0;

			break;

		case 4:
			// unten rechts
			x_lower_bound = 0;
			x_upper_bound = 1;
			y_lower_bound = 0;
			y_upper_bound = 1;

			break;
		}
		for (int i = grid[0] + x_lower_bound; i <= grid[0] + x_upper_bound; i++) {
			for (int j = grid[1] + y_lower_bound; j <= grid[1] + y_upper_bound; j++) {
				pointsAroundClick.addAll(calcIntersection(i, j));
			}
		}

		return pointsAroundClick;
	}

	public static double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	// kriegt array von umliegenden punkten und berechnet min distanz
	public static double[] getClosestPoint(double x, double y, ArrayList<Integer> indices) {
		// f�r x und y int liste mit indizes von buckets
		// punkt zum vergleichen �bergeben
		double cp_x = Data.x_buckets[0];
		double cp_y = Data.y_buckets[0];
		double min_distance = Double.MAX_VALUE;

		for (int i = 0; i < indices.size(); i++) {
			double min_x = Data.x_buckets[indices.get(i)];
			double min_y = Data.y_buckets[indices.get(i)];
			double tmp_distance = getDistance(x, y, min_x, min_y);
			if (tmp_distance <= min_distance) {
				min_distance = tmp_distance;
				cp_x = min_x;
				cp_y = min_y;
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
			}
			if (Data.gridY[tmp[1]] == null) {
				Data.gridY[tmp[1]] = new ArrayList<Integer>();
			}
			Data.gridX[tmp[0]].add(i);
			Data.gridY[tmp[1]].add(i);
		}
	}
}
