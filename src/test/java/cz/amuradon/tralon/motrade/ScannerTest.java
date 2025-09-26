package cz.amuradon.tralon.motrade;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ScannerTest {

	@Mock
	private MexcClient mexcClientMock;
	
	private Scanner scanner;
	
	@BeforeEach
	void prepare() {
		scanner = new Scanner(mexcClientMock);
	}
	
	@Test
	void test() {
		when(mexcClientMock.ticker()).thenReturn(
				new Ticker[] {new Ticker("TKNUSDT", 1, new BigDecimal(100), new BigDecimal(10000), new BigDecimal(100))},
				new Ticker[] {new Ticker("TKNUSDT", 1, new BigDecimal(106), new BigDecimal(11600), new BigDecimal(110))});
		scanner.init();
		scanner.mexcTicker();
	}
}
