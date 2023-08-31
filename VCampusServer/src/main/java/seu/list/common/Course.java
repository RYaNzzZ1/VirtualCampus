package seu.list.common;
//package seu.list.common;


import java.io.Serializable;
import java.util.Vector;
/**
 * @author 郭念宗
 * @version jdk1.8.0
 */
public class Course implements Serializable {
	private static final long serialVersionUID = 6424750174292826127L;
	private  String semester;
	private String courseID;
	private String courseMajor;
	private String courseName;
	private String teacherID;
	private String courseState;
	private String courseType;

	/**
	 * 无参构造器
	 */

	public Course() {
	}
	/**
	 * 构造函数
	 * @param semester 授课学期
	 * @param courseID 课程编号
	 * @param courseMajor 专业
	 * @param courseName 课程名称
	 * @param teacherID 授课教师
	 * @param courseState 课程状态
	 * @param courseType 课程类型
	 */
	public Course(String semester, String courseID, String courseMajor, String courseName, String teacherID, String courseState, String courseType) {
		this.semester = semester;
		this.courseID = courseID;
		this.courseMajor = courseMajor;
		this.courseName = courseName;
		this.teacherID = teacherID;
		this.courseState = courseState;
		this.courseType = courseType;
	}
	/**
	 *
	 * @return 授课学期
	 */
	public String getSemester() {
		return semester;
	}
	/**
	 *
	 * @param semester 书号
	 */
	public void setSemester(String semester) {
		this.semester = semester;
	}
	/**
	 *
	 * @return 课程编号
	 */
	public String getCourseID() {
		return courseID;
	}
	/**
	*
	* @param courseID 课程编号
	*/
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	/**
	 *
	 * @return 专业
	 */
	public String getCourseMajor() {
		return courseMajor;
	}
	/**
	 *
	 * @param courseMajor 专业
	 */
	public void setCourseMajor(String courseMajor) {
		this.courseMajor = courseMajor;
	}
	/**
	 *
	 * @return 课程名称
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 *
	 * @param courseName 课程名称
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 *
	 * @return 授课教师
	 */
	public String getTeacherID() {
		return teacherID;
	}
	/**
	 *
	 * @param teacherID 授课教师
	 */
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	/**
	 *
	 * @return 课程状态
	 */
	public String getCourseState() {
		return courseState;
	}
	/**
	 *
	 * @param courseState 课程状态
	 */
	public void setCourseState(String courseState) {
		this.courseState = courseState;
	}
	/**
	 *
	 * @return 课程类型
	 */
	public String getCourseType() {
		return courseType;
	}
	/**
	 *
	 * @param courseType 课程类型
	 */
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
	/**
	 *
	 * @return 课程信息（授课学期，课程编号，专业，课程名称，授课教师，课程状态，课程类型）
	 */
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
	/**
	 *
	 * @param content 课程信息
	 */
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
