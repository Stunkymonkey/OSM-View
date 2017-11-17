package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Fileread {

	private static String fileName;
	private static BufferedReader bufferedReader;

	public Fileread(String fileName) {
		Fileread.fileName = fileName;
		try {
			FileReader fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);

			for (int i = 0; i < 5; i++) {
				bufferedReader.readLine();
			}
			Data.AmountNodes = Integer.parseInt(bufferedReader.readLine());
			Data.AmountEdges = Integer.parseInt(bufferedReader.readLine());
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException e) {
			System.out.println("Error reading file '" + fileName + "'");
			e.printStackTrace();
		}
	}

	public static String getLine() {
		String line = null;
		try {
			line = bufferedReader.readLine();
			if (line != null) {
				return line;
			} else {
				bufferedReader.close();
			}
		} catch (IOException e) {
			System.out.println("Error reading file '" + fileName + "'");
			e.printStackTrace();
		}
		return null;
	}
}
