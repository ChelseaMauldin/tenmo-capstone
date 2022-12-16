package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer getTransfer(int userId, int transferId);

    List<Transfer> getAllTransfers(int userId);

    boolean Transfer(int userSending, int userReceiving, BigDecimal amountToTransfer);

    boolean createTransfer(int userSending, int userReceiving,
                           BigDecimal amountToTransfer, String status);


}
