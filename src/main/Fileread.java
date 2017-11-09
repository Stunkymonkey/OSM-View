package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Fileread {

	private String fileName;
	private BufferedReader bufferedReader;

	protected int AmountNodes;
	protected int AmountEdges;
	protected String line = null;
	protected int[] src, target, weight;
	protected double[] latitude, longitude;
	protected int[] LUT;
	protected double max_lat = Double.MAX_VALUE;
	protected double max_lon = Double.MAX_VALUE;
	protected double min_lat = Double.MIN_VALUE;
	protected double min_lon = Double.MIN_VALUE;

	public Fileread(String fileName) {

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
		latitude = new double[this.AmountEdges];
		longitude = new double[this.AmountEdges];



		String[] tmpArray;
		try {

			for (int i = 0; i < this.AmountNodes; i++) {
				line = bufferedReader.readLine();
				tmpArray = line.split(" ");

				latitude[i] = Double.parseDouble(tmpArray[2]);
				longitude[i] = Double.parseDouble(tmpArray[3]);
				if (latitude[i] > max_lat) {
					max_lat = latitude[i];
				} else if (latitude[i] < min_lat) {
					min_lat = latitude[i];
				}
				if (longitude[i] > max_lon) {
					max_lon = longitude[i];
				} else if (longitude[i] < min_lon) {
					min_lon = longitude[i];
				}

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
		LUT = new int[this.AmountNodes + 1];
		LUT[AmountNodes] = AmountEdges;
		int current_node = -1;
		for (int i = 0; i < src.length; i++) {
			if (current_node < src[i]) {
				current_node = src[i];
				if (current_node > 0) {
					for (int j = 0; j < (src[i] - src[i - 1]); j++) {
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
