package com.example.c196studentscheduler.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
@Entity(tableName = "Notes", foreignKeys = @ForeignKey(entity = Course.class,
                                                       parentColumns = "CourseId",
                                                       childColumns = "CourseId",
                                                        onDelete = CASCADE))
public class Note {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "NoteId")
    private int noteId;

    @NonNull
    @ColumnInfo(name = "NoteText")
    private String noteText;


    @ColumnInfo(name = "CourseId")
    private int courseId;

    public Note(int noteId,String noteText, int courseId) {
        this.noteId = noteId;
        this.noteText = noteText;
        this.courseId = courseId;
    }
    @Ignore
    public Note(String noteText, int courseId) {
        this.noteText = noteText;
        this.courseId = courseId;
    }
    @Ignore
    public Note() {
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
