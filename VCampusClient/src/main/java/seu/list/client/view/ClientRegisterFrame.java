package seu.list.client.view;


import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ClientRegisterFrame extends JFrame implements ActionListener {
    JComboBox comboBox = new JComboBox();
    JComboBox cobSex = new JComboBox();
    String role;
    boolean asstate = false;
    private Socket socket;
    private JTextField jtf_id;
    private JTextField jtf_name;
    private JPasswordField jtf_pwd;
    private JTextField jtf_sex;
    private JTextField jtf_age;
    private JTextField jtf_grade;
    private JTextField jtf_money;
    private JTextField jtf_major;

    /**
     * create the frame
     *
     * @param socket 保证与服务端的通信
     */

    public ClientRegisterFrame(Socket socket) {
        this.socket = socket;
        //设置背景图片：


        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/ClientRegisterFrame.png"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        setBounds(d.width / 2 - 424, d.height / 2 - 320, 848, 640);
        backgroundImageLabel.setBounds(0, 0, 848, 640);
        setSize(848, 660);
        setLayout(null);
        backgroundImageLabel.setOpaque(false);

        this.setTitle("新用户注册");
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        //名字文本框
        jtf_name = new JTextField();
        jtf_name.setFont(new Font("华文行楷", Font.PLAIN + Font.BOLD, 24));
        jtf_name.setBounds(167, 219, 238, 41);
        add(jtf_name);
        jtf_name.setOpaque(false);
        jtf_name.setBorder(new EmptyBorder(0, 0, 0, 0));

        //密码文本框
        jtf_pwd = new JPasswordField();
        jtf_pwd.setBounds(504, 304, 238, 41);
        jtf_pwd.setFont(new Font("", Font.BOLD, 24));
        jtf_pwd.setOpaque(false);
        add(jtf_pwd);
        jtf_pwd.setBorder(new EmptyBorder(0, 0, 0, 0));

        //性别 --下拉框实现
        jtf_sex = new JTextField();
        jtf_sex.setBounds(123, 270, 101, 27);

        cobSex.setForeground(Color.BLACK);
        cobSex.setModel(new DefaultComboBoxModel(new String[]{"男", "女"}));
        cobSex.setFont(new Font("华文行楷", Font.PLAIN, 24));
        cobSex.setBounds(171, 393, 133, 41);
        cobSex.setOpaque(false);
        add(cobSex);


        //年龄
        jtf_age = new JTextField();
        jtf_age.setBounds(168, 305, 238, 41);
        jtf_age.setFont(new Font("华文行楷", Font.PLAIN + Font.BOLD, 24));
        add(jtf_age);
        jtf_age.setOpaque(false);
        jtf_age.setBorder(new EmptyBorder(0, 0, 0, 0));


        //年级
        jtf_grade = new JTextField();
        jtf_grade.setBounds(505, 219, 238, 41);
        jtf_grade.setFont(new Font("华文行楷", Font.PLAIN + Font.BOLD, 24));
        add(jtf_grade);
        jtf_grade.setOpaque(false);
        jtf_grade.setBorder(new EmptyBorder(0, 0, 0, 0));

        //金钱
        jtf_money = new JTextField();


        //ID
        jtf_id = new JTextField();
        jtf_id.setFont(new Font("华文行楷", Font.PLAIN + Font.BOLD, 24));
        jtf_id.setBounds(169, 136, 238, 41);
        add(jtf_id);
        jtf_id.setOpaque(false);
        jtf_id.setBorder(new EmptyBorder(0, 0, 0, 0));


        //身份选择
        comboBox.setForeground(Color.BLACK);
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"学生", "管理员"}));
        comboBox.setFont(new Font("华文行楷", Font.PLAIN, 24));
        comboBox.setBounds(504, 393, 133, 41);
        comboBox.setOpaque(false);
        add(comboBox);
        comboBox.addActionListener(e -> {
            if (comboBox.getSelectedItem() == "管理员" && asstate == false) {
                ClientAssistanceFrame caf = new ClientAssistanceFrame(this, socket);
            }
        });

        //2.学院
        jtf_major = new JTextField();
        jtf_major.setBounds(503, 134, 238, 41);
        jtf_major.setFont(new Font("华文行楷", Font.PLAIN + Font.BOLD, 24));
        add(jtf_major);
        jtf_major.setOpaque(false);
        jtf_major.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(backgroundImageLabel);
        //确定按钮
        JButton register = new JButton("确定");
        register.setBounds(255, 495, 100, 43);
        register.addActionListener(this);
        register.setActionCommand("register");
        register.setOpaque(false);
        add(register);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);

        //退出按钮
        JButton exit = new JButton("退出");
        exit.setBounds(509, 494, 100, 43);
        exit.setOpaque(false);
        add(exit);
        exit.addActionListener(event ->
        {
            this.dispose();
        });
    }


    /**
     * 事件响应
     *
     * @param e 事件
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // TODO Auto-generated method stub
        //身份处理
        if (comboBox.getSelectedItem() == "学生") {
            role = "0";
        }
        if (comboBox.getSelectedItem() == "管理员") {
            role = "1";
        }

        if (comboBox.getSelectedItem() == "男")
            jtf_sex.setText("男");
        else
            jtf_sex.setText("女");


        //to check whether the input is right
        int i = 0;
        Boolean flag = true;
        Boolean flagall = true;

        System.out.println(jtf_id.getText() == null);
        if (jtf_id.getText().trim().equals("")
                || jtf_age.getText().trim().equals("")
                || jtf_grade.getText().trim().equals("")
                || jtf_major.getText().trim().equals("")
                || jtf_name.getText().trim().equals("")
                || jtf_pwd.getText().trim().equals("")
                || jtf_sex.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "请完成基本信息填写！", "提示", JOptionPane.WARNING_MESSAGE);
            System.out.println("11111111");
        } else {  //所有表单均填写完整
            i = 0;
		/*	while(i < jtf_money.getText().length()) {

				if(jtf_money.getText().charAt(i) > '9' || jtf_money.getText().charAt(i) < '0') {
					if(jtf_money.getText().charAt(i) == '.'&&i != 0) {
						//empty
					}else if(jtf_money.getText().charAt(i) == '.'&&i == 0) {
						JOptionPane.showMessageDialog(null, "请正确填写金额(非小数点开头)！", "提示", JOptionPane.WARNING_MESSAGE);
						flagall = false;
						break;
					}else {
						JOptionPane.showMessageDialog(null, "请正确填写金额(数值)！", "提示", JOptionPane.WARNING_MESSAGE);
						flagall = false;
						break;
					}
				}
				if(jtf_money.getText().charAt(i) == '0' && i == 0) {
					JOptionPane.showMessageDialog(null, "请正确填写金额(非零开头)！", "提示", JOptionPane.WARNING_MESSAGE);
					flagall = false;
					break;
				}
				i++;

			}*/
            if (!jtf_sex.getText().equals("男") && !jtf_sex.getText().equals("女")) {
                JOptionPane.showMessageDialog(null, "请正确输入性别！", "提示", JOptionPane.WARNING_MESSAGE);
                flagall = false;
            }
            if (!jtf_pwd.getText().matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$")) {
                JOptionPane.showMessageDialog(null, "密码为6-16位，且必须包含数字和字母", "提示", JOptionPane.WARNING_MESSAGE);
                flagall = false;
            }
            if (flagall) {
                Student temp = new Student();
                if (role.equals("0")) {//check the student
                    flag = false;
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
                    String targetid = jtf_id.getText();
                    targetid.replaceAll("\\p{C}", "");
                    int studenttemp = 0;
                    while (studenttemp < StuAll.size()) {
                        String tempid = StuAll.get(studenttemp).getStudentid();
                        tempid.replaceAll("\\p{C}", "");
                        if (tempid.equals(targetid)) {
                            flag = true;
                            temp = StuAll.get(studenttemp);
                            break;
                        }
                        studenttemp++;
                    }
                }


                if (flag) {
                    if (temp.getMajor() != null && !jtf_major.getText().replaceAll("\\p{C}", "").equals(temp.getMajor())) {
                        JOptionPane.showMessageDialog(null, "请正确输入您的所属专业", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (role == "1" && asstate == false) {
                            JOptionPane.showMessageDialog(null, "辅助验证失败，无法注册为管理员", "错误", JOptionPane.ERROR_MESSAGE);
                            comboBox.setSelectedItem("学生");
                        } else {
                            User user = new User();
                            user.setId(jtf_id.getText());
                            user.setAge(jtf_age.getText());
                            user.setGrade(jtf_grade.getText());
                            user.setMajor(jtf_major.getText());
                            user.setMoney(jtf_money.getText());
                            user.setName(jtf_name.getText());
                            user.setPwd(jtf_pwd.getText());
                            user.setSex(jtf_sex.getText());
                            user.setRole(role);
                            System.out.println(user);
                            Message clientreq = new Message();
                            clientreq.setModuleType(ModuleType.User);
                            clientreq.setMessageType(MessageType.REQ_REGISTER);
                            clientreq.setContent(user.getContent());
                            Client client = new Client(this.socket);
                            Message rec = client.sendRequestToServer(clientreq);
                            int sign = rec.getUserType();

                            if (e.getActionCommand().equals("register")) {
                                //System.out.print(ccs.sign);
                                //返回的权限

                                if (sign == 3) {
                                    JOptionPane.showMessageDialog(null, "您已经注册，请不要重复", "错误", JOptionPane.WARNING_MESSAGE);
                                } else if (sign == 0 || sign == 1) {
                                    JOptionPane.showMessageDialog(null, "注册成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                                    this.dispose();
                                    //update student message
                                    if (role.equals("0")) {
                                        if (jtf_sex.getText().equals("男")) {
                                            temp.setStudentgender(true);
                                        } else if (jtf_sex.getText().equals("女")) {
                                            temp.setStudentgender(false);
                                        } else {
                                            //empty body
                                        }
                                        //temp.setStudentcredit(Double.parseDouble(jtf_money.getText()));
                                        temp.setStudentcredit(Integer.parseInt(jtf_money.getText()));
                                        temp.setStudentName(jtf_name.getText());

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
                                        client = null;
                                        client = new Client(ClientMainFrame.socket);

                                        Message serverResponse = new Message();
                                        serverResponse = client.sendRequestToServer(mes);
                                        int res = (int) serverResponse.getData();
                                    }
                                    this.dispose();
                                    ClientLoginFrame ctf = new ClientLoginFrame(this.socket);
                                } else {
                                    JOptionPane.showMessageDialog(null, "注册失败", "错误", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                } else {
                    System.out.println(jtf_id.getText().trim().equals(""));
                    JOptionPane.showMessageDialog(null, "注册失败,请先在管理员处登记", "错误", JOptionPane.ERROR_MESSAGE);
                }

                asstate = false;
            }
        }
    }


}
