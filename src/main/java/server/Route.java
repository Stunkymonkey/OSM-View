package server;

import java.util.LinkedList;

public class Route {
	LinkedList<Point> route;

	public Route(LinkedList<Point> path) {
		this.route= path;
	}

	public LinkedList<Point> getPath() {
		return route;
	}

	@Override
	public String toString() {
		String result = "Route: ";
		for(Point p: this.route) {
			result += ", "  + p.toString();
		}
		return result;
	}
}
