package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exception.AccountNotFoundException;
import com.techelevator.tenmo.exception.InvalidTransferException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;
    private UserDao userDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao, UserDao userDao){
        this.transferDao=transferDao;
        this.accountDao=accountDao;
        this.userDao=userDao;
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

    @GetMapping("/myAccount/transfers")
    public List<TransferDTO> getMyTransfers(Principal principal){
        User myUser = userDao.findByUsername(principal.getName());
        return transferDao.getTransfersByUser(myUser);
    }

    @GetMapping("/myAccount/transfers/{transferId}")
    public TransferDTO getTransferById(@PathVariable Long transferId,Principal principal){
        TransferDTO transferDTO=transferDao.getTransferById(transferId);
        transferDTO.setUsernameFrom(userDao.findUsernameByAccountId(transferDTO.getAccountFromId()));
        transferDTO.setUsernameTo(userDao.findUsernameByAccountId(transferDTO.getAccountToId()));
        return transferDTO;
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