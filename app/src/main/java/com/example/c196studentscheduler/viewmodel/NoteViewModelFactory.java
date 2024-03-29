package com.example.c196studentscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class NoteViewModelFactory implements ViewModelProvider.Factory {

    private int courseId;
    private Application application;
    //Createa a view model factory that allows us to pass the courseID to the view model
    public NoteViewModelFactory(Application application, int courseId) {
        this.courseId = courseId;
        this.application = application;
    }

    public NoteViewModelFactory(Application application) {
        this.application = application;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NoteViewModel(application, courseId);
    }
}
