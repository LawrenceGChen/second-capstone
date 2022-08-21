package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

public class AccountService {
    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url) {
        BASE_URL = url;
    }

    public BigDecimal getBalance (User user){
        Account account = restTemplate.getForObject(BASE_URL+"/account", Account.class, user);
        try {
            assert account != null;
            return account.getBalance();
        } catch (AssertionError e){
            BasicLogger.log(e.getMessage());
        }
        return null;
    }
}
