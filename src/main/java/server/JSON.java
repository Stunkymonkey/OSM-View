package server;

import java.util.LinkedList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "setStart" path)
 */
@Path("route")
@Produces(MediaType.APPLICATION_JSON)
public class JSON {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Route route() {
		LinkedList<Double[]> asdf = new LinkedList<Double[]>();

		Double[] point1 = new Double[2];
		Double[] point2 = new Double[2];
		Double[] point3 = new Double[2];
		point1[0] = 43.444;
		point1[1] = 42.444;
		point2[0] = 44.444;
		point2[1] = 45.444;
		point3[0] = 46.444;
		point3[1] = 47.444;
		asdf.add(point1);
		asdf.add(point2);
		asdf.add(point3);

		Route route = new Route(asdf);
        return route;
    }
}
