package com.roc.api;

import com.roc.cons.ApiServer;
import com.roc.entity.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ApiServer("http://localhost")
public interface IUserApi {
    @GetMapping
    Flux<User> getAll();

    @DeleteMapping("/{id}")
    Mono<Void> delete(@PathVariable("id") String id);
}
