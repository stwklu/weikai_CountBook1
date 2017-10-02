package com.example.weikailu.weikai_countbook;

import android.view.View;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by weikailu on 2017-10-01.
 */

public class Counter implements Serializable{

    private int count;
    private String name;
    private Date initialDate;
    private Date updateDate;
    private String comment;
    private int initial_count;

    public Counter(String name, Integer count,Date date) {
        this.name = name;
        this.initialDate = date;
        this.updateDate = date;
        this.count = count;
        this.initial_count = count;
        this.comment ="";

    }

    public Counter(String name, Integer count, Date initialDate, Date updateDate){
        this.name = name;
        this.initialDate = initialDate;
        this.count = count;
        this.initial_count = count;
        this.comment = "";
        this.updateDate = updateDate;
    }

    public void countUpdate(int value){
        // update new count value
        this.count = value;
        this.updateDate = new Date();
    }

    public void changeName(String name){
        // Chang counter name
        this.name = name;
    }


    public Date getUpdateDate() {
        return updateDate;
    }

    public Date getInitialDate(){
        return initialDate;
    }

    public String getName(){
        return name;
    }

    public int getCount(){
        return count;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public int getInitial_count() {
        return initial_count;
    }

    public void setInitial_count(int count){
        this.initial_count = count;
    }

    @Override
    public String toString(){
        return this.getName() + "\n  Count: " + this.getCount() + "\n  Created on: " + this.getInitialDate();
    }
}
