package server;

import java.util.LinkedList;
import java.util.List;

/**
 * saving route given from json and returning in json
 */
public class Route {
	List<Double[]> route = new LinkedList<Double[]>();

	/**
	 * empty constructor needed for JAX.RS
	 */
	public Route() {
	}

	public void setRoute(List<Double[]> result) {
		this.route = result;
	}

	public List<Double[]> getRoute() {
		return route;
	}

	@Override
	public String toString() {
		String result = "Route:";
		for (Double[] p : this.route) {
			result += " [" + p[0].toString() + ", " + p[1].toString() + "] ,";
		}
		// for pretty output
		if (result != null && result.length() > 0 && result.charAt(result.length() - 3) == ']') {
			result = result.substring(0, result.length() - 2);
		}
		return result;
	}
}
