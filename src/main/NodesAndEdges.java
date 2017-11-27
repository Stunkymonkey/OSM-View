package main;

public class NodesAndEdges {

	public static void createArrays() {
		initArrays();
		createNodeTable();
		createEdgeTables();
		createOffsetTable();
	}
	
	private static void initArrays() {
		Data.source = new int[Data.AmountEdges];
		Data.target = new int[Data.AmountEdges];
		Data.weight = new int[Data.AmountEdges];
		Data.x_buckets = new double[Data.AmountNodes];
		Data.y_buckets = new double[Data.AmountNodes];
	}

	private static void createNodeTable() {
		String line = null;
		String[] tmpArray;

		for (int i = 0; i < Data.AmountNodes; i++) {
			line = Fileread.getLine();
			tmpArray = line.split(" ");

			Data.x_buckets[i] = Double.parseDouble(tmpArray[2]);
			Data.y_buckets[i] = Double.parseDouble(tmpArray[3]);
			
			if (Data.x_buckets[i] > Data.max_x) {
				Data.max_x = Data.x_buckets[i];
			}
			if (Data.x_buckets[i] < Data.min_x) {
				Data.min_x = Data.x_buckets[i];
			}
			if (Data.y_buckets[i] > Data.max_y) {
				Data.max_y = Data.y_buckets[i];
			}
			if (Data.y_buckets[i] < Data.min_y) {
				Data.min_y = Data.y_buckets[i];
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
