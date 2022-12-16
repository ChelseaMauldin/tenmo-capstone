package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests {


    private JdbcUserDao sut;
    private JdbcAccountDao dao2;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
        dao2 = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER", "test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

    @Test
    public void createNewUser_adds_1000_to_account() {
        // Act
        boolean userCreated = sut.create("TEST_USER", "test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());

        // Arrange
        BigDecimal balance = dao2.getBalance(user.getId());

        // Assert
        Assert.assertEquals(new BigDecimal("1000.00"), balance);
    }

    @Test
    public void findIdByUsername_send_in_username_return_id() {
        // Arrange
        int expected = 1001;

        // Act
        int actual = sut.findIdByUsername("bob");

        // Assert
        Assert.assertEquals(expected, actual);

        expected = 1002;
        actual = sut.findIdByUsername("user");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void listUsernames_returns_list_of_users_except_the_one_logged_in() {
        List<String> expected = new ArrayList<>();
        expected.add("user");
        expected.add("ann");
        expected.add("jim");
        expected.add("joe");
        List<String> actual = sut.listUsernames("bob");
        Assert.assertEquals(expected, actual);
    }
    
}
