package com.pabwoopj.maven;

import org.junit.Test;
import static org.junit.Assert.*;


public class BankInterfaceTest {

    private Bank b=new Bank();
    private Integer acc=b.createAccount("ala", "tam");

    @Test
    public void createAccount() {
        assertEquals(acc, b.createAccount("ala","tam"));
    }

    @Test
    public void findAccount() {
        assertEquals(acc,b.findAccount("ala","tam"));
    }
    @Test
    public void findAccountNull(){
        assertNull(b.findAccount("Cezary", "Z Sosnowca"));
    }

    @Test
    public void deposit() {
        long amount=100;
        long tmp=b.getBalance(b.findAccount("ala","tam"));
        b.deposit(b.findAccount("ala","tam"), amount);
        assertEquals(b.getBalance(b.findAccount("ala","tam")),amount+tmp);
    }

    @Test
    public void depositThreeTimes(){
        b.createAccount("ola","m");
        b.deposit(b.findAccount("ola","m"),100);
        b.deposit(b.findAccount("ola","m"),200);
        b.deposit(b.findAccount("ola","m"),300);
        assertEquals(600, b.getBalance(b.findAccount("ola","m")));

    }

    @Test
    public void depositAccountIdException(){
        try{
            b.deposit(3,100);
            fail("Exception wasn't thrown.");
        }
        catch(BankInterface.AccountIdException e) {
            assertNull(e.getMessage());
        }
    }

    @Test
    public void getBalance() {
        b.createAccount("jan","wwa");
        b.createAccount("Andrzej","krk");
        b.deposit(b.findAccount("jan","wwa"),100);
        b.deposit(b.findAccount("Andrzej","krk"),100);
        assertEquals(b.getBalance(b.findAccount("Andrzej","krk")),b.getBalance(b.findAccount("jan","wwa")));
    }

    @Test
    public void getBalanceForNewAccount() {
        b.createAccount("Hanka","Z kartonu");
        assertEquals(0,b.getBalance(b.findAccount("Hanka","Z kartonu")));
    }

    @Test
    public void getBalanceAccountIdException(){
        try{
            b.getBalance(3);
            fail("Exception wasn't thrown!");
        }
        catch(BankInterface.AccountIdException e){
            assertNull(e.getMessage());
        }
    }

    @Test
    public void withdraw() {
        b.createAccount("Ania","Mak");
        b.deposit(b.findAccount("Ania","Mak"),100);
        b.withdraw(b.findAccount("Ania","Mak"),50);
        assertEquals(50,b.getBalance(b.findAccount("Ania","Mak")));
    }

    @Test
    public void withdrawThreeTimes(){
        b.createAccount("Ania","Mak");
        b.deposit(b.findAccount("Ania","Mak"),100);
        b.withdraw(b.findAccount("Ania","Mak"),25);
        b.withdraw(b.findAccount("Ania","Mak"),25);
        b.withdraw(b.findAccount("Ania","Mak"),25);
        assertEquals(25,b.getBalance(b.findAccount("Ania","Mak")));
    }

    @Test
    public void withdrawAccountIdException(){
        try{
            b.withdraw(3,0);
            fail("Exception wasn't thrown.");
        }
        catch(BankInterface.AccountIdException e) {
            assertNull(e.getMessage());
        }
    }

    @Test
    public void withdrawInsufficientFundsException(){
        b.createAccount("Ania","Mak");
        b.deposit(b.findAccount("Ania","Mak"),100);
        try{
            b.withdraw(b.findAccount("Ania","Mak"),400);
            fail("Exception wasn't thrown.");
        }
        catch(BankInterface.InsufficientFundsException e){
            assertNull(e.getMessage());
        }
    }

    @Test
    public void transfer() {
        b.createAccount("1","1");
        b.createAccount("2","2");
        b.deposit(b.findAccount("1","1"),300);
        b.deposit(b.findAccount("2","2"),300);
        b.transfer(b.findAccount("1","1"),b.findAccount("2","2"),100);
        assertEquals(200,b.getBalance(b.findAccount("1","1")));
        assertEquals(400,b.getBalance(b.findAccount("2","2")));
    }

    @Test
    public void transferAccountIdException(){
        b.createAccount("1","1");
        b.createAccount("2","2");
        b.deposit(b.findAccount("1","1"),300);
        b.deposit(b.findAccount("2","2"),300);
        try{
            b.transfer(b.findAccount("5","5"),b.findAccount("2","2"),100);
            fail("Exception wasn't thrown.");
        }
        catch(BankInterface.AccountIdException e) {
            assertNull(e.getMessage());
        }
    }

    @Test
    public void InsufficientFundsException(){
        b.createAccount("1","1");
        b.createAccount("2","2");
        b.deposit(b.findAccount("1","1"),300);
        b.deposit(b.findAccount("2","2"),300);
        try{
            b.transfer(b.findAccount("1","1"),b.findAccount("2","2"),400);
            fail("Exception wasn't thrown.");
        }
        catch(BankInterface.InsufficientFundsException e) {
            assertNull(e.getMessage());
        }
    }

}