package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer getTransfer(int userId, int transferId) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, user_sending, user_receiving, amount, status " +
                "FROM transfer " +
                "WHERE (user_sending = ? OR user_receiving = ?) AND transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public List<Transfer> getAllTransfers(int userId) {
        List<Transfer> transferList = new ArrayList<>();
        String sql = "SELECT transfer_id, user_sending, user_receiving, amount, status " +
                "FROM transfer " +
                "WHERE user_sending = ? OR user_receiving = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while (results.next()) {
            transferList.add(mapRowToTransfer(results));
        }
        return transferList;
    }

    @Override
    public boolean transfer(int userSending, int userReceiving, BigDecimal amountToTransfer) {
        String sql = "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE user_id = ?; " +
                "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE user_id = ?;";

        String getBalance = "SELECT balance FROM account " +
                "WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(getBalance, userSending);
        BigDecimal balance = new BigDecimal("0.00");
        if (results.next()) {
            balance = results.getBigDecimal("balance");
        }

        if ((userSending != userReceiving) && (amountToTransfer.compareTo(new BigDecimal("0.00")) > 0)
                && (amountToTransfer.compareTo(balance) <= 0)) {
            jdbcTemplate.update(sql, amountToTransfer, userSending, amountToTransfer, userReceiving);
            return true;
        }
        return false;
    }

    @Override
    public boolean createTransfer(int userSending, int userReceiving, BigDecimal amountToTransfer, String status) {
        String sql = "INSERT INTO transfer (user_sending, user_receiving, amount, status) " +
                "VALUES (?, ?, ?, ?) RETURNING transfer_id";
        try {
            Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class,
                    userSending, userReceiving, amountToTransfer, status);
        } catch (DataAccessException e) {
            return false;
        }

        return true;
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        int transferId = results.getInt("transfer_id");
        int userSending = results.getInt("user_sending");
        int userReceiving = results.getInt("user_receiving");
        BigDecimal moneyTransferred = results.getBigDecimal("amount");
        String status = results.getString("status");
        return new Transfer(transferId, userSending, userReceiving, moneyTransferred, status);
    }
}

// Posting/deleting JSON objects to database

// Small methods:
// 1: Java checks userSending and userReceiving to make sure they aren't the same person
// 2: Java checks amountToTransfer to make sure it is positive (greater than 0)
// 3: Java checks the amount in userSending's account to make sure it's greater than amountToTransfer
// 4: Java doesn't run the transaction if the user doesn't have permissions (logged in user can only be userSending, can't initiate a transaction from another person's account)
// 5: Java assigns the transaction a number, starting at 3001 & going up for every transaction, no matter who does it
// 6: Java stores that number in the transaction table in relation to the userIds of userSending and userReceiving


// Notes from Margaret:

// 1: Have API with /listOfUsers
// 3: transfer model with user id's / account id's - join tables. userTo, userFrom, amount, status
// 6: Verify sender has enough money - if I have enough money, then I'm going to: decrease my account.
//      `Decrease my account
//          If this fails, jump out / throw an exception
//       Increase their account
//          If this fails, jump out / throw an exception
//       Insert into transfer table