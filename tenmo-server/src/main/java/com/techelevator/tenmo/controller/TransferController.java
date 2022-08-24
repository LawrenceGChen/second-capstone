package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.exception.AccountNotFoundException;
import com.techelevator.tenmo.exception.InvalidTransferException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.security.Principal;

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
        Account senderAccount;
        try {
            senderAccount = accountDao.findAccountByAccountId(transfer.getSenderAccount().getAccountId());
        } catch (AccountNotFoundException e) {
            BasicLogger.log("findAccountByAccountId: "+e.getMessage());
            return false;
        }

        if (senderAccount == null) {
            BasicLogger.log("sufficientBalance() failure: senderAccount == null");
            return false;
        }

        return senderAccount.getBalance().compareTo(transfer.getAmount()) >= 0;
    }

    private boolean validAccounts(Transfer transfer, Principal principal){
        Account senderAccount = null;
        Account recipientAccount = null;
        Account principalAccount = null;

        try {
            senderAccount = accountDao.findAccountByAccountId(transfer.getSenderAccount().getAccountId());
            recipientAccount = accountDao.findAccountByAccountId(transfer.getRecipientAccount().getAccountId());
            principalAccount = accountDao.findAccountByUsername(principal.getName());
        } catch (AccountNotFoundException e) {
            BasicLogger.log(e.getMessage());
        }

        if (senderAccount == null || recipientAccount == null){
            BasicLogger.log("senderAccount or recipientAccount are null");
            return false;
        }
        if (!senderAccount.equals(principalAccount)){
            BasicLogger.log("senderAccount != principalAccount");
            return false;
        }
        if (senderAccount.equals(recipientAccount)){
            BasicLogger.log("senderAccount == recipientAccount");
            return false;
        }

        return true;
    }

}