package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.Book;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LibraryManage extends JFrame {

    JTable table;
    JScrollPane scrollPane2;
    private JTextField findText;
    private JButton deleteButton, addButton;

    /**
     * Create the frame.
     */
    public LibraryManage() {
        ArrayList<Book> booklist = new ArrayList<Book>();

        Message mes = new Message();
        Client client = new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Library);
        mes.setMessageType(MessageType.LibraryBookGetAll);
        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);
        booklist = (ArrayList<Book>) serverResponse.getData();

        setTitle("图书馆-管理员");
        setDefaultCloseOperation(2);

        //设置背景图片
        //把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(null); // 使用绝对定位
        // 创建带有背景图片的JLabel
        ImageIcon image = new ImageIcon("VCampusClient/image/LibraryManage.png");
        JLabel backlabel = new JLabel(image);
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 1280 / 2, d.height / 2 - 720 / 2, 1280, 720);
        backlabel.setSize(1280, 720);
        this.getLayeredPane().add(backlabel, new Integer(Integer.MIN_VALUE));
        backlabel.setOpaque(false); // 设置背景透明
        setResizable(false); //阻止用户拖拽改变窗口的大小
        setVisible(true);


        JScrollPane scrollPane = new JScrollPane();

        findText = new JTextField();
        findText.setFont(new Font("华文行楷", Font.PLAIN, 32));
        //findText.setForeground(SystemColor.textText);
        findText.setText("（书名/书号）");
        findText.setBounds(692, 151, 975 - 692, 196 - 151);
        add(findText);
        findText.setVisible(true);
        findText.setOpaque(false);
        findText.setBorder(new EmptyBorder(0, 0, 0, 0));


        //表格
        Object[][] tableDate = new Object[booklist.size()][6];

        for (int i = 0; i < booklist.size(); i++) {

            tableDate[i][0] = booklist.get(i).getName();
            tableDate[i][1] = booklist.get(i).getId();
            tableDate[i][2] = booklist.get(i).getAuthor();
            tableDate[i][3] = booklist.get(i).getPress();
            tableDate[i][4] = String.valueOf(booklist.get(i).getStock());
            if (booklist.get(i).getState() == true)
                tableDate[i][5] = "可借";
            else
                tableDate[i][5] = "不可借";


        }


        String[] tablename = {"书名", "书号", "作者", "出版社", "库存", "状态"};
        DefaultTableModel dataModel1 = new DefaultTableModel(tableDate, tablename);
        table = new JTable(dataModel1);


        scrollPane2 = new JScrollPane();
        scrollPane2.setOpaque(false);
        scrollPane2.getViewport().setOpaque(false);
        scrollPane2.setViewportView(table);
        table.setBounds(0, 0, 1143 - 243, 588 - 233 + 13);
        add(scrollPane2);
        scrollPane2.setBounds(243, 223, 900, 588 - 233 + 13);
        scrollPane2.setVisible(true);
        setVisible(true);

        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Serif", Font.BOLD, 28));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {
                "书名", "书号", "作者", "出版社", "库存", "状态"};
        for (int i = 0; i < 6; i++) {
            table.getColumn(Names[i]).setCellRenderer(renderer);//单格渲染
            TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
            column.setHeaderRenderer(renderer);//表头渲染
        }
        table.setOpaque(false);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
        scrollPane2.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
        scrollPane2.setOpaque(false);
        scrollPane2.getViewport().setOpaque(false);
        scrollPane2.setColumnHeaderView(table.getTableHeader());
        scrollPane2.getColumnHeader().setOpaque(false);

        add(scrollPane2);

        add(backlabel);


        JButton modifyButton = new JButton("修改");
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ModifyAvtshow(e);
            }
        });
        modifyButton.setForeground(new Color(0, 0, 128));
        modifyButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        //modifyButton.setBackground(Color.LIGHT_GRAY);
        modifyButton.setBounds(107, 416, 223 - 107, 272 - 220);
        add(modifyButton);
        modifyButton.setVisible(true);
        modifyButton.setOpaque(false);

        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ExitAvt(e);
            }
        });
        //exitButton.setForeground(new Color(0, 0, 128));
        exitButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        //exitButton.setBackground(Color.LIGHT_GRAY);
        exitButton.setBounds(107, 513, 223 - 107, 272 - 220);
        add(exitButton);
        exitButton.setVisible(true);
        exitButton.setOpaque(false);

        JButton findButton = new JButton();
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FindAvt(e);
            }
        });
        findButton.setBounds(992, 148, 1110 - 992, 198 - 148);
        add(findButton);
        findButton.setVisible(true);
        findButton.setOpaque(false);


        deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteAvtshow(e);
            }
        });
        //deleteButton.setForeground(new Color(0, 0, 128));
        deleteButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        //deleteButton.setBackground(Color.LIGHT_GRAY);
        deleteButton.setBounds(107, 319, 223 - 107, 272 - 220);
        add(deleteButton);
        deleteButton.setVisible(true);
        deleteButton.setOpaque(false);

        addButton = new JButton("增加");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddAvtshow(e);
            }
        });
        //addButton.setForeground(new Color(0, 0, 128));
        addButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        addButton.setBounds(107, 220, 223 - 107, 272 - 220);
        add(addButton);
        addButton.setVisible(true);
        addButton.setOpaque(false);


        //居中显示
        this.setLocationRelativeTo(null);
    }


    //搜索响应
    protected void FindAvt(ActionEvent e) {
        Message mes = new Message();
        Client client = new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Library);
        mes.setMessageType(MessageType.LibraryBookFind);
        mes.setData(findText.getText());
        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);

        ArrayList<Book> resbook = new ArrayList<Book>();
        resbook = (ArrayList<Book>) serverResponse.getData();

        Object[][] tableDate1 = new Object[resbook.size()][6];
        for (int i = 0; i < resbook.size(); i++) {

            tableDate1[i][0] = resbook.get(i).getName();
            tableDate1[i][1] = resbook.get(i).getId();
            tableDate1[i][2] = resbook.get(i).getAuthor();
            tableDate1[i][3] = resbook.get(i).getPress();
            tableDate1[i][4] = String.valueOf(resbook.get(i).getStock());
            if (resbook.get(i).getState() == true)
                tableDate1[i][5] = "可借";
            else
                tableDate1[i][5] = "不可借";


        }


        String[] tablename1 = {"书名", "书号", "作者", "出版社", "库存", "状态"};
        DefaultTableModel dataModel2 = new DefaultTableModel(tableDate1, tablename1);
        table.setModel(dataModel2);


        //scrollPane2=new JScrollPane();
        scrollPane2.setOpaque(false);
        scrollPane2.getViewport().setOpaque(false);
        scrollPane2.setViewportView(table);
        table.setBounds(0, 0, 1143 - 243, 588 - 233 + 13);

        scrollPane2.setBounds(243, 223, 900, 588 - 233 + 13);
        scrollPane2.setVisible(true);
        setVisible(true);

        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Serif", Font.BOLD, 28));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {
                "书名", "书号", "作者", "出版社", "库存", "状态"};
        for (int i = 0; i < 6; i++) {
            table.getColumn(Names[i]).setCellRenderer(renderer);//单格渲染
            TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
            column.setHeaderRenderer(renderer);//表头渲染
        }
        table.setOpaque(false);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
        scrollPane2.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
        scrollPane2.setOpaque(false);
        scrollPane2.getViewport().setOpaque(false);
        scrollPane2.setColumnHeaderView(table.getTableHeader());
        scrollPane2.getColumnHeader().setOpaque(false);

    }


    //显示修改书籍信息界面
    protected void ModifyAvtshow(ActionEvent e) {
        LibraryManage3 d1 = new LibraryManage3(this);
    }


    //显示删除书籍界面
    protected void DeleteAvtshow(ActionEvent e) {
        LibraryManage2 d1 = new LibraryManage2(this);

    }


    // 显示增加书籍界面
    protected void AddAvtshow(ActionEvent e) {
        LibraryManage1 d1 = new LibraryManage1(this);
    }


    // 重新读取服务端传来的数据，显示在界面table中
    public void SetTableShow() {
        ArrayList<Book> booklist = new ArrayList<Book>();

        Message mes = new Message();
        Client client = new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Library);
        mes.setMessageType(MessageType.LibraryBookGetAll);
        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);
        booklist = (ArrayList<Book>) serverResponse.getData();

        Object[][] tableDate = new Object[booklist.size()][6];

        for (int i = 0; i < booklist.size(); i++) {
            tableDate[i][0] = booklist.get(i).getName();
            tableDate[i][1] = booklist.get(i).getId();
            tableDate[i][2] = booklist.get(i).getAuthor();
            tableDate[i][3] = booklist.get(i).getPress();
            tableDate[i][4] = String.valueOf(booklist.get(i).getStock());
            if (booklist.get(i).getState() == true)
                tableDate[i][5] = "可借";
            else
                tableDate[i][5] = "不可借";
        }


        String[] tablename = {"书名", "书号", "作者", "出版社", "库存", "状态"};
        DefaultTableModel dataModel2 = new DefaultTableModel(tableDate, tablename);
        table.setModel(dataModel2);

        scrollPane2 = new JScrollPane();
        scrollPane2.setOpaque(false);
        scrollPane2.getViewport().setOpaque(false);
        scrollPane2.setViewportView(table);
        table.setBounds(0, 0, 1143 - 243, 588 - 233 + 13);
        scrollPane2.setBounds(243, 223, 900, 588 - 233 + 13);
        scrollPane2.setVisible(true);
        setVisible(true);

        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Serif", Font.BOLD, 28));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {
                "书名", "书号", "作者", "出版社", "库存", "状态"};
        for (int i = 0; i < 6; i++) {
            table.getColumn(Names[i]).setCellRenderer(renderer);//单格渲染
            TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
            column.setHeaderRenderer(renderer);//表头渲染
        }
        table.setOpaque(false);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
        scrollPane2.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
        scrollPane2.setOpaque(false);
        scrollPane2.getViewport().setOpaque(false);
        scrollPane2.setColumnHeaderView(table.getTableHeader());
        scrollPane2.getColumnHeader().setOpaque(false);

        add(scrollPane2, 0);

    }

    //图书馆管理员界面退出响应
    protected void ExitAvt(ActionEvent e) {

        this.setVisible(false);//关闭本界面

    }

    public boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}