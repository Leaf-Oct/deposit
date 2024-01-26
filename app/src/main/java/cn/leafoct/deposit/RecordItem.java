package cn.leafoct.deposit;

import java.util.ArrayList;

public class RecordItem {
    public String date, description;
    public double value, balance;
    public boolean is_expense;

    public RecordItem(String date, String description, double value, double balance, boolean is_expense) {
        this.date = date;
        this.description = description;
        this.value = value;
        this.balance = balance;
        this.is_expense = is_expense;
    }
}
