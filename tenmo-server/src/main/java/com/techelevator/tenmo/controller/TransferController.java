package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.exception.AccountNotFoundException;
import com.techelevator.tenmo.exception.InvalidTransferException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.security.Principal;
import java.lang.Number;

@RestController
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao){
        this.transferDao=transferDao;
        this.accountDao=accountDao;
    }

    @PostMapping(path="/transfer/send")
    @ResponseStatus(HttpStatus.CREATED)
    public Transfer transferSend(@RequestBody @Valid Transfer transfer, Principal principal){
        if(validTransfer(transfer,principal)){
            return transferDao.create(transfer);
        }
        else{
            throw new InvalidTransferException("Sorry, that transfer is invalid.");
        }
    }

    private boolean validTransfer(Transfer transfer, Principal principal){
        return sufficientBalance(transfer) && validAccounts(transfer, principal);
    }
    private boolean sufficientBalance(Transfer transfer) {
        try {
            return accountDao.findAccountById(transfer.getAccountFrom()).getBalance().compareTo(transfer.getAmount()) > 0;
        } catch(AccountNotFoundException e) {
            BasicLogger.log(e.getMessage());
        }
        return false;
    }

    private boolean validAccounts(Transfer transfer, Principal principal){
        try {
            return (transfer.getAccountTo().longValue()!=accountDao.findAccountByUsername(principal.getName()).getId().longValue()&&
                    transfer.getAccountFrom().longValue()!=accountDao.findAccountByUsername(principal.getName()).getId().longValue()&&
                    transfer.getAccountTo().longValue()!=transfer.getAccountFrom()
                    );
        } catch (AccountNotFoundException e) {
            BasicLogger.log(e.getMessage());
        }
        return false;
    }

}


























