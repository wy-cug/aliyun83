package com.aliyun.code83.round4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class WebConfig implements WebFluxConfigurer {

    @Autowired
    @Qualifier("ReactiveWebSocketHandler")
    private WebSocketHandler webSocketHandler;

    @Bean
    public HandlerMapping webSocketHandlerMapping() {
        return new SimpleUrlHandlerMapping(webSocketUrlMap(), 1);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public Map<String, WebSocketHandler> webSocketUrlMap() {
        return Utils.randomWords(3)
                .stream()
                .map(w -> "/ws/" + w)
                .collect(Collectors.toMap(Function.identity(), w -> webSocketHandler));
    }

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(new HandlerMethodArgumentResolver(){
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return UserDetails.class.isAssignableFrom(parameter.getParameterType());
            }

            @Override
            public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext,
                                                ServerWebExchange exchange) {
                return exchange.getFormData().map(formData -> User.builder()
                        .username(formData.getFirst("username"))
                        .password(formData.getFirst("password"))
                        .roles(formData.getFirst("roles"))
                        .build());
            }
        });

    }
}
