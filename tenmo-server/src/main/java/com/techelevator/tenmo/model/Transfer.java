package com.techelevator.tenmo.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Transfer {
    private Long transferId;
    @Min(value=1)
    @Max(value=2)
    private Long transferTypeId;
    @Min(value=1)
    @Max(value=3)
    private Long transferStatusId;
//    @Positive
//    private Long accountFrom;
//
//    @Positive
//    private Long accountTo;

    @NotNull
    private Account senderAccount;

    @NotNull
    private Account recipientAccount;

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getRecipientAccount() {
        return recipientAccount;
    }

    public void setRecipientAccount(Account recipientAccount) {
        this.recipientAccount = recipientAccount;
    }

    @Positive
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

//    public Long getAccountFrom() {
//        return accountFrom;
//    }
//
//    public void setAccountFrom(Long accountFrom) {
//        this.accountFrom = accountFrom;
//    }
//
//    public Long getAccountTo() {
//        return accountTo;
//    }
//
//    public void setAccountTo(Long accountTo) {
//        this.accountTo = accountTo;
//    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
