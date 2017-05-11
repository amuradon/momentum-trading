package cz.amuradon.cryptotrader;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import rx.functions.Action1;
import ws.wamp.jawampa.WampClient;
import ws.wamp.jawampa.WampClientBuilder;
import ws.wamp.jawampa.connection.IWampConnectorProvider;
import ws.wamp.jawampa.transport.netty.NettyWampClientConnectorProvider;

public class WebSocketTest {

	@Test
	public void test() throws Exception {
		WampClient client;
		WampClientBuilder builder = new WampClientBuilder();
		IWampConnectorProvider connectorProvider = new NettyWampClientConnectorProvider();
		builder.withConnectorProvider(connectorProvider).withUri("wss://api.poloniex.com").withRealm("realm1")
				.withInfiniteReconnects().withReconnectInterval(5, TimeUnit.SECONDS);
		client = builder.build();

		client.statusChanged().subscribe(new Action1<WampClient.State>() {

			@Override
			public void call(WampClient.State t1) {
				if (t1 instanceof WampClient.ConnectedState) {
					// subscription =
					client.makeSubscription("ticker").subscribe((s) -> {
						System.out.println(s.arguments());
					});
				}
			}
		});
		client.open();

		Thread.sleep(10000);
	}
}
