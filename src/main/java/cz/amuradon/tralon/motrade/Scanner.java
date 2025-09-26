package cz.amuradon.tralon.motrade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Scanner {
	
	private final MexcClient mexcClient;
	
	private final Map<String, LinkedList<SymbolValues>> movingValues;

	public Scanner(@RestClient final MexcClient mexcClient) {
		this.mexcClient = mexcClient;
		this.movingValues = new HashMap<>();
	}
	
	// TODO 
	@Startup
	void init() {
		Ticker[] tickers = mexcClient.ticker();
		for (Ticker ticker : tickers) {
			if (filter(ticker)) {
				LinkedList<SymbolValues> values = initiateValues(ticker);
				movingValues.put(ticker.symbol(), values);
			}
		}
	}

	private LinkedList<SymbolValues> initiateValues(Ticker ticker) {
		LinkedList<SymbolValues> values = new LinkedList<>();
		for (int i = 0; i < 15; i++) {
			values.add(new SymbolValues(ticker.lastPrice(), ticker.quoteVolume()));
		}
		return values;
	}
	
	@Scheduled(cron = "0 * * * * ?", delay = 40, delayUnit = TimeUnit.SECONDS)
	void mexcTicker() {
		Ticker[] tickers = mexcClient.ticker();
		for (Ticker ticker : tickers) {
			if (filter(ticker)) {
				LinkedList<SymbolValues> values = movingValues.computeIfAbsent(ticker.symbol(), k -> initiateValues(ticker));
				SymbolValues m15 = values.getFirst();
				SymbolValues m5 = values.get(9);
				SymbolValues m1 = values.getLast();
				
				// Spocitat % rozdilu pro cenu a volume
				BigDecimal lastPrice = ticker.lastPrice();
				BigDecimal quoteVolume = ticker.quoteVolume();
				
				BigDecimal m1PriceChange = getPercentage(lastPrice, m1.lastPrice());
				BigDecimal m5PriceChange = getPercentage(lastPrice, m5.lastPrice());
				BigDecimal m15PriceChange = getPercentage(lastPrice, m15.lastPrice());
				if (aboveThreshold(m1PriceChange) || aboveThreshold(m5PriceChange) || aboveThreshold(m15PriceChange)) {
					BigDecimal m1VolumeChange = getPercentage(quoteVolume, m1.quoteVolume());
					BigDecimal m5VolumeChange = getPercentage(quoteVolume, m5.quoteVolume());
					BigDecimal m15VolumeChange = getPercentage(quoteVolume, m15.quoteVolume());
					Log.infof("Symbol %s, 1p: %s, 5p: %s, 15p: %s, 1v: %s, 5v: %s, 15v: %s", ticker.symbol(),
							m1PriceChange, m5PriceChange, m15PriceChange, m1VolumeChange, m5VolumeChange,
							m15VolumeChange);
				}
				values.removeFirst();
				values.addLast(new SymbolValues(ticker.lastPrice(), ticker.quoteVolume()));
			}
		}
	}

	private BigDecimal getPercentage(BigDecimal current, BigDecimal previous) {
		return current.subtract(previous).divide(safeZero(previous), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
	}
	
	private BigDecimal safeZero(BigDecimal value) {
		return value.compareTo(BigDecimal.ZERO) == 0 ? new BigDecimal("0.000001") : value;
	}
	
	private boolean aboveThreshold(BigDecimal value) {
		return value.compareTo(BigDecimal.valueOf(5)) == 1;
	}
	
	private boolean filter(Ticker ticker) {
		return ticker.symbol().endsWith("USDT") && ticker.quoteVolume().compareTo(BigDecimal.valueOf(50000)) == 1;
	}
}
