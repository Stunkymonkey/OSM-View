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
		LUT = new int[this.AmountNodes + 1];
		int temp_node = AmountNodes-1;
		int temp;
		LUT[0] = 0;
		int j;
		LUT[AmountNodes] = AmountEdges;
		for (int i = src.length-1; i > 0; i--) {
			temp = src[i];
			if (temp_node == src[i]) {
				temp_node = src[i];
			} else if (temp_node == (src[i]+1)) {
				LUT[temp_node] = i+1;
				temp_node = src[i];
			} else if (temp < src[i] + 1) {
				for(j=temp_node; j > src[i]; j-- ) {
					LUT[j] = i+1;
				}
				i = j;
			}
			
			
		}

		// for (int i = src.length-1; i > 0; i--) {
		// System.out.println("temp"+temp_node);
		// if (temp_node == src[i]) {
		// break;
		// } else if(temp_node == src[i]-1) {
		// LUT[src[i]] = i;
		// temp_node = temp_node -1;
		//
		// } else if(temp_node >= src[i]-2) {
		// LUT[src[i]] = i;
		// }
		//
		// }
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
