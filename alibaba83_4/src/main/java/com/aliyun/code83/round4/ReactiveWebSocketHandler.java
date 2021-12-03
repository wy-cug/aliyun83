package com.aliyun.code83.round4;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * 二进制包格式
 * byte 字符集长度; n1
 * byte[n1] 字符集数据； n1 = 字符集长度
 * byte[n2] 有效数据；n2 = 包总长度 - n1 - 1
 */
@Component("ReactiveWebSocketHandler")
public class ReactiveWebSocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                session.receive()
                        .map(WebSocketMessage::getPayload)
                        .map(getBufferConverter())
                        .map(Utils::decodeMessage)
                        .map(Utils::stripHtmlTag)
                        .log()
                        .map(session::textMessage));
    }

    private Function<DataBuffer, byte[]> getBufferConverter() {
        final byte[] buffer = new byte[1024];
        return (DataBuffer dataBuffer) -> {
            int length = dataBuffer.readableByteCount();
            dataBuffer.read(buffer, 0, length);
            return buffer;
        };
    }


}
