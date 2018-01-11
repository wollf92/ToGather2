package com.example.wollf.togather;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wollf on 17-12-2017.
 */

public class BalanceCalculator {
    public static void main(String[] args){
        //for local testing
        User user1 = new User("Peter",null,null);
        User user2 = new User("Adam",null,null);
        User user3 = new User("Harold",null,null);
        User user4 = new User("Sam",null,null);
        User user5 = new User("Suzy",null,null);
        User user6 = new User("Michael",null,null);
        User user7 = new User("Emilia",null,null);
        BalanceCalculator bc = new BalanceCalculator();
        bc.addBalance(user1, 10);
        bc.addBalance(user2, 20);
        bc.addBalance(user3, 13);
        bc.addBalance(user4, 23.4);
        bc.addBalance(user5, 219);
        bc.addBalance(user2, 5);
        bc.addBalance(user6, 99);
        bc.addBalance(user7, 0);
        List<Transaction> trans = bc.calculateTransaction();
        for(Transaction t : trans){
            System.out.printf("User: %s, pays %.2f to User: %s\n",t.getFrom(), t.getAmount(), t.getTo());
            //System.out.printf(t.getFrom() + " " + t.getAmount() + " " + t.getTo());
        }
    }
    private Map<User, Double> balance;

    public BalanceCalculator(){
        balance = new HashMap<>();
    }
    public void addUser(User u){
        if(!balance.containsKey(u))
            balance.put(u,0.0);
    }
    public void addBalance(User u, double amount){
        double curAmount = balance.getOrDefault(u, 0.0);
        curAmount += amount;
        balance.put(u,curAmount);
    }

    public List<Transaction> calculateTransaction(){
        List<Transaction> minimumTransactions = new ArrayList<>();
        Map<User, Double> calculatedBalance = new HashMap<>();
        double sum = getSum(balance.values());
        double res = -(sum / balance.size());
        for(Map.Entry<User, Double> e : balance.entrySet()){
            calculatedBalance.put(e.getKey(), e.getValue() + res);
        }
        System.out.println(calculatedBalance);
        recursiveCalculate(calculatedBalance, minimumTransactions);
        return minimumTransactions;
    }

    private void recursiveCalculate(Map<User, Double> calculatedBalance, List<Transaction> minimumTransactions) {
        Map.Entry<User, Double> mxCredit = getMax(calculatedBalance);
        Map.Entry<User, Double> mxDebit = getMin(calculatedBalance);
        if(mxCredit.getValue().doubleValue() == 0.0 && mxDebit.getValue().doubleValue() == 0.0)
            return;
        double min = Math.min(-mxDebit.getValue(),mxCredit.getValue());
        calculatedBalance.put(mxCredit.getKey(), mxCredit.getValue() - min);
        calculatedBalance.put(mxDebit.getKey(), mxDebit.getValue() + min);
        minimumTransactions.add(new Transaction(mxDebit.getKey(), min, mxCredit.getKey()));
        recursiveCalculate(calculatedBalance, minimumTransactions);
    }

    private Map.Entry<User,Double> getMax(Map<User, Double> calculatedBalance) {
        Map.Entry<User, Double> max = calculatedBalance.entrySet().iterator().next();
        for(Map.Entry<User, Double> e : calculatedBalance.entrySet()){
            if(e.getValue() > max.getValue()){
                max = e;
            }
        }
        return max;
    }

    private Map.Entry<User, Double> getMin(Map<User, Double> calculatedBalance){
        Map.Entry<User, Double> min = calculatedBalance.entrySet().iterator().next();
        for(Map.Entry<User, Double> e : calculatedBalance.entrySet()){
            if(e.getValue() < min.getValue()) {
                min = e;
            }
        }
        return min;
    }

    private double getSum(Collection<Double> values) {
        double sum = 0;
        for(double d : values)
            sum += d;
        return sum;
    }

    public double getUserTotalPayments(User u){
        return balance.getOrDefault(u, 0.0);
    }
}
