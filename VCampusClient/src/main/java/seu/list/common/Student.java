package seu.list.common;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentid = null;
    private String studentName = null;
    private String teacher = null;
    private String major = null;
    private String classid = null;
    private Boolean studentgender = true;
    private String studentorigion = null;
    private String studentstatus = null;
    private String studentphone = null;
    private double studentcredit = 0.0;

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public Boolean getStudentgender() {
        return studentgender;
    }

    public void setStudentgender(Boolean studentGender) {
        this.studentgender = studentGender;
    }

    public String getStudentorigion() {
        return studentorigion;
    }

    public void setStudentorigion(String studentorigion) {
        this.studentorigion = studentorigion;
    }

    public String getStudentstatus() {
        return studentstatus;
    }

    public void setStudentstatus(String studentstatus) {
        this.studentstatus = studentstatus;
    }

    public String getStudentphone() {
        return studentphone;
    }

    public void setStudentphone(String studentphone) {
        this.studentphone = studentphone;
    }

    public double getStudentcredit() {
        return studentcredit;
    }

    public void setStudentcredit(int studentcredit) {
        this.studentcredit = studentcredit;
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
        this.classid = classID;
        this.studentcredit = credit;
        this.studentgender = gender;
        this.studentid = studentID;
        this.studentName = name;
        this.studentorigion = origin;
        this.studentphone = phone;
        this.studentstatus = status;
        this.teacher = Teacher;
        this.major = Major;
    }

    @Override
    public String toString() {
        return "Student [Studentname=" + studentName + " StudentID=" + studentid + " Teacher=" + teacher + " ClassID=" + classid + " Major=" + major + " StudentOrigion=" +
                studentorigion + " StudentStatus=" + studentstatus + " StudentGender=" + studentgender + " StudentPhone=" + studentphone + " StudentCredit=" + studentcredit + "]";
    }
}

