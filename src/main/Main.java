package main;

public class Main {
	// get fileread max and min stuff

	public static void main(String[] args) {
		String filename;
		filename = "toy.fmi";
//		filename = "MV.fmi";
//		filename = "stgtregbz.fmi";
//		filename = "bw.fmi";

		Fileread fr = new Fileread(filename);
//		System.out.println(fr.LUT.length);
		for (int i = 0; i < fr.LUT.length; i++) {
			System.out.println(i + ": " + fr.LUT[i]);
		}
		Geolocation g = new Geolocation();
		int [] test = {1,22,5,3};
		g.getMinMax(fr.latitude);
		
	}
}
