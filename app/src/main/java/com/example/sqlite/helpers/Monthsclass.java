package com.example.sqlite.helpers;

public class Monthsclass {
    public static int getMonthInYear(String month){
        int monthofyear;
        if(month.equalsIgnoreCase("January")){
            monthofyear = 0;
        }
        else if(month.equalsIgnoreCase("February")){
            monthofyear = 1;
        }
        else if(month.equalsIgnoreCase("March")){
            monthofyear = 2;
        }
        else if(month.equalsIgnoreCase("April")){
            monthofyear = 3;
        }
        else if(month.equalsIgnoreCase("May")){
            monthofyear = 4;
        }
        else if(month.equalsIgnoreCase("June")){
            monthofyear = 5;
        }
        else if(month.equalsIgnoreCase("July")){
            monthofyear = 6;
        }
        else if(month.equalsIgnoreCase("August")){
            monthofyear = 7;
        }
        else if(month.equalsIgnoreCase("September")){
            monthofyear = 8;
        }
        else if(month.equalsIgnoreCase("October")){
            monthofyear = 9;
        }
        else if(month.equalsIgnoreCase("November")){
            monthofyear = 10;
        }
        else{
            monthofyear = 11;
        }

        return monthofyear;
    }
    public static String getMonthFromId(int monthid){
        String month;
        if(monthid == 0){
           month = "January";
        }
        else if(monthid == 1){
            month = "February";
        }
        else if(monthid == 2){
            month = "March";
        }
        else if(monthid == 3){
            month = "April";
        }
        else if(monthid == 4){
            month = "May";
        }
        else if(monthid == 5){
            month = "June";
        }
        else if(monthid == 6){
            month = "July";
        }
        else if(monthid == 7){
            month = "August";
        }
        else if(monthid == 8){
            month = "September";
        }
        else if(monthid == 9){
            month = "October";
        }
        else if(monthid == 10){
            month = "November";
        }
        else{
            month = "December";
        }
        return month;
    }
}
