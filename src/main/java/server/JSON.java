package server;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import backend.Grid;

/**
 * Root resource (exposed at "route" path)
 */
@Path("route")
@Produces(MediaType.APPLICATION_JSON)
public class JSON {

    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as "application/json" media type.
     *
     * @return String that will be returned as a application/json response.
     */
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Route route(Route question) {
    	System.out.println("Request: " + question.toString());
    	
    	List<Integer> edges;
    	List<Integer> nodes;
    	
		List<Double[]> result = new LinkedList<Double[]>();
		
		Double[] start_pos = question.getRoute().get(0);
		Double[] goal_pos = question.getRoute().get(1);
				
		System.out.println("start-pos: " + start_pos[0].toString() + ", " + start_pos[1].toString());
		System.out.println("goal-pos: " + goal_pos[0].toString() + ", " + goal_pos[1].toString());
		
		int start = Grid.getNearestNeighborNaive(start_pos[1], start_pos[0]);
		int goal = Grid.getNearestNeighborNaive(goal_pos[1], goal_pos[0]);
		//System.out.println(start == Grid.getNearestNeighbor(start_pos[1], start_pos[0]));
		//System.out.println(goal == Grid.getNearestNeighbor(goal_pos[1], goal_pos[0]));
		
		System.out.println("start_nn: " + start);
		System.out.println("goal_nn: " + goal);
		
		Main.d.setStart(start);
		edges = Main.d.findWay(goal);
		nodes = backend.Dijkstra.edgesToNodes(edges);
		result = backend.Grid.getCoordsOfPoints(nodes);

		Route route = new Route();
		route.setRoute(result);
        return route;
    }
}
