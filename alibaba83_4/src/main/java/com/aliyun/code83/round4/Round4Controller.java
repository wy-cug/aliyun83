package com.aliyun.code83.round4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.Map;
import java.util.Set;

@RestController
public class Round4Controller {

    @Autowired
    @Qualifier("webSocketUrlMap")
    private Map<String, WebSocketHandler> webSocketUrlMap;

    @Autowired
    private MapReactiveUserDetailsService userDetailsService;

    @GetMapping("/endpoints")
    public Set<String> wsEndpoints() {
        return webSocketUrlMap.keySet();
    }

    @PostMapping("/users")
    public void addUser(UserDetails user) {
        userDetailsService.addUser(user);
    }

}
