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

public class LibraryManage2 extends JFrame {

    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JPanel contentPane, modifyPane, panel, addPane, deletePane;
    private JTextField findText, oldIDText, modifiedText;
    private JLayeredPane layerPane;
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

    public LibraryManage2(LibraryManage tem) {
        tem.dispose();

        ArrayList<Book> booklist = new ArrayList<Book>();

        t = tem;

        setTitle("删除");
        setDefaultCloseOperation(2);


        //设置背景图片
        //把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(null); // 使用绝对定位
        // 创建带有背景图片的JLabel
        ImageIcon image = new ImageIcon("VCampusClient/image/LibraryManage2.png");
        JLabel backlabel = new JLabel(image);
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 847 / 2, d.height / 2 - 441 / 2, 847, 441);
        backlabel.setSize(847, 441);
        this.getLayeredPane().add(backlabel, new Integer(Integer.MIN_VALUE));
        backlabel.setOpaque(false); // 设置背景透明
        setResizable(false); //阻止用户拖拽改变窗口的大小
        setVisible(true);


        delIDText = new JTextField();
        delIDText.setBounds(266, 156, 631 - 266, 199 - 156);
        delIDText.setFont(new Font("华文行楷", Font.PLAIN, 30));
        delIDText.setVisible(true);
        add(delIDText);
        delIDText.setOpaque(false);
        delIDText.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(backlabel);

        delqrButton = new JButton("确定");
        delqrButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DelqrAvt(e);
            }
        });
        delqrButton.setForeground(Color.BLACK);
        delqrButton.setFont(new Font("华文行楷", Font.BOLD, 29));
        delqrButton.setBounds(255, 276, 357 - 255, 329 - 276);
        delqrButton.setVisible(true);
        add(delqrButton);
        delqrButton.setOpaque(false);


        delqxButton = new JButton("取消");
        delqxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DelqxAvt(e);
            }
        });
        delqxButton.setForeground(Color.BLACK);
        delqxButton.setFont(new Font("华文行楷", Font.BOLD, 29));
        delqxButton.setBounds(523, 276, 357 - 255, 329 - 276);
        delqxButton.setVisible(true);
        add(delqxButton);
        delqxButton.setOpaque(false);

    }

    ///删除书籍界面确认键的响应
    protected void DelqrAvt(ActionEvent e) {
        Message mes = new Message();
        Client client = new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Library);
        mes.setMessageType(MessageType.LibraryBookDelete);
        mes.setData(delIDText.getText());
        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);

        int res = 0;
        res = (int) serverResponse.getData();
        if (res == 0) {
            JOptionPane.showMessageDialog(null, "此书号不存在", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, "完成");

        t.SetTableShow();
        dispose();

    }

    // 删除书籍界面取消键的响应
    protected void DelqxAvt(ActionEvent e) {
        LibraryManage d = new LibraryManage();
        dispose();
    }


    public boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }
}
