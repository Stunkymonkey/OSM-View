package server;

import java.util.LinkedList;
import java.util.List;

public class Route {
	List<Double[]> route = new LinkedList<Double[]>();

	public Route(){}
	
	public void setRoute(List<Double[]> result) {
		this.route= result;
	}

	public List<Double[]> getRoute() {
		return route;
	}

	@Override
	public String toString() {
		String result = "Route: ";
		for(Double[] p: this.route) {
			result += " ["  + p[0].toString() + ", " + p[1].toString() + "] ,";
		}
		return result;
	}
}
