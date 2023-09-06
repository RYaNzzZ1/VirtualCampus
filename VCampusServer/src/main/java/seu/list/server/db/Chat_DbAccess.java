package seu.list.server.db;

import java.sql.*;

public class Chat_DbAccess {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public Connection getConnection() {
        try {
            // 装载驱动
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            String dbpath = "VCampusServer\\src\\main\\resources\\vCampus.accdb";
            // 建立连接
            String url = "jdbc:ucanaccess://" + dbpath;
            conn = DriverManager.getConnection(url, "", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection(Connection con, ResultSet rs, Statement s) {
        try {
            rs.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
