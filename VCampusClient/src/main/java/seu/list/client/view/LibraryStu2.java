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


public class LibraryStu2 extends JFrame {
    //创建框架
    private JPanel contentPane;
    private JPanel lendPane, returnPane; //借书、还书界面
    private JTextField returnIDText;
    private JButton qrReturnButton, qxReturnButton; //lendPane&returnPane
    private JLabel ReturnLabel;
    private LibraryStu t;

    JScrollPane scrollPane2;
    JTable table;

    public LibraryStu2(LibraryStu tem) {
        tem.dispose();

        t = tem;

        contentPane = new JPanel();
        lendPane = new JPanel();
        returnPane = new JPanel();

        setLayout(null);

        setTitle("还书");
        setDefaultCloseOperation(2);

        //设置背景图片
        //把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // 使用绝对定位
        // 创建带有背景图片的JLabel
        ImageIcon image = new ImageIcon("VCampusClient/Image/LibraryStu2new.png");
        JLabel backlabel = new JLabel(image);
        backlabel.setBounds(0,0,848,594);
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 848 / 2, d.height / 2 - 594 / 2, 848, 594+25);
        backlabel.setSize(848, 594);
        this.getLayeredPane().add(backlabel, new Integer(Integer.MIN_VALUE));
        backlabel.setOpaque(false); // 设置背景透明
        setResizable(false); //阻止用户拖拽改变窗口的大小
        setVisible(true);

        returnIDText = new JTextField();
        returnIDText.setForeground(SystemColor.textText);
        returnIDText.setFont(new Font("华文行楷", Font.PLAIN, 32));
        returnIDText.setVisible(true);
        returnIDText.setBounds(180, 497, 541 - 180, 537 - 497);
        add(returnIDText, 0);
        returnIDText.setOpaque(false);
        returnIDText.setBorder(new EmptyBorder(0, 0, 0, 0));


        ArrayList<Book> bookborringrecord = new ArrayList<Book>();
        Message mes = new Message();
        setLayout(null);
        Client client = new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Library);
        mes.setMessageType(MessageType.LibraryBookGetLend);
        mes.setData(t.uID);
        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);
        bookborringrecord = (ArrayList<Book>) serverResponse.getData();


        Object[][] tableDate = new Object[bookborringrecord.size()][6];

        for (int i = 0; i < bookborringrecord.size(); i++) {

            tableDate[i][0] = bookborringrecord.get(i).getName();
            tableDate[i][1] = bookborringrecord.get(i).getId();
            tableDate[i][2] = bookborringrecord.get(i).getAuthor();
            tableDate[i][3] = bookborringrecord.get(i).getPress();
            //tableDate[i][4] = String.valueOf(bookborringrecord.get(i).getStock());
            if (bookborringrecord.get(i).getState() == true)
                tableDate[i][4] = "已还";
            else
                tableDate[i][4] = "未还";
        }


        String[] tablename = {/*"序号",*/"书名", "书号", "作者", "出版社", "状态"};
        DefaultTableModel dataModel1 = new DefaultTableModel(tableDate, tablename);
        table = new JTable(dataModel1);
        //table = new JTable(tableDate,tablename);
		/*table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);*/

        scrollPane2 = new JScrollPane();
        scrollPane2.setOpaque(false);
        scrollPane2.getViewport().setOpaque(false);
        scrollPane2.setViewportView(table);
        table.setBounds(0, 0, 774 - 81, 455 - 113);
        add(scrollPane2);
        scrollPane2.setBounds(81, 113, 774 - 81, 455 - 113);
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
                /*"序号",*/"书名", "书号", "作者", "出版社", "状态"};
        for (int i = 0; i < 5; i++) {
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

        qrReturnButton = new JButton("确认");
        qrReturnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ReturnAvt(e);
            }
        });
        qrReturnButton.setForeground(new Color(0, 0, 128));
        qrReturnButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        qrReturnButton.setBackground(Color.LIGHT_GRAY);
        qrReturnButton.setVisible(true);
        qrReturnButton.setBounds(576, 492, 673 - 576, 543 - 492);
        qrReturnButton.setVisible(true);
        add(qrReturnButton,-1);
        qrReturnButton.setOpaque(false);

        qxReturnButton = new JButton("取消");
        qxReturnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                qxReturnAvt(e);
            }
        });
        qxReturnButton.setForeground(new Color(0, 0, 128));
        qxReturnButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        qxReturnButton.setBackground(Color.LIGHT_GRAY);
        qxReturnButton.setVisible(true);
        qxReturnButton.setBounds(707, 492, 673 - 576, 543 - 492);
        qxReturnButton.setVisible(true);
        add(qxReturnButton,-1);
        qxReturnButton.setOpaque(false);


        //居中显示
        this.setLocationRelativeTo(null);

    }
    protected void ReturnAvt(ActionEvent e) {
        contentPane.setVisible(false);
        lendPane.setVisible(false);
        returnPane.setVisible(true);

        String []data=new String[2];
		data[0]=returnIDText.getText();
		data[1]=t.uID;
		Message mes =new Message();
		Client client=new Client(ClientMainFrame.socket);
		mes.setModuleType(ModuleType.Library);
		mes.setMessageType(MessageType.LibraryBookReturn);
		mes.setData(data);
		Message serverResponse=new Message();
		serverResponse=client.sendRequestToServer(mes);

        int res = (int)serverResponse.getData();
        if(res==0) {
            JOptionPane.showMessageDialog(null, "该书号不存在！", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }
        else if(res==-1){
            JOptionPane.showMessageDialog(null, "非法操作:库存已达上限！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if(res > 0) {
            JOptionPane.showMessageDialog(null, "还书完成", "提示", JOptionPane.WARNING_MESSAGE);
            dispose();
        }
        t.SetTableShow();

        returnIDText.setText("");
        //dispose();
    }

    //还书取消
    protected void qxReturnAvt(ActionEvent e) {
        LibraryStu d=new LibraryStu(t.uID);
        dispose();
    }
}
