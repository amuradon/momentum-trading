package cz.amuradon.cryptotrader;

import org.junit.Test;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;

public class WebSocketTest {

    @Test
    public void test() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());
        
        stompClient.connect("wss://api.poloniex.com", new StompSessionHandlerAdapter() {
            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("ticker", new StompFrameHandler() {
                    
                    public void handleFrame(StompHeaders headers, Object payload) {
                        System.out.println(payload);
                    }
                    
                    public Type getPayloadType(StompHeaders headers) {
                        return Object.class;
                    }
                });
            }
        });
    }
}
