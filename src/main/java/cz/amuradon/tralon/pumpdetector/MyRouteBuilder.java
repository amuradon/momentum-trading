package cz.amuradon.tralon.pumpdetector;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class MyRouteBuilder extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:getData24h?period=60000&fixedRate=true")
            .to("vertx-http:https://sapi.xt.com/v4/public/ticker/24h")
            .unmarshal().json(JsonLibrary.Jackson, Tickers24h.class)
            .setHeader(TickerProcessor.EXCHANGE_HEADER_NAME, constant("XT"))
            .bean(TickerProcessor.BEAN_NAME)
            .log("${body}");
    }

}
