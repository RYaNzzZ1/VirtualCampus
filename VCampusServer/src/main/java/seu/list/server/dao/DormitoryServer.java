package seu.list.server.dao;

import seu.list.common.Dormitory;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.server.db.Dormitory_DbAccess;

import java.sql.*;
import java.util.ArrayList;

public class DormitoryServer extends Dormitory_DbAccess {
    Connection con = null;
    Statement s = null;
    ResultSet rs = null;
    ArrayList<Dormitory> dormitories = new ArrayList<Dormitory>();
    private Message mesFromClient; // 从客户端收到的数据
    private Message mesToClient;

    public DormitoryServer(Message mesFromClient) {
        super();
        this.mesFromClient = mesFromClient;
    }

    @SuppressWarnings("unchecked")
    public void execute() {
        // 根据类型去执行不同的DAO层操作，不同模块的DAO类需要修改这个函数
        // 如果操作需要的参数，请在mesFromClient内取出
        // 如果操作需要返回数据给客户端，请存入dataToClient，如果没有返回值，则默认为null
        con = getConnection();
        mesToClient = new Message();
        switch (this.mesFromClient.getMessageType()) {
            case MessageType.DormMaintain:
                try {
                    ArrayList<String> data = (ArrayList<String>) this.mesFromClient.getData();
                    Dormitory dormitory = this.Maintain(data);
                    this.mesToClient.setData(dormitory);
                    //this.mesToClient.setData(this.Maintain((ArrayList<String>)this.mesFromClient.getData()));
                } catch (SQLException e3) {
                    // TODO Auto-generated catch block
                    e3.printStackTrace();
                }
                break;
            case MessageType.DormExcange:
                try {
                    this.mesToClient.setData(this.Exchange((ArrayList<String>) this.mesFromClient.getData()));
                } catch (SQLException e3) {
                    // TODO Auto-generated catch block
                    e3.printStackTrace();
                }
                break;
            case MessageType.DormStShow:
                try {
                    this.mesToClient.setData(this.Show(this.mesFromClient.getData().toString()));
                } catch (SQLException e3) {
                    // TODO Auto-generated catch block
                    e3.printStackTrace();
                }
                break;
            case MessageType.DormApplyShow:
                this.mesToClient.setData(this.getDormitoriesByApply());
                break;
            case MessageType.DormAdd:
                try {
                    this.mesToClient.setData(this.Add((Dormitory) this.mesFromClient.getData()));
                } catch (Exception e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
                break;
            case MessageType.DormDelete:
                try {
                    this.mesToClient.setData(this.Delete(this.mesFromClient.getData().toString()));
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                break;
            case MessageType.DormModify:
                try {
                    this.mesToClient.setData(this.Modify((ArrayList<String>) this.mesFromClient.getData()));
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                break;
            case MessageType.DormAdShow:
                this.mesToClient.setData(this.AllDormitory());
                break;
            case MessageType.DormSearch:
                try {
                    String s1 = this.mesFromClient.getData().toString();
                    ArrayList<Dormitory> dormitory = this.SearchuserID(s1);
                    this.mesToClient.setData(dormitory);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case MessageType.DormCommitApply:
                try {
                    for (String datum : (ArrayList<String>) this.mesFromClient.getData()) {
                        System.out.println(datum);
                    }
                    this.mesToClient.setData(this.applySuccess((ArrayList<String>) this.mesFromClient.getData()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case MessageType.DormDivestApply:
                try {
                    this.mesToClient.setData(this.applyFail((ArrayList<String>) this.mesFromClient.getData()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case MessageType.DormUpdate:
                try {
                    this.mesToClient.setData(this.Update(this.mesFromClient.getContent().get(0), this.mesFromClient.getContent().get(1)));
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private boolean applyFail(ArrayList<String> para) throws SQLException {
        con = this.getConnection();
        s = con.createStatement();
        System.out.println("dormserver:para:" + para);
        for (int i = 0; i < para.size(); i += 2) {
            String uID = para.get(i + 1);
            String applyType = para.get(i);
            System.out.println("applyType:" + applyType);
            if (applyType.equals("MaintainApply")) {
                s.executeUpdate("update tb_Dormitory set MaintainApply='No'" + "where userID='" + uID + "'");
                ResultSet rsDormMaintain = s.executeQuery("select * from tb_Dormitory where userID='" + uID + "'");
                rsDormMaintain.next();
                String maintain = rsDormMaintain.getString(7).split("-")[0];
                System.out.println("dormMaintain=" + maintain);
                rsDormMaintain.close();
                s.executeUpdate("update tb_Dormitory set DormitoryMaintain='" + maintain + "-否决" + "'where userID='" + uID + "'");
            } else if (applyType.equals("ExchangeApply")) {
                s.executeUpdate("update tb_Dormitory set ExchangeApply='No'" + "where userID='" + uID + "'");
                ResultSet rsDormExchange = s.executeQuery("select * from tb_Dormitory where userID='" + uID + "'");
                rsDormExchange.next();
                String dormitoryExchange = rsDormExchange.getString(8).split("-")[0];
                System.out.println("dormExchange=" + dormitoryExchange);
                rsDormExchange.close();
                s.executeUpdate("update tb_Dormitory set DormitoryID='" + dormitoryExchange + "'where userID='" + uID + "'");
                s.executeUpdate("update tb_Dormitory set StudentExchange='" + dormitoryExchange + "-否决" + "'where userID='" + uID + "'");
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean applySuccess(ArrayList<String> para) throws SQLException {
        con = this.getConnection();
        s = con.createStatement();
        for (int i = 0; i < para.size(); i += 2) {
            String uID = para.get(i + 1);
            String applyType = para.get(i);
            if (applyType.equals("MaintainApply")) {
                s.executeUpdate("update tb_Dormitory set MaintainApply='No'" + "where userID='" + uID + "'");
                ResultSet rsDormMaintain = s.executeQuery("select * from tb_Dormitory where userID='" + uID + "'");
                rsDormMaintain.next();
                String maintain = rsDormMaintain.getString(7).split("-")[0];
                System.out.println("dormMaintain=" + maintain);
                rsDormMaintain.close();
                s.executeUpdate("update tb_Dormitory set DormitoryMaintain='" + maintain + "-成功" + "'where userID='" + uID + "'");
            } else if (applyType.equals("ExchangeApply")) {
                s.executeUpdate("update tb_Dormitory set ExchangeApply='No'" + "where userID='" + uID + "'");
                ResultSet rsDormExchange = s.executeQuery("select * from tb_Dormitory where userID='" + uID + "'");
                rsDormExchange.next();
                String dormitoryExchange = rsDormExchange.getString(8).split("-")[0];
                System.out.println("dormExchange=" + dormitoryExchange);
                rsDormExchange.close();
                s.executeUpdate("update tb_Dormitory set DormitoryID='" + dormitoryExchange + "'where userID='" + uID + "'");
                s.executeUpdate("update tb_Dormitory set StudentExchange='" + dormitoryExchange + "-成功" + "'where userID='" + uID + "'");
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 宿舍维修
     *
     * @param para
     * @return
     * @throws SQLException
     */
    private Dormitory Maintain(ArrayList<String> para) throws SQLException {
        // TODO Auto-generated method stub
        System.out.println(para);
        String userID = para.get(0);
        String dormID = para.get(1);
        String maintain = para.get(2);
        Dormitory temp = new Dormitory();
        dormitories = AllDormitory();
        con = this.getConnection();
        s = con.createStatement();
        for (int i = 0; i < dormitories.size(); i++)
            if (dormitories.get(i).getuserID().equals(userID)) {
                //dormitories.get(i).setDormitoryMaintain(maintain);
                temp = dormitories.get(i);
                try {
                    int result = s.executeUpdate("update tb_Dormitory set DormitoryMaintain='" + maintain + "-申请中" + "'where userID='" + userID + "'");
                    s.executeUpdate("update tb_Dormitory set MaintainApply='Yes' where userID='" + userID + "'");
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    s.close();
                    con.close();
                }
            }
        return temp;
    }

    /**
     * 宿舍调换
     *
     * @param para
     * @return
     * @throws SQLException
     */
    private Dormitory Exchange(ArrayList<String> para) throws SQLException {
        System.out.println(para);
        String userID = para.get(0);
        String dormID = para.get(1);
        String exchange = para.get(2);
        Dormitory temp = new Dormitory();
        dormitories = AllDormitory();
        con = this.getConnection();
        s = con.createStatement();
        for (int i = 0; i < dormitories.size(); i++)
            if (dormitories.get(i).getuserID().equals(userID)) {
                //dormitories.get(i).setStudentExchange(exchange);
                temp = dormitories.get(i);
                try {
                    int result = s.executeUpdate("update tb_Dormitory set StudentExchange='" + exchange + "-申请中" + "'where userID='" + userID + "'");
                    s.executeUpdate("update tb_Dormitory set ExchangeApply= 'Yes' where userID='" + userID + "'");
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    s.close();
                    con.close();
                }
            }
        return temp;
    }

    /**
     * 显示学生信息
     *
     * @param userID
     * @return
     * @throws SQLException
     */
    private Dormitory Show(String userID) throws SQLException {
        // TODO Auto-generated method stub
        dormitories = AllDormitory();
        Dormitory temp = new Dormitory();
        con = this.getConnection();
        s = con.createStatement();
        for (int i = 0; i < dormitories.size(); i++) {
            if (dormitories.get(i).getuserID().equals(userID)) {
                temp = dormitories.get(i);
                break;
            }
        }
        System.out.println("!!!!!!");
        System.out.println(temp);
        return temp;
    }

    /**
     * 修改学生宿舍
     *
     * @param para
     * @return
     * @throws SQLException
     */
    private ArrayList<Dormitory> Modify(ArrayList<String> para) throws SQLException {
        System.out.println(para);
        String userID = para.get(0);
        String usertype = para.get(1);
        String temp = para.get(2);
        dormitories = AllDormitory();
        con = this.getConnection();
        s = con.createStatement();
        for (int i = 0; i < dormitories.size(); i++)
            if (dormitories.get(i).getuserID().equals(userID)) {
                if ("宿舍".equals(usertype)) {
                    dormitories.get(i).setStudentExchange(temp);
                    try {
                        int result = s.executeUpdate("update tb_Dormitory set DormitoryID='" + temp + "'where userID='" + userID + "'");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if ("卫生评分".equals(usertype)) {
                    dormitories.get(i).setDormitoryScore(Integer.parseInt(temp));
                    try {
                        int result = s.executeUpdate("update tb_Dormitory set DormitoryScore='" + temp + "'where userID='" + userID + "'");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if ("水费".equals(usertype)) {
                    dormitories.get(i).setWater(Integer.parseInt(temp));
                    try {
                        int result = s.executeUpdate("update tb_Dormitory set Water='" + temp + "'where userID='" + userID + "'");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if ("电费".equals(usertype)) {
                    dormitories.get(i).setElectricity(Integer.parseInt(temp));
                    try {
                        int result = s.executeUpdate("update tb_Dormitory set Electricity='" + temp + "'where userID='" + userID + "'");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if ("宿舍维修".equals(usertype)) {
                    dormitories.get(i).setDormitoryMaintain(temp);
                    try {
                        int result = s.executeUpdate("update tb_Dormitory set DormitoryMaintain='" + temp + "'where userID='" + userID + "'");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if ("宿舍调换".equals(usertype)) {
                    dormitories.get(i).setStudentExchange(temp);
                    try {
                        int result = s.executeUpdate("update tb_Dormitory set StudentExchange='" + temp + "'where userID='" + userID + "'");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        return dormitories;
    }

    /**
     * 添加学生宿舍
     *
     * @param data
     * @return
     * @throws SQLException
     */
    private ArrayList<Dormitory> Add(Dormitory data) throws SQLException {
        ArrayList<Dormitory> dorm = AllDormitory();
        dorm.add(data);
        con = this.getConnection();
        PreparedStatement ps1 = con.prepareStatement("insert into tb_Dormitory(userID, DormitoryID, StudentBunkID, Water, Electricity, DormitoryScore, DormitoryMaintain, StudentExchange) values('" + data.getuserID() + "','" + data.getDormitoryID() + "','" + data.getStudentBunkID() + "','" + data.getWater() + "','" + data.getElectricity() + "','" + data.getDormitoryScore() + "','" + data.getDormitoryMaintain() + "','" + data.getStudentExchange() + "')");
        //PreparedStatement ps2 = con.prepareStatement("insert into tb_Dormitory_Maintain(userID, DormitoryID, StudentBunkID, Water, Electricity, DormitoryScore, DormitoryMaintain, StudentExchange) values('" + data.getuserID() + "','" + data.getDormitoryID() + "','" + data.getStudentBunkID() + "','" + data.getWater() + "','" + data.getElectricity() + "','" + data.getDormitoryScore() + "','" + data.getDormitoryMaintain() + "','" + data.getStudentExchange() + "')");
        //PreparedStatement ps3 = con.prepareStatement("insert into tb_Dormitory_Exchange(userID, DormitoryID, StudentBunkID, Water, Electricity, DormitoryScore, DormitoryMaintain, StudentExchange) values('" + data.getuserID() + "','" + data.getDormitoryID() + "','" + data.getStudentBunkID() + "','" + data.getWater() + "','" + data.getElectricity() + "','" + data.getDormitoryScore() + "','" + data.getDormitoryMaintain() + "','" + data.getStudentExchange() + "')");
        int result1 = ps1.executeUpdate();
        //int result2 = ps2.executeUpdate();
        //int result3 = ps3.executeUpdate();
        System.out.println("add success");
        ps1.close();
        //ps2.close();
        //ps3.close();
        con.close();
        return dorm;
    }

    /**
     * 更新学生ID
     *
     * @param oldid
     * @param newid
     * @return
     * @throws SQLException
     */
    private int Update(String oldid, String newid) throws SQLException {
        Statement statement = con.createStatement();
        ArrayList<Dormitory> dorm = AllDormitory();
        for (int i = 0; i < dorm.size(); i++) {
            if (dorm.get(i).getuserID().equals(oldid)) {
                dorm.get(i).setuserID(newid);
                break;
            }
        }
        con = this.getConnection();
        statement = con.createStatement();
        int res = -1;
        res = statement.executeUpdate("update tb_Dormitory set userID='" + newid + "' where userID='" + oldid + "'");
        //res = statement.executeUpdate("update tb_Dormitory_Maintain set userID='" + newid + "' where userID='" + oldid + "'");
        //res = statement.executeUpdate("update tb_Dormitory_Exchange set userID='" + newid + "' where userID='" + oldid + "'");
        statement.close();
        con.close();
        System.out.println("update dorm success");
        return res;
    }

    /**
     * 删除学生宿舍
     *
     * @param string
     * @return
     * @throws SQLException
     */
    private ArrayList<Dormitory> Delete(String string) throws SQLException {
        // TODO Auto-generated method stub
        ArrayList<Dormitory> dorm = AllDormitory();
        for (int i = 0; i < dorm.size(); i++)
            if (dorm.get(i).getuserID().equals(string)) dorm.remove(i);
        con = this.getConnection();
        s = con.createStatement();
        try {
            int result1 = s.executeUpdate("delete from tb_Dormitory where userID='" + string + "'");
            //int result2 = s.executeUpdate("delete from tb_Dormitory_Maintain where userID='" + string + "'");
            //int result3 = s.executeUpdate("delete from tb_Dormitory_Exchange where userID='" + string + "'");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            s.close();
            con.close();
        }

        return dorm;
    }

    /**
     * 获取宿舍列表
     *
     * @return
     */
    private ArrayList<Dormitory> AllDormitory() //从数据库中获取所有数据
    {
        // TODO Auto-generated method stub
        ArrayList<Dormitory> dorm = new ArrayList<>();
        try {
            con = getConnection();
            s = con.createStatement();// 创建SQL语句对象
            rs = s.executeQuery("select * from tb_Dormitory"); // 查询商品信息
            // 把数据库中的数据读入

            while (rs.next()) {
                Dormitory temp = new Dormitory();
                temp.setuserID(rs.getString("userID"));
                temp.setDormitoryID(rs.getString("DormitoryID"));
                temp.setStudentBunkID(rs.getInt("StudentBunkID"));
                temp.setWater(rs.getInt("Water"));
                temp.setElectricity(rs.getInt("Electricity"));
                temp.setDormitoryScore(rs.getInt("DormitoryScore"));
                temp.setDormitoryMaintain(rs.getString("DormitoryMaintain"));
                temp.setStudentExchange(rs.getString("StudentExchange"));
                System.out.println(temp);
                dorm.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
        System.out.println("get all information");
        return dorm;
    }

    private ArrayList<Dormitory> getDormitoriesByApply() //从数据库中获取所有数据
    {
        // TODO Auto-generated method stub
        ArrayList<Dormitory> dorm = new ArrayList<>();
        try {
            con = getConnection();
            s = con.createStatement();// 创建SQL语句对象
            rs = s.executeQuery("select * from tb_Dormitory where MaintainApply=" + "'Yes'" + " or " + "ExchangeApply=" + "'Yes'"); // 查询商品信息
            // 把数据库中的数据读入

            while (rs.next()) {
                Dormitory temp = new Dormitory();
                temp.setuserID(rs.getString("userID"));
                temp.setDormitoryID(rs.getString("DormitoryID"));
                temp.setStudentBunkID(rs.getInt("StudentBunkID"));
                temp.setWater(rs.getInt("Water"));
                temp.setElectricity(rs.getInt("Electricity"));
                temp.setDormitoryScore(rs.getInt("DormitoryScore"));
                temp.setDormitoryMaintain(rs.getString("DormitoryMaintain"));
                temp.setStudentExchange(rs.getString("StudentExchange"));
                System.out.println(temp);
                dorm.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
        System.out.println("get Apply information");
        return dorm;
    }

    /**
     * 查找学生宿舍
     *
     * @param userID
     * @return
     */
    private ArrayList<Dormitory> SearchuserID(String userID) throws SQLException {
        ArrayList<Dormitory> dorm = new ArrayList<Dormitory>();
        if (userID == null) {
            dorm = AllDormitory();
            return dorm;
        }
        con = getConnection();
        s = con.createStatement();
        rs = s.executeQuery("select * from tb_Dormitory where userID like'%" + userID + "%'");
        while (rs.next()) {
            Dormitory temp = new Dormitory();
            temp.setuserID(rs.getString("userID"));
            temp.setDormitoryID(rs.getString("DormitoryID"));
            temp.setStudentBunkID(rs.getInt("StudentBunkID"));
            temp.setWater(rs.getInt("Water"));
            temp.setElectricity(rs.getInt("Electricity"));
            temp.setDormitoryScore(rs.getInt("DormitoryScore"));
            temp.setDormitoryMaintain(rs.getString("DormitoryMaintain"));
            temp.setStudentExchange(rs.getString("StudentExchange"));
            System.out.println(temp);
            dorm.add(temp);
        }
        return dorm;
    }


    /**
     * @return 发送给客户端的消息
     */
    public Message getMesToClient() { // 无需修改，网络层需要调用这个函数
        return this.mesToClient;
    }
};
