package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
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
        boolean success = false;
        int linesReturned = (jdbcTemplate.update(sql, amountToTransfer, userSending, amountToTransfer, userReceiving));
        if (userSending == userReceiving){
            return success;
        }
        if (linesReturned == 1){
            success = true;
        }
        return success;
    }
}
