package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TransferDao transferDao;

//    @RequestMapping(path = "/account/{id}", method = RequestMethod.GET)
//    public Account getAccount(@PathVariable int id){
//        Account account = accountDao.getAccount(id);
//        if (account == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
//        } else {
//            return account;
//        }
//    }

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public Account getAccount(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        Account account = accountDao.getAccountFromUserId(userId);
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        } else {
            return account;
        }
    }

    @RequestMapping(path = "/account/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        BigDecimal balance = accountDao.getBalance(userId);
        if (balance == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Balance not found.");
        } else {
            return balance;
        }
    }

    @RequestMapping(path = "/account/transfer", method = RequestMethod.GET)
    public List<String> getListOfUsers(Principal principal) {
        return userDao.listUsernames(principal.getName());
    }

    @RequestMapping(path = "/account/transfer", method = RequestMethod.POST)
    public boolean transferMoney(@RequestBody Transfer transfer, Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        if ((userId != transfer.getUserSending()) || ((transferDao.transfer(transfer.getUserSending(), transfer.getUserReceiving(), transfer.getMoneyTransferred())) == false)) {
            transferDao.createTransfer(transfer.getUserSending(), transfer.getUserReceiving(), transfer.getMoneyTransferred(), "Denied");
        } else {
            transferDao.createTransfer(transfer.getUserSending(), transfer.getUserReceiving(), transfer.getMoneyTransferred(), "Approved");
            return true;
        }
        // if transfer is true,
        // create transfer
        // plug in things
        // set approved to true
        // if transfer is false,
        // create transfer
        // plug in things
        // set approved to false
        return false;
    }

    @RequestMapping(path = "/account/transfer/all_transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        return transferDao.getAllTransfers(userId);
    }

    @RequestMapping(path = "/account/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer transfer(@PathVariable int transferId, Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        return transferDao.getTransfer(userId, transferId);
    }


}
