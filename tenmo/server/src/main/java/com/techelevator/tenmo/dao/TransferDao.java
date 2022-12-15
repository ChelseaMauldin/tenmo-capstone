package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer getTransfer();

    List<Transfer> getAllTransfers();

    boolean Transfer(int userSending, int userReceiving, BigDecimal amountToTransfer);


}
