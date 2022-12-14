package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public class JdbcTransferDao implements TransferDao {


    /*@Override
    *//*public boolean transfer(int userSending, int userReceiving, BigDecimal transferAmount) {


        return false;
    }*/

    @Override
    public Transfer getTransfer() {
        return null;
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return null;
    }
}
