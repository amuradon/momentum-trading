package cz.amuradon.tralon.pumpdetector;

import java.util.List;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import cz.amuradon.tralon.pumpdetector.tickers.LatokenTicker;
import cz.amuradon.tralon.pumpdetector.tickers.LatokenTickers24h;
import cz.amuradon.tralon.pumpdetector.tickers.MexcTicker;
import cz.amuradon.tralon.pumpdetector.tickers.MexcTickers24h;
import cz.amuradon.tralon.pumpdetector.tickers.PoloniexTicker;
import cz.amuradon.tralon.pumpdetector.tickers.PoloniexTickers24h;
import cz.amuradon.tralon.pumpdetector.tickers.XtTickers24h;

public class MyRouteBuilder extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:getDataXT?period=1000&fixedRate=true")
    		.routeId("getDataXT")
            .to("vertx-http:https://sapi.xt.com/v4/public/ticker/24h")
            .to("file:data/xt?fileName=${date:now:yyyyMMdd}/${date:now:yyyyMMdd-hh_mm_ss}.json")
            .to("direct:processXT");
 
        from("timer:getDataMEXC?period=1000&fixedRate=true")
        	.routeId("getDataMEXC")
            .to("vertx-http:https://api.mexc.com/api/v3/ticker/24hr")
            .to("file:data/mexc?fileName=${date:now:yyyyMMdd}/${date:now:yyyyMMdd-HH_mm_ss}.json")
            .to("direct:processMexc");

        from("timer:getDataLatoken?period=1000&fixedRate=true")
        	.routeId("getDataLatoken")
	        .to("vertx-http:https://api.latoken.com/v2/ticker")
	        .to("file:data/latoken?fileName=${date:now:yyyyMMdd}/${date:now:yyyyMMdd-HH_mm_ss}.json")
	        .to("direct:processLatoken");
        
        from("timer:getDataPoloniex?period=1000&fixedRate=true")
        		.routeId("getDataPoloniex")
            .to("vertx-http:https://api.poloniex.com/markets/ticker24h")
            .to("file:data/poloniex?fileName=${date:now:yyyyMMdd}/${date:now:yyyyMMdd-HH_mm_ss}.json")
            .to("direct:processPoloniex");
    	
//    	from("file:data/mexc/20250131?noop=true")
//    		.log("${header.CamelFileNameConsumed}")
//    		.unmarshal().json(JsonLibrary.Jackson, MexcTicker[].class)
//    		.process(e -> e.getMessage().setBody(new MexcTickers24h(e.getMessage().getBody(MexcTicker[].class))))
//    		.bean(SnapshotProcessor.BEAN_NAME);
//    	
//    	from("file:data/poloniex/20250202?noop=true")
//	    	.log("${header.CamelFileNameConsumed}")
//	    	.unmarshal().json(JsonLibrary.Jackson, PoloniexTicker[].class)
//	    	.process(e -> e.getMessage().setBody(new PoloniexTickers24h(e.getMessage().getBody(PoloniexTicker[].class))))
//	    	.bean(SnapshotProcessor.BEAN_NAME);
        
        from("direct:processXT")
        	.unmarshal().json(JsonLibrary.Jackson, XtTickers24h.class)
        	.bean(SnapshotProcessor.BEAN_NAME);
        	
        from("direct:processMexc")
	        .unmarshal().json(JsonLibrary.Jackson, MexcTicker[].class)
	    	.process(e -> e.getMessage().setBody(new MexcTickers24h(e.getMessage().getBody(MexcTicker[].class))))
	    	.bean(SnapshotProcessor.BEAN_NAME);
        
        from("direct:processLatoken")
	        .unmarshal().json(JsonLibrary.Jackson, LatokenTicker[].class)
	        .process(e -> e.getMessage().setBody(new LatokenTickers24h(e.getMessage().getBody(LatokenTicker[].class))))
	        .log("${body}")
	        .bean(SnapshotProcessor.BEAN_NAME);
        
        from("direct:processPoloniex")
	        .unmarshal().json(JsonLibrary.Jackson, PoloniexTicker[].class)
	    	.process(e -> e.getMessage().setBody(new PoloniexTickers24h(e.getMessage().getBody(PoloniexTicker[].class))))
	    	.bean(SnapshotProcessor.BEAN_NAME);
    }

}
