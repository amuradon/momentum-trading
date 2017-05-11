package cz.amuradon.cryptotrader;

import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.rx.ReactiveCamel;
import org.junit.Test;

import rx.functions.Action1;
import ws.wamp.jawampa.ApplicationError;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.connection.IWampConnectorProvider;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class WebSocketTest {

	@Test
	public void test() throws Exception {
		final WampClient client = createWampClient();
		final ReactiveCamel rxCamel = createRxCamel();

		client.statusChanged().subscribe(new Action1<WampClient.State>() {

			@Override
			public void call(WampClient.State t1) {
				if (t1 instanceof WampClient.ConnectedState) {
					rxCamel.sendTo(client.makeSubscription("ticker"), "direct://websocket");
				}
			}
		});
		client.open();

		Thread.sleep(10000);
	}

	private WampClient createWampClient() throws ApplicationError, Exception {
		final WampClientBuilder builder = new WampClientBuilder();
		final IWampConnectorProvider connectorProvider = new NettyWampClientConnectorProvider();
		builder.withConnectorProvider(connectorProvider)
				.withUri("wss://api.poloniex.com")
				.withRealm("realm1")
				.withInfiniteReconnects()
				.withReconnectInterval(5, TimeUnit.SECONDS);
		final WampClient client = builder.build();
		return client;
	}
	
	private ReactiveCamel createRxCamel() throws Exception {
		final CamelContext camel = new DefaultCamelContext();
		camel.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				// The body type is PubSubData
				from("direct://websocket")
						.log("${body.arguments}");
			}
		});
		camel.start();
		
		return new ReactiveCamel(camel);
	}
}
