package cz.amuradon.tralon.pumpdetector.tickers;

import java.util.Arrays;
import java.util.List;

public class PoloniexTickers24h implements Tickers24h {

	private final List<PoloniexTicker> tickers;  

	public PoloniexTickers24h(final PoloniexTicker[] tickers) {
		this.tickers = Arrays.asList(tickers);
	}
	
	@Override
	public List<? extends Ticker> getTickers() {
		return tickers;
	}

	@Override
	public String toString() {
		return String.format("PoloniexTickers24h(%s)", tickers);
	}
}
