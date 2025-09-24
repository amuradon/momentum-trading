package cz.amuradon.tralon.motrade;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import cz.amuradon.tralon.motrade.tickers.MexcTicker;
import cz.amuradon.tralon.motrade.tickers.MexcTickers24h;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@ClientHeaderParam(name = "Content-Type", value = "application/json")
@RegisterRestClient(configKey = "mexc-api")
@Retry(maxRetries = 10)
public interface MexcClient {

	@Path("/ticker/24hr")
	@GET
	MexcTicker[] ticker();


}
