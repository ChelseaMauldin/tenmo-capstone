package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    /*boolean transfer(int userSending, int userReceiving, BigDecimal transferAmount);*/

    Transfer getTransfer();

    List<Transfer> getAllTransfers();

}
