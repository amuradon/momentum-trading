package cz.amuradon.cryptotrader;

import java.util.concurrent.TimeUnit;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.rx.ReactiveCamel;

import rx.functions.Action1;
import ws.wamp.jawampa.ApplicationError;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.connection.IWampConnectorProvider;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;


public class WebSocketRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// The body type is PubSubData
		from("direct://websocket")
				.log("${body.arguments}");
		
		runWampClient();
	}

	private void runWampClient() throws ApplicationError, Exception {
		final WampClientBuilder builder = new WampClientBuilder();
		final IWampConnectorProvider connectorProvider = new NettyWampClientConnectorProvider();
		builder.withConnectorProvider(connectorProvider)
				.withUri("wss://api.poloniex.com")
				.withRealm("realm1")
				.withInfiniteReconnects()
				.withReconnectInterval(5, TimeUnit.SECONDS);
		final WampClient client = builder.build();
		
		final ReactiveCamel rxCamel = new ReactiveCamel(getContext());

		client.statusChanged().subscribe(new Action1<WampClient.State>() {

			@Override
			public void call(WampClient.State t1) {
				if (t1 instanceof WampClient.ConnectedState) {
					rxCamel.sendTo(client.makeSubscription("ticker"), "direct://websocket");
				}
			}
		});
		client.open();
	}
}
