package com.pabwoopj.maven;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.LinkedList;


public class Bank implements BankInterface {

    private static final Logger log = LogManager.getLogger();
    public Bank() { }
    //lista kont w banku
    private LinkedList<Account> accounts = new LinkedList<>();

    //  uzyskanie obiektu konta wyszukując za pomoca nazwy i adresu
    private Account getAccount(String name, String address) {
        for ( Account a : accounts ) {
            if ( a.getName().equals(name) ) {
                if ( a.getAddres().equals(address) ) return a;
            }
        }
        return null;
    }

    //  uzyskanie obiektu konta wyszukując za pomoca numeru konta
    private Account getAccount(Integer id) {
        for(Account a : accounts){
            if ( a.getId().equals(id) ) return a;
        }
        return null;
    }


    @Override
    public Integer createAccount(String name, String address) {
        log.info("Zostala wywolana metoda createAccount dla parametrow name-{}, address-{}.", name, address);
        Account acc = getAccount(name, address);
        if(acc != null) {
            log.debug("Podane konto już istnieje.");
            return acc.getId();
        }
        else {
            acc = new Account(name, address);
            accounts.add(acc);
            Collections.sort(accounts,new SortByAddress());
            //lub Collections.sort(accounts);
            return acc.getId();
        }
    }


    @Override
    public Integer findAccount(String name, String address) {
        log.info("Zostala wywolana metoda findAccount dla parametrow name-{}, address-{}.", name, address);
        Account acc = getAccount(name,address);
        if(acc != null) return acc.getId();
        else return null;
    }


    @Override
    public void deposit(Integer id, long amount) throws AccountIdException {
        log.info("Zostala wywolana metoda deposit dla parametrow id-{}, amount-{}.", id, amount);
        Account acc = getAccount(id);
        if(acc != null) acc.setAmount(acc.getAmount() + amount);
        else {
            throw new AccountIdException();
        }
    }


    @Override
    public long getBalance(Integer id) throws AccountIdException{
        log.info("Zostala wywolana metoda getBalance dla parametru id-{}.", id);
        Account acc = getAccount(id);
        if(acc != null) return acc.getAmount();
        else throw new AccountIdException();
    }

    @Override
    public void withdraw(Integer id, long amount) throws AccountIdException, InsufficientFundsException {
        log.info("Zostala wywolana metoda withdraw dla parametrow id-{}, amount-{}.", id, amount);
        Account acc = getAccount(id);
        if(acc != null){
            if (acc.getAmount() > amount) acc.setAmount(acc.getAmount() - amount);
            else throw new InsufficientFundsException();
        }
        else throw new AccountIdException();
    }

    @Override
    public void transfer(Integer idSource, Integer idDestination, long amount)throws AccountIdException, InsufficientFundsException{
        log.info("Zostala wywolana metoda transfer dla parametrow id_nadawcy-{}, id_odbiorcy-{}, amount-{}.", idSource, idDestination, amount);
        Account accSource = getAccount(idSource);
        Account accDest = getAccount(idDestination);
        if(accSource == null || accDest == null) throw new AccountIdException();
        else {
            if(accSource.getAmount() > amount) {
                accDest.setAmount(accDest.getAmount() + amount);
                accSource.setAmount(accSource.getAmount() - amount);
            }
            else throw new InsufficientFundsException();
        }
    }
}
