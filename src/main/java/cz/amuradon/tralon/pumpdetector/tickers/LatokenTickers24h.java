package cz.amuradon.tralon.pumpdetector.tickers;

import java.util.Arrays;
import java.util.List;

public class LatokenTickers24h implements Tickers24h {

	private final List<LatokenTicker> tickers;  

	public LatokenTickers24h(final LatokenTicker[] tickers) {
		this.tickers = Arrays.asList(tickers);
	}
	
	@Override
	public List<? extends Ticker> getTickers() {
		return tickers;
	}

	@Override
	public String toString() {
		return String.format("LatokenTickers24h(%s)", tickers);
	}
}
