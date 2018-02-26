package server;

import java.util.LinkedList;

public class Route {
	LinkedList<Double[]> route = new LinkedList<Double[]>();

	public Route(){}
	
	public Route(LinkedList<Double[]> route) {
		this.route= route;
	}

	public LinkedList<Double[]> getRoute() {
		return route;
	}

	@Override
	public String toString() {
		String result = "Route: ";
		for(Double[] p: this.route) {
			for(Double d: p) {
				result += ", "  + d.toString();
			}
		}
		return result;
	}
}
