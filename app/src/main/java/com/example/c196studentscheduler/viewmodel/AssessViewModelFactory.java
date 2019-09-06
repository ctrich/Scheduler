package com.example.c196studentscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AssessViewModelFactory implements ViewModelProvider.Factory {

    private int courseID;
    private Application application;

    public AssessViewModelFactory(Application application, int courseID) {
        this.application = application;
        this.courseID = courseID;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AssessViewModel(application, courseID);
    }
}
