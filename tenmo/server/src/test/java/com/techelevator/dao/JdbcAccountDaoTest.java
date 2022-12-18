package com.techelevator.dao;

import com.techelevator.dao.BaseDaoTests;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcAccountDaoTest extends BaseDaoTests {

    private static final Account ACCOUNT_1 = new Account(2001, 1001, new BigDecimal("1000.00"));
    private static final Account ACCOUNT_2 = new Account(2002, 1002, new BigDecimal("1000.00"));
    private static final Account ACCOUNT_3 = new Account(2003, 1003, new BigDecimal("1000.00"));
    private static final Account ACCOUNT_4 = new Account(2004, 1004, new BigDecimal("1000.00"));
    private static final Account ACCOUNT_5 = new Account(2005, 1005, new BigDecimal("500.00"));

    private JdbcAccountDao dao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getAccountFromAccountId_returns_proper_account() {
        Account expected = ACCOUNT_1;
        Account actual = dao.getAccountFromAccountId(2001);
        assertAccountsMatch(expected, actual);

        expected = ACCOUNT_2;
        actual = dao.getAccountFromAccountId(2002);
        assertAccountsMatch(expected, actual);
    }

    @Test
    public void getAccountFromUserId_returns_proper_account() {
        Account expected = ACCOUNT_1;
        Account actual = dao.getAccountFromUserId(1001);
        assertAccountsMatch(expected, actual);

        expected = ACCOUNT_2;
        actual = dao.getAccountFromUserId(1002);
        assertAccountsMatch(expected, actual);
    }

    @Test
    public void getAllAccounts_returns_list_of_all_accounts() {
        List<Account> expected = new ArrayList<>();
        expected.add(ACCOUNT_1);
        expected.add(ACCOUNT_2);
        expected.add(ACCOUNT_3);
        expected.add(ACCOUNT_4);
        expected.add(ACCOUNT_5);
        List<Account> actual = dao.getAllAccounts();
        assertAccountListsMatch(expected, actual);
    }

    @Test
    public void getBalance_returns_correct_balance() {
        BigDecimal expected = new BigDecimal("1000.00");
        BigDecimal actual = dao.getBalance(1001);
        Assert.assertEquals(expected, actual);

        expected = new BigDecimal("500.00");
        actual = dao.getBalance(1005);
        Assert.assertEquals(expected, actual);
    }


    private void assertAccountsMatch(Account expected, Account actual) {
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getBalance(), actual.getBalance());
    }

    private void assertAccountListsMatch(List<Account> expected, List<Account> actual) {
        if (expected.size() == actual.size()) {
            for (int i = 0; i < expected.size(); i++) {
                Assert.assertEquals(expected.get(i).getAccountId(), actual.get(i).getAccountId());
                Assert.assertEquals(expected.get(i).getUserId(), actual.get(i).getUserId());
                Assert.assertEquals(expected.get(i).getBalance(), actual.get(i).getBalance());
            }
        }


    }

}