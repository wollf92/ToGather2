package com.example.wollf.togather;

/**
 * Created by wollf on 17-12-2017.
 */

public class Transaction {
    private String from, to;
    private double amount;
    public Transaction(User from, double amount, User to){
        this.from = from.getName();
        this.amount = amount;
        this.to = to.getName();
    }
    public String getFrom(){
        return from;
    }
    public String getTo(){
        return to;
    }
    public double getAmount(){
        return amount;
    }
}
