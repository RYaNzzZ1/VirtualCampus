package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;
import seu.list.common.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Shop_StudentForPay extends JFrame {
    private Shop_StudentFrame SSF = null;
    private Double Credit = 0.0;
    private JPasswordField pwd;
    private Student thisStu = null;
    private JFrame shop = null;


    /**
     * Create the frame.
     */
    public Shop_StudentForPay(final Shop_StudentFrame ssf, JFrame oldframe, final Double sum, final String id, final String PWD) {
        SSF = ssf;
        shop = oldframe;
        setTitle("结账");
        setLayout(null);

        //设置背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Shop_StudentForPay.png"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        setBounds(d.width / 2 - 847 / 2, d.height / 2 - 558 / 2, 847, 580);
        backgroundImageLabel.setBounds(0, 0, 847, 558);


        //显示金额的标签
        JLabel money = new JLabel("New label", JLabel.CENTER);
        money.setText(sum + "");
        money.setFont(new Font("华文行楷", Font.BOLD, 30));
        money.setBounds(612, 208, 718 - 612, 233 - 208);
        add(money);
        //密码输入框
        pwd = new JPasswordField();
        pwd.setBounds(400, 314, 758 - 400 + 2, 353 - 314 + 3);
        pwd.setFont(new Font("", Font.BOLD, 24));

        pwd.setOpaque(false);
        pwd.setBorder(new EmptyBorder(0, 0, 0, 0));
        add(pwd);


        add(backgroundImageLabel);
        //下面代码不用管
        Vector<Student> StuAll;
        Message mes = new Message();
        mes.setModuleType(ModuleType.Student);
        mes.setMessageType(MessageType.ClassAdminGetAll);
        List<Object> sendData = new ArrayList<Object>();
        mes.setData(sendData);

        Client client = new Client(ClientMainFrame.socket);

        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);
        StuAll = (Vector<Student>) serverResponse.getData();

        for (int i = 0; i < StuAll.size(); i++) {
            System.out.println("StuAll" + i + ":" + StuAll.get(i).toString());
        }

        thisStu = new Student();

        int studentTemp = 0;
        while (studentTemp < StuAll.size()) {
            String tempid = StuAll.get(studentTemp).getId();
            id.replaceAll("\\p{C}", "");
            tempid.replaceAll("\\p{C}", "");
            if (tempid.equals(id)) {
                System.out.println("stuTemp:" + studentTemp);
                thisStu = StuAll.get(studentTemp);
                break;
            }
            studentTemp++;
        }


        //确认按钮
        JButton commitbtn = new JButton("确认");
        commitbtn.setBounds(241, 448, 331 - 214 - 10 - 10, 485 - 448 + 7);
        add(commitbtn);
        commitbtn.setOpaque(false);
        commitbtn.addActionListener(e -> {
            String in = String.valueOf(pwd.getPassword());
            in.replaceAll("\\p{C}", "");
            PWD.replaceAll("\\p{C}", "");
            System.out.println("sum:" + sum);
            System.out.println("thisStu:" + thisStu.getStudentcredit());
            if (in.equals(PWD)) {
                Credit = thisStu.getStudentcredit() - sum;
                System.out.println("Credit:" + Credit);
                if (Credit.compareTo(0.0) < 0) {
                    JOptionPane.showMessageDialog(null, "余额不足，无法进行支付！", "提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "结账完成！", "提示", JOptionPane.WARNING_MESSAGE);

                    Message mes1 = new Message();
                    mes1.setModuleType(ModuleType.Student);
                    mes1.setMessageType(MessageType.ClassAdminUpdate);
                    List<Object> sendData1 = new ArrayList<Object>();
                    sendData1.add(8);
                    sendData1.add(Credit);
                    sendData1.add(id);
                    mes1.setData(sendData1);

                    Client client1 = new Client(ClientMainFrame.socket);

                    Message serverResponse1 = new Message();
                    serverResponse1 = client1.sendRequestToServer(mes1);
                    int res = (int) serverResponse1.getData();

                    System.out.println("Pay Success");
                    ssf.buy();
                    ssf.show();
                    close();
                }
            } else {
                JOptionPane.showMessageDialog(null, "密码错误！", "提示", JOptionPane.WARNING_MESSAGE);
            }

        });


        //退出按钮
        JButton exitbtn = new JButton("退出");
        exitbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        exitbtn.setOpaque(false);
        exitbtn.setBounds(507, 446, 331 - 214 - 20, 485 - 448 + 5);
        add(exitbtn);


        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    void close() {
        shop.setEnabled(true);
        this.dispose();
    }
}
