package seu.list.common;


import java.util.Vector;
/**
 * @author 郭念宗
 * @version jdk1.8.0
 */
public class Course implements java.io.Serializable{


	private static final long serialVersionUID = 6424750174292826127L;
	private  String semester;
	private String courseID;
	private String courseMajor;
	private String courseName;
	private String teacherID;
	private String courseState;
	private String courseType;



	public Course() {
	}

	public Course(String semester, String courseID, String courseMajor, String courseName, String teacherID, String courseState, String courseType) {
		this.semester = semester;
		this.courseID = courseID;
		this.courseMajor = courseMajor;
		this.courseName = courseName;
		this.teacherID = teacherID;
		this.courseState = courseState;
		this.courseType = courseType;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getCourseMajor() {
		return courseMajor;
	}

	public void setCourseMajor(String courseMajor) {
		this.courseMajor = courseMajor;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	public String getCourseState() {
		return courseState;
	}

	public void setCourseState(String courseState) {
		this.courseState = courseState;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	@Override
	public String toString() {
		return "Course{" +
				"Semester='" + semester + '\'' +
				", CourseID='" + courseID + '\'' +
				", CourseMajor='" + courseMajor + '\'' +
				", CourseName='" + courseName + '\'' +
				", teacherID='" + teacherID + '\'' +
				", CourseState='" + courseState + '\'' +
				", CourseType='" + courseType + '\'' +
				'}';
	}

	public Vector<String> getContent() {
		Vector<String> courseContents = new Vector<String>();
		courseContents.add(semester);
		courseContents.add(courseID);
		courseContents.add(courseMajor);
		courseContents.add(courseName);
		courseContents.add(teacherID);
		courseContents.add(courseState);
		courseContents.add(courseType);
		return courseContents;
	}
	public void setContent(Vector<String> content) {
		semester = content.get(0);
		courseID = content.get(1);
		courseMajor = content.get(2);
		courseName = content.get(3);
		teacherID = content.get(4);
		courseState =content.get(5);
		courseType =content.get(6);
	}
}
