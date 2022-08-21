package com.techelevator.tenmo.services;

import org.springframework.web.client.RestTemplate;

public class TransferService {
    private final String BASE_URL;
    private final RestTemplate restTemplate=new RestTemplate();

    public TransferService(String url){
        BASE_URL=url;
    }
}
