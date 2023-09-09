
package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;
import seu.list.common.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ClientAssistanceFrame extends JDialog {

    private final JPanel contentPanel;
    public JTextField admintextField;
    public JPasswordField passwordField;
    public ClientRegisterFrame crf;
    private JPanel buttonPane;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel maintainLabel;
    //private boolean flag=false,finished=false;
    //public boolean getflag(){return flag;}
    private Socket socket;


    public ClientAssistanceFrame(ClientRegisterFrame crf, Socket socket) {
        this.crf = crf;
        this.socket = socket;

        setTitle("辅助验证");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //设置图片背景
        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage backgroundImage = ImageIO.read(new File("VCampusClient/Image/ClientAssistanceFrame.png"));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        contentPanel.setLayout(null);

        //设置文本框按钮位置和样式
        admintextField = new JTextField();
        admintextField.setOpaque(false);
        admintextField.setBounds(156, 97, 190, 24);
        admintextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        Font f = new Font("华文行楷", Font.BOLD, 22);
        admintextField.setFont(f);

        passwordField = new JPasswordField();
        passwordField.setOpaque(false);
        passwordField.setBounds(156, 137, 190, 24);
        passwordField.setBorder(new EmptyBorder(0, 0, 0, 0));
        Font f1 = new Font("", Font.BOLD, 22);
        passwordField.setFont(f1);

        okButton = new JButton();
        okButton.setOpaque(false);
        okButton.setContentAreaFilled(false); // 设置按钮内容区域为透明
        okButton.setBorderPainted(false); // 不绘制按钮的边框
        okButton.setBounds(114, 196, 53, 32);

        cancelButton = new JButton();
        cancelButton.setOpaque(false);
        cancelButton.setContentAreaFilled(false); // 设置按钮内容区域为透明
        cancelButton.setBorderPainted(false); // 不绘制按钮的边框
        cancelButton.setBounds(252, 196, 53, 32);

        //添加监听
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                MaintainAct(e);
                //setVisible(false);
            }

        });

        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                setVisible(false);
            }
        });

        //添加至contentPanel
        contentPanel.add(admintextField);
        contentPanel.add(passwordField);
        contentPanel.add(okButton);
        contentPanel.add(cancelButton);

        add(contentPanel);


        //居中显示
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(2);

        setVisible(true);
        setResizable(false);

    }
    /**
     * 辅助验证
     * @param e
     */
    protected void MaintainAct (ActionEvent e){
        // TODO Auto-generated method stub
        //添加判断
        String adminText = admintextField.getText();
        String password = new String(passwordField.getPassword());
        if (adminText.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "账号或密码不能为空", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Client ccs = new Client(this.socket);
        User u = new User();
        u.setId(admintextField.getText());
        u.setPwd(passwordField.getText());
        Message mes = new Message();
        mes.setContent(u.getContent());
        mes.setModuleType(ModuleType.User);
        mes.setMessageType(MessageType.REQ_LOGIN);
        Message res = ccs.sendRequestToServer(mes);
        if (res.getUserType() == 1) {
            JOptionPane.showMessageDialog(null, "辅助验证成功", "提示", JOptionPane.WARNING_MESSAGE);
            crf.asstate = true;
            this.dispose();
            mes.setMessageType(MessageType.REQ_LOGOUT);
            res = ccs.sendRequestToServer(mes);
        } else {
            JOptionPane.showMessageDialog(null, "辅助验证失败，无法注册为管理员", "错误", JOptionPane.ERROR_MESSAGE);
            crf.asstate = false;
        }

    }
}
