package com.example.wollf.togather;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wollf on 15-1-2018.
 */

public class BalanceCalculatorTest {
    private static List<User> USERS = Arrays.asList(
            new User("TESTUSER1",null,null),
            new User("TESTUSER2",null,null),
            new User("TESTUSER3",null,null),
            new User("TESTUSER4",null,null));

    @Test
    public void test_addUser() throws Exception{
        BalanceCalculator bc = new BalanceCalculator();
        bc.addUser(USERS.get(0));
        double total = bc.getUserTotalPayments(USERS.get(0));
        assertEquals(0.0, total, 0);
    }

    @Test
    public void test_addBalance() throws Exception{
        BalanceCalculator bc = new BalanceCalculator();
        bc.addBalance(USERS.get(0), 10.0);
        bc.addBalance(USERS.get(0), 20.0);
        assertEquals(30.0, bc.getUserTotalPayments(USERS.get(0)),0);
    }


}
