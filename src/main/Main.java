package main;

public class Main {

	public static void main(String[] args) {
		fileread fr = new fileread("toy.fmi");
		System.out.println(fr.LUT.length);
		for (int i = 0; i < fr.LUT.length; i++) {
			System.out.println(i + ": " + fr.LUT[i]);
		}
		
	}
}
