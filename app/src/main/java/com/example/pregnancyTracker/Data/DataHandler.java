package com.example.pregnancyTracker.Data;

import io.realm.RealmObject;

public interface DataHandler {
    void storageDataSaved(RealmObject savedObject);
    void storageDataError(Throwable error);
}