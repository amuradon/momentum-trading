package cz.amuradon.tralon.motrade;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import cz.amuradon.tralon.motrade.tickers.MexcTicker;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScheduledJobs {
	
	private final MexcClient mexcClient;

	public ScheduledJobs(@RestClient final MexcClient mexcClient) {
		this.mexcClient = mexcClient;
	}
	
	@Scheduled(every = "1m")
	void mexcTicker() {
		MexcTicker[] ticker = mexcClient.ticker();
		System.out.println(ticker);
		
		// SnapshotProcessor
	}
}
