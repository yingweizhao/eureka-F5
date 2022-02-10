package com.f5.consumer2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
    @Autowired(required = true)
    RestTemplate restTemplate;
    @GetMapping("/consumer")
    public String consumer() {
        return restTemplate.getForEntity("http://F5-PROVIDER-APP1/helloeureka", String.class).getBody();
    }
}
