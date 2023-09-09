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

public class LibraryManage3 extends JFrame {

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

    public LibraryManage3(LibraryManage tem) {
        tem.dispose();

        ArrayList<Book> booklist = new ArrayList<Book>();

        t = tem;

        setTitle("增加");
        setDefaultCloseOperation(2);


        //设置背景图片
        //把图片添加到标签里（把标签的大小设为和图片大小相同），把标签放在分层面板的最底层；
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(null); // 使用绝对定位
        // 创建带有背景图片的JLabel
        ImageIcon image = new ImageIcon("VCampusClient/image/LibraryManage3.png");
        JLabel backlabel = new JLabel(image);
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 847 / 2, d.height / 2 - 633 / 2, 847, 633);
        backlabel.setSize(847, 633);
        this.getLayeredPane().add(backlabel, new Integer(Integer.MIN_VALUE));
        backlabel.setOpaque(false); // 设置背景透明
        setResizable(false); //阻止用户拖拽改变窗口的大小
        setVisible(true);


        nameRadioButton = new JRadioButton("书名");
        buttonGroup.add(nameRadioButton);
        nameRadioButton.setFont(new Font("华文行楷", Font.BOLD, 30));
        nameRadioButton.setOpaque(false);

        //JLabel selectLabel = new JLabel("请选择修改信息：");
        //selectLabel.setFont(new Font("华文行楷", Font.BOLD, 30));

        idRadioButton = new JRadioButton("书号");
        buttonGroup.add(idRadioButton);
        idRadioButton.setFont(new Font("华文行楷", Font.BOLD, 30));
        idRadioButton.setOpaque(false);

        authorRadioButton = new JRadioButton("作者");
        buttonGroup.add(authorRadioButton);
        authorRadioButton.setFont(new Font("华文行楷", Font.BOLD, 30));
        authorRadioButton.setOpaque(false);

        pressRadioButton = new JRadioButton("出版社");
        buttonGroup.add(pressRadioButton);
        pressRadioButton.setFont(new Font("华文行楷", Font.BOLD, 30));
        pressRadioButton.setOpaque(false);

        stockRadioButton = new JRadioButton("库存上限");
        buttonGroup.add(stockRadioButton);
        stockRadioButton.setFont(new Font("华文行楷", Font.BOLD, 30));
        stockRadioButton.setOpaque(false);

        JPanel panel = new JPanel();
        panel.add(nameRadioButton);
        panel.add(idRadioButton);
        panel.add(authorRadioButton);
        panel.add(pressRadioButton);
        panel.add(stockRadioButton);


        panel.setVisible(true);
        panel.setBounds(100, 159, 766 - 116, 236 - 159);
        add(panel);
        panel.setOpaque(false);

        /*nameRadioButton.setOpaque(false);
        idRadioButton.setOpaque(false);
        authorRadioButton.setOpaque(false);
        pressRadioButton.setOpaque(false);
        stockRadioButton.setOpaque(false);

        panel = new JPanel();
        panel.setForeground(UIManager.getColor("Panel.background"));
        panel.setOpaque(false);*/

        oldIDText = new JTextField();
        oldIDText.setBounds(303, 260, 668 - 303, 301 - 260);
        oldIDText.setFont(new Font("华文行楷", Font.PLAIN, 30));
        oldIDText.setVisible(true);
        add(oldIDText);
        oldIDText.setOpaque(false);
        oldIDText.setBorder(new EmptyBorder(0, 0, 0, 0));


        modifiedText = new JTextField();
        modifiedText.setBounds(303, 331, 668 - 303, 301 - 260);
        modifiedText.setFont(new Font("华文行楷", Font.PLAIN, 30));
        modifiedText.setVisible(true);
        add(modifiedText);
        modifiedText.setOpaque(false);
        modifiedText.setBorder(new EmptyBorder(0, 0, 0, 0));


        add(backlabel);

        JButton modqrButton = new JButton("确认");
        modqrButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ModiInfo(e);
            }
        });
        modqrButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        modqrButton.setBounds(222, 433, 323 - 222, 485 - 433);
        modqrButton.setVisible(true);
        add(modqrButton);
        modqrButton.setOpaque(false);

        modqxButton = new JButton("取消");
        modqxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modqxAvt(e);
            }
        });
        modqxButton.setFont(new Font("华文行楷", Font.BOLD, 25));
        modqxButton.setBounds(489, 433, 323 - 222, 485 - 433);
        modqxButton.setVisible(true);
        add(modqxButton);
        modqxButton.setOpaque(false);


    }

    //修改书籍信息页面取消键的响应
    protected void modqxAvt(ActionEvent e) {
        LibraryManage d = new LibraryManage();
        dispose();
    }


    // 修改书籍信息确认界面
    protected void ModiInfo(ActionEvent e) {
        if ((nameRadioButton.isSelected() == false) && (idRadioButton.isSelected() == false) && (authorRadioButton.isSelected() == false) && (pressRadioButton.isSelected() == false) && (stockRadioButton.isSelected() == false)) {
            JOptionPane.showMessageDialog(null, "请选择修改的书籍信息！", "要求", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Message mes = new Message();
        Client client = new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Library);
        mes.setMessageType(MessageType.LibraryBookUpdate);  //
        if (nameRadioButton.isSelected()) {
            ArrayList<String> para = new ArrayList<String>();
            para.add(oldIDText.getText());
            para.add("Name");
            para.add(modifiedText.getText());
            mes.setData(para);
        }
        if (idRadioButton.isSelected()) {
            ArrayList<String> para = new ArrayList<String>();
            para.add(oldIDText.getText());
            para.add("ID");
            para.add(modifiedText.getText());
            mes.setData(para);
        }
        if (authorRadioButton.isSelected()) {
            ArrayList<String> para = new ArrayList<String>();
            para.add(oldIDText.getText());
            para.add("Author");
            para.add(modifiedText.getText());
            mes.setData(para);
        }
        if (pressRadioButton.isSelected()) {
            ArrayList<String> para = new ArrayList<String>();
            para.add(oldIDText.getText());
            para.add("Press");
            para.add(modifiedText.getText());
            mes.setData(para);
        }
        if (stockRadioButton.isSelected()) {
            if ((!this.isNumeric(modifiedText.getText())) || (Integer.valueOf(modifiedText.getText()) <= 0)) {
                JOptionPane.showMessageDialog(null, "库存上限请输入正整数！", "警告", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ArrayList<String> para = new ArrayList<String>();
            para.add(oldIDText.getText());
            para.add("Max");
            para.add(modifiedText.getText());
            mes.setData(para);
        }

        int res = 0;

        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);
        res = (int) serverResponse.getData();

        if (res > 0) JOptionPane.showMessageDialog(null, "修改完成");
        if (res == 0) {
            JOptionPane.showMessageDialog(null, "此书号不存在", "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }

        buttonGroup.clearSelection();

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
