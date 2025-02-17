package cz.amuradon.tralon.pumpdetector;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Body;

import cz.amuradon.tralon.pumpdetector.tickers.Ticker;
import cz.amuradon.tralon.pumpdetector.tickers.Tickers24h;
import io.quarkus.logging.Log;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

// Dependent as there needs to be a new instance for each route
@Dependent
@Named(SnapshotProcessor.BEAN_NAME)
@RegisterForReflection
public class SnapshotProcessor {

	public static final String BEAN_NAME = "SnapshotProcessor";

	public static final String EXCHANGE_HEADER_NAME = "SourceExchange";

    private Map<String, Ticker> snapshots = new HashMap<>();
    
    public List<String> process(@Body Tickers24h tickers) {
    	System.out.println("SnapshotProcessor " + this.hashCode());
        List<String> messages = new ArrayList<>();
        messages.add("\n");
        
        if (snapshots.isEmpty()) {
        	for (Ticker ticker : tickers.getTickers()) {
        		snapshots.put(ticker.getSymbol(), ticker);
        	}
        } else {
        	for (Ticker ticker : tickers.getTickers()) {
        		Ticker snapshot = snapshots.get(ticker.getSymbol());
        		
        		BigDecimal qtyChange = getPercentChange(ticker.getQuantity(), snapshot.getQuantity());
        		BigDecimal priceChange = getPercentChange(ticker.getClose(), snapshot.getClose());
        		if (qtyChange.compareTo(new BigDecimal(1000)) > 0) {
					Log.infof("Symbol %s has quantity change %s, initial %s, now %s, vol %s, price %s",
        					ticker.getSymbol(),
        					qtyChange, snapshot.getQuantity(), ticker.getQuantity(),
        					getPercentChange(ticker.getVolume(), snapshot.getVolume()),
        					priceChange);
        		}
        		
//        		BigDecimal change = getPercentChange(ticker.getClose(), snapshot.getClose());
//        		if (change.compareTo(new BigDecimal(percent)) > 0) {
//        			Log.infof("Symbol %s has close change %s, initial %s, now %s", ticker.getSymbol(),
//        					change, snapshot.getClose(), ticker.getClose());
//        		}
        	}
        }
        
        return messages;
    }


	private BigDecimal getPercentChange(BigDecimal ticker, BigDecimal snapshot) {
		if (snapshot.compareTo(BigDecimal.ZERO) != 0) {
			return ticker.subtract(snapshot).divide(snapshot, 6, RoundingMode.CEILING).multiply(new BigDecimal(100));
		} else {
			return BigDecimal.ZERO;
		}
			
	}
}
