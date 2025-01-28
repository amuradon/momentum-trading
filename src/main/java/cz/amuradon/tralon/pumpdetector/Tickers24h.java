package cz.amuradon.tralon.pumpdetector;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Tickers24h(@JsonProperty("mc") String status, @JsonProperty("result") List<Ticker> tickers) {

}
