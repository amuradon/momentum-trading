package cz.amuradon.tralon.pumpdetector.tickers;

import java.util.Arrays;
import java.util.List;

public class MexcTickers24h implements Tickers24h {

	private final List<MexcTicker> tickers;  

	public MexcTickers24h(final MexcTicker[] tickers) {
		this.tickers = Arrays.asList(tickers);
	}
	
	@Override
	public List<? extends Ticker> getTickers() {
		return tickers;
	}

	@Override
	public String toString() {
		return String.format("MexcTickers24h(%s)", tickers);
	}
}
