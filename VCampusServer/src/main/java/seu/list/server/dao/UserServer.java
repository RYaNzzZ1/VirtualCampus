package seu.list.server.dao;

import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.Student;
import seu.list.common.User;
import seu.list.server.db.SqlHelper;
import seu.list.server.driver.ServerClientThreadMgr;
import seu.list.server.driver.ServerSocketThread;

import java.util.List;
import java.util.Vector;

public class UserServer {
    private Message mesFromClient;
    private Message mesToClient = new Message();
    private String id;

    public UserServer(Message mesFromClient, String id) {
        this.mesFromClient = mesFromClient;
        this.id = id;
    }

    public Message getMesFromClient() {
        return mesFromClient;
    }

    public void setMesFromClient(Message mesFromClient) {
        this.mesFromClient = mesFromClient;
    }

    public Message getMesToClient() {
        return mesToClient;
    }

    public void setMesToClient(Message mesToClient) {
        this.mesToClient = mesToClient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void excute() {
        User u = new User();
        u.setContent(mesFromClient.getContent());
        switch (this.mesFromClient.getMessageType()) {
            case MessageType.REQ_LOGIN: {
                User user = this.getUserByPwd(this.mesFromClient.getContent());
                if (user == null) {
                    System.out.println("User" + u.getId() + "验证失败");
                    mesToClient.setMessageType("FAILED");
                    mesToClient.setUserType(2);
                } else {
                    System.out.println("User " + u.getId() + " 验证成功 ");
                    int usertype = Integer.valueOf(user.getRole());
                    mesToClient.setUserType(usertype);
                    mesToClient.setData(user);

                    String uid = user.getId();
                    String preid = ServerClientThreadMgr.getId(uid);
                    if (preid != null) {//重复登录，下线之前登录的线程
                        ServerSocketThread prethread = ServerClientThreadMgr.getPreThread(uid);
                        prethread.close();
                        mesToClient.setUserType(30 + usertype);
                        ServerClientThreadMgr.unbind(uid);
                    }
                    //绑定当前线程，用户状态变为在线
                    ServerClientThreadMgr.bind(user.getId(), this.id);
                    user.changeState(1);
                }
                break;
            }
            case MessageType.REQ_REGISTER: {
                User user = this.getUser(u);
                if (user == null) {
                    user = this.addUser(u);
                    mesToClient.setUserType(Integer.parseInt(u.getRole()));
                } else {
                    System.out.println("User" + u.getId() + "注册失败，已有此人");
                    mesToClient.setMessageType("FAILED");
                    mesToClient.setUserType(3);
                }
                break;
            }
            case MessageType.REQ_LOGOUT: {
                String userID = mesFromClient.getContent().get(0);
                User user = this.searchUser(userID);
                if (user == null) {
                    System.out.println("此人不存在");
                } else {
                    ServerClientThreadMgr.unbind(userID);
                    ServerClientThreadMgr.remove(this.id);
                    System.out.println("User " + u.getId() + " 登出成功 ");
                    user.setId(userID);
                    user.changeState(0);
                }
                break;
            }

            case MessageType.REQ_USERDEL: {
                String userID = this.mesFromClient.getContent().get(0);
                User user = this.searchUser(userID);
                if (user == null) {
                    System.out.println("不存在该用户");
                } else {
                    Boolean flag = this.delUser(userID);
                    if (flag) {
                        System.out.println("成功删除该用户");
                    } else {
                        System.out.println("无法执行删除操作");
                    }
                }
                break;
            }
            case MessageType.REQ_USERUPDATE: {
                Vector<String> tempdata = (Vector<String>) this.mesFromClient.getData();
                String userID = tempdata.get(0);
                String newID = tempdata.get(1);
                User user = this.searchUser(userID);
                if (user == null) {
                    System.out.println("不存在该用户");
                } else {
                    Boolean flag = this.updateUser(userID, newID);
                    if (flag) {
                        System.out.println("成功更新用户");
                    } else {
                        System.out.println("未完成更新操作");
                    }
                }
                break;
            }
            default:
                break;
        }
    }


    public User getUser(User user) {
        // TODO 自动生成的方法存根
        String sql = "select * from tb_User where uID= ? and uName=?";
        String[] paras = new String[2];
        paras[0] = user.getId();
        paras[1] = user.getName();
        List<User> users = new SqlHelper().sqlUserQuery(sql, paras);
        if (users != null && users.size() > 0) {
            return users.get(0);
        } else
            return null;
    }


    public boolean updateUser(String userID, String newID) {
        String sql = "select * from tb_User where uID= ?";
        String[] paras = new String[1];
        paras[0] = userID;
        List<User> users = new SqlHelper().sqlUserQuery(sql, paras);
        User u = users.get(0);
        boolean flag1 = this.delUser(userID);
        u.setId(newID);
        u = this.addUser(u);
        boolean flag2 = u != null;
        System.out.println("update success!");
        return flag1 && flag2;

    }


    public User addUser(User user) {
        // TODO 自动生成的方法存根
        List<User> allu = getAllUsers();
        int nextuid = allu.size() + 2301;//uid按顺序加1
        String sql2 = "update tb_Student set uID = ? where StudentID= ? ";
        String[] paras1 = new String[2];
        paras1[0] = String.valueOf(nextuid);
        paras1[1] = user.getId();
        new SqlHelper().sqlUpdate(sql2, paras1);

        String sql = "insert into tb_User(uID, uName, uAge, uSex,uGrade, uMajor,uPwd,uRole,uMoney,StudentID) values (?,?,?,?,?,?,?,?,?,?)";
        String[] paras = new String[10];
        System.out.println(user);
        //paras[0] = user.getId();
        paras[0] = String.valueOf(nextuid);
        paras[1] = user.getName();
        paras[2] = user.getAge();
        paras[3] = user.getSex();
        paras[4] = user.getGrade();
        paras[5] = user.getMajor();
        paras[6] = user.getPwd();
        paras[7] = user.getRole();
        paras[8] = user.getMoney();
        if (paras[7].equals("0"))//学生
            paras[9] = user.getId();
        else
            paras[9] = "";
        new SqlHelper().sqlUpdate(sql, paras);
        return searchUser(user.getId());
    }

    public boolean delUser(String userID) {
        if (this.searchUser(userID).getRole().equals("1")) {
            return false;
        } else {
            String sql = "delete from tb_User where uID = ?";
            return new SqlHelper().sqlUpdate(sql, new String[]{userID});
        }
    }


    public User searchUser(String id) {
        // TODO 自动生成的方法存根
        String sql = "select * from tb_User where uID=?";
        String[] paras = new String[1];
        paras[0] = id;
        List<User> users = new SqlHelper().sqlUserQuery(sql, paras);
        if (users != null && users.size() > 0) {
            return users.get(0);
        } else
            return null;
    }


    public List<User> getAllUsers() {
        // TODO 自动生成的方法存根
        String sql = "select * from tb_User";
        return new SqlHelper().sqlUserQuery(sql, new String[]{});
    }


    public User getUserByPwd(Vector<String> content) {
        String sql = "select * from tb_User where (uID= ? or StudentID=?) and uPwd=?";
        String[] paras = new String[3];
        paras[0] = content.get(0);
        paras[1] = content.get(0);
        paras[2] = content.get(6);
        List<User> users = new SqlHelper().sqlUserQuery(sql, paras);

        if (users.isEmpty())
            return null;
        if (users.get(0).getRole().equals("0")) {
            String sql1 = "select * from tb_Student where uID = ?";
            String[] paras1 = new String[1];
            paras1[0] = paras[0];
            List<Student> students = new SqlHelper().sqlStudentCreditQuery(sql1, paras1);
            users.get(0).setMoney(String.valueOf(students.get(0).getStudentcredit()));
        }
        if (users != null && !users.isEmpty()) {
            return users.get(0);
        } else
            return null;
    }
    //public String getStudentId(String uid){}
}
