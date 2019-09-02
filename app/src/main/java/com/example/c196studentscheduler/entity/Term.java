package com.example.c196studentscheduler.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TermId")
    private int termId;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "StartDate")
    private Date startDate;

    @ColumnInfo(name = "EndDate")
    private Date endDate;

    public Term(int termId, String name, Date startDate, Date endDate) {
        termId = this.termId;
        name = this.name;
        startDate = this.startDate;
        endDate = this.endDate;
    }
    @Ignore
    public Term(String name, Date startDate, Date endDate) {
        name = this.name;
        startDate = this.startDate;
        endDate = this.endDate;
    }

    @Ignore
    public Term() {
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
