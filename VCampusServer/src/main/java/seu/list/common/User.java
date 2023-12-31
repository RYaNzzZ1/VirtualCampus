package seu.list.common;


import seu.list.server.dao.CourseDaoImp;
import seu.list.server.db.SqlHelperImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author 郭念宗
 * @version jdk1.8.0
 */
public class User implements java.io.Serializable {
    private static final long serialVersionUID = 2342342342342342342L;//为了让ServerThread能够写回它
    private String id;//0
    private String name;//1
    private String age;//2
    private String sex;//3
    private String major;//4
    private String grade;//5
    private String pwd;//6
    private String role;//7
    private String money;//8


    public void print() {
        System.out.print(id + "\n" + name + "\n" + age + "\n" + sex + "\n" + major + "\n" + grade + "\n" + pwd + "\n" + role + "\n" + money + "\n" + "\n");
    }

    /**
     * @param content 用户信息
     */
    public void setContent(Vector<String> content) {
        id = content.get(0);
        name = content.get(1);
        age = content.get(2);
        sex = content.get(3);
        major = content.get(4);
        grade = content.get(5);
        pwd = content.get(6);
        role = content.get(7);
        money = content.get(8);
    }

    ;

    /**
     * @return 学号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 学号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 年龄
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age 年龄
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return 年级
     */
    public String getGrade() {
        return grade;
    }

    /**
     * @param grade 年级
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * @return 专业
     */
    public String getMajor() {
        return major;
    }

    /**
     * @param major 专业
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * @return 密码
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd 密码
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return 权限
     */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return 余额
     */
    public String getMoney() {
        return money;
    }

    /**
     * @param money 余额
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * @return 用户信息
     */
    public Vector<String> getContent() {
        Vector<String> userContents = new Vector<String>();
        userContents.add(id);
        userContents.add(name);
        userContents.add(age);
        userContents.add(sex);
        userContents.add(major);
        userContents.add(grade);
        userContents.add(pwd);
        userContents.add(role);
        userContents.add(money);
        return userContents;
    }

    /**
     * @return 用户已选课程
     */
    public List<Course> getCourses() {
        String sql = "select * from tb_Stc where uID = ?";
        List<String> courseIDset = new SqlHelperImp().sqlRelationQuery(sql, new String[]{id});
        List<Course> cList = new ArrayList<Course>();
        for (String cID : courseIDset) {
            cList.add(new CourseDaoImp().searchCourseByID(cID));
        }
        return cList;
    }


    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                ", age='" + getAge() + "'" +
                ", sex='" + getSex() + "'" +
                ", major='" + getMajor() + "'" +
                ", grade='" + getGrade() + "'" +
                ", pwd='" + getPwd() + "'" +
                ", role='" + getRole() + "'" +
                ", money='" + getMoney() + "'" +
                "}";
    }
}
