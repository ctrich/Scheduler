package com.example.c196studentscheduler.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.c196studentscheduler.Database.SchedulerRepository;
import com.example.c196studentscheduler.entity.Note;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteViewModel extends AndroidViewModel {

    public MutableLiveData<Note> mLiveNote = new MutableLiveData<>();
    private SchedulerRepository schedulerRepository;
    private Executor executor = Executors.newSingleThreadExecutor();
    public LiveData<List<Note>> noteByCourseId;

    public NoteViewModel(@NonNull Application application, int courseId) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        noteByCourseId = schedulerRepository.getNotesByCourseId(courseId);
    }

    public void addNote(Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.createNote(note);
            }
        });
    }

    public void updateNote(Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.updateNote(note);
            }
        });
    }

    public void deleteNote(Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.deleteNote(note);
            }
        });
    }

    public void loadData(int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Note note = schedulerRepository.getNoteByid(noteId);
                mLiveNote.postValue(note);
            }
        });

    }
}
