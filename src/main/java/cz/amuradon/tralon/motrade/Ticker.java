package cz.amuradon.tralon.motrade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Ticker(String symbol,
		long closeTime,
		BigDecimal lastPrice,
		BigDecimal quoteVolume,
		BigDecimal volume) {
	
	@Override
	public String toString() {
		return String.format("MexcTicker(%s, %d, %s, %s, %s)", symbol, closeTime, lastPrice, quoteVolume, volume);
	}
}
