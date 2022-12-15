package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transfer_id;
    private int userSending;
    private int userReceiving;
    private BigDecimal moneyTransferred;
    private String status;

    public Transfer() {
    }

    public Transfer(int transfer_id, int userSending, int userReceiving, BigDecimal moneyTransferred, String status) {
        this.transfer_id = transfer_id;
        this.userSending = userSending;
        this.userReceiving = userReceiving;
        this.moneyTransferred = moneyTransferred;
        this.status = status;
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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
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
