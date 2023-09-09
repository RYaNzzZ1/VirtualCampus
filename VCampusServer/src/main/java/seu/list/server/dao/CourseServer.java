package seu.list.server.dao;

import seu.list.common.Course;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.User;
import seu.list.server.db.SqlHelper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class CourseServer {

    private Message mesFromClient;
    private Message mesToClient = new Message();

    public CourseServer() {
    }

    public CourseServer(Message mesFromClient) {
        this.mesFromClient = mesFromClient;
    }

    public void execute() {

        switch (this.mesFromClient.getMessageType()) {
            case MessageType.REQ_STU_ADD_LESSON: {
                System.out.println("serving REQ_STU_ADD_LESSON");
                System.out.println("adding....");
                //检查课程是否存在
                Course course = new Course();
                String CourseId = this.mesFromClient.getContent().get(0);
                System.out.println("CourseID为" + CourseId);
                course = this.searchCourseByID(CourseId);
                if (course == null)
                    this.mesToClient.setSeccess(false);
                else {
                    //String CourseId = this.mesFromClient.getContent().get(0);
                    String UserId = this.mesFromClient.getContent().get(1);

                    this.mesToClient.setSeccess(this.sigAddCourse(CourseId, UserId));
                }
                System.out.println("REQ_STU_ADD_LESSON finished");
                break;
            }
            case MessageType.REQ_STU_REMOVE_LESSON: {
                System.out.println("serving REQ_STU_REMOVE_LESSON");
                System.out.println("removing....");
                String CourseId = this.mesFromClient.getContent().get(0);
                String UserId = this.mesFromClient.getContent().get(1);

                this.mesToClient.setSeccess(this.sigRemoveCourse(CourseId, UserId));
                System.out.println("REQ_STU_REMOVE_LESSON finshed");
                break;
            }
            case MessageType.REQ_ADD_LESSON: {
                System.out.println("serving REQ_ADD_LESSON");
                System.out.println("adding....");
                //
                Course course = new Course();
                course.setContent(this.mesFromClient.getContent());

                this.mesToClient.setSeccess(this.genAddCourse(course));
                //this.mesToClient.setData(this.genAddCourse(course));
                System.out.println("REQ_ADD_LESSON finished");
                break;
            }
            case MessageType.REQ_REMOVE_LESSON: {
                System.out.println("serving REQ_REMOVE_LESSON");
                System.out.println("removing.....");
                String courseName = this.mesFromClient.getContent().get(2);

                this.mesToClient.setSeccess(this.genRemoveCourse(courseName));
                System.out.println("REQ_REMOVE_LESSON finished");
                break;
            }
            case MessageType.REQ_SEARCH_LESSON: {
                System.out.println("serving REQ_SEARCH_LESSON");
                System.out.println("searching.....");
                Course course = new Course();
                String courseID = this.mesFromClient.getContent().get(1);
                System.out.println("CourseID为" + courseID);
                course = this.searchCourseByID(courseID);
                //System.out.println(course.getContent());
                if (course == null) this.mesToClient.setSeccess(false);
                else {
                    this.mesToClient.setContent(course.getContent());
                    this.mesToClient.setSeccess(true);
                }


                //System.out.println(this.mesToClient.getContent());
                System.out.println("REQ_SEARCH_LESSON finished");
                break;
            }
            case MessageType.REQ_SHOW_ALL_LESSON: {
                System.out.println("serving REQ_SHOW_ALL_LESSON");
                System.out.println("grabbing.....");
                Vector<String> sigCourseContent = new Vector<String>();
                Vector<String> allCourseContent = new Vector<String>();
                List<Course> allCourse = new LinkedList<Course>();
                allCourse = this.getAllCourse();
                Iterator<Course> iteAllCourse = allCourse.iterator();
                while (iteAllCourse.hasNext()) {
                    sigCourseContent = iteAllCourse.next().getContent();
                    for (int i = 0; i <= 6; i++) {
                        allCourseContent.add(sigCourseContent.get(i));
                    }
                }
                System.out.println(allCourseContent);
                this.mesToClient.setContent(allCourseContent);
                System.out.println("REQ_SHOW_ALL_LESSON finished");
                break;
            }
            case MessageType.REQ_STU_ALL_CHOOOSE: {
                System.out.println("serving REQ_STU_ALL_CHOOOSE");
                System.out.println("grabbing......");
                //�������и�ѧ����ѡ�γ�
                Vector<String> sigCourseContent = new Vector<String>();
                Vector<String> allCourseContent = new Vector<String>();
                List<Course> allCourse; //= new LinkedList<Course>();
                User user = new User();
                user.setContent(this.mesFromClient.getContent());
                allCourse = user.getCourses();
                Iterator<Course> iteAllCourse = allCourse.iterator();
                while (iteAllCourse.hasNext()) {
                    sigCourseContent = iteAllCourse.next().getContent();
                    if (sigCourseContent != null)
                        for (int i = 0; i <= 6; i++) {
                            allCourseContent.add(sigCourseContent.get(i));
                        }

                }
                this.mesToClient.setContent(allCourseContent);
                System.out.println("REQ_STU_ALL_CHOOOSE finished");
                break;
            }
            default:
                break;
        }
    }

    public Message getMesToClient() { // �����޸ģ��������Ҫ�����������
        System.out.println("mesToClient的内容是" + this.mesToClient.getContent());
        return this.mesToClient;
    }


    public Course searchCourseByID(String courseID) {
        // TODO Auto-generated method stub
        String sql = "select * from tb_Class where cID =?";
        String[] paras = new String[1];
        paras[0] = courseID;
        List<Course> cList = new SqlHelper().sqlCourseQuery(sql, paras);
        if (cList != null && cList.size() > 0) {
            System.out.println(cList.get(0));
            return cList.get(0);
        } else
            return null;
    }

    public List<Course> getAllCourse() {
        // TODO Auto-generated method stub
        String sql = "select * from tb_Class";
        return new SqlHelper().sqlCourseQuery(sql, new String[]{});
    }


    public boolean sigAddCourse(String courseID, String uID) {
        String sql = "select * from tb_Class where cID= ?";
        String[] paras = new String[1];
        paras[0] = courseID;
        List<Course> thiscourse = new SqlHelper().sqlCourseQuery(sql, paras);

        String[] paras2 = new String[4];
        paras2[0] = uID + courseID;
        paras2[1] = uID;
        paras2[2] = courseID;
        paras2[3] = thiscourse.get(0).getCourseName();
        String sql2 = "insert into tb_Stc(scID,uID,cID,courseName) values(?,?,?,?)";

        return new SqlHelper().sqlUpdate(sql2, paras2);
    }


    public boolean sigRemoveCourse(String courseID, String uID) {
        String[] paras2 = new String[2];
        String sql2 = "delete from tb_Stc where uID = ? and cID = ?";
        paras2[0] = uID;
        paras2[1] = courseID;
        return new SqlHelper().sqlUpdate(sql2, paras2);
    }

    public boolean genAddCourse(Course course) {
        // TODO Auto-generated method stub
        String sql = "insert into tb_Class(Semester,cID,CourseMajor,courseName,teacherID,CourseState,CourseType) values (?,?,?,?,?,?,?)";
        String[] paras = new String[7];
        paras[0] = course.getSemester();
        paras[1] = course.getCourseID();
        paras[2] = course.getCourseMajor();
        paras[3] = course.getCourseName();
        paras[4] = course.getTeacherID();
        paras[5] = course.getCourseState();
        paras[6] = course.getCourseType();
        return new SqlHelper().sqlUpdate(sql, paras);
    }

    public boolean genRemoveCourse(String courseName) {
        // TODO Auto-generated method stub
        String sql = "delete from tb_Class where courseName = ?";
        String[] paras = new String[1];
        paras[0] = courseName;
        System.out.println("课程名是：" + paras[0]);
        //学生选课同步删除
        String sql1 = "delete from tb_Stc where courseName = ?";
        new SqlHelper().sqlUpdate(sql1, paras);

        return new SqlHelper().sqlUpdate(sql, paras);
    }
}
