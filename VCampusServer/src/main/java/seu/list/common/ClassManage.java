 /*
   * ClassName: Class
   *
   * Version 1.0
   *
   * Date: 2022.08.10
   * 
   * ClassID, Major, TeacherID, ClassSize
   * 
   * Set to manage student
   * 
   * Last modified by Liu
   */


package seu.list.common;

import java.io.Serializable;

public class ClassManage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String classID = null;
	private String Major = null;
	private String TeacherID = null;
	private int ClassSize = 0;//exist students' number
	
	public ClassManage() {
		super();
	}
	
	public ClassManage(String classid, String teacherID, String major, int classsize)
	{
		classID = classid;
		Major = major;
		TeacherID = teacherID;
		ClassSize = classsize;
	}

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

	public String getMajor() {
		return Major;
	}

	public void setMajor(String major) {
		Major = major;
	}

	public String getTeacherID() {
		return TeacherID;
	}

	public void setTeacherID(String teacherID) {
		TeacherID = teacherID;
	}

	public int getClassSize() {
		return ClassSize;
	}

	public void setClassSize(int classSize) {
		ClassSize = classSize;
	}
	
	@Override
	public String toString() {
		return "Class{" + "ClassID" + classID + ", Major" + Major + ", TeacherID" + TeacherID + ", ClassSize" + ClassSize + "}";
	}
}