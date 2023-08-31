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
	private String major = null;
	private String teacherID = null;
	private int classSize = 0;//exist students' number
	
	public ClassManage() {
		super();
	}
	
	public ClassManage(String classid, String teacherID, String major, int classsize)
	{
		classID = classid;
		this.major = major;
		this.teacherID = teacherID;
		classSize = classsize;
	}

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	public int getClassSize() {
		return classSize;
	}

	public void setClassSize(int classSize) {
		this.classSize = classSize;
	}
	
	@Override
	public String toString() {
		return "Class{" + "ClassID" + classID + ", Major" + major + ", TeacherID" + teacherID + ", ClassSize" + classSize + "}";
	}
}
