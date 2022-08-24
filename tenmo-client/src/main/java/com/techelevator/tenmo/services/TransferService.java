package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;

public class TransferService {
    private final String BASE_URL;
    private final RestTemplate restTemplate=new RestTemplate();

    public TransferService(String url){
        BASE_URL=url;
    }

//    public void sendBucks(AuthenticatedUser user, Long senderId, Long recipientId, BigDecimal amount){
//        Transfer transfer = new Transfer();
//        transfer.setTransferTypeId(2L);
//        transfer.setTransferStatusId(2L);
//        transfer.setSenderAccount(senderId);
//        transfer.setRecipientAccount(recipientId);
//        transfer.setAmount(amount);
//        try{
//            HttpEntity<Transfer> requestEntity = new HttpEntity<>(transfer,requestHeaders);
//
//        }
//    }

    public int validateTransferAmount(BigDecimal transferAmount, BigDecimal accountBalance){
        if (transferAmount.compareTo(BigDecimal.valueOf(0))<=0){
            return 0;
        };
        if (transferAmount.compareTo(accountBalance)<0){
            return -1;
        }
        return 1;
    }

    private HttpEntity<Void> makeAuthEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
