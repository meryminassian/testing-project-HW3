package com.example;

public class BankAccount {
    private Balance balance;
    private AccountNumber accountNumber;
    private AccountType type;

    public BankAccount(){
    }

    public BankAccount(Balance balance, AccountNumber accountNumber, AccountType type) {
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.type = type;
    }

    public BankAccount(Balance balance) {
        this.balance = balance;
    }

    public BankAccount(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BankAccount(AccountType type) {
        this.type = type;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
}
