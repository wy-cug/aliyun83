package com.aliyun.code83.round4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.StandardWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
        properties = "spring.main.web-application-type=reactive")
@ActiveProfiles("test")
class ReactiveWebSocketHandlerTest {

    @LocalServerPort
    private String port;

    @Test
    public void echo() throws Exception {
        int count = 4;
        final Duration duration = Duration.ofMillis(5000);
        Flux<String> input = Flux.range(1, count).map(index -> "msg-" + index);

        ReplayProcessor<Object> output = ReplayProcessor.create(count);

        WebSocketClient client = new StandardWebSocketClient();
        HttpHeaders headers = new HttpHeaders();

        headers.setBasicAuth("test", "test");
        client.execute(getUrl("/ws/test"),
                        headers,
                        session -> session
                                .send(input.map(msg -> Utils.encodeMessage(msg + "<>", StandardCharsets.UTF_8))
                                        .map(bytes -> session.binaryMessage(dataBufferFactory -> dataBufferFactory.wrap(bytes))))
                                .thenMany(session.receive().take(count)
                                        .map(WebSocketMessage::getPayloadAsText)
                                        .map(s -> s.substring(0, 5)))
                                .subscribeWith(output)
                                .then())
                .block(duration);
        System.out.println(input.toString());
        System.out.println(output.toString());
        assertEquals(input.collectList().block(duration),
                output.collectList().block(duration));
    }

    private URI getUrl(String path) throws URISyntaxException {
        return new URI("ws://localhost:" + this.port + path);
    }

}