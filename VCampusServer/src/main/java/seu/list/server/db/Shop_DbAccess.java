package seu.list.server.db;

import java.sql.*;

/**
 * 类{@code Shop_DbAccess}是商店和数据库进行连接的基类，用的是ucanAccess jdbc驱动
 */
public class Shop_DbAccess {
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

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

	
	
	    

