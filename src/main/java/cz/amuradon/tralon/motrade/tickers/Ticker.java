package cz.amuradon.tralon.motrade.tickers;

import java.math.BigDecimal;

public interface Ticker {
	
	String getSymbol();

	void setSymbol(String symbol);

	long getTime();

	void setTime(long time);

	BigDecimal getClose();

	void setClose(String close);

	BigDecimal getQuantity();

	void setQuantity(String quantity);

	BigDecimal getVolume();

	void setVolume(String volume);
	
}
