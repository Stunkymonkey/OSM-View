package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class fileread {

	private String fileName;
	private BufferedReader bufferedReader;

	protected int AmountNodes;
	protected int AmountEdges;
	protected String line = null;
	protected int[] src, target, weight;
	protected int[] LUT;

	public fileread(String fileName) {

		this.fileName = fileName;
		try {
			FileReader fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);

			for (int i = 0; i < 5; i++) {
				line = bufferedReader.readLine();
			}

			AmountNodes = Integer.parseInt(bufferedReader.readLine());
			AmountEdges = Integer.parseInt(bufferedReader.readLine());
			createArrays();
			createLUT();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException e) {
			System.out.println("Error reading file '" + fileName + "'");
			// e.printStackTrace();
		}
	}

	public void createArrays() {
		int[] nodes = new int[this.AmountNodes];
		int[] edges = new int[this.AmountEdges];
		src = new int[this.AmountEdges];
		target = new int[this.AmountEdges];
		weight = new int[this.AmountEdges];

		String[] tmpArray;
		try {

			for (int i = 0; i < this.AmountNodes; i++) {
				line = bufferedReader.readLine();
			}

			for (int i = 0; i < this.AmountEdges; i++) {
				line = bufferedReader.readLine();
				tmpArray = line.split(" ");
				src[i] = Integer.parseInt(tmpArray[0]);
				target[i] = Integer.parseInt(tmpArray[1]);
				weight[i] = Integer.parseInt(tmpArray[2]);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void createLUT() {
		LUT = new int[this.AmountNodes +1];
		LUT[AmountNodes] = AmountEdges;
		int current_node = -1;
		for (int i = 0; i < src.length; i++) {
			if (current_node < src[i]) {
				current_node = src[i];
				if (current_node > 0) {
					for (int j = 0; j < (src[i]-src[i-1]); j++) {
						LUT[current_node - j] = i;
					}
				}				
			}
		}
	}

	public String getLine() {
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
			// e.printStackTrace();
		}
		return null;
	}
}
