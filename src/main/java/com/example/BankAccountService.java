package com.example;

import java.util.ArrayList;
import java.util.List;

public class BankAccountService {

    private List<AccountNumber> accountNumbersList;

    public BankAccountService() {
        this.accountNumbersList = new ArrayList<>();
    }

    //making sure to have unique accountNumber
    public boolean CreateAccount(BankAccount bankAccount){
        for (int i = 0; i < accountNumbersList.size(); i++) {
            if(accountNumbersList.get(i).getAccountNumber() == bankAccount.getAccountNumber().getAccountNumber()){
                //System.out.println("This account number is already taken");
                return false;
            }
        }
        accountNumbersList.add(bankAccount.getAccountNumber());
        return true;
    }

    public String ShowAccountDetails(BankAccount bankAccount){
        //checking by one by the fields being null or not;
        //throwing corresponding Errors
        if(bankAccount.getBalance() == null){
            throw new InvalidBalanceException();
        }
        if(bankAccount.getAccountNumber() == null){
            throw new InvalidAccountNumberException();
        }
        if(bankAccount.getType() == null){
            throw new InvalidTypeException();
        }

        return (bankAccount.getAccountNumber().getAccountNumber() + "" +
                " " + bankAccount.getBalance().getBalance() + " " + bankAccount.getType());

    }

    public double deposit(BankAccount bankAccount, double amount){
        if(bankAccount.getBalance() == null){
            throw new InvalidBalanceException();
        }

        if(amount <= 0){
            throw new InvalidInputAmount();
        }
        double currentBalance = bankAccount.getBalance().getBalance();
        double newBalance = currentBalance + amount;
        bankAccount.getBalance().setBalance(newBalance);
        return bankAccount.getBalance().getBalance();
    }

    //if the type is Saving then cannot withdraw more that the 10%
    public double withdraw(BankAccount bankAccount, double amount){
        if(bankAccount.getType() == null){
            throw new InvalidTypeException();
        }
        if(amount <= 0){
            throw new InvalidInputAmount();
        }
        double currentBalance = bankAccount.getBalance().getBalance();

        if(bankAccount.getType() == AccountType.SAVING){
            if ((currentBalance * 0.1) >= amount){
                double newBalance = currentBalance - amount;
                bankAccount.getBalance().setBalance(newBalance);
                return bankAccount.getBalance().getBalance();
            } else{
                throw new InvalidInputAmount();
            }
        } else if (currentBalance >= amount){
            double newBalance = currentBalance - amount;
            bankAccount.getBalance().setBalance(newBalance);
            return bankAccount.getBalance().getBalance();
        } else throw new InvalidInputAmount();
    }

}
