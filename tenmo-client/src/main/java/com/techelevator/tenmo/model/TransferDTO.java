package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class TransferDTO implements Comparable<TransferDTO>{

    private Long transferId;
    private String usernameFrom;
    private String usernameTo;
        private Long transferTypeId;
    private Long transferStatusId;
    private BigDecimal amount;
    private boolean fromPrincipal;

    public boolean isFromPrincipal() {
        return fromPrincipal;
    }

    public void setFromPrincipal(boolean fromPrincipal) {
        this.fromPrincipal = fromPrincipal;
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public String getUsernameFrom() {
        return usernameFrom;
    }

    public void setUsernameFrom(String usernameFrom) {
        this.usernameFrom = usernameFrom;
    }

    public String getUsernameTo() {
        return usernameTo;
    }

    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void printMyTransfer(){
        String transferDetails;
        String toOrFrom = fromPrincipal? "From: " : "To: ";
        String otherUser = fromPrincipal? this.usernameTo : this.usernameFrom;
        transferDetails = String.format("%-12d%-6s%-17s", this.getTransferId(), toOrFrom, otherUser);
        transferDetails += NumberFormat.getCurrencyInstance().format(this.getAmount());
        System.out.println(transferDetails);
    }

    @Override
    public int compareTo(TransferDTO otherTransferDTO) {
        if(this.getTransferId()<otherTransferDTO.getTransferId()){
            return -1;
        }
        else if(this.getTransferId()> otherTransferDTO.getTransferId()){
            return 1;
        }
        return 0;
    }
}
