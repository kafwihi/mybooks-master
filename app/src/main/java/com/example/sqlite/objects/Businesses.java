package com.example.sqlite.objects;

public class Businesses {
    public String businessname;
    public String businessdescription;
    public long id;
    public Businesses(String businessname,String businessdescription,long id){
        this.businessname = businessname;
        this.businessdescription = businessdescription;
        this.id = id;
    }
    public void setBusinessname(String businessname){
        this.businessname = businessname;
    }
    public String getBusinessname(){
        return businessname;
    }
    public void setBusinessdescription(String businessdescription){
        this.businessdescription = businessdescription;
    }
    public String getBusinessdescription(){
        return businessdescription;
    }
    public void setId(long Id){
        this.id = id;
    }
    public long getId(){
        return id;
    }
}
