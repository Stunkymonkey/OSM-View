package server;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import backend.Grid;

/**
 * Root resource (exposed at "route" path)
 */
@Path("route")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JSON {

	/**
	 * Method handling HTTP POST requests. The returned object will be sent to the
	 * client as "application/json" media type.
	 *
	 * @return String that will be returned as a application/json response.
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Route route(Route question) {
		long totalrequestTime = System.currentTimeMillis();
		// outputting request
		System.out.println("Request: " + question.toString());

		// init lists for results
		List<Integer> edges;
		List<Integer> nodes;

		List<Double[]> result = new LinkedList<Double[]>();

		// get coordinates of request
		Double[] start_pos = question.getRoute().get(0);
		Double[] goal_pos = question.getRoute().get(1);

		// get nearest neighbors of given coordinates
		int start = Grid.getNearestNeighborNaive(start_pos[1], start_pos[0]);
		int goal = Grid.getNearestNeighborNaive(goal_pos[1], goal_pos[0]);

		// run Dijkstra on graph
		Main.d.setStart(start);
		edges = Main.d.findWay(goal);
		// get nodes from edges
		nodes = backend.Dijkstra.edgesToNodes(edges);
		// get coordinates from nodes
		result = backend.Grid.getCoordsOfPoints(nodes);

		// return response with route
		Route route = new Route();
		route.setRoute(result);
		System.out.println(
				String.format("Answered Request in : %ds", ((System.currentTimeMillis() - totalrequestTime) / 1000)));
		return route;
	}
}
