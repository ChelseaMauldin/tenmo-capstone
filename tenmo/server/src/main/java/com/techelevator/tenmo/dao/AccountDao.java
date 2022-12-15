package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    //CRUD

    Account getAccountFromAccountId(int accountId);

    Account getAccountFromUserId(int userId);

    List<Account> getAllAccounts();

//    Account createAccount (Account account);

    boolean updateAccount(Account account);

    boolean deleteAccount(int accountId);

    BigDecimal getBalance(int userId);

}
