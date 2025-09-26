package cz.amuradon.tralon.motrade;

import java.math.BigDecimal;

public record SymbolValues(BigDecimal lastPrice,
		BigDecimal quoteVolume) {

}
