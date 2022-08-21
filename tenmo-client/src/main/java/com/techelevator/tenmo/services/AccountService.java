package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {
    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url) {
        BASE_URL = url;
    }

    public BigDecimal getBalance (AuthenticatedUser user){
        Account account = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(BASE_URL+"/account", HttpMethod.GET, makeAuthEntity(user.getToken()), Account.class);
            account = response.getBody();
            assert account != null;
            return account.getBalance();
        } catch (AssertionError e){
            BasicLogger.log(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private HttpEntity<Void> makeAuthEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
