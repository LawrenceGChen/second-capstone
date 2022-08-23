package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private Long transferId;
    private Long transferTypeId;
    private Long transferStatusId;
    private Long senderAccount;
    private Long recipientAccount;
    private BigDecimal amount;

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public Long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public Long getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Long senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Long getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(Long recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
