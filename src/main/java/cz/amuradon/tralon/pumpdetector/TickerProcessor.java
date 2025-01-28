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
        
        for (Ticker ticker : tickers.tickers()) {
            String symbol = ticker.symbol();
            if (ticker.volume() <= 10000 || whitelist.contains(symbol)) {
                // TODO how to remove from whitelist if volume is above threshold for some time
                whitelist.add(symbol);
                        
                var priceChangeRate = ticker.priceChangeRate();
                var openLow = (ticker.low() - ticker.open()) / ticker.open();
                var openHigh = (ticker.high() - ticker.open()) / ticker.open();
                var closeLow = (ticker.low() - ticker.close()) / ticker.close();
                var closeHigh = (ticker.high() - ticker.close()) / ticker.close();
                var openLowHigh = (ticker.high() - ticker.low()) / ticker.open();
                var closeLowHigh = (ticker.high() - ticker.low()) / ticker.close();
                
                if (Math.abs(priceChangeRate) >= 0.1
                        || Math.abs(openLow) >= 0.1
                        || Math.abs(openHigh) >= 0.1
                        || Math.abs(closeLow) >= 0.1
                        || Math.abs(closeHigh) >= 0.1
                        || Math.abs(openLowHigh) >= 0.1
                        || Math.abs(closeLowHigh) >= 0.1) {

                    messages.add("Symbol " + symbol
                        + " price change rate " + (priceChangeRate * 100)
                        + " open-low " + (openLow * 100)
                        + " open-high " + (openHigh * 100)
                        + " close-low " + (closeLow * 100)
                        + " close-high " + (closeHigh * 100)
                        + " open-low-high " + (openLowHigh * 100)
                        + " close-low-high " + (closeLowHigh * 100)
                        );
                }
            
                var tickerMinusOne = minusOne.get(symbol);
                var tickerMinusTwo = minusTwo.get(ticker.symbol());
                if (tickerMinusOne != null) {
                    if (ticker.time() > tickerMinusOne.time()) {
                        minusOne.put(symbol, ticker);
                        minusTwo.put(symbol, tickerMinusOne);

                        // TODO small workaround to avoid NPE
                        if (tickerMinusTwo == null) {
                            tickerMinusTwo = tickerMinusOne;
                        }

                        var quantityChangeRateMinusOne = (ticker.quantity() - tickerMinusOne.quantity()) / tickerMinusOne.quantity();
                        var quantityChangeRateMinusTwo = (ticker.quantity() - tickerMinusTwo.quantity()) / tickerMinusTwo.quantity();
                        if (Math.abs(quantityChangeRateMinusOne) >= 0.1
                                || Math.abs(quantityChangeRateMinusTwo) >= 0.1) {
                            messages.add("Symbol " + symbol + " changes quantity - ticker-1 " + (quantityChangeRateMinusOne * 100) + " ticker-2 " + (quantityChangeRateMinusTwo * 100));
                        }

                        double timeDiff = ticker.time() - tickerMinusOne.time();
                        if (timeDiff > 600000) {
                            messages.add("Symbol " + symbol + " is waking up after " + (timeDiff / 60000) + " minutes");
                        }
                    }
                } else {
                    minusOne.put(symbol, ticker);
                }
            }
        }
        return messages;
    }
}
