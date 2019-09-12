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
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class NoteViewModel extends AndroidViewModel {
    //Used to display the data in the edit note activity
    public MutableLiveData<Note> mLiveNote = new MutableLiveData<>();
    private SchedulerRepository schedulerRepository;
    //Used to run function on a background thread
    private Executor executor = Executors.newSingleThreadExecutor();
    public LiveData<List<Note>> noteByCourseId;

    public NoteViewModel(@NonNull Application application, int courseId) {
        super(application);
        schedulerRepository = SchedulerRepository.getInstance(application.getApplicationContext());
        noteByCourseId = schedulerRepository.getNotesByCourseId(courseId);
    }

    /**
     *
     * @param note
     * Add a new note to the database
     */
    public void addNote(Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.createNote(note);
            }
        });
    }

    /**
     *
     * @param note
     * Updates an existing note in the database
     */
    public void updateNote(Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.updateNote(note);
            }
        });
    }

    /**
     *
     * @param note
     * Deletes a note from the database
     */
    public void deleteNote(Note note) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                schedulerRepository.deleteNote(note);
            }
        });
    }

    /**
     *
     * @param noteId
     * Loads the note data in the edit activity
     */
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
