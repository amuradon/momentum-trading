package cz.amuradon.cryptotrader;

import org.apache.camel.CamelContext;
import org.apache.camel.main.Main;
import org.apache.camel.main.MainListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMain {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyMain.class);
	
	private static Main main;

	public static void main(String[] args) throws Exception {
		main = new Main();
		final WebSocketRouter wsBuilder = new WebSocketRouter();
		main.addRouteBuilder(wsBuilder);
		main.addRouteBuilder(new RestRouter());
		
		main.addMainListener(new MainListenerSupport() {
			
			@Override
			public void configure(CamelContext context) {
				try {
					wsBuilder.runWampClient(context);
				} catch (Exception e) {
					LOGGER.error("Oh, shit.", e);
				}
			}
		});
		
		main.run();
	}
}
