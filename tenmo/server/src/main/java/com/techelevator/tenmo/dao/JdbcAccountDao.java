package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    JdbcTemplate jdbcTemplate;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccount(int accountId) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance " +
                "FROM account " +
                "WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if (results.next()){
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance " +
                "FROM account";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            accountList.add(mapRowToAccount(results));
        }
        return accountList;
    }

    @Override
    public Account createAccount(Account account) {
        Account newAccount = null;
        String sql = "INSERT INTO account (user_id, balance) " +
                "VALUES (?, ?) " +
                "RETURNING account_id";
        Integer accountId = jdbcTemplate.queryForObject(sql, Integer.class,
                account.getUserId(), account.getBalance());
        newAccount = getAccount(accountId);
        return newAccount;
    }

    @Override
    public boolean updateAccount(Account account) {
        String sql = "UPDATE account " +
                "SET user_name = ?, balance = ? " +
                "WHERE account_id = ?";
        boolean success = false;
        int linesReturned = jdbcTemplate.update(sql, account.getUserId(), account.getBalance(), account.getAccountId());
        if (linesReturned == 1){
            success = true;
        }
        return success;
    }

    @Override
    public boolean deleteAccount(int accountId) {
        String sql = "DELETE FROM account " +
                "WHERE account_id = ?";
        boolean success = false;
        int linesReturned = jdbcTemplate.update(sql,accountId);
        if (linesReturned == 1){
            success = true;
        }
        return success;
    }

    private Account mapRowToAccount(SqlRowSet results){
        int accountId = results.getInt("account_id");
        int userId = results.getInt("user_id");
        BigDecimal balance = results.getBigDecimal("balance");
        return new Account(accountId, userId, balance);
    }
}
