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


public class LibraryStu2 extends JFrame {
    //创建框架
    private JPanel contentPane;
    private JPanel lendPane,returnPane; //借书、还书界面
    private JTextField returnIDText;
    private JButton qrReturnButton,qxReturnButton; //lendPane&returnPane
    private JLabel ReturnLabel;
    private LibraryStu t;

    public LibraryStu2(LibraryStu tem) {
        tem.dispose();
        ArrayList<Book> booklist = new ArrayList<Book>();

        t=tem;

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
        ImageIcon image = new ImageIcon("VCampusClient/Image/LibraryStu2.png");
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

        returnIDText = new JTextField();
        returnIDText.setForeground(SystemColor.textText);
        returnIDText.setFont(new Font("华文行楷", Font.PLAIN, 32));
        returnIDText.setVisible(true);
        returnIDText.setBounds(269, 154, 632-269, 193-154);
        add(returnIDText,0);
        returnIDText.setOpaque(false);
        returnIDText.setBorder(new EmptyBorder(0,0,0,0));

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
        qrReturnButton.setBounds(256, 274, 359-256, 331-274);
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
        qxReturnButton.setBounds(524, 273, 359-256, 331-274);
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
        else if(res > 0)
            JOptionPane.showMessageDialog(null,"还书完成","提示",JOptionPane.WARNING_MESSAGE);

        t.SetTableShow();

        returnIDText.setText("");
    }

    //还书取消
    protected void qxReturnAvt(ActionEvent e) {
        LibraryStu d=new LibraryStu(t.uID);
        dispose();
    }
}
