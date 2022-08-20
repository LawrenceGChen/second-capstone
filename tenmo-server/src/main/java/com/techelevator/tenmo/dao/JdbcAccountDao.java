package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.AccountNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;



import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Account> findAll() {
        List<Account> accounts=new ArrayList<>();
        String sql= "SELECT account_id, user_id, balance FROM account;";
        SqlRowSet results=jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            Account account=mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account findIdByUser(User user) throws AccountNotFoundException {
        String sql="SELECT account, user_id, balance FROM account WHERE user_id = ?;";
        SqlRowSet rowSet= jdbcTemplate.queryForRowSet(sql,user.getId());
        if(rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        throw new AccountNotFoundException("Account for user " + user.getUsername() + " was not found.");
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setId(rs.getLong("account_id"));
        account.setUserId(rs.getLong("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}
