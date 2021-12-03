package com.aliyun.code83.round4;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.Map;

/**
 * 勿动，影响评分
 */
@Configuration
public class PostProcessorConfiguration {

    @Autowired
    @Qualifier("ReactiveWebSocketHandler")
    private WebSocketHandler webSocketHandler;

    @Bean
    public BeanPostProcessor userDetailsServicePostProcessor(){
        return new BeanPostProcessor(){
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if(bean instanceof MapReactiveUserDetailsService){
                    ((MapReactiveUserDetailsService) bean).addUser( User.builder()
                            .username("test")
                            .password("{noop}test")
                            .roles("TEST")
                            .build());
                } else if(beanName.equals("webSocketUrlMap")){
                    ((Map<String, WebSocketHandler>) bean).put("/ws/test", webSocketHandler);
                }
                return bean;
            }
        };
    }
}
