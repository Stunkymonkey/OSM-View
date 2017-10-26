package main;

public class Main {

	public static void main(String[] args) {
		String filename;
		filename = "toy.fmi";
//		filename = "MV.fmi";
//		filename = "stgtregbz.fmi";
//		filename = "bw.fmi";

		fileread fr = new fileread(filename);
//		System.out.println(fr.LUT.length);
		for (int i = 0; i < fr.LUT.length; i++) {
			System.out.println(i + ": " + fr.LUT[i]);
		}
		
	}
}
