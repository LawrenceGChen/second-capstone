package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.AccountNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface AccountDao {

    List<Account> findAll();

    Account findAccountByUserId(User user) throws AccountNotFoundException;

    Account findAccountByUsername(String username) throws AccountNotFoundException;
}
