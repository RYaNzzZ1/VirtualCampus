package seu.list.common;


import java.io.Serializable;
import java.util.Vector;

/**
 * @version jdk1.8.0
 */
public class Course implements Serializable {
    private static final long serialVersionUID = 6424750174292826127L;
    private String Semester;
    private String CourseID;
    private String CourseMajor;
    private String CourseName;
    private String teacherID;
    private String CourseState;
    private String CourseType;

    /**
     * 无参构造器
     */

    public Course() {
    }

    /**
     * 构造函数
     *
     * @param semester    授课学期
     * @param courseID    课程编号
     * @param courseMajor 专业
     * @param courseName  课程名称
     * @param teacherID   授课教师
     * @param courseState 课程状态
     * @param courseType  课程类型
     */
    public Course(String semester, String courseID, String courseMajor, String courseName, String teacherID, String courseState, String courseType) {
        Semester = semester;
        CourseID = courseID;
        CourseMajor = courseMajor;
        CourseName = courseName;
        this.teacherID = teacherID;
        CourseState = courseState;
        CourseType = courseType;
    }

    /**
     * @return 授课学期
     */
    public String getSemester() {
        return Semester;
    }

    /**
     * @param semester 书号
     */
    public void setSemester(String semester) {
        Semester = semester;
    }

    /**
     * @return 课程编号
     */
    public String getCourseID() {
        return CourseID;
    }

    /**
     * @param courseID 课程编号
     */
    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public String getCourseMajor() {
        return CourseMajor;
    }

    public void setCourseMajor(String courseMajor) {
        CourseMajor = courseMajor;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getCourseState() {
        return CourseState;
    }

    public void setCourseState(String courseState) {
        CourseState = courseState;
    }

    public String getCourseType() {
        return CourseType;
    }

    public void setCourseType(String courseType) {
        CourseType = courseType;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Semester='" + Semester + '\'' +
                ", CourseID='" + CourseID + '\'' +
                ", CourseMajor='" + CourseMajor + '\'' +
                ", CourseName='" + CourseName + '\'' +
                ", teacherID='" + teacherID + '\'' +
                ", CourseState='" + CourseState + '\'' +
                ", CourseType='" + CourseType + '\'' +
                '}';
    }

    public Vector<String> getContent() {
        Vector<String> courseContents = new Vector<String>();
        courseContents.add(Semester);
        courseContents.add(CourseID);
        courseContents.add(CourseMajor);
        courseContents.add(CourseName);
        courseContents.add(teacherID);
        courseContents.add(CourseState);
        courseContents.add(CourseType);
        return courseContents;
    }

    public void setContent(Vector<String> content) {
        Semester = content.get(0);
        CourseID = content.get(1);
        CourseMajor = content.get(2);
        CourseName = content.get(3);
        teacherID = content.get(4);
        CourseState = content.get(5);
        CourseType = content.get(6);
    }
}
