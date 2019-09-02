package com.example.c196studentscheduler.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.c196studentscheduler.DAO.AssessmentDAO;
import com.example.c196studentscheduler.DAO.CourseDAO;
import com.example.c196studentscheduler.DAO.MentorDAO;
import com.example.c196studentscheduler.DAO.NoteDAO;
import com.example.c196studentscheduler.DAO.TermDAO;
import com.example.c196studentscheduler.entity.Assessment;
import com.example.c196studentscheduler.entity.Course;
import com.example.c196studentscheduler.entity.Mentor;
import com.example.c196studentscheduler.entity.Note;
import com.example.c196studentscheduler.entity.Term;

@Database(entities = {Term.class, Course.class, Mentor.class, Note.class, Assessment.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class SchedulerRoomDatabase extends RoomDatabase {
    private static final String TAG = "SchedulerRoomDatabase";
    public static final String DATABASE_NAME = "Scheduler.db";
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract MentorDAO mentorDAO();
    public abstract NoteDAO noteDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile SchedulerRoomDatabase INSTANCE;
    private static final Object lock = new Object();

    static SchedulerRoomDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (lock) {
                if (INSTANCE == null)
                    Log.d(TAG, "getDatabase: running " + context);
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SchedulerRoomDatabase.class, DATABASE_NAME).build();
            }
        }
        return INSTANCE;
    }
}
