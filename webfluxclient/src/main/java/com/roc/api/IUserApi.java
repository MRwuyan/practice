package com.roc.api;

import com.roc.cons.ApiServer;
import com.roc.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@ApiServer("http://localhost")
public interface IUserApi {
    @GetMapping
    Flux<User> getAll();
}
