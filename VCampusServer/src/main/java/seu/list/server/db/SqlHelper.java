package seu.list.server.db;

import seu.list.common.Chat;
import seu.list.common.Course;
import seu.list.common.Student;
import seu.list.common.User;
import seu.list.server.dao.DAOUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


public class SqlHelper {
    private static final String ACCESS_DRIVER = "net.ucanaccess.jdbc.UcanaccessDriver";
    private static String url;

    static {
        String dbpath = "VCampusServer\\src\\main\\resources\\vCampus.accdb";
        url = "jdbc:ucanaccess://" + dbpath;
    }

    String user = "";
    String passwd = "";

    public boolean sqlUpdate(String sql, String[] paras) {
        PreparedStatement ps = null;
        Connection ct = null;
        boolean b = true;
        try {

            Class.forName(ACCESS_DRIVER);

            ct = DriverManager.getConnection(url, user, passwd);
            ps = ct.prepareStatement(sql);

            for (int i = 0; i < paras.length; i++) {
                ps.setString(i + 1, paras[i]);
            }


            ps.executeUpdate();

        } catch (Exception e) {
            b = false;
            e.printStackTrace();
        } finally {
            //??????
            try {
                if (ps != null)
                    ps.close();
                if (ct != null)
                    ct.close();

            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return b;
    }


    public List<User> sqlUserQuery(String sql, String[] paras) {
        PreparedStatement ps = null;
        Connection ct = null;
        ResultSet rs = null;
        List<User> users = null;

        try {
            //1.????????
            Class.forName(ACCESS_DRIVER);
            //2.???????
            ct = DriverManager.getConnection(url, user, passwd);

            ps = ct.prepareStatement(sql);
            //??ps???????
            for (int i = 0; i < paras.length; i++) {
                ps.setString(i + 1, paras[i]);
            }
            //??閿熸枻鎷�???
            rs = ps.executeQuery();
            users = DAOUtil.UserResultSet2List(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //??????
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (ct != null)
                    ct.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return users;
    }


    public List<Course> sqlCourseQuery(String sql, String[] paras) {
        // TODO Auto-generated method stub
        PreparedStatement ps = null;
        Connection ct = null;
        ResultSet rs = null;
        List<Course> courses = null;
        try {
            Class.forName(ACCESS_DRIVER);
            ct = DriverManager.getConnection(url, user, passwd);
            ps = ct.prepareStatement(sql);
            for (int i = 0; i < paras.length; i++) {
                ps.setString(i + 1, paras[i]);
            }
            rs = ps.executeQuery();
            courses = DAOUtil.CourseResultSet2List(rs);
            System.out.println(courses);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (ct != null)
                    ct.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return courses;
    }
    public List<Chat> sqlChatQuery(String sql, String[] paras) {
        // TODO Auto-generated method stub
        PreparedStatement ps = null;
        Connection ct = null;
        ResultSet rs = null;
        List<Chat> chats = null;
        try {
            Class.forName(ACCESS_DRIVER);
            ct = DriverManager.getConnection(url, user, passwd);
            ps = ct.prepareStatement(sql);
            for (int i = 0; i < paras.length; i++) {
                ps.setString(i + 1, paras[i]);
            }
            rs = ps.executeQuery();
            chats = DAOUtil.ChatResultSet2List(rs);
            System.out.println(chats);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (ct != null)
                    ct.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return chats;
    }

    public List<String> sqlRelationQuery(String sql, String[] paras) {

        PreparedStatement ps = null;
        Connection ct = null;
        ResultSet rs = null;
        List<String> objs = null;
        try {
            Class.forName(ACCESS_DRIVER);
            ct = DriverManager.getConnection(url, user, passwd);
            ps = ct.prepareStatement(sql);
            for (int i = 0; i < paras.length; i++) {
                ps.setString(i + 1, paras[i]);
            }
            rs = ps.executeQuery();
            objs = DAOUtil.relationResulList(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (ct != null)
                    ct.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return objs;
    }

    public List<Student> sqlStudentCreditQuery(String sql, String[] paras) {
        PreparedStatement ps = null;
        Connection ct = null;
        ResultSet rs = null;
        List<Student> students = null;

        try {
            //1.????????
            Class.forName(ACCESS_DRIVER);
            //2.???????
            ct = DriverManager.getConnection(url, user, passwd);

            ps = ct.prepareStatement(sql);
            //??ps???????
            for (int i = 0; i < paras.length; i++) {
                ps.setString(i + 1, paras[i]);
            }
            //??閿熸枻鎷�???
            rs = ps.executeQuery();
            students = DAOUtil.StudentCreditResultSetList(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //??????
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (ct != null)
                    ct.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return students;
    }
}
