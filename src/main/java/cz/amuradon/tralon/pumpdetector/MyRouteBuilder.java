package cz.amuradon.tralon.pumpdetector;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class MyRouteBuilder extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {
        /*
        from("timer:getData24h?period=60000&fixedRate=true")
            .to("vertx-http:https://sapi.xt.com/v4/public/ticker/24h")
            .unmarshal().json(JsonLibrary.Jackson, Tickers24h.class)
            .setHeader(TickerProcessor.EXCHANGE_HEADER_NAME, constant("XT"))
            .bean(TickerProcessor.BEAN_NAME)
            .log("${body}");
            */

        // Store ticker 24h every second to file to run just before pump for analysis and back testing
        /*
        from("timer:getDataXT?period=1000&fixedRate=true")
            .to("vertx-http:https://sapi.xt.com/v4/public/ticker/24h")
            .to("file:data/xt?fileName=${date:now:yyyyMMdd}/${date:now:yyyyMMdd-hh_mm_ss}.json");
        */
        /*
        from("timer:getDataMEXC?period=1000&fixedRate=true&time=2025-01-31 15:00:00")
            .to("vertx-http:https://api.mexc.com/api/v3/ticker/24hr")
            .to("file:data/mexc?fileName=${date:now:yyyyMMdd}/${date:now:yyyyMMdd-HH_mm_ss}.json");
        */
        from("timer:getDataPoloniex?period=1000&fixedRate=true")
            .to("vertx-http:https://api.poloniex.com/markets/ticker24h")
            .to("file:data/poloniex?fileName=${date:now:yyyyMMdd}/${date:now:yyyyMMdd-HH_mm_ss}.json");
    }

}
