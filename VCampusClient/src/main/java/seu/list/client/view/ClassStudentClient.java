package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;
import seu.list.common.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalExclusionType;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ClassStudentClient extends JFrame {
    private JTextField name;
    private JTextField studentid;
    private JTextField major;
    private JTextField classid;
    private JTextField teacherid;
    private JTextField phone;
    private JTextField credit;
    private JTextField origion;
    private Double Money;
    private String Name;
    private String PWD;
    private String ID;
    private Student thisStu = null;
    private String statusmy = null;
    private MainMenu Mainmenu = null;

    @SuppressWarnings("unchecked")
    public ClassStudentClient(String id, String pwd, MainMenu mainmenu) {
        this.Mainmenu = mainmenu;

        //1.设置背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/ClassStudentClient.png"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        setBounds(d.width / 2 - 441, d.height / 2 - 635 / 2, 882, 670);
        backgroundImageLabel.setBounds(0, 0, 882, 635);
        setResizable(false);
        setLayout(null);


        PWD = pwd;
        ID = id;
        setTitle("学生个人信息管理界面");
        this.setDefaultCloseOperation(2);  //关闭按钮


        //下面代码无需改动
        Vector<Student> StuAll = new Vector<Student>();
        Message mes = new Message();
        mes.setModuleType(ModuleType.Student);
        mes.setMessageType(MessageType.ClassAdminGetAll);
        List<Object> sendData = new ArrayList<Object>();
        mes.setData(sendData);

        Client client = new Client(ClientMainFrame.socket);

        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);
        StuAll = (Vector<Student>) serverResponse.getData();

        thisStu = new Student();

        int studenttemp = 0;
        while (studenttemp < StuAll.size()) {
            String tempid = StuAll.get(studenttemp).getId();
            id.replaceAll("\\p{C}", "");
            tempid.replaceAll("\\p{C}", "");
            if (tempid.equals(id)) {
                thisStu = StuAll.get(studenttemp);
                break;
            }
            studenttemp++;
        }


        //提示标签


        //信息输入框（显示框）
        name = new JTextField();  //姓名
        name.setText("null");
        name.setEditable(false);
        name.setFont(new Font("华文行楷", Font.PLAIN, 24));
        name.setBounds(545, 153, 783 - 545, 128 - 91);
        name.setText(thisStu.getStudentName());
        Name = thisStu.getStudentName();
        add(name);
        name.setOpaque(false);
        name.setBorder(new EmptyBorder(0, 0, 0, 0));

        studentid = new JTextField();      //学号
        studentid.setEditable(false);
        studentid.setFont(new Font("华文行楷", Font.PLAIN, 24));
        studentid.setBounds(545, 91, 783 - 545, 128 - 91);
        studentid.setText("null");
        studentid.setText(thisStu.getStudentid());
        add(studentid);
        studentid.setOpaque(false);
        studentid.setBorder(new EmptyBorder(0, 0, 0, 0));

        major = new JTextField();  //专业
        major.setText("null");
        major.setEditable(false);
        major.setFont(new Font("华文行楷", Font.PLAIN, 24));
        major.setBounds(545, 328, 783 - 545, 128 - 91);
        major.setText(thisStu.getMajor());
        add(major);
        major.setOpaque(false);
        major.setBorder(new EmptyBorder(0, 0, 0, 0));

        classid = new JTextField();   //班级
        classid.setText("null");
        classid.setEditable(false);
        classid.setFont(new Font("华文行楷", Font.PLAIN, 24));
        classid.setBounds(545, 382, 783 - 545, 128 - 91);
        classid.setText(thisStu.getClassid());
        add(classid);
        classid.setOpaque(false);
        classid.setBorder(new EmptyBorder(0, 0, 0, 0));

        teacherid = new JTextField();   //老师
        teacherid.setText("null");
        teacherid.setEditable(false);
        teacherid.setFont(new Font("华文行楷", Font.PLAIN, 24));
        teacherid.setBounds(169, 444, 783 - 545, 128 - 91);
        teacherid.setText(thisStu.getTeacherid());
        add(teacherid);
        teacherid.setOpaque(false);
        teacherid.setBorder(new EmptyBorder(0, 0, 0, 0));

        phone = new JTextField();  //联系电话
        phone.setText("null");
        phone.setEditable(false);
        phone.setFont(new Font("华文行楷", Font.PLAIN, 24));
        phone.setBounds(545, 438, 783 - 545, 128 - 91);
        phone.setText(thisStu.getStudentphone());
        add(phone);
        phone.setOpaque(false);
        phone.setBorder(new EmptyBorder(0, 0, 0, 0));


        DecimalFormat df = new DecimalFormat("0.00");
        credit = new JTextField();   //账户余额
        credit.setEditable(false);
        credit.setFont(new Font("华文行楷", Font.PLAIN, 24));
        credit.setBounds(545, 502, 783 - 545, 128 - 91);
        credit.setText("" + df.format(thisStu.getStudentcredit()));
        Money = thisStu.getStudentcredit();
        add(credit);
        credit.setOpaque(false);
        credit.setBorder(new EmptyBorder(0, 0, 0, 0));

        origion = new JTextField(); //籍贯
        origion.setText("null");
        origion.setEditable(false);
        origion.setFont(new Font("华文行楷", Font.PLAIN, 24));
        origion.setBounds(545, 267, 783 - 545, 128 - 91);
        origion.setText(thisStu.getStudentorigion());
        origion.setOpaque(false);
        origion.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(origion);


        final JComboBox gender = new JComboBox();  //男女的下拉框
        gender.setEnabled(false);
        gender.setFont(new Font("华文行楷", Font.PLAIN, 24));
        gender.addItem("男");
        gender.addItem("女");
        gender.setBounds(544, 199, 783 - 545, 128 - 91);
        add(gender);
        if (thisStu.getStudentgender() == false) {
            gender.setSelectedIndex(1);
        }
        gender.setOpaque(false);

        gender.setToolTipText("");

        final JComboBox status = new JComboBox();  //政治面貌的下拉框
        status.setEnabled(false);
        status.setFont(new Font("宋体", Font.PLAIN, 16));
        status.addItem("群众");
        status.addItem("共青团员");
        status.addItem("中共党员");
        status.setBounds(169, 504, 783 - 545, 128 - 91);
        status.setOpaque(false);
        //下面的代码用于显示默认的政治面貌
        if (thisStu.getStudentstatus() != null) {
            statusmy = thisStu.getStudentstatus().replaceAll("\\p{C}", "");
            if (statusmy.length() != 0) {
                switch (statusmy) {
                    case "群众": {
                        status.setSelectedIndex(0);
                    }
                    break;
                    case "共青团员": {
                        status.setSelectedIndex(1);
                    }
                    break;
                    case "中共党员": {
                        status.setSelectedIndex(2);
                    }
                    break;
                    default:
                        break;
                }
            }
        }
        add(status);
        add(backgroundImageLabel);//这行代码出现在按钮之前，TextField之后


        //修改按钮
        final JButton modifybutton = new JButton("修改");
        modifybutton.setFont(new Font("新宋体", Font.PLAIN, 20));
        modifybutton.setBounds(404, 579, 262 - 166, 608 - 579);
        add(modifybutton);
        modifybutton.setOpaque(false);
        //充值按钮
        JButton investbutton = new JButton("充值");
        investbutton.setFont(new Font("宋体", Font.PLAIN, 20));
        investbutton.setBounds(166, 579, 262 - 166, 608 - 579);
        add(investbutton);
        investbutton.setOpaque(false);

        //修改按钮监听事件
        modifybutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //增加一个弹窗来表示
                JFrame tem = new JFrame();
                tem.setLayout(null);
                tem.setVisible(true);
                setVisible(false);
                //绘制背景图片
                JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/MotifyStudent.JPG"));
                Toolkit k = Toolkit.getDefaultToolkit();
                Dimension d = k.getScreenSize();
                tem.setBounds(d.width / 2 - 861 / 2, d.height / 2 - 631 / 2, 861, 631 + 25);
                backgroundImageLabel.setBounds(0, 0, 861, 631);
                tem.setResizable(false);
                tem.setLayout(null);
                tem.setVisible(true);

                //2.绘制退出按钮
                //得到鼠标的坐标（用于推算对话框应该摆放的坐标）
   /*  backgroundImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
			}
        });*/
                JTextField name1 = new JTextField(thisStu.getStudentName());
                JTextField origion1 = new JTextField(thisStu.getStudentorigion());
                JTextField phone1 = new JTextField(thisStu.getStudentphone());
                JComboBox gender1 = new JComboBox();
                gender1.addItem("男");
                gender1.addItem("女");
                if (thisStu.getStudentgender() == false) {
                    gender1.setSelectedIndex(1);
                }


                JComboBox status1 = new JComboBox();
                status1.addItem("群众");
                status1.addItem("共青团员");
                status1.addItem("中共党员");
                if (thisStu.getStudentstatus() != null) {
                    statusmy = thisStu.getStudentstatus().replaceAll("\\p{C}", "");
                    if (statusmy.length() != 0) {
                        switch (statusmy) {
                            case "群众": {
                                status1.setSelectedIndex(0);
                            }
                            break;
                            case "共青团员": {
                                status1.setSelectedIndex(1);
                            }
                            break;
                            case "中共党员": {
                                status1.setSelectedIndex(2);
                            }
                            break;
                            default:
                                break;
                        }
                    }
                }

                Font f = new Font("华文行楷", Font.PLAIN, 24);
                name1.setBounds(278, 135, 612 - 278, 184 - 135);
                origion1.setBounds(278, 290, 612 - 278, 184 - 135);
                phone1.setBounds(278, 444, 612 - 278, 184 - 135);
                status1.setBounds(278, 369, 250, 184 - 135);
                gender1.setBounds(278, 212, 250, 184 - 135);
                name1.setFont(f);
                origion1.setFont(f);
                phone1.setFont(f);
                status1.setFont(f);
                gender1.setFont(f);
                tem.add(origion1);
                tem.add(gender1);
                tem.add(status1);
                tem.add(name1);
                tem.add(phone1);
                origion1.setOpaque(false);
                origion1.setBorder(new EmptyBorder(0, 0, 0, 0));
                name1.setOpaque(false);
                name1.setBorder(new EmptyBorder(0, 0, 0, 0));
                phone1.setOpaque(false);
                phone1.setBorder(new EmptyBorder(0, 0, 0, 0));

                tem.add(backgroundImageLabel);
                JButton OK = new JButton("OK");
                OK.setBounds(250, 532, 110, 583 - 532);
                tem.add(OK);
                OK.setOpaque(false);

                OK.addActionListener(event ->
                {
                    if (name1.getText().trim().equals("") ||
                            origion1.getText().trim().equals("") ||
                            phone1.getText().trim().equals("")) {
                        JOptionPane.showMessageDialog(null, "请完善学生信息！", "提示", JOptionPane.WARNING_MESSAGE);
                    } else {

                        if (phone1.getText().length() != 11) {
                            JOptionPane.showMessageDialog(null, "请正确填写电话号码（11位）！", "提示", JOptionPane.WARNING_MESSAGE);
                        } else {
                            //保存当前的所有信息
                            Student temp = new Student();
                            temp.setClassid(classid.getText());
                            temp.setMajor(major.getText());
                            if (gender1.getSelectedIndex() == 0) {
                                temp.setStudentgender(true);//boy
                            } else {
                                temp.setStudentgender(false);//girl
                            }
                            temp.setStudentid(studentid.getText());
                            temp.setStudentName(name1.getText());
                            temp.setStudentorigion(origion1.getText());
                            temp.setStudentphone(phone1.getText());//remember to check
                            temp.setTeacherid(teacherid.getText());
                            switch (gender1.getSelectedIndex()) {
                                case 0:
                                    temp.setStudentgender(true);
                                    gender.setSelectedIndex(0);
                                    break;
                                case 1:
                                    gender.setSelectedIndex(1);
                                    temp.setStudentgender(false);
                                    break;
                                default:
                                    break;
                            }
                            switch (status1.getSelectedIndex()) {
                                case 0:
                                    temp.setStudentstatus("群众");
                                    status.setSelectedIndex(0);
                                    break;
                                case 1:
                                    status.setSelectedIndex(1);
                                    temp.setStudentstatus("共青团员");
                                    break;
                                case 2:
                                    status.setSelectedIndex(2);
                                    temp.setStudentstatus("中共党员");
                                    break;
                                default:
                                    break;

                            }
                            name.setText(name1.getText());
                            phone.setText(phone1.getText());
                            origion.setText(origion1.getText());
                            save(temp);
                            setVisible(true);
                            tem.dispose();
                        }
                    }

                });
                JButton c = new JButton("取消");
                c.setBounds(451, 531, 110, 583 - 532);
                tem.add(c);
                c.setOpaque(false);
                c.addActionListener(event ->
                {
                    setVisible(true);
                    tem.dispose();
                });
            }

        });

        //充值按钮监听事件
        investbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setinvestframe();  //单独一个框架
            }
        });


        //退出按钮
        JButton exitbutton = new JButton("退出");
        exitbutton.setBounds(655, 575, 262 - 166, 608 - 579);
        add(exitbutton);
        exitbutton.setOpaque(false);

        //退出按钮监听事件
        exitbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (modifybutton.getText().compareTo("修改") == 0) {
                    close();
                } else {    //不保存直接退出
                    //exit without saving
                    int input = JOptionPane.showConfirmDialog(null, "确认放弃该操作吗？", "提示", JOptionPane.YES_NO_OPTION);
                    if (input == 0) {
                        modifybutton.setText("修改");
                        name.setEditable(false);
                        studentid.setEditable(false);
                        major.setEditable(false);
                        classid.setEditable(false);
                        teacherid.setEditable(false);
                        origion.setEditable(false);
                        phone.setEditable(false);
                        status.setEnabled(false);
                        gender.setEnabled(false);
                    }
                }
            }
        });
        exitbutton.setFont(new Font("新宋体", Font.PLAIN, 20));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
    }

    //保存修改之后的信息
    void save(Student temp) {
        Message mes = new Message();
        mes.setModuleType(ModuleType.Student);
        mes.setMessageType(MessageType.ClassAdminUpdate);
        List<Object> sendData = new ArrayList();
        sendData.add(15);//name, origin, phone, status, gender ---- id
        sendData.add(temp.getStudentName());
        sendData.add(temp.getStudentorigion());
        sendData.add(temp.getStudentphone());
        sendData.add(temp.getStudentstatus());
        sendData.add(temp.getStudentgender());
        sendData.add(temp.getStudentid());
        mes.setData(sendData);

        Client client = new Client(ClientMainFrame.socket);

        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);
        int res = (int) serverResponse.getData();

        this.Name = temp.getStudentName();
    }

    public void setinvestframe() {
        this.setEnabled(false);
        this.setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
        ClassStudentForInvest frame = new ClassStudentForInvest(this, Money, ID, PWD);
        frame.setVisible(true);
    }

    //更新
    public void update(Double temp) {
        DecimalFormat df = new DecimalFormat("0.00");
        credit.setText("" + df.format(temp));
        Money = temp;
    }

    //关闭
    void close() {
        this.dispose();
        Mainmenu.set(Name, Money);
    }
}
