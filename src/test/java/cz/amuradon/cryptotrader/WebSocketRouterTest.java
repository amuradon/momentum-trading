package cz.amuradon.cryptotrader;

import java.util.Arrays;

import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import ws.wamp.jawampa.PubSubData;

public class WebSocketRouterTest {

	/*
	 * ["BTC_LTC","0.01382900","0.01382900","0.01380110","0.00072364","21243.85331169","1633655.91188318",0,"0.01449899","0.01163000"]
		["BTC_ETC","0.00311803","0.00311803","0.00310494","-0.07772693","5443.73664206","1656063.31782075",0,"0.00374900","0.00303780"]
		["BTC_OMNI","0.00703327","0.00703302","0.00662386","-0.07581009","60.70979500","8855.88577482",0,"0.00799997","0.00525964"]
		["BTC_VRC","0.00008331","0.00008173","0.00008015","-0.08751369","38.15723898","447100.76906802",0,"0.00009400","0.00007800"]
	 */
	@Test
	public void test() throws Exception {
		final DefaultCamelContext context = new DefaultCamelContext();
		context.addRoutes(new WebSocketRouter());
		context.start();
		
		final JsonNodeFactory factory = new JsonNodeFactory(true);
		final ArrayNode arguments = factory.arrayNode();
		arguments.addAll(Arrays.asList(
				factory.textNode("BTC_LTC"), // currencyPair
				factory.textNode("0.01382900"), // last
				factory.textNode("0.01382900"), // lowestAsk
				factory.textNode("0.01380110"), // highestBid
				factory.textNode("0.00072364"), // percentChange
				factory.textNode("21243.85331169"), // baseVolume
				factory.textNode("1633655.91188318"), // quoteVolume
				factory.numberNode(0), // isFrozen
				factory.textNode("0.01449899"), // 24hrHigh
				factory.textNode("0.01163000"))); // 24hrLow
		
		final PubSubData data = new PubSubData(null, arguments, null);
		context.createProducerTemplate().sendBody(WebSocketRouter.DIRECT_WEBSOCKET, data);
	}
}
