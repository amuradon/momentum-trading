package cz.amuradon.cryptotrader;

import org.apache.camel.main.Main;

public class MyMain {

	private static Main main;

	public static void main(String[] args) throws Exception {
		main = new Main();
		main.addRouteBuilder(new WebSocketRouter());
		main.addRouteBuilder(new RestRouter());
		main.run();
	}
}
