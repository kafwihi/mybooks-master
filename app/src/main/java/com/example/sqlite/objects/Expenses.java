package com.example.sqlite.objects;

public class Expenses {
    public String expensetype;
    public String expensedescription;
    public String expensecost;
    public long id;

    public Expenses(String expensetype,String expensedescription,String expensecost,long id){
        this.expensetype = expensetype;
        this.expensedescription = expensedescription;
        this.expensecost = expensecost;
        this.id = id;
    }
    public void setExpensetype(String expensetype){
        this.expensetype = expensetype;
    }
    public String getExpensetype(){
        return expensetype;
    }
    public void setExpensecost(String expensecost){
        this.expensecost = expensecost;
    }
    public String getExpensecost(){
        return expensecost;
    }
    public void setExpensedescription(String expensedescription){
        this.expensedescription = expensedescription;
    }
    public String getExpensedescription(){
        return expensedescription;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }
}
