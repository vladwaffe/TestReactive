package com.userservice.controller;

import com.userservice.model.AppUser;
import com.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private final WebClient webClient;

    @Autowired
    public UserController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    @PostMapping
    public Mono<AppUser> createUser(@RequestBody AppUser appUser) {
        return Mono.just(userRepository.save(appUser));
    }

    @GetMapping("/{id}")
    public Mono<AppUser> getUser(@PathVariable Long id) {
        return Mono.justOrEmpty(userRepository.findById(id));
    }

    @GetMapping("/{id}/products")
    public Mono<String> getUserWithProducts(@PathVariable Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products")
                        .queryParam("userId", id)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(products -> Mono.justOrEmpty(userRepository.findById(id))
                        .map(appUser -> "User: " + appUser.getName() + ", Products: " + products)
                        .switchIfEmpty(Mono.just("User not found or no products available")));
    }



}
