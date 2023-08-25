package seu.list.server.db;

import java.sql.*;

/**
 * 连接数据库
 *
 * @author 王映方
 * @version jdk1.8.0
 */

public class Library_DbAccess {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    /**
     * 与数据库连接
     *
     * @return 连接
     */
    public Connection getConnection() {
        try {
            // 装载驱动
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String dbpath = "VCampusServer\\src\\main\\resources\\vCampus.accdb";
            // 建立连接
            String url = "jdbc:ucanaccess://" + dbpath;
            conn = DriverManager.getConnection(url, "", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭与数据库连接
     *
     * @param con 数据库的连接
     * @param rs  连接结果
     * @param s   连接状态
     */
    public void closeConnection(Connection con, ResultSet rs, Statement s) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (con != null) {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }
}