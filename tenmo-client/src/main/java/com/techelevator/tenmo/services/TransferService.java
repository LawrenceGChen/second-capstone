package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;

public class TransferService {
    private final String BASE_URL;
    private final RestTemplate restTemplate=new RestTemplate();

    public TransferService(String url){
        BASE_URL=url;
    }

    public void sendBucks(Principal principal, Long userId, BigDecimal amount){
        Transfer transfer = new Transfer();
        transfer.setAccountTo(userId);
        transfer.setAmount(amount);
        //Todo get the principal account Id
    }
}
