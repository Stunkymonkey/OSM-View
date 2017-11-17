package main;

public class Main {

	public static void main(String[] args) {
		String filename;
		filename = "toy.fmi";
		// filename = "MV.fmi";
		// filename = "stgtregbz.fmi";
		// filename = "bw.fmi";

		Data.fr = new Fileread(filename);
		Arrays.initArrays();
		Arrays.createArrays();
		for (int i = 0; i < Data.OffsetTable.length; i++) {
			System.out.println(i + ": " + Data.OffsetTable[i]);
		}
		for (int i = 0; i < Data.source.length; i++) {
			System.out.println(i + ": " + Data.source[i]);
		}

		Geolocation g = new Geolocation();
		// g.getMinMax(fr.latitude);

	}
}
