package cz.amuradon.tralon.pumpdetector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TickerProcessorTest {

    private TickerProcessor processor;

    private List<Ticker> list;

    private Tickers24h tickers;

    private long now;

    @BeforeEach
    public void prepare() {
        processor = new TickerProcessor();
        list = new ArrayList<>();
        tickers = new Tickers24h("SUCCESS", list);
        now = new Date().getTime();
    }

    @Test
    void testProcessPrices() {
        list.add(new Ticker("coina_usdt", now, 0.1, 10, 10, 10, 10, 10, 100));
        list.add(new Ticker("coinb_usdt", now, 0.0, 10, 9, 9.5, 9.5, 10, 100));
        list.add(new Ticker("coinc_usdt", now, 0.0, 10, 1, 10, 1, 10, 100));
        list.add(new Ticker("coind_usdt", now, 0.0, 10, 1, 10, 10, 10, 100));
        
        var messages = processor.process("XT", tickers);
        
        Assert.assertEquals(4, messages.size());
        Assert.assertEquals("Symbol coina_usdt price change rate 10.0 open-low 0.0 open-high 0.0 close-low 0.0 close-high 0.0 open-low-high 0.0 close-low-high 0.0", messages.get(0));
        Assert.assertEquals("Symbol coinb_usdt price change rate 0.0 open-low -10.0 open-high -5.0 close-low -5.263157894736842 close-high 0.0 open-low-high 5.0 close-low-high 5.263157894736842", messages.get(1));
        Assert.assertEquals("Symbol coinc_usdt price change rate 0.0 open-low -90.0 open-high 0.0 close-low 0.0 close-high 900.0 open-low-high 90.0 close-low-high 900.0", messages.get(2));
        Assert.assertEquals("Symbol coind_usdt price change rate 0.0 open-low -90.0 open-high 0.0 close-low -90.0 close-high 0.0 open-low-high 90.0 close-low-high 90.0", messages.get(3));
    }

    @Test
    void testProcessQuantity() {
        list.add(new Ticker("coina_usdt", now, 0.0, 10, 10, 10, 10, 100, 100));
        list.add(new Ticker("coinb_usdt", now, 0.0, 10, 10, 10, 10, 100, 100));

        var messages = processor.process("XT", tickers);
        
        Assert.assertTrue(messages.isEmpty());

        list.clear();
        list.add(new Ticker("coina_usdt", now + 1, 0.0, 10, 10, 10, 10, 110, 100));
        list.add(new Ticker("coinb_usdt", now + 1, 0.0, 10, 10, 10, 10, 109, 100));
        messages = processor.process("XT", tickers);

        Assert.assertFalse(messages.isEmpty());
        Assert.assertEquals("Symbol coina_usdt changes quantity - ticker-1 10.0 ticker-2 10.0", messages.get(0));
        
        list.clear();
        list.add(new Ticker("coina_usdt", now + 2, 0.0, 10, 10, 10, 10, 122, 100));
        list.add(new Ticker("coinb_usdt", now + 2, 0.0, 10, 10, 10, 10, 109, 100));
        messages = processor.process("XT", tickers);
        
        Assert.assertFalse(messages.isEmpty());
        Assert.assertEquals("Symbol coina_usdt changes quantity - ticker-1 10.909090909090908 ticker-2 22.0", messages.get(0));
    }

    @Test
    void testProcessWakingUp() {
        list.add(new Ticker("coina_usdt", now, 10.0, 10, 10, 10, 10, 10, 100));
        list.add(new Ticker("coinb_usdt", now, 10.0, 10, 10, 10, 10, 10, 100));

        var messages = processor.process("XT", tickers);

        list.clear();
        list.add(new Ticker("coina_usdt", now + 600001, 0.0, 10, 10, 10, 10, 10, 100));
        list.add(new Ticker("coinb_usdt", now + 600000, 0.0, 10, 10, 10, 10, 10, 100));
        messages = processor.process("XT", tickers);

        Assert.assertFalse(messages.isEmpty());
        Assert.assertEquals("Symbol coina_usdt is waking up after 10.000016666666667 minutes", messages.get(0));
    }
}
