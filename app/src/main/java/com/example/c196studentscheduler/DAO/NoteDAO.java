package com.example.c196studentscheduler.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196studentscheduler.entity.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void isertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM NOTES")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM NOTES WHERE CourseId = :courseId")
    LiveData<List<Note>> getNoteByCourseId(int courseId);

    @Query("SELECT * FROM Notes WHERE NoteId = :noteId")
    LiveData<Note> getNoteById(int noteId);
}
