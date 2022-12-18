package com.techelevator.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.techelevator.dao.BaseDaoTests;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcTransferDaoTest extends BaseDaoTests {

    private static final Transfer TRANSFER_1 = new Transfer(3001, 1001, 1002,
            new BigDecimal("325.00"), "Approved");
    private static final Transfer TRANSFER_2 = new Transfer(3002, 1002, 1002,
            new BigDecimal("400.00"), "Denied");
    private static final Transfer TRANSFER_3 = new Transfer(3003, 1003, 1004,
            new BigDecimal("0.00"), "Denied");
    private static final Transfer TRANSFER_4 = new Transfer(3004, 1004, 1005,
            new BigDecimal("-325.00"), "Denied");
    private static final Transfer TRANSFER_5 = new Transfer(3005, 1005, 1004,
            new BigDecimal("600.00"), "Denied");

    private JdbcTransferDao dao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void getTransfer_send_in_user_id_and_transfer_id_return_transfer() {
        Transfer expected = TRANSFER_1;
        Transfer actual = dao.getTransfer(1001, 3001);
        assertTransfersMatch(expected, actual);

        expected = TRANSFER_2;
        actual = dao.getTransfer(1002, 3002);
        assertTransfersMatch(expected, actual);
    }

    @Test
    public void getAllTransfers_returns_all_transfers() {
        List<Transfer> expected = new ArrayList<>();
        expected.add(TRANSFER_1);
        List<Transfer> actual = dao.getAllTransfers(1001);
        assertTransferListMatch(expected, actual);

        expected = new ArrayList<>();
        expected.add(TRANSFER_3);
        expected.add(TRANSFER_4);
        expected.add(TRANSFER_5);
        actual = new ArrayList<>();
        actual = dao.getAllTransfers(1004);
    }

    @Test
    public void createTransfer_returns_true_when_transfer_created() {
        boolean transferCreated = dao.createTransfer(1001, 1002, new BigDecimal("25.00"), "Approved");
        Assert.assertTrue(transferCreated);
        Transfer newTransfer = dao.getTransfer(1001, 3006);
        Assert.assertEquals(3006, newTransfer.getTransfer_id());

        transferCreated = dao.createTransfer(1002, 1003, new BigDecimal("25.00"), "Approved");
        Assert.assertTrue(transferCreated);
        newTransfer = dao.getTransfer(1002, 3007);
        Assert.assertEquals(3007, newTransfer.getTransfer_id());
    }

    @Test
    public void transfer_is_approved_when_all_requirements_met() {
        boolean transferApproved = dao.transfer(1001, 1002, new BigDecimal("25.00"));
        Assert.assertTrue(transferApproved);

        transferApproved = dao.transfer(1003, 1002, new BigDecimal("1000.00"));
        Assert.assertTrue(transferApproved);
    }

    @Test
    public void transfer_is_denied_when_userSending_is_userReceiving() {
        boolean transferApproved = dao.transfer(1002, 1002, new BigDecimal("25.00"));
        Assert.assertFalse(transferApproved);
    }

    @Test
    public void transfer_is_denied_when_amountToTransfer_is_0_or_negative() {
        boolean transferApproved = dao.transfer(1002, 1001, new BigDecimal("0.00"));
        Assert.assertFalse(transferApproved);

        transferApproved = dao.transfer(1002, 1001, new BigDecimal("-25.00"));
        Assert.assertFalse(transferApproved);
    }

    @Test
    public void transfer_is_denied_when_userSending_doesnt_have_enough_money() {
        boolean transferApproved = dao.transfer(1002, 1001, new BigDecimal("1500.00"));
        Assert.assertFalse(transferApproved);

        transferApproved = dao.transfer(1002, 1001, new BigDecimal("1000.01"));
        Assert.assertFalse(transferApproved);
    }

    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getTransfer_id(), actual.getTransfer_id());
        Assert.assertEquals(expected.getUserSending(), actual.getUserSending());
        Assert.assertEquals(expected.getUserReceiving(), actual.getUserReceiving());
        Assert.assertEquals(expected.getMoneyTransferred(), actual.getMoneyTransferred());
        Assert.assertEquals(expected.getStatus(), actual.getStatus());
    }

    private void assertTransferListMatch(List<Transfer> expected, List<Transfer> actual) {
        if (expected.size() == actual.size()) {
            for (int i = 0; i < expected.size(); i++) {
                Assert.assertEquals(expected.get(i).getTransfer_id(), actual.get(i).getTransfer_id());
                Assert.assertEquals(expected.get(i).getUserSending(), actual.get(i).getUserSending());
                Assert.assertEquals(expected.get(i).getUserReceiving(), actual.get(i).getUserReceiving());
                Assert.assertEquals(expected.get(i).getMoneyTransferred(), actual.get(i).getMoneyTransferred());
                Assert.assertEquals(expected.get(i).getStatus(), actual.get(i).getStatus());
            }
        }
    }
}