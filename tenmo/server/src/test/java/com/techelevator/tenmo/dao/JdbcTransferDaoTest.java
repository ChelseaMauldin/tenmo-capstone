package com.techelevator.tenmo.dao;

import com.techelevator.dao.BaseDaoTests;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

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
    public void getTransfer_send_in_user_id_and_transfer_id_return_transfer(){
        Transfer expected = TRANSFER_1;

        Transfer actual = dao.getTransfer(1001, 3001);

        assertTransfersMatch(expected, actual);
    }

    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getTransfer_id(), actual.getTransfer_id());
        Assert.assertEquals(expected.getUserSending(), actual.getUserSending());
        Assert.assertEquals(expected.getUserReceiving(), actual.getUserReceiving());
        Assert.assertEquals(expected.getMoneyTransferred(), actual.getMoneyTransferred());
        Assert.assertEquals(expected.getStatus(), actual.getStatus());
    }

}