package server;

import java.util.LinkedList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import server.Point;
import server.Route;

@Path("/json")
public class JSONService {
	
	Point start = new Point();
	Point goal = new Point();

	@GET
	@Path("/getRoute")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRoute() {

		Point point1 = new Point();
		point1.setLongitude(43.444);
		point1.setLatitude(42.444);
		Point point2 = new Point();
		point2.setLongitude(44.444);
		point2.setLatitude(45.444);
		LinkedList<Point> asdf = new LinkedList<Point>();
		asdf.add(point1);
		asdf.add(point2);

		Route path = new Route(asdf);

		return Response.ok(path).build();
	}

	@POST
	@Path("/setStart")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setStartJSON(Point start) {
		this.start = start;
		String result = "Start saved : " + start;
		return Response.status(201).entity(result).build();
		
	}
	
	@POST
	@Path("/setGoal")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setGoalJSON(Point goal) {
		this.goal = goal;
		String result = "Goal saved : " + goal;
		return Response.status(201).entity(result).build();
		
	}
	
}