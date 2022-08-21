package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.exception.AccountNotFoundException;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AccountController {

    private AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping(value = "/account")
    public Account getAccount(Principal principal) throws AccountNotFoundException {
        return accountDao.findAccountByUsername(principal.getName());
    }
}