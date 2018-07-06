package com.axon.complaintsstats.queryside.entity;

public class AccountBalance {
    private String accountId;
    private int balance;

    public AccountBalance() {
    }

    public AccountBalance(String accountId, int balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
