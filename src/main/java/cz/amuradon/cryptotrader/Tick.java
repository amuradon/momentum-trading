package cz.amuradon.cryptotrader;

import java.io.Serializable;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class Tick implements Serializable {

	private static final long serialVersionUID = -3323992486148500549L;
	
	private final String currencyPair;
	private final String last;
	private final String lowestAsk;
	private final String highestBid;
	private final String percentChange;
	private final String baseVolume;
	private final String quoteVolume;
	private final boolean isFrozen;
	private final String high24hr;
	private final String low24hr;

	public Tick(ArrayNode node) {
		currencyPair = node.get(0).asText();
		last = node.get(1).asText();
		lowestAsk = node.get(2).asText();
		highestBid = node.get(3).asText();
		percentChange = node.get(4).asText();
		baseVolume = node.get(5).asText();
		quoteVolume = node.get(6).asText();
		isFrozen = node.get(7).asBoolean();
		high24hr = node.get(8).asText();
		low24hr = node.get(9).asText();
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public String getLast() {
		return last;
	}

	public String getLowestAsk() {
		return lowestAsk;
	}

	public String getHighestBid() {
		return highestBid;
	}

	public String getPercentChange() {
		return percentChange;
	}

	public String getBaseVolume() {
		return baseVolume;
	}

	public String getQuoteVolume() {
		return quoteVolume;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public String getHigh24hr() {
		return high24hr;
	}

	public String getLow24hr() {
		return low24hr;
	}
}
