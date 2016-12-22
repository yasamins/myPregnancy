package com.example.pregnancyTracker.Model;

import java.util.Date;

import io.realm.RealmObject;

public class User extends RealmObject {

    private Date dueDate;

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}

