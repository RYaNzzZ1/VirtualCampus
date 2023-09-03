package seu.list.client.view;


import seu.list.client.bz.Client;
import seu.list.client.bz.ClientMainFrame;
import seu.list.common.Book;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LibraryManage1 extends JFrame {

    private JPanel contentPane, modifyPane, panel, addPane, deletePane;
    private JTextField findText, oldIDText, modifiedText;
    private JLayeredPane layerPane;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton nameRadioButton, idRadioButton, authorRadioButton, pressRadioButton, stockRadioButton;

    private JButton deleteButton, addButton;
    private JLabel addNameLabel, addIDLabel, addAuthorLabel, addPressLabel, addStockLabel;
    private JTextField addNameText, addIDText, addAuthorText, addPressText, addStockText;
    private JButton addqrButton, addqxButton;
    private JLabel delIDLabel;
    private JTextField delIDText;
    private JButton delqrButton, delqxButton;
    private JButton modqxButton;
    private LibraryManage t;

    public LibraryManage1(LibraryManage tem) {
        tem.dispose();

        ArrayList<Book> booklist = new ArrayList<Book>();

        t = tem;

        setTitle("增加");
        setDefaultCloseOperation(2);


        //设置背景图片
        //把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // 使用绝对定位
        // 创建带有背景图片的JLabel
        ImageIcon image = new ImageIcon("VCampusClient/image/LibraryManage1.png");
        JLabel backlabel = new JLabel(image);
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 844 / 2, d.height / 2 - 624 / 2, 844, 624);
        backlabel.setSize(844, 624);
        this.getLayeredPane().add(backlabel, new Integer(Integer.MIN_VALUE));
        backlabel.setOpaque(false); // 设置背景透明
        setResizable(false); //阻止用户拖拽改变窗口的大小
        setVisible(true);


        addNameText = new JTextField();
        addNameText.setBounds(258,106,621-256,146-106);
        addNameText.setFont(new Font("华文行楷", Font.PLAIN, 30));
        addNameText.setVisible(true);
        add(addNameText);
        addNameText.setOpaque(false);
        addNameText.setBorder(new EmptyBorder(0,0,0,0));

        addIDText = new JTextField();
        addIDText.setBounds(258,178,621-256,146-106);
        addIDText.setFont(new Font("华文行楷", Font.PLAIN, 30));
        addIDText.setVisible(true);
        add(addIDText);
        addIDText.setOpaque(false);
        addIDText.setBorder(new EmptyBorder(0,0,0,0));

        addAuthorText = new JTextField();
        addAuthorText.setBounds(258,253,621-256,146-106);
        addAuthorText.setFont(new Font("华文行楷", Font.PLAIN, 30));
        addAuthorText.setVisible(true);
        add(addAuthorText);
        addAuthorText.setOpaque(false);
        addAuthorText.setBorder(new EmptyBorder(0,0,0,0));
        addAuthorText.setFont(new Font("华文行楷", Font.PLAIN, 30));

        addPressText = new JTextField();
        addPressText.setBounds(258,332,621-256,146-106);
        addPressText.setFont(new Font("华文行楷", Font.PLAIN, 30));
        addPressText.setVisible(true);
        add(addPressText);
        addPressText.setOpaque(false);
        addPressText.setBorder(new EmptyBorder(0,0,0,0));

        addStockText = new JTextField();
        addStockText.setBounds(258,412,621-256,146-106);
        addStockText.setFont(new Font("华文行楷", Font.PLAIN, 30));
        addStockText.setVisible(true);
        add(addStockText);
        addStockText.setOpaque(false);
        addStockText.setBorder(new EmptyBorder(0,0,0,0));

        add(backlabel);

        addqrButton = new JButton("确定");
        addqrButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddbookAvt(e);
            }
        });
        //addqrButton.setForeground(Color.BLACK);
        addqrButton.setFont(new Font("华文行楷", Font.BOLD, 29));
        addqrButton.setBounds(236,501,337-236,554-501);
        addqrButton.setVisible(true);
        add(addqrButton);
        addqrButton.setOpaque(false);



        addqxButton = new JButton("取消");
        addqxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddqxAvt(e);
            }
        });
        //addqxButton.setForeground(Color.BLACK);
        addqxButton.setFont(new Font("华文行楷", Font.BOLD, 29));
        addqxButton.setBounds(502,500,337-236,554-501);
        addqxButton.setVisible(true);
        add(addqxButton);
        addqxButton.setOpaque(false);




    }

    //增加书籍界面取消键的响应
    protected void AddqxAvt(ActionEvent e) {
        LibraryManage d=new LibraryManage();
        dispose();
    }

    //增加书籍界面确认键的响应
    protected void AddbookAvt(ActionEvent e) {
        if((!this.isNumeric(addStockText.getText()))||(Integer.valueOf(addStockText.getText())<0)) {
            JOptionPane.showMessageDialog(null, "库存请输入正整数！", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[]arr=new String[5];
        arr[0]=addNameText.getText();
        arr[1]=addIDText.getText();
        arr[2]=addAuthorText.getText();
        arr[3]=addPressText.getText();
        arr[4]=addStockText.getText();

        Message mes =new Message();
        Client client=new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Library);
        mes.setMessageType(MessageType.LibraryBookAdd);
        mes.setData(arr);
        Message serverResponse=new Message();
        serverResponse=client.sendRequestToServer(mes);

        int res=0;
        res=(int) serverResponse.getData();
        if(res==0) {
            JOptionPane.showMessageDialog(null, "此书号已存在！", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null,"完成");

        t.SetTableShow();
        dispose();
    }


    public boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}


