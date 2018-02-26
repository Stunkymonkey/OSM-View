package server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

import backend.*;

/**
 * Main class.
 *
 */
public class Main {
	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "http://localhost:8081/api/";

	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		Backend.main(args);
		final HttpServer server = startServer();
		System.out.println(String.format(
				"API with WADL available at " + "%sapplication.wadl",
				BASE_URI));
		System.out.println(String.format(
				"APP is available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI.replace("api/", "")));
		System.in.read();
		server.stop();
	}

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
	 * application.
	 * 
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer() {
		// create a resource config that scans for JAX-RS resources and providers
		// in server package
		final ResourceConfig rc = new ResourceConfig().packages("server");
		
		rc.register(new CORSFilter());

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

		server.getServerConfiguration().addHttpHandler(new org.glassfish.grizzly.http.server.CLStaticHttpHandler(
				Main.class.getClassLoader(), "/"), "/");

		return server;
	}
}
