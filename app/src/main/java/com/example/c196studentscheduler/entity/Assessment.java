package com.example.c196studentscheduler.entity;

        import androidx.annotation.NonNull;
        import androidx.room.ColumnInfo;
        import androidx.room.Entity;
        import androidx.room.ForeignKey;
        import androidx.room.Ignore;
        import androidx.room.PrimaryKey;

        import java.util.Date;

        import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Assessments", foreignKeys = @ForeignKey(entity = Course.class,
                                                             parentColumns = "CourseId",
                                                             childColumns = "CourseId",
                                                             onDelete = CASCADE))
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "AssessmentId")
    private int assessmentId;

    @ColumnInfo(name = "CourseId")
    private int courseId;

    @NonNull
    @ColumnInfo(name = "Name")
    private String name;

    @NonNull
    @ColumnInfo(name = "Type")
    private String type;

    @NonNull
    @ColumnInfo(name = "DueDate")
    private Date dueDate;

    public Assessment(int assessmentId, int courseId,String name,String type,Date dueDate) {
        this.assessmentId = assessmentId;
        this.courseId = courseId;
        this.name = name;
        this.type = type;
        this.dueDate = dueDate;
    }
    @Ignore
    public Assessment(int courseId,String name,String type,Date dueDate) {
        this.courseId = courseId;
        this.name = name;
        this.type = type;
        this.dueDate = dueDate;
    }
    @Ignore
    public Assessment() {
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
