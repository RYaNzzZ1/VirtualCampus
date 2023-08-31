package seu.list.common;

import java.io.Serializable;

public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
    private String studentId = null;
    private String studentName = null;
    private String teacher = null;
    private String major = null;
    private String classId = null;
    private Boolean studentGender = true;
    private String studentOrigion = null;
    private String studentStatus = null;
    private String studentPhone = null;
    private double studentCredit = 0.0;
    
    public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Boolean getStudentGender() {
		return studentGender;
	}

	public void setStudentGender(Boolean studentGender) {
		this.studentGender = studentGender;
	}

	public String getStudentOrigion() {
		return studentOrigion;
	}

	public void setStudentOrigion(String studentOrigion) {
		this.studentOrigion = studentOrigion;
	}

	public String getStudentStatus() {
		return studentStatus;
	}

	public void setStudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}

	public String getStudentPhone() {
		return studentPhone;
	}

	public void setStudentPhone(String studentPhone) {
		this.studentPhone = studentPhone;
	}

	public double getStudentCredit() {
		return studentCredit;
	}

	public void setStudentCredit(int studentCredit) {
		this.studentCredit = studentCredit;
	}

    public String getTeacherid() {
		return teacher;
	}

	public void setTeacherid(String teacherid) {
		this.teacher = teacherid;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Student() {
        super();
    }

    public Student(String studentID, String name, String Teacher, String classID, String Major, String origin, String status, Boolean gender, String phone, double credit) {
        this.classId = classID;
        this.studentCredit = credit;
        this.studentGender = gender;
        this.studentId = studentID;
        this.studentName = name;
        this.studentOrigion = origin;
        this.studentPhone = phone;
        this.studentStatus = status;
        this.teacher=Teacher;
        this.major = Major;
    }
    
    @Override
    public String toString() {
    	 return "Student [Studentname=" + studentName + " StudentID=" + studentId + " Teacher=" + teacher + " ClassID=" + classId + " Major=" + major + " StudentOrigion=" +
				 studentOrigion + " StudentStatus=" + studentStatus + " StudentGender=" + studentGender + " StudentPhone=" + studentPhone + " StudentCredit=" + studentCredit + "]";
    }
}

