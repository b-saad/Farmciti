package com.prototype.farmCiti.datamodel;

import java.util.Date;

public class MealObject {
    private String type;
    private double amount;
    private Date dateConsumed;

    public MealObject(String type, double amount, Date dateConsumed) {
        this.type = type;
        this.amount = amount;
        this.dateConsumed = dateConsumed;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDateConsumed() {
        return dateConsumed;
    }
}
