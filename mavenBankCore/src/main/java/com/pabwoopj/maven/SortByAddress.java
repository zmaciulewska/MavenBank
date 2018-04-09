package com.pabwoopj.maven;

import java.util.Comparator;

public class SortByAddress implements Comparator<Account> {

    @Override
    public int compare(Account acc1, Account acc2) {
        return acc1.getAddres().compareToIgnoreCase(acc2.getAddres());
    }
}
