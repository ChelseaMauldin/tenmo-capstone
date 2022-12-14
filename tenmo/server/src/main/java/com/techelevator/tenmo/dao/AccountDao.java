package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {
    //CRUD

    Account getAccount(int accountId);

    List<Account> getAllAccounts();

    Account createAccount (Account account);

    boolean updateAccount(Account account);

    boolean deleteAccount(int accountId);
}
