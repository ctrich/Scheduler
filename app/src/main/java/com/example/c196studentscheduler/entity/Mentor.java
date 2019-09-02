package com.example.c196studentscheduler.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Mentors", indices = {@Index(value = "Phone", unique = true)})
public class Mentor {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "MentorId")
    private int mentorId;

    @ColumnInfo(name = "Name")
    @NonNull
    private String name;

    @ColumnInfo(name = "Phone")
    @NonNull
    private String phone;

    @ColumnInfo(name = "Email")
    @NonNull
    public String eMail;

    public Mentor(int mentorId, String name, String phone, String eMail) {
        this.mentorId = mentorId;
        this.name = name;
        this.phone = phone;
        this.eMail = eMail;
    }

    @Ignore
    public Mentor(String name, String phone, String eMail) {
        this.name = name;
        this.phone = phone;
        this.eMail = eMail;
    }

    @Ignore
    public Mentor() {
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    @NonNull
    public String geteMail() {
        return eMail;
    }

    public void seteMail(@NonNull String eMail) {
        this.eMail = eMail;
    }
}
