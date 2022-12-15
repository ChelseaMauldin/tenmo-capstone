package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transfer_id;
    private int userSending;
    private int userReceiving;
    private BigDecimal moneyTransferred;
    private boolean isApproved;

    public Transfer() {
    }

    public Transfer(int transfer_id, int userSending, int userReceiving, BigDecimal moneyTransferred, boolean isApproved) {
        this.transfer_id = transfer_id;
        this.userSending = userSending;
        this.userReceiving = userReceiving;
        this.moneyTransferred = moneyTransferred;
        this.isApproved = isApproved;
    }

    public int getTransfer_id() {
        return transfer_id;
    }
    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getUserSending() {
        return userSending;
    }
    public void setUserSending(int userSending) {
        this.userSending = userSending;
    }

    public int getUserReceiving() {
        return userReceiving;
    }
    public void setUserReceiving(int userReceiving) {
        this.userReceiving = userReceiving;
    }

    public BigDecimal getMoneyTransferred() {
        return moneyTransferred;
    }
    public void setMoneyTransferred(BigDecimal moneyTransferred) {
        this.moneyTransferred = moneyTransferred;
    }

    public boolean isApproved() {
        return isApproved;
    }
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "userSending=" + userSending +
                ", userReceiving=" + userReceiving +
                ", moneyTransferred=" + moneyTransferred +
                '}';
    }
}
