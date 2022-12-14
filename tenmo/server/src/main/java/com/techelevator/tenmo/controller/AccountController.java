package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    @Autowired
    private AccountDao accountDao;

    @RequestMapping(path = "/account/{username}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable String username, Principal principal){
        username = principal.getName();
        BigDecimal balance = accountDao.getBalance(Integer.parseInt(username));
        return balance;
    }

}
