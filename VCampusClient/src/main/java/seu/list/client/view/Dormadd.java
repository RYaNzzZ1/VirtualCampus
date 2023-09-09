/**
 * @version jdk1.8.0
 */
package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.Dormitory;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;

public class Dormadd extends JDialog {

    static Socket socket;
    private final JPanel contentPanel = new JPanel();
    public ArrayList<Dormitory> allDormitoryContents;
    private JPanel buttonPane;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel lblNewLabel;
    private JTextField AuserIDField;
    private JTextField AdormIDField;
    private JTextField AbunkIDField;
    private JTextField AscoreField;
    private JTextField AwaterField;
    private JTextField AelectricityField;
    private Message mes = new Message();
    private Client client;
    private Dormitory temp;
    private DormitoryAdminClient c = null;

    /**
     * Create the dialog.
     */
    public Dormadd(final DormitoryAdminClient C, Socket socket) {
        c = C;

        Dormadd.socket = socket;
        setFont(new Font("微软雅黑", Font.BOLD, 12));
        setTitle("添加宿舍");
        setLayout(null);

        // 创建带有背景图片的JLabel
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Dormadd.PNG"));
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        backgroundImageLabel.setBounds(0, 0, 870, 545);
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 870 / 2, d.height / 2 - 545 / 2, 870, 545);
        backgroundImageLabel.setOpaque(false); // 设置背景透明
        setVisible(true);

        setResizable(false); //阻止用户拖拽改变窗口的大小

        //设置输入文本框
        AuserIDField = new JTextField();
        AuserIDField.setFont(new Font("华文行楷", Font.PLAIN, 32));
        AuserIDField.setColumns(10);
        AuserIDField.setBounds(170, 150, 247, 45);
        add(AuserIDField);
        AuserIDField.setOpaque(false);
        AuserIDField.setBorder(new EmptyBorder(0, 0, 0, 0));

        AdormIDField = new JTextField();
        AdormIDField.setFont(new Font("华文行楷", Font.PLAIN, 32));
        AdormIDField.setColumns(10);
        AdormIDField.setBounds(515, 153, 247, 45);
        add(AdormIDField);
        AdormIDField.setOpaque(false);
        AdormIDField.setBorder(new EmptyBorder(0, 0, 0, 0));

        AbunkIDField = new JTextField();
        AbunkIDField.setFont(new Font("华文行楷", Font.PLAIN, 32));
        AbunkIDField.setColumns(10);
        AbunkIDField.setBounds(168, 244, 247, 45);
        add(AbunkIDField);
        AbunkIDField.setOpaque(false);
        AbunkIDField.setBorder(new EmptyBorder(0, 0, 0, 0));

        AscoreField = new JTextField();
        AscoreField.setFont(new Font("华文行楷", Font.PLAIN, 32));
        AscoreField.setColumns(10);
        AscoreField.setBounds(515, 243, 247, 45);
        add(AscoreField);
        AscoreField.setOpaque(false);
        AscoreField.setBorder(new EmptyBorder(0, 0, 0, 0));

        AwaterField = new JTextField();
        AwaterField.setFont(new Font("华文行楷", Font.PLAIN, 32));
        AwaterField.setColumns(10);
        AwaterField.setBounds(167, 338, 247, 45);
        add(AwaterField);
        AwaterField.setOpaque(false);
        AwaterField.setBorder(new EmptyBorder(0, 0, 0, 0));

        AelectricityField = new JTextField();
        AelectricityField.setFont(new Font("华文行楷", Font.PLAIN, 32));
        AelectricityField.setColumns(10);
        AelectricityField.setBounds(516, 338, 247, 45);
        add(AelectricityField);
        AelectricityField.setOpaque(false);
        AelectricityField.setBorder(new EmptyBorder(0, 0, 0, 0));


        //添加背景图片
        add(backgroundImageLabel);

        //设置按钮
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        {
            buttonPane = new JPanel();
            {
                okButton = new JButton("确定");
                okButton.setActionCommand("OK");
                getRootPane().setDefaultButton(okButton);
                okButton.setBounds(234, 427, 105, 55);
                add(okButton);
                okButton.setOpaque(false);
                okButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        AddAct(e);
                        setVisible(false);
                    }
                });
            }
            {
                cancelButton = new JButton("取消");
                cancelButton.setActionCommand("Cancel");
                cancelButton.setBounds(580, 427, 105, 55);
                add(cancelButton);
                cancelButton.setOpaque(false);
                cancelButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        setVisible(false);
                    }
                });
            }
        }
    }

    /**
     * 完成添加宿舍
     * @param e 点击事件
     */
    protected void AddAct(ActionEvent e) {
        // TODO Auto-generated method stub
        temp = new Dormitory();
        temp.setuserID(AuserIDField.getText());
        temp.setDormitoryID(AdormIDField.getText());
        temp.setStudentBunkID(Integer.parseInt(AbunkIDField.getText()));
        temp.setWater(Integer.parseInt(AwaterField.getText()));
        temp.setElectricity(Integer.parseInt(AelectricityField.getText()));
        temp.setDormitoryScore(Integer.parseInt(AscoreField.getText()));
//        temp.setDormitoryMaintain(AmaintainField.getText());
//        temp.setStudentExchange(AexchangeField.getText());

        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormAdd);
        client = new Client(ClientMainFrame.socket);
        mes.setData(temp);

        Message rec = new Message();
        rec = client.sendRequestToServer(mes);

        allDormitoryContents = (ArrayList<Dormitory>) rec.getData();
        System.out.println(allDormitoryContents);
        c.setEnabled(true);
        c.updateFrame(temp);
        this.dispose();
    }
}
