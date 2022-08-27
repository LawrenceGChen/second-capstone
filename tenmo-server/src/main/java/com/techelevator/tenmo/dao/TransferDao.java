package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface TransferDao {
    Transfer create(Transfer transfer);

    List<TransferDTO> getTransfersByUser(User user);
}
