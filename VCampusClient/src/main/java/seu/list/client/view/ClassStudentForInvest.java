package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClassStudentForInvest extends JFrame {
    private JTextField money;
    private ClassStudentClient CSC;
    private Double Credit = 0.0;
    private JPasswordField pwd;

    public ClassStudentForInvest(ClassStudentClient csc, final Double credit, final String id, final String PWD) {
        CSC = csc;
        //1.设置背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/ClassStudentForInvest.png"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        setBounds(d.width / 2 - 843 / 2, d.height / 2 - 553 / 2, 843, 585);
        backgroundImageLabel.setBounds(0, 0, 843, 553);
        setResizable(false);
        setLayout(null);

        //money输入框
        money = new JTextField();
        money.setBounds(268, 135, 646 - 268, 189 - 135 + 6);
        money.setFont(new Font("华文行楷", Font.BOLD, 36));
        add(money);
        money.setOpaque(false);
        money.setBorder(new EmptyBorder(0, 0, 0, 0));
        //密码输入框
        pwd = new JPasswordField();
        pwd.setBounds(270, 261, 646 - 268, 189 - 135 + 6);
        pwd.setFont(new Font("", Font.BOLD, 36));
        add(pwd);
        pwd.setOpaque(false);
        pwd.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(backgroundImageLabel);
        setVisible(true);
        //确认按钮
        JButton commitbtn = new JButton("确认");
        commitbtn.setBounds(244, 448, 333 - 244, 484 - 448 + 7);
        add(commitbtn);
        commitbtn.setOpaque(false);
        //事件监听
        commitbtn.addActionListener(e -> {
            if (money.getText() == null) {
                JOptionPane.showMessageDialog(null, "请填写充值金额！", "提示", JOptionPane.WARNING_MESSAGE);
            } else {
                int i = 0;
                boolean flag = true;
                while (i < money.getText().length()) {
                    if (money.getText().charAt(i) == '.' && i != 0) {
                        //empty
                        i++;
                    } else if (money.getText().charAt(i) == '.' && i == 0) {
                        JOptionPane.showMessageDialog(null, "请正确填写充值金额(非小数点开头)！", "提示", JOptionPane.WARNING_MESSAGE);
                        flag = false;
                        break;
                    } else if (money.getText().charAt(i) == '0' && i == 0 && 1 < money.getText().length() && money.getText().charAt(1) != '.') {
                        JOptionPane.showMessageDialog(null, "请正确填写充值金额(非零开头)！", "提示", JOptionPane.WARNING_MESSAGE);
                        flag = false;
                        break;
                    } else if (money.getText().charAt(i) > '9' || money.getText().charAt(i) < '0') {
                        JOptionPane.showMessageDialog(null, "请正确填写充值金额(数值)！", "提示", JOptionPane.WARNING_MESSAGE);
                        flag = false;
                        break;
                    } else {
                        i++;
                    }
                }
                if (flag) {
                    String in = String.valueOf(pwd.getPassword());
                    in.replaceAll("\\p{C}", "");
                    PWD.replaceAll("\\p{C}", "");

                    if (in.equals(PWD)) {
                        JOptionPane.showMessageDialog(null, "充值完成！", "提示", JOptionPane.WARNING_MESSAGE);
                        Credit = credit + Double.parseDouble(money.getText());

                        Message mes = new Message();
                        mes.setModuleType(ModuleType.Student);
                        mes.setMessageType(MessageType.ClassAdminUpdate);
                        List<Object> sendData = new ArrayList<>();
                        sendData.add(8);
                        sendData.add(Credit);
                        sendData.add(id);
                        mes.setData(sendData);
                        System.out.println("充值时id：" + id);

                        Client client = new Client(ClientMainFrame.socket);

                        Message serverResponse = client.sendRequestToServer(mes);
                        int res = (int) serverResponse.getData();

                        System.out.println(Credit);
                        close();
                    } else {
                        JOptionPane.showMessageDialog(null, "密码错误！", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //退出按钮
        JButton exitbtn = new JButton("退出");
        exitbtn.setBounds(508, 446, 333 - 244, 484 - 448 + 7);
        exitbtn.setOpaque(false);
        add(exitbtn);
        exitbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });


    }

    void close() {
        CSC.update(Credit);
        CSC.setEnabled(true);
        this.dispose();
    }

    void close2() {
        CSC.setEnabled(true);
        this.dispose();

    }

}
