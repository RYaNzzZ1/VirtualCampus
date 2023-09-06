package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.Book;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class LibraryStu extends JFrame {

    public String uID;
    //private JTable table2;
    JScrollPane scrollPane2;
    JTable table;
    private JPanel contentPane;
    private JPanel lendPane, returnPane; //借书、还书界面
    private JTextField findText, lendIDText, returnIDText;
    private JButton returnBookButton, exitButton, lendBookButton;  //contentPane
    private JButton qrLendButton, qrReturnButton, qxLendButton, qxReturnButton; //lendPane&returnPane
    private JLabel lendLabel;
    private JLabel returnLabel;


    //创建框架
    public LibraryStu(String uID) {
        this.uID = uID;
        ArrayList<Book> booklist = new ArrayList<Book>();


        contentPane = new JPanel();
        lendPane = new JPanel();
        returnPane = new JPanel();
        ;

        Message mes = new Message();
        setLayout(null);
        Client client = new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Library);
        mes.setMessageType(MessageType.LibraryBookGetAll);
        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);
        booklist = (ArrayList<Book>) serverResponse.getData();

        System.out.print(serverResponse.getData());

        setTitle("图书馆-学生");
        setDefaultCloseOperation(2);

        //设置背景图片
        //把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // 使用绝对定位
        // 创建带有背景图片的JLabel
        ImageIcon image = new ImageIcon("VCampusClient/Image/LibraryStu.png");
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



	/*backlabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
			}
		});
*/


        //搜索输入框
        findText = new JTextField();
        findText.setText("(书号）/(书名)");
        findText.setFont(new Font("华文行楷", Font.PLAIN, 32));
        findText.setBounds(686 + 4, 150, 288, 48);
        add(findText);
        findText.setOpaque(false);
        findText.setBorder(new EmptyBorder(0, 0, 0, 0));


        findText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                autodisplay(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                autodisplay(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                autodisplay(e);
            }

        });

        JScrollPane scrollPane = new JScrollPane();


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
        //table = new JTable(tableDate,tablename);
		/*table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);*/

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

        add(backlabel); //放在所有输入框之后，按钮之前


        //搜索按钮
        JButton findButton = new JButton();
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println("lllll");
                FindAvt(e);
            }
        });
        findButton.setBounds(993, 147, 117, 52);
        //findButton.setOpaque(false);
        add(findButton);
        findButton.setOpaque(false);

        //还书按钮
        returnBookButton = new JButton("还书");
        returnBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //System.out.println("lllll");
                ReturnShow(e);
            }
        });
        returnBookButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        returnBookButton.setBounds(107, 379, 114, 53);
        add(returnBookButton);
        returnBookButton.setOpaque(false);


        //退出按钮
        exitButton = new JButton("退出");
        exitButton.setBounds(107, 496, 114, 53);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ExitAvt(e);
            }
        });
        add(exitButton);
        exitButton.setOpaque(false);
        exitButton.setFont(new Font("华文行楷", Font.BOLD, 25));


        //借书按钮
        lendBookButton = new JButton("借书");
        lendBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LendShow(e);
            }
        });
        lendBookButton.setOpaque(false);
        lendBookButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        lendBookButton.setBounds(107, 258, 114, 53);
        add(lendBookButton);

    }


    //搜索为空显示所有
    private void autodisplay(DocumentEvent e) {
        String text = findText.getText();
        if (Objects.equals(text, ""))
            SetTableShow();
    }

    //借书响应
    protected void LendShow(ActionEvent e) {
        LibraryStu1 d1 = new LibraryStu1(this);
    }

    //还书响应
    protected void ReturnShow(ActionEvent e) {
        LibraryStu2 d2 = new LibraryStu2(this);
    }


    //搜索响应
    protected void FindAvt(ActionEvent e) {
        Message mes = new Message();
        Client client = new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Library);
        mes.setMessageType(MessageType.LibraryBookFind);
        mes.setData(findText.getText());
        System.out.println(findText.getText().toString());
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


    //重新读取服务端传来的数据，显示在界面table中
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
        //登录界面LibraryLogin

        this.setVisible(false);//关闭本界面

    }
}
