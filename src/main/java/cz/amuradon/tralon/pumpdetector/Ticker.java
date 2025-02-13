package cz.amuradon.tralon.pumpdetector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Ticker(@JsonProperty("s") String symbol,
    @JsonProperty("t") double time,
    @JsonProperty("c") double close,
    @JsonProperty("q") double quantity,
    @JsonProperty("v") double volume) {

}
