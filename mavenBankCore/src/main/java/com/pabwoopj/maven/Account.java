package com.pabwoopj.maven;

public class Account implements Comparable<Account> {

    static private Integer number = 1000;
    private String name;
    private String address;
    private Integer id;
    private long amount;

    Account() { }


    Account(String name, String address) {
        this.address = address;
        this.name = name;
        this.id = number++;
        this.amount = 0;
    }

    /**
     *
     * @param acc
     * @return
     */
    @Override
    public int compareTo(Account acc) {
        return this.name.compareToIgnoreCase(acc.name);
        //mozna uzyc collator ktory  jest wolniejszy w dzialaniu od compareTo,
        //ale uwzglednia polskie znaki
        //import java.text.Collator
        //return Collator.getInstance().compare(this.name,acc.name);
    }


    /**
     * @return id numer konta
     */
    public final Integer getId(){
        return id;
    }

    /**
     * @return name nazwa klienta
     */
    public final String getName() {
        return name;
    }

    /**
     * @return address adres klienta
     */
    public final String getAddres() {
        return address;
    }

    /**
     * @param amount setting an amount for this account
     */
    public final void setAmount(long amount){
        this.amount = amount;
    }

    /**
     * @return amount środki na koncie klienta
     */
    public final long getAmount(){
        return amount;
    }

    /**
     * @return informacje o koncie
     */
    public final String toString(){
        return name + " " + address + " " + id;
    }
}