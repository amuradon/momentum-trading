package cz.amuradon.tralon.pumpdetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.amuradon.tralon.pumpdetector.tickers.Ticker;
import cz.amuradon.tralon.pumpdetector.tickers.Tickers24h;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named(TickerProcessor.BEAN_NAME)
@RegisterForReflection
public class TickerProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TickerProcessor.class);

	public static final String BEAN_NAME = "TickerProcessor";

	public static final String EXCHANGE_HEADER_NAME = "SourceExchange";

    private Set<String> whitelist = new HashSet<>();
    private Map<String, Ticker> minusOne = new HashMap<>();
    private Map<String, Ticker> minusTwo = new HashMap<>();
    
    public List<String> process(@Header(EXCHANGE_HEADER_NAME) String exchange, @Body Tickers24h tickers) {
        List<String> messages = new ArrayList<>();
        messages.add("\n");
        
//        for (Ticker ticker : tickers.tickers()) {
//            String symbol = ticker.symbol();
//            if (ticker.volume() <= 10000 || whitelist.contains(symbol)) {
//                // TODO how to remove from whitelist if volume is above threshold for some time
//                whitelist.add(symbol);
//            
//                var tickerMinusOne = minusOne.get(symbol);
//                var tickerMinusTwo = minusTwo.get(ticker.symbol());
//                if (tickerMinusOne != null) {
//                    if (ticker.time() > tickerMinusOne.time()) {
//                        minusOne.put(symbol, ticker);
//                        minusTwo.put(symbol, tickerMinusOne);
//
//                        // TODO small workaround to avoid NPE
//                        if (tickerMinusTwo == null) {
//                            tickerMinusTwo = tickerMinusOne;
//                        }
//
//                        // TODO how to not report buys for ~$3 for price e.g. 25% higher, just ignore such trade
//                        var closeChangeRateMinusOne = (ticker.close() - tickerMinusOne.close()) / tickerMinusOne.close();
//                        var closeChangeRateMinusTwo = (ticker.close() - tickerMinusTwo.close()) / tickerMinusTwo.close();
//                        if (Math.abs(closeChangeRateMinusOne) >= 0.1
//                                || Math.abs(closeChangeRateMinusTwo) >= 0.1) {
//                            messages.add("PRICE: " + symbol + " - ticker-1 " + (closeChangeRateMinusOne * 100) + " ticker-2 "
//                                + (closeChangeRateMinusTwo * 100)
//                                + " ticker-2: " + tickerMinusTwo.close()
//                                + " ticker-1: " + tickerMinusOne.close()
//                                + " ticker: " +  ticker.close()
//                                + "\n");
//                        }
//
//                        var quantityChangeRateMinusOne = (ticker.quantity() - tickerMinusOne.quantity()) / tickerMinusOne.quantity();
//                        var quantityChangeRateMinusTwo = (ticker.quantity() - tickerMinusTwo.quantity()) / tickerMinusTwo.quantity();
//                        if (Math.abs(quantityChangeRateMinusOne) >= 0.1
//                                || Math.abs(quantityChangeRateMinusTwo) >= 0.1) {
//                            messages.add("QTY: " + symbol + " - ticker-1 " + (quantityChangeRateMinusOne * 100) + " ticker-2 "
//                                + (quantityChangeRateMinusTwo * 100)
//                                + " ticker-2: " + tickerMinusTwo.quantity()
//                                + " ticker-1: " + tickerMinusOne.quantity()
//                                + " ticker: " +  ticker.quantity()
//                                + "\n");
//                        }
//
//                        // XXX Wake up is not correct as the ticker is updated without a trade. Solution might to query last trade for a given symbol.
//                        /*
//                        double timeDiff = ticker.time() - tickerMinusOne.time();
//                        if (timeDiff > 600000) {
//                            messages.add("Symbol " + symbol + " is waking up after " + (timeDiff / 60000) + " minutes");
//                        }
//                        */
//                    }
//                } else {
//                    minusOne.put(symbol, ticker);
//                }
//            }
//        }
        return messages;
    }
}
