package cz.amuradon.cryptotrader;

import org.apache.camel.builder.RouteBuilder;


public class RestRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration("jetty")
			.host("localhost")
			.port(8080);
		
		rest("/")
			.get("/trader")
			.produces("text/html")
			.route()
			.setBody(constant("THIS IS MY BODY"))
			.to("freemarker:webapp/index.ftl");

		rest("/say")
			.get("/hello")
				.to("direct:hello")
			.get("/bye")
				.consumes("application/json")
				.to("direct:bye")
			.post("/bye")
				.to("mock:update");

		from("direct:hello")
			.log("REST")
			.transform()
			.constant("Hello World");
		from("direct:bye")
			.transform()
			.constant("Bye World");
	}

}
