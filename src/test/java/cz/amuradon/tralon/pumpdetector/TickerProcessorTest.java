package cz.amuradon.tralon.pumpdetector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.amuradon.tralon.pumpdetector.tickers.Ticker;
import cz.amuradon.tralon.pumpdetector.tickers.Tickers24h;

public class TickerProcessorTest {

    private TickerProcessor processor;

    private List<Ticker> list;

    private Tickers24h tickers;

    private long now;

//    @BeforeEach
//    public void prepare() {
//        processor = new TickerProcessor();
//        list = new ArrayList<>();
//        tickers = new Tickers24h("SUCCESS", list);
//        now = new Date().getTime();
//    }

//    @Test
//    void testProcessPrice() {
//        list.add(new Ticker("coina_usdt", now, 10, 10, 100));
//        list.add(new Ticker("coinb_usdt", now, 10, 10, 100));
//
//        var messages = processor.process("XT", tickers);
//        
//        Assert.assertTrue(messages.isEmpty());
//
//        list.clear();
//        list.add(new Ticker("coina_usdt", now + 1, 11, 10, 100));
//        list.add(new Ticker("coinb_usdt", now + 1, 10, 10, 100));
//        messages = processor.process("XT", tickers);
//
//        Assert.assertFalse(messages.isEmpty());
//        Assert.assertEquals("Symbol coina_usdt changes close price - ticker-1 10.0 ticker-2 10.0", messages.get(0));
//        
//        list.clear();
//        list.add(new Ticker("coina_usdt", now + 2, 13, 10, 100));
//        list.add(new Ticker("coinb_usdt", now + 2, 10, 10, 100));
//        messages = processor.process("XT", tickers);
//        
//        Assert.assertFalse(messages.isEmpty());
//        Assert.assertEquals("Symbol coina_usdt changes close price - ticker-1 18.181818181818183 ticker-2 30.0", messages.get(0));
//    }
//
//    @Test
//    void testProcessQuantity() {
//        list.add(new Ticker("coina_usdt", now, 10, 100, 100));
//        list.add(new Ticker("coinb_usdt", now, 10, 100, 100));
//
//        var messages = processor.process("XT", tickers);
//        
//        Assert.assertTrue(messages.isEmpty());
//
//        list.clear();
//        list.add(new Ticker("coina_usdt", now + 1, 10, 110, 100));
//        list.add(new Ticker("coinb_usdt", now + 1, 10, 109, 100));
//        messages = processor.process("XT", tickers);
//
//        Assert.assertFalse(messages.isEmpty());
//        Assert.assertEquals("Symbol coina_usdt changes quantity - ticker-1 10.0 ticker-2 10.0", messages.get(0));
//        
//        list.clear();
//        list.add(new Ticker("coina_usdt", now + 2, 10, 122, 100));
//        list.add(new Ticker("coinb_usdt", now + 2, 10, 109, 100));
//        messages = processor.process("XT", tickers);
//        
//        Assert.assertFalse(messages.isEmpty());
//        Assert.assertEquals("Symbol coina_usdt changes quantity - ticker-1 10.909090909090908 ticker-2 22.0", messages.get(0));
//    }
//
//    @Test
//    void testProcessWakingUp() {
//        list.add(new Ticker("coina_usdt", now, 10, 10, 100));
//        list.add(new Ticker("coinb_usdt", now, 10, 10, 100));
//
//        var messages = processor.process("XT", tickers);
//
//        list.clear();
//        list.add(new Ticker("coina_usdt", now + 600001, 10, 10, 100));
//        list.add(new Ticker("coinb_usdt", now + 600000, 10, 10, 100));
//        messages = processor.process("XT", tickers);
//
//        Assert.assertFalse(messages.isEmpty());
//        Assert.assertEquals("Symbol coina_usdt is waking up after 10.000016666666667 minutes", messages.get(0));
//    }
}
