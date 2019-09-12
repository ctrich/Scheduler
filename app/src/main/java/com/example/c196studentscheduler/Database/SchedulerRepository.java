package com.example.c196studentscheduler.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

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

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/**
 * Chris Richardson
 * C196
 * Student ID #000895452
 */
public class SchedulerRepository {
    private static final String TAG = "SchedulerRepository";
    //Access to all data access objects
    private TermDAO termDAO;
    private CourseDAO courseDAO;
    private MentorDAO mentorDAO;
    private NoteDAO noteDAO;
    private AssessmentDAO assessmentDAO;

    public LiveData<List<Term>> mTerms;
    public List<Term> listTerms;
    public LiveData<List<Course>> mCourses;
    private SchedulerRoomDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    private static SchedulerRepository ourInstance;
    //If an instance of the repository does not exist create one
    public static SchedulerRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SchedulerRepository(context);
        }
        return ourInstance;
    }

    private SchedulerRepository(Context context) {
        Log.d(TAG, "SchedulerRepository: running" + context);
        mDb = SchedulerRoomDatabase.getDatabase(context);
        termDAO = mDb.termDAO();
        courseDAO = mDb.courseDAO();
        mentorDAO = mDb.mentorDAO();
        assessmentDAO = mDb.assessmentDAO();
        noteDAO = mDb.noteDAO();
        mCourses = getAllCourses();
        mTerms = getAllTerms();
        getTerms();
    }



    //return all
    public LiveData<List<Term>> getAllTerms() {
        return termDAO.getAllTerms();
    }

    public void getTerms() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                listTerms = termDAO.getTerms();
            }
        });

    }

    public LiveData<List<Course>> getAllCourses() {
        return courseDAO.getAllCourses();
    }

        //inserts

        public void createTerm(Term term) {
            executor.execute(() -> mDb.termDAO().insert(term));
        }

        public void createCourse(Course course) {
            mDb.courseDAO().insertCourse(course);
        }

        public void createMentor(Mentor mentor) {
           mDb.mentorDAO().insertMentor(mentor);
        }

        public void createNote(Note note) {
            mDb.noteDAO().insertNote(note);
        }
        public void createAssessment(Assessment assessment) {
            assessmentDAO.insertAssessment(assessment);
        }



    //updates
    public void updateTerm(Term term) {
        termDAO.updateTerm(term);
    }

    public void updateCourse(Course course) {
        courseDAO.updateCourse(course);
    }

    public void updateMentor(Mentor mentor) {
        mentorDAO.updateMentor(mentor);
    }

    public void updateNote(Note note) {
        noteDAO.updateNote(note);
    }

    public void updateAssessment(Assessment assessment) {
        assessmentDAO.updateAssessment(assessment);
    }

    //deletes
    public void deleteTerm(Term term) {
        termDAO.deleteTerm(term);
    }

    public void deleteCourse(Course course) {
        courseDAO.deleteCourse(course);
    }

    public void deleteMentor(Mentor mentor) {
        mDb.mentorDAO().deleteMentor(mentor);;
    }

    public void deleteNote(Note note) {
        noteDAO.deleteNote(note);
    }

    public void deleteAssessment(Assessment assessment) {
        assessmentDAO.deleteAssessment(assessment);
    }

    //search by id
    public Term getTermByid(int termId) {
        return termDAO.getTermById(termId);
    }


    public int numCoursesInTerm(int termId) {
        try {
            return new CourseCount().execute(termId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Course getCourseByid(int courseId) {
        return courseDAO.getCourseById(courseId);
    }

    public Mentor getMentorByid(int mentorId) {
        return mentorDAO.getMentorById(mentorId);
    }

    public Note getNoteByid(int noteId) {
        return noteDAO.getNoteById(noteId);
    }

    public Assessment getAssessmentByid(int assessmentId) {
        return assessmentDAO.getAssessmentById(assessmentId);
    }

    //search by related objects
    public LiveData<List<Course>> getCoursesByTermId(int termId) {
        return courseDAO.getCoursesByTerm(termId);
    }

    public LiveData<List<Note>> getNotesByCourseId(int courseId) {
        return noteDAO.getNoteByCourseId(courseId);
    }

    public LiveData<List<Mentor>> getMentorByCourseId(int courseId) {
        return mentorDAO.getMentorsByCourseId(courseId);
    }
    public LiveData<List<Assessment>> getAssessmentByCourseId(int courseId) {
        return assessmentDAO.getAssessmentsByCourseId(courseId);
    }

    private class CourseCount extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            int termId = integers[0].intValue();
            return courseDAO.getCoursesForTermDelete(termId);
        }
    }
}
