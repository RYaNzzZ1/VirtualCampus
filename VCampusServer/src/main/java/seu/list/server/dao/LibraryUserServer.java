package seu.list.server.dao;

import seu.list.common.Book;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.server.db.Library_DbAccess;
import seu.list.server.db.SqlHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @version jdk1.8.0
 */

public class LibraryUserServer extends Library_DbAccess {
    Connection con = null;
    Statement s = null;
    ResultSet rs = null, rsl = null;

    private Message mesFromClient; //从客户端发来的数据
    private Message mesToClient;  //送回给客户端的信息

    /**
     * 构造函数
     *
     * @param mesfromclient 从客户端发来的数据
     */
    public LibraryUserServer(Message mesfromclient) {
        this.mesFromClient = new Message();
        this.mesFromClient = mesfromclient;
    }

    /**
     * 进行不同种类的操作
     */
    public void execute() {
        mesToClient = new Message();
        switch (this.mesFromClient.getMessageType()) {
            case MessageType.LibraryBookGetAll:
                this.mesToClient.setData(this.createList());
                break;
            case MessageType.LibraryBookGetLend:
                this.mesToClient.setData(this.createBorrowList(this.mesFromClient.getData().toString()));
                break;
            case MessageType.LibraryBookAdd:
                this.mesToClient.setData(this.AddBook((String[]) this.mesFromClient.getData()));
                break;
            case MessageType.LibraryBookDelete:
                this.mesToClient.setData(this.DeleteBook(this.mesFromClient.getData().toString()));
                break;
            case MessageType.LibraryBookFind:
                this.mesToClient.setData(this.FindBook(this.mesFromClient.getData().toString()));
                break;
            case MessageType.LibraryBookLend:
                this.mesToClient.setData(this.LendBook((String[]) this.mesFromClient.getData()));
                break;
            case MessageType.LibraryBookReturn:
                this.mesToClient.setData(this.ReturnBook((String[]) this.mesFromClient.getData()));
                break;
            case MessageType.LibraryBookUpdate:
                this.mesToClient.setData(this.ModifyBook((ArrayList<String>) this.mesFromClient.getData()));
                break;
            default:
                break;
        }
    }

    /**
     * @return 送回给客户端的信息
     */
    public Message getMesToClient() {
        return this.mesToClient;
    }

    /**
     * 从数据库读取书籍信息
     *
     * @return 书的列表
     */
    public ArrayList<Book> createList() {
        ArrayList<Book> bookList = new ArrayList<Book>();
        try {
            con = getConnection();
            s = con.createStatement();// 创建SQL语句对象
            rs = s.executeQuery("select * from tb_BookList");    // 查询书籍信息

            //把数据库中的数据读入bookList
            while (rs.next()) {
                Book tempBook = new Book();
                tempBook.setName(rs.getString("Name"));
                tempBook.setId(rs.getString("ID"));
                tempBook.setAuthor(rs.getString("Author"));
                tempBook.setPress(rs.getString("Press"));
                tempBook.setStock(rs.getInt("Stock"));
                tempBook.setState((rs.getInt("State") == 0) ? false : true);

                bookList.add(tempBook);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
        System.out.println("create completion\t");
        return bookList;
    }

    /**
     * 从数据库读取书籍借阅记录
     *
     * @return 未归还书的列表
     */
    public ArrayList<Book> createBorrowList(String uid) {
        ArrayList<Book> borrowbookList = new ArrayList<Book>();
        try {
            con = getConnection();
            s = con.createStatement();// 创建SQL语句对象
            rs = s.executeQuery("select * from tb_BookBorringRecord where scID='" + uid + "'");    // 查询学生借阅信息

            //把数据库中的数据读入借阅列表
            while (rs.next()) {
                Book tempBook = new Book();
                tempBook.setName(rs.getString("Name"));
                tempBook.setId(rs.getString("ID"));
                tempBook.setAuthor(rs.getString("Author"));
                tempBook.setPress(rs.getString("Press"));
                tempBook.setStock(rs.getInt("Num"));//保存记录编号
                tempBook.setState((rs.getInt("State") == 0) ? false : true);
                tempBook.setScid(uid);
                if (tempBook.getState() == false)
                    borrowbookList.add(tempBook);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
        System.out.println("create records completion\t");
        return borrowbookList;
    }

    /**
     * 通过书名或书号查找书籍
     *
     * @param fbookid 书名或书号
     * @return 搜索到的书名或书号符合条件的书籍列表
     */
    public ArrayList<Book> FindBook(String fbookid) {
        ArrayList<Book> bookList = new ArrayList<Book>();
        bookList = createList();
        ArrayList<Book> resbook = new ArrayList<Book>();
        try {
            for (int i = 0; i < bookList.size(); i++) {
                Book tbook = bookList.get(i);
                if (tbook.getName().contains(fbookid) || tbook.getId().contains(fbookid))
                    resbook.add(tbook);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
        return resbook;
    }

    /**
     * 学生操作：借书
     *
     * @param data 书号,学生ID
     * @return 操作结果<br>
     * return 0:书号不存在<br>
     * return {@code -}1:库存为0不可借<br>
     * return 正整数：操作正常
     */
    public int LendBook(String[] data) { //用书号查找（唯一）
        //return 0:书号不存在  return -1:库存为0不可借
        String bookid = data[0];
        String uid = data[1];
        int res = 0;
        try {
            con = getConnection();
            s = con.createStatement();// 创建SQL语句对象

            int result = 0;
            ResultSet rsr = s.executeQuery("select * from tb_BookList where ID='" + bookid + "'");
            int stock = 0;
            int state = 0;
            if (rsr.next()) {
                stock = rsr.getInt("Stock");
                state = rsr.getInt("State");
            } else
                return 0;

            if ((stock - 1) >= 0) {
                stock = stock - 1;
                if (stock == 0)
                    state = 0;
                else
                    state = 1;
                result = s.executeUpdate("update tb_BookList set Stock='" + stock + "' where ID='" + bookid + "'");
                result = s.executeUpdate("update tb_BookList set State='" + state + "' where ID='" + bookid + "'");
                //添加借阅记录
                String sql = "insert into tb_BookBorringRecord(Name,ID,Author,Press,scID,State) values(?,?,?,?,?,?)";
                String[] paras = new String[6];
                paras[0] = rsr.getString("Name");
                paras[1] = rsr.getString("ID");
                paras[2] = rsr.getString("Author");
                paras[3] = rsr.getString("Press");
                paras[4] = uid;
                paras[5] = "0";//借阅
                new SqlHelper().sqlUpdate(sql, paras);
            } else {
                return -1;
            }
            if (result > 0) {
                res = result;
                System.out.println("Lend completion\t");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
        return res;
    }

    /**
     * 学生操作：还书
     *
     * @param data 书号,学生ID
     * @return 操作结果<br>
     * return 0:书号不存在<br>
     * return 正整数：操作正常
     */
    public int ReturnBook(String[] data) {
        //return 0:书号不存在
        String bookid = data[0];
        String uid = data[1];
        int res = 0;
        try {
            con = getConnection();
            s = con.createStatement();// 创建SQL语句对象

            int result = 0;
            ResultSet rsr = s.executeQuery("select * from tb_BookList where ID='" + bookid + "'");
            //Integer Max=0;
            int Stock = 0;
            int state = 0;
            if (rsr.next()) {
                //Max=rsr.getInt("Max");
                Stock = rsr.getInt("Stock");
                state = rsr.getInt("State");
            } else
                return 0;
            //更新借阅记录表
            boolean borrow = false;
            ArrayList<Book> borrowbooklist;
            borrowbooklist = createBorrowList(uid);
            for (int i = 0; i < borrowbooklist.size(); i++) {
                Book tbook = borrowbooklist.get(i);
                if (Objects.equals(tbook.getId(), bookid) && tbook.getState() == false) {
                    int Num = tbook.getStock();//获取第一条记录的编号
                    //s.executeUpdate("update tb_BookBorringRecord set State = 1 where ID='"+bookid+"'and scID='"+uid+"'");
                    con = getConnection();
                    s = con.createStatement();// 创建SQL语句对象
                    s.executeUpdate("update tb_BookBorringRecord set State = 1 where Num='" + Num + "'");
                    borrow = true;
                    break;
                }
            }
            if (borrow) {
                Stock = Stock + 1;
                /*if(Stock>Max){
                res=-1;
                System.out.println("Invaild operation");
                }else{*/
                con = getConnection();
                s = con.createStatement();// 创建SQL语句对象
                result = s.executeUpdate("update tb_BookList set Stock='" + Stock + "' where ID='" + bookid + "' ");
                if (state == 0) {
                    result = s.executeUpdate("update tb_BookList set State=1 where ID='" + bookid + "'");
                }
                if (result > 0) {
                    res = result;
                    System.out.println("Return completion\t");
                }
                //}
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
        return res;
    }

    /**
     * 管理员操作：增加书籍
     *
     * @param arr String[]类型的书籍信息：书号，所要修改的书籍属性（书名/书号/作者/出版社/库存），修改后内容
     * @return 操作结果<br>
     * return 0:书号已存在<br>
     * return 正整数：操作正常
     */
    public int AddBook(String[] arr) {
        //return 0:书号已存在
        /*int bstate = 0;
        if (Integer.valueOf(arr[4]) == 0)
            bstate = 0;
        else
            bstate = 1;*/

        int res = 0;

        try {
            int result;
            con = getConnection();
            s = con.createStatement();// 创建SQL语句对象

            ResultSet rsr = s.executeQuery("select * from tb_BookList where ID='" + arr[1] + "'");
            if (rsr.next())
                return 0;

            result = s.executeUpdate("insert into tb_BookList values('" + arr[0] + "','" + arr[1] + "','" + arr[2] +
                    "','" + arr[3] + "','" + arr[4] + "','" + 1 + "','" + arr[4] + "')");

            if (result > 0) {
                res = result;
                System.out.println("Add completion\t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
        return res;
    }

    /**
     * 管理员操作：删除书籍
     *
     * @param bookid 所要删除书籍的书号
     * @return 操作结果<br>
     * return 0:书号已存在<br>
     * return 正整数：操作正常
     */
    public int DeleteBook(String bookid) { //用书号查找（唯一）
        //return 0:书号不存在
        int res = 0;
        try {
            int result = 0;
            con = getConnection();
            s = con.createStatement();// 创建SQL语句对象

            ResultSet rsr = s.executeQuery("select * from tb_BookList where ID='" + bookid + "'");
            if (!rsr.next())
                return 0;

            result = s.executeUpdate("delete from tb_BookList where ID='" + bookid + "'");
            if (result > 0) {
                res = result;
                System.out.println("Delete completion\t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
        return res;
    }

    /**
     * 修改书籍信息
     *
     * @param arr {@code ArrayList<String>}类型的书籍信息：[书号，所要修改的书籍属性（书名/书号/作者/出版社/库存），修改后内容]
     * @return 操作结果<br>
     * return 0:书号不存在<br>
     * return 正整数：操作正常
     */
    public int ModifyBook(ArrayList<String> arr) { //用书号查找（唯一）
        //return 0:书号不存在

        String bookid = arr.get(0);
        String attr = arr.get(1);
        String modattr = arr.get(2);
        int result = 0;
        try {
            con = getConnection();
            s = con.createStatement();// 创建SQL语句对象

            //检查书号是否存在
            ResultSet res = s.executeQuery("select *  from tb_BookList where ID='" + bookid + "'");
            if (res.next()) {
                int oldstock = res.getInt("Stock");
                result = s.executeUpdate("update tb_BookList set " + attr + "='" + modattr + "' where ID='" + bookid + "'");
                if (attr.equals("Stock")) {
                    int oldmax = res.getInt("Max");
                    int newmax = oldmax + Integer.valueOf(modattr) - oldstock;
                    String newMax = String.valueOf(newmax);
                    int ms = Integer.parseInt(modattr);
                    //同时提高上限
                    result = s.executeUpdate("update tb_BookList set Max='" + newMax + "' where ID='" + bookid + "'");
                    if (ms == 0)
                        result = s.executeUpdate("update tb_BookList set State=0 where ID='" + bookid + "'");
                    else
                        result = s.executeUpdate("update tb_BookList set State=1 where ID='" + bookid + "'");
                }

                if (result > 0)
                    System.out.println("update completion\t");
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
        return result;
    }

}
