package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {
    private final String BASE_URL;
    private final RestTemplate restTemplate=new RestTemplate();

    public TransferService(String url){
        BASE_URL=url;
    }

    public boolean sendBucks(AuthenticatedUser user, Account senderAccount, Account recipientAccount, BigDecimal amount){
        Transfer transfer = new Transfer();
        transfer.setTransferTypeId(2L);
        transfer.setTransferStatusId(2L);
        transfer.setSenderAccount(senderAccount);
        transfer.setRecipientAccount(recipientAccount);
        transfer.setAmount(amount);
        HttpEntity<Transfer> entity= makeTransferEntity(transfer,user.getToken());
        boolean success = false;
        try{
            restTemplate.exchange(BASE_URL+"transfer/send",HttpMethod.POST,entity,Void.class);
            success=true;
        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public int validateTransferAmount(BigDecimal transferAmount, BigDecimal accountBalance){
        if (transferAmount.compareTo(BigDecimal.valueOf(0))<0){
            return 0;
        };
        if (transferAmount.compareTo(accountBalance)>=0){
            return -1;
        }
        return 1;
    }

    private HttpEntity<Void> makeAuthEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer,headers);
    }
}
