package com.aliyun.code83.round4;

import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapReactiveUserDetailsService implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

    private final Map<String, UserDetails> users;

    public MapReactiveUserDetailsService(UserDetails... users) {
        this(Arrays.asList(users));
    }

    public MapReactiveUserDetailsService(Collection<UserDetails> users) {
        Assert.notEmpty(users, "users cannot be null or empty");
        this.users = new ConcurrentHashMap<>();
        for (UserDetails user : users) {
            this.users.put(getKey(user.getUsername()), user);
        }
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        return Mono.just(user)
                .map((userDetails) -> withNewPassword(userDetails, newPassword))
                .doOnNext((userDetails) -> {
                    String key = getKey(user.getUsername());
                    this.users.put(key, userDetails);
                });
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        String key = getKey(username);
        UserDetails result = this.users.get(key);
        return (result != null) ? Mono.just(User.withUserDetails(result).build()) : Mono.empty();
    }

    public void addUser(UserDetails user) {
        this.users.put(getKey(user.getUsername()), user);
    }

    private UserDetails withNewPassword(UserDetails userDetails, String newPassword) {
        return User.withUserDetails(userDetails)
                .password(newPassword)
                .build();
    }

    private String getKey(String username) {
        return username.toLowerCase();
    }


}
