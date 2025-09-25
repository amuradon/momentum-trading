package cz.amuradon.tralon.motrade;

import java.util.Arrays;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScheduledJobs {
	
	private final MexcClient mexcClient;

	public ScheduledJobs(@RestClient final MexcClient mexcClient) {
		this.mexcClient = mexcClient;
	}
	
	@Scheduled(cron = "0 * * * * ?")
	void mexcTicker() {
		Ticker[] ticker = mexcClient.ticker();
		System.out.println(Arrays.toString(ticker));
	}
}
