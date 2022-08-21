package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class AccountService {
    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url) {
        BASE_URL = url;
    }
}
