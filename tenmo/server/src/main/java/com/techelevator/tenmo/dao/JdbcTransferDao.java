package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer getTransfer() {
        return null;
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return null;
    }

    @Override
    public boolean Transfer(int userSending, int userReceiving, BigDecimal amountToTransfer) {
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
                && (amountToTransfer.compareTo(balance) < 0)) {
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



    /*@Override
    public boolean Transfer(int userSending, int userReceiving, BigDecimal amountToTransfer) {
        String sql = "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE user_id = ?; " +
                "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE user_id = ?;";
        boolean success = false;
        int linesReturned = (jdbcTemplate.update(sql, amountToTransfer, userSending, amountToTransfer, userReceiving));
        if (userSending == userReceiving){
            return success;
        }
        if (linesReturned == 1){
            success = true;
        }
        return success;
    }*/

    /*@Override
    public int Transfer(int userSending, int userReceiving, BigDecimal amountToTransfer) {
        String sql = "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE user_id = ?; " +
                "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE user_id = ?;";
        boolean success = false;
        Integer linesReturned = jdbcTemplate.queryForObject(sql, Integer.class, amountToTransfer, userSending, amountToTransfer, userReceiving);
        if (userSending != userReceiving){
            return success;
        }
        if (linesReturned == 1){
            *//*success = true;*//*
        }
        return linesReturned;
    }*/
}

// Posting/deleting JSON objects to database

// Small methods:
// 1: Java checks userSending and userReceiving to make sure they aren't the same person
// 2: Java checks amountToTransfer to make sure it is positive (greater than 0)
// 3: Java checks the amount in userSending's account to make sure it's greater than amountToTransfer
// 4: Java doesn't run the transaction if the user doesn't have permissions (logged in user can only be userSending, can't initiate a transaction from another person's account)
// 5: Java assigns the transaction a number, starting at 3001 & going up for every transaction, no matter who does it
// 6: Java stores that number in the transaction table in relation to the userIds of userSending and userReceiving