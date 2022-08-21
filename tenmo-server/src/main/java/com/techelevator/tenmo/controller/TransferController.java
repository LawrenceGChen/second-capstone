package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {
    private TransferDao transferDao;

    public TransferController(TransferDao transferDao){
        this.transferDao=transferDao;
    }

    @PostMapping(path="/transfer/send")
    @ResponseStatus(HttpStatus.CREATED)
    public Transfer transferSend(@RequestBody Transfer transfer){
        return transferDao.create(transfer);
    }
}
