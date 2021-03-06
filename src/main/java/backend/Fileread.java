package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Fileread {

	private static String fileName;
	private static BufferedReader bufferedReader;

	/**
	 * open file with bufferedReader
	 * 
	 * @param fileName
	 */
	public Fileread(String fileName) {
		Fileread.fileName = fileName;
		try {
			FileReader fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			// ignore fmi-comments
			for (int i = 0; i < 5; i++) {
				bufferedReader.readLine();
			}
			Data.AmountNodes = Integer.parseInt(bufferedReader.readLine());
			Data.AmountEdges = Integer.parseInt(bufferedReader.readLine());
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Error reading file '" + fileName + "'");
			e.printStackTrace();
			System.exit(2);
		}
	}

	/**
	 * @return next line of bufferedReader
	 */
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
			System.exit(3);
		}
		return null;
	}
}
