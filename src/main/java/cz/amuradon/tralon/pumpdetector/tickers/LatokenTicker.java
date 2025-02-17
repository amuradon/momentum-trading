package cz.amuradon.tralon.pumpdetector.tickers;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LatokenTicker implements Ticker {
	
	private String symbol;
    private long time;
    private BigDecimal close;
    private BigDecimal quantity;
    private BigDecimal volume;
	
    @Override
	public String getSymbol() {
		return symbol;
	}

	@JsonProperty("symbol")
	@Override
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public long getTime() {
		return time;
	}

	@JsonProperty("updateTimestamp")
	@Override
	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public BigDecimal getClose() {
		return close;
	}

	@JsonProperty("lastPrice")
	@Override
	public void setClose(String close) {
		this.close = new BigDecimal(close);
	}

	@Override
	public BigDecimal getQuantity() {
		return quantity;
	}

	@JsonProperty("amount24h")
	@Override
	public void setQuantity(String quantity) {
		this.quantity = new BigDecimal(quantity);
	}

	@Override
	public BigDecimal getVolume() {
		return volume;
	}

	@JsonProperty("volume24h")
	@Override
	public void setVolume(String volume) {
		this.volume = new BigDecimal(volume);
	}
	
	@Override
	public String toString() {
		return String.format("LatokenTicker(%s, %d, %s, %s, %s)", symbol, time, close, quantity, volume);
	}
}
