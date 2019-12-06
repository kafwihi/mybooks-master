package com.example.sqlite.objects;

public class Users {
    public String firstname;
    public String lastname;

    public Users(String firstname,String lastname){
        this.firstname = firstname;
        this.lastname = lastname;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public String getFirstname(){
        return firstname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public String gelastname(){
        return lastname;
    }
}
