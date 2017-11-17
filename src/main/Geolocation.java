package main;

import java.util.Arrays;

public class Geolocation {

	public void createGrid() {

		int skal = 10000;
		int min_lat_int = (int) (Data.min_lat * skal);
		int max_lat_int = (int) (Data.max_lat * skal);
		int min_lon_int = (int) (Data.min_lon * skal);
		int max_lon_int = (int) (Data.max_lon * skal);
		System.out.println("min: " + min_lat_int);
		System.out.println("max: " + max_lat_int);
		int difference_lat = max_lat_int - min_lat_int;
		int difference_lon = max_lon_int - min_lon_int;

		System.out.println(difference_lat);
		int[] grid_lat = new int[difference_lat];
		int[] grid_lon = new int[difference_lon];
		System.out.println("dif: " + difference_lat);

		for (int i = 0; i < difference_lat; i++) {
			grid_lat[i] = min_lat_int + i;
		}
		for (int j = 0; j < difference_lon; j++) {
			grid_lon[j] = min_lon_int + j;
		}
		System.out.println(grid_lat.length);
		// System.out.println(Arrays.toString(grid_lon));

	}

	public int[] getGridPosition(double lat, double lon) {
		int grid_pos_lat = (int) lat * 10000;
		int grid_pos_lon = (int) lon * 10000;

		return new int[] { grid_pos_lat, grid_pos_lon };
	}

}
