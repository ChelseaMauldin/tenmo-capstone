package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;

//    @RequestMapping(path = "/account/{id}", method = RequestMethod.GET)
//    public Account getAccount(@PathVariable int id){
//        Account account = accountDao.getAccount(id);
//        if (account == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
//        } else {
//            return account;
//        }
//    }

    @RequestMapping(path = "/account/{username}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable String username, Principal principal){
        username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        Account account = accountDao.getAccountFromUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        } else {
            return account;
        }
    }

    @RequestMapping(path = "/account/{username}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable String username, Principal principal){
        username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        BigDecimal balance = accountDao.getBalance(userId);
        if (balance == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not found.");
        } else {
            return balance;
        }
    }

}
