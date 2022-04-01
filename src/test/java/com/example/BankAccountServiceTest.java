package com.example;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BankAccountServiceTest {

//    BankAccount bankAccount1;
//    BankAccount bankAccount2;
    BankAccountService service;

    @BeforeSuite
    void setUp(){
//        bankAccount1 = new BankAccount();
//        bankAccount2 = new BankAccount();
        service = new BankAccountService();

    }

    @Test(description = "testing valid unique account number")
    void CreateAccountValid(){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(new AccountNumber(765));
        assertTrue(service.CreateAccount(bankAccount));
    }

    @Test(description = "testing that repeated account number is not Valid")
    void CreateAccountInvalid(){
        BankAccount bankAccount1 = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();
        bankAccount1.setAccountNumber(new AccountNumber(98765));
        bankAccount2.setAccountNumber(new AccountNumber(98765));
        //first creating account1 with number 98765
        service.CreateAccount(bankAccount1);
        //since we already have account number 98765, we can not create the second one;
        assertFalse(service.CreateAccount(bankAccount2));
    }


    @Test(description = "testing if the method will throw BallanceError")
    void ShowAccountDetailsWithNullBalance(){
        //should throw invalid balanceError
        BankAccount acc = new BankAccount();
        acc.setAccountNumber(new AccountNumber(2451));
        acc.setType(AccountType.SAVING);
        assertThrows(InvalidBalanceException.class, () -> service.ShowAccountDetails(acc));
    }

    @Test(description = "testing if the method will throw invalid AccountNumber Error")
    void ShowAccountDetailsWithNullAccountNumber(){
        BankAccount acc = new BankAccount();
        acc.setBalance(new Balance(10000));
        acc.setType(AccountType.SAVING);
        assertThrows(InvalidAccountNumberException.class, () -> service.ShowAccountDetails(acc));
    }

    @Test(description = "testing if the method will throw Invalid Type Error")
    void ShowAccountDetailsWithNullType(){
        BankAccount acc = new BankAccount();
        acc.setBalance(new Balance(10000));
        acc.setAccountNumber(new AccountNumber(532));
        assertThrows(InvalidTypeException.class, () -> service.ShowAccountDetails(acc));
    }

    @Test(description = "testing valid Bank Account")
    void ShowAccountDetailsValid(){
        BankAccount acc = new BankAccount();
        acc.setBalance(new Balance(100.54));
        acc.setAccountNumber(new AccountNumber(543));
        acc.setType(AccountType.SAVING);
        assertEquals("543 100.54 SAVING", service.ShowAccountDetails(acc));
    }


    @Test(description = "Testing valid amount for deposit")
    void depositValid(){
        BankAccount acc = new BankAccount(new Balance(1000.65));
        assertEquals(1100.65, service.deposit(acc, 100));
    }

    @Test(description = "Testing invalid amount for deposit")
    void depositInValid(){
        BankAccount acc = new BankAccount(new Balance(65400));
        assertThrows(InvalidInputAmount.class, ()->  service.deposit(acc, -7654));
    }

    //while testing the method deposit, we can notice that our Balance can be null,
    // but we didnt cover that in the method, so we go back to the method,
    // and add a condition of balance being null, -- TDD

    @Test(description = "balance being null")
    void depositBalanceNull(){
        BankAccount bankAccount = new BankAccount();
        assertThrows(InvalidBalanceException.class, () -> service.deposit(bankAccount, 53));
    }


    @Test(description = "withdrawing with Invalid Account Type")
    void withdrawInvalidType(){
        BankAccount acc = new BankAccount();
        assertThrows(InvalidTypeException.class, () -> service.withdraw(acc, -441));
    }

    @Test(description = "withdrawing invalid amount of money")
    void withdrawInvalidAmount(){
        BankAccount acc = new BankAccount(new Balance(5000), new AccountNumber(76234), AccountType.SAVING);
        assertThrows(InvalidInputAmount.class, () -> service.withdraw(acc, -441));
    }

    @Test(description = "withdrawing with Type SAVING, but more than 10% of the balance")
    void withdrawSavingInvalid(){
        BankAccount acc = new BankAccount(new Balance(1000), new AccountNumber(1122), AccountType.SAVING);
        //1000 * 10% = 100, so cant withdraw more than 100
        assertThrows(InvalidInputAmount.class, () -> service.withdraw(acc, 200));
    }

    @Test(description = "withdrawing with Type SAVING, but less than 10% of the balance => valid case")
    void withdrawSavingValid(){
        BankAccount acc = new BankAccount(new Balance(1000), new AccountNumber(1122), AccountType.SAVING);
        //1000 * 10% = 100, so cant withdraw more than 100
        assertEquals(910 , service.withdraw(acc, 90));
    }

    @Test(description = "withdrawing with Type CHECKING, but more than the balance")
    void withdrawCheckingInvalid(){
        BankAccount acc = new BankAccount(new Balance(1000), new AccountNumber(1122), AccountType.CHECKING);
        assertThrows(InvalidInputAmount.class, () -> service.withdraw(acc, 1300));
    }

    @Test(description = "withdrawing with Type CHECKING, but less than the balance => valid case")
    void withdrawCheckingValid(){
        BankAccount acc = new BankAccount(new Balance(1000), new AccountNumber(1122), AccountType.CHECKING);
        assertEquals(700 , service.withdraw(acc, 300));
    }

}