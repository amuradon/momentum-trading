package cz.amuradon.tralon.pumpdetector.tickers;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PoloniexTicker implements Ticker {
	
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

	@JsonProperty("ts")
	@Override
	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public BigDecimal getClose() {
		return close;
	}

	@JsonProperty("close")
	@Override
	public void setClose(String close) {
		this.close = new BigDecimal(close);
	}

	@Override
	public BigDecimal getQuantity() {
		return quantity;
	}

	@JsonProperty("quantity")
	@Override
	public void setQuantity(String quantity) {
		this.quantity = new BigDecimal(quantity);
	}

	@Override
	public BigDecimal getVolume() {
		return volume;
	}

	@JsonProperty("amount")
	@Override
	public void setVolume(String volume) {
		this.volume = new BigDecimal(volume);
	}
	
	@Override
	public String toString() {
		return String.format("PoloniexTicker(%s, %d, %s, %s, %s)", symbol, time, close, quantity, volume);
	}
}
