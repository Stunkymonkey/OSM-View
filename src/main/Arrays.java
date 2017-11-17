package main;

public class Arrays {
	public static void initArrays() {
		Data.source = new int[Data.AmountEdges];
		Data.target = new int[Data.AmountEdges];
		Data.weight = new int[Data.AmountEdges];
		Data.latitude = new double[Data.AmountNodes];
		Data.longitude = new double[Data.AmountNodes];
	}

	public static void createArrays() {
		createNodeTable();
		createEdgeTables();
		createOffsetTable();
	}

	private static void createNodeTable() {
		String line = null;
		String[] tmpArray;

		for (int i = 0; i < Data.AmountNodes; i++) {
			line = Fileread.getLine();
			System.out.println(line);
			tmpArray = line.split(" ");

			Data.latitude[i] = Double.parseDouble(tmpArray[2]);
			Data.longitude[i] = Double.parseDouble(tmpArray[3]);
			if (Data.latitude[i] > Data.max_lat) {
				Data.max_lat = Data.latitude[i];
			} else if (Data.latitude[i] < Data.min_lat) {
				Data.min_lat = Data.latitude[i];
			}
			if (Data.longitude[i] > Data.max_lon) {
				Data.max_lon = Data.longitude[i];
			} else if (Data.longitude[i] < Data.min_lon) {
				Data.min_lon = Data.longitude[i];
			}
		}
	}

	private static void createEdgeTables() {
		String line = null;
		String[] tmpArray;

		for (int i = 0; i < Data.AmountEdges; i++) {
			line = Fileread.getLine();
			tmpArray = line.split(" ");
			Data.source[i] = Integer.parseInt(tmpArray[0]);
			Data.target[i] = Integer.parseInt(tmpArray[1]);
			Data.weight[i] = Integer.parseInt(tmpArray[2]);
			for (int j = 0; j < Data.source.length; j++) {
				System.out.println(j + ": " + Data.source[j]);
			}
		}
	}

	private static void createOffsetTable() {
		Data.OffsetTable = new int[Data.AmountNodes + 1];
		Data.OffsetTable[Data.AmountNodes] = Data.AmountEdges;
		int current_node = -1;
		for (int i = 0; i < Data.source.length; i++) {
			if (current_node < Data.source[i]) {
				current_node = Data.source[i];
				if (current_node > 0) {
					for (int j = 0; j < (Data.source[i] - Data.source[i - 1]); j++) {
						Data.OffsetTable[current_node - j] = i;
					}
				}
			}
		}
	}
}
