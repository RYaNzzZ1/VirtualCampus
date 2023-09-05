package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
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


public class LibraryStu1 extends JFrame {
    //创建框架
    private JPanel contentPane;
    private JPanel lendPane,returnPane; //借书、还书界面
    private JTextField lendIDText;
    private JButton qrLendButton,qxLendButton; //lendPane&returnPane
    private LibraryStu t;
    
    public LibraryStu1(LibraryStu tem) {
        tem.dispose();
        ArrayList<Book> booklist = new ArrayList<Book>();

        t=tem;

        contentPane = new JPanel();
        lendPane = new JPanel();
        returnPane = new JPanel();

        setLayout(null);

        setTitle("借书");
        setDefaultCloseOperation(2);

        //设置背景图片
        //把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // 使用绝对定位
        // 创建带有背景图片的JLabel
        ImageIcon image = new ImageIcon("VCampusClient/Image/LibraryStu1.png");
        JLabel backlabel = new JLabel(image);
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 847 / 2, d.height / 2 - 429 / 2, 847, 429);
        backlabel.setSize(847, 429);
        this.getLayeredPane().add(backlabel, new Integer(Integer.MIN_VALUE));
        backlabel.setOpaque(false); // 设置背景透明
        setResizable(false); //阻止用户拖拽改变窗口的大小
        setVisible(true);


        /*backlabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
            }
        });*/


        lendIDText = new JTextField();
        //lendIDText.setForeground(SystemColor.textText);
        lendIDText.setFont(new Font("华文行楷", Font.PLAIN, 32));
        lendIDText.setBounds(269, 154, 632-269, 193-154);
        lendIDText.setVisible(true);
        add(lendIDText,0);
        lendIDText.setOpaque(false);
        lendIDText.setBorder(new EmptyBorder(0,0,0,0));


        add(backlabel);

        qrLendButton = new JButton("确认");
        qrLendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LendAvt(e);
            }
        });
        //qrLendButton.setForeground(new Color(0, 0, 128));
        qrLendButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        //qrLendButton.setBackground(Color.LIGHT_GRAY);
        qrLendButton.setBounds(256, 274, 359-256, 331-274);
        qrLendButton.setVisible(true);
        add(qrLendButton,-1);
        qrLendButton.setOpaque(false);


        qxLendButton = new JButton("取消");
        qxLendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                qxLendAvt(e);
            }
        });
        //qxLendButton.setForeground(new Color(0, 0, 128));
        qxLendButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        //qxLendButton.setBackground(Color.LIGHT_GRAY);
        qxLendButton.setBounds(524, 273, 359-256, 331-274);
        qxLendButton.setVisible(true);
        add(qxLendButton,-1);
        qxLendButton.setOpaque(false);


        //居中显示
        this.setLocationRelativeTo(null);

    }

    //借书确认响应
    protected void LendAvt(ActionEvent e) {

        contentPane.setVisible(false);
        returnPane.setVisible(false);
        lendPane.setVisible(true);

        String []data=new String[2];
        data[0]=lendIDText.getText();
        data[1]=t.uID;
        Message mes =new Message();
        Client client=new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Library);
        mes.setMessageType(MessageType.LibraryBookLend);
        mes.setData(data);
        Message serverResponse=new Message();
        serverResponse=client.sendRequestToServer(mes);

        int res = (int)serverResponse.getData();

        if(res==0) {
            JOptionPane.showMessageDialog(null, "该书号不存在！", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }else if(res==-1) {
            JOptionPane.showMessageDialog(null, "该书库存为0不可借", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }else if(res > 0)
            JOptionPane.showMessageDialog(null,"借书完成","提示",JOptionPane.WARNING_MESSAGE);

        t.SetTableShow();


        lendIDText.setText("");
        dispose();
    }

    //借书取消
    protected void qxLendAvt(ActionEvent e) {
        LibraryStu d=new LibraryStu(t.uID);
        dispose();
    }

}
