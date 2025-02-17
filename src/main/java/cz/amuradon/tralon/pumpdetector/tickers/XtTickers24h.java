package cz.amuradon.tralon.pumpdetector.tickers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XtTickers24h implements Tickers24h {
	List<XtTicker> tickers;  
	
	@Override
	public List<? extends Ticker> getTickers() {
		return tickers;
	}

	@JsonProperty("result") 
	public void setTickers(List<XtTicker> tickers) {
		this.tickers = tickers;
	}
	
	@Override
	public String toString() {
		return String.format("XtTickers24h(%s)", tickers);
	}
}
