package com.example.c196studentscheduler.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Courses", foreignKeys = {@ForeignKey(entity = Term.class,
                                   parentColumns = "TermId",
                                   childColumns = "TermId"),
                        @ForeignKey(entity = Mentor.class,
                                    parentColumns = "MentorId",
                                    childColumns = "MentorId"),
                        @ForeignKey(entity = Note.class,
                                    parentColumns = "NoteId",
                                    childColumns = "NoteId")})
public class Course {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CourseId")
    private int courseId;

    @ColumnInfo(name = "Name")
    @NonNull
    private String name;

    @ColumnInfo(name = "TermId")
    private int termId;

    @ColumnInfo(name = "MentorId")
    private int mentorId;

    @ColumnInfo(name = "Status")
    @NonNull
    private String status;

    @ColumnInfo(name = "NoteId")
    private int noteId;

    @ColumnInfo(name = "StartDate")
    @NonNull
    private Date startDate;

    @ColumnInfo(name = "EndDate")
    @NonNull
    private Date endDate;

    public Course(int courseId, String name, int termId, int mentorId, String status, int noteId, Date startDate, Date endDate) {
        this.courseId = courseId;
        this.name = name;
        this.termId = termId;
        this.mentorId = mentorId;
        this.status = status;
        this.noteId = noteId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Ignore
    public Course(String name, int termId, int mentorId, String status, int noteId, Date startDate, Date endDate) {
        this.name = name;
        this.termId = termId;
        this.mentorId = mentorId;
        this.status = status;
        this.noteId = noteId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    @Ignore
    public Course() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int note) {
        this.noteId = note;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
