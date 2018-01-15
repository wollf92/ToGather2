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
            new User("TESTUSER4",null,null),
            new User("TESTUSER5",null,null),
            new User("TESTUSER6",null,null));

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

    @Test
    public void test_singleUserTransaction() throws Exception{
        BalanceCalculator bc = new BalanceCalculator();
        bc.addBalance(USERS.get(0), 10);
        int size = bc.calculateTransaction().size();
        assertEquals(0, size);
    }

    @Test
    public void test_twoUserTransaction() throws Exception{
        BalanceCalculator bc = new BalanceCalculator();
        bc.addBalance(USERS.get(0), 10);
        bc.addBalance(USERS.get(1), 5);
        List<Transaction> transactions = bc.calculateTransaction();
        assertEquals(1, transactions.size());
        Transaction t = transactions.get(0);
        assertEquals(t.getFrom(), USERS.get(1).getName());
        assertEquals(t.getAmount(), 2.5, 0);
        assertEquals(t.getTo(), USERS.get(0).getName());
    }
    @Test
    public void test_threeUserRoundedTransaction() throws Exception{
        BalanceCalculator bc = new BalanceCalculator();
        bc.addBalance(USERS.get(0), 10);
        bc.addUser(USERS.get(1));
        bc.addUser(USERS.get(2));
        List<Transaction> transactions = bc.calculateTransaction();
        assertEquals(2, transactions.size());
        Transaction t1 = transactions.get(0);
        assertEquals(t1.getFrom(), USERS.get(1).getName());
        assertEquals(t1.getAmount(), 3.33, 0);
        assertEquals(t1.getTo(), USERS.get(0).getName());
        Transaction t2 = transactions.get(1);
        assertEquals(t2.getFrom(), USERS.get(2).getName());
        assertEquals(t2.getAmount(), 3.33, 0);
        assertEquals(t2.getTo(), USERS.get(0).getName());
    }

    @Test
    public void complicatedRoundedTransaction() throws Exception{
        BalanceCalculator bc = new BalanceCalculator();
        bc.addBalance(USERS.get(0), 100);
        bc.addBalance(USERS.get(1), 100);
        bc.addBalance(USERS.get(2), 50);
        bc.addBalance(USERS.get(3), 25);
        bc.addBalance(USERS.get(4),0);
        bc.addBalance(USERS.get(5),0);
        //45.833333333333333333333333333333333333333333333333333333333
        List<Transaction> transactions = bc.calculateTransaction();
        assertEquals(5, transactions.size());

        Transaction t1 = transactions.get(0);
        assertEquals(t1.getFrom(), USERS.get(4).getName());
        assertEquals(t1.getAmount(), 45.83,0);
        assertEquals(t1.getTo(), USERS.get(1).getName());

        Transaction t2 = transactions.get(1);
        assertEquals(t2.getFrom(), USERS.get(5).getName());
        assertEquals(t2.getAmount(), 45.83,0);
        assertEquals(t2.getTo(), USERS.get(0).getName());

        Transaction t3 = transactions.get(2);
        assertEquals(t3.getFrom(), USERS.get(3).getName());
        assertEquals(t3.getAmount(), 8.33,0);
        assertEquals(t3.getTo(), USERS.get(1).getName());

        Transaction t4 = transactions.get(3);
        assertEquals(t4.getFrom(), USERS.get(3).getName());
        assertEquals(t4.getAmount(),8.33,0);
        assertEquals(t4.getTo(), USERS.get(0).getName());

        Transaction t5 = transactions.get(4);
        assertEquals(t5.getFrom(), USERS.get(3).getName());
        assertEquals(t5.getAmount(), 4.17, 0);
        assertEquals(t5.getTo(), USERS.get(2).getName());
    }


}
