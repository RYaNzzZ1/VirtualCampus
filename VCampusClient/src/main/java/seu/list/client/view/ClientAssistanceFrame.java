/**
 * 
 * @version jdk1.8.0
 */
package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;
import seu.list.common.User;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

public class ClientAssistanceFrame extends JDialog {

    private final JPanel contentPanel =new JPanel();
    private JPanel buttonPane;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel maintainLabel;

    public JTextField admintextField;
    public JPasswordField passwordField;
    public ClientRegisterFrame crf;
    //private boolean flag=false,finished=false;
    //public boolean getflag(){return flag;}



    private Socket socket;


    public ClientAssistanceFrame(ClientRegisterFrame crf, Socket socket) {
        this.crf=crf;
        this.socket=socket;
        setVisible(true);
        setTitle("辅助验证");
        setBounds(100, 100, 450, 300);
        //添加图标
        Image image=new ImageIcon("VCampusClient/VCampusClient/src/main/resources/image/xiaobiao.jpg").getImage();
        setIconImage(image);

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        {
            buttonPane = new JPanel();
            {
                okButton = new JButton("确定");
                okButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                okButton.setActionCommand("OK");
                getRootPane().setDefaultButton(okButton);

                okButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        MaintainAct(e);
                        //setVisible(false);
                    }

                });
            }
            {
                cancelButton = new JButton("取消");
                cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                cancelButton.setActionCommand("Cancel");

                cancelButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        setVisible(false);
                    }
                });
            }
        }
        GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
        gl_buttonPane.setHorizontalGroup(
                gl_buttonPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_buttonPane.createSequentialGroup()
                                .addGap(90)
                                .addComponent(okButton, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                .addGap(75)
                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                .addGap(89))
        );
        gl_buttonPane.setVerticalGroup(
                gl_buttonPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_buttonPane.createSequentialGroup()
                                .addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(okButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(47, Short.MAX_VALUE))
        );
        buttonPane.setLayout(gl_buttonPane);
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(contentPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                                        .addComponent(buttonPane, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
                                .addGap(10))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(buttonPane, GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
        );
        {
            maintainLabel = new JLabel("管理员辅助验证");
            maintainLabel.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        }
        JLabel nameLabel = new JLabel("管理员账号：");
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        nameLabel.setBackground(new Color(240, 240, 240));
        JLabel pwdLabel = new JLabel("密码：");
        pwdLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));


        admintextField = new JTextField();
        admintextField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        admintextField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        passwordField.setColumns(10);

        GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
        gl_contentPanel.setHorizontalGroup(
                gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup()
                                .addGap(72)
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(pwdLabel)
                                        .addComponent(nameLabel))
                                .addGap(12)
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
                                        .addComponent(passwordField, Alignment.LEADING)
                                        .addComponent(admintextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                        .addComponent(maintainLabel, Alignment.LEADING))
                                        .addContainerGap(111, Short.MAX_VALUE))
        );
        gl_contentPanel.setVerticalGroup(
                gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(maintainLabel)
                                .addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(nameLabel)
                                        .addComponent(admintextField, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPanel.createSequentialGroup()
                                                .addGap(20)
                                                .addComponent(pwdLabel))
                                        .addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
                                                .addGap(18)
                                                .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
                                .addGap(18))
        );
        contentPanel.setLayout(gl_contentPanel);
        getContentPane().setLayout(groupLayout);

        //居中显示
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
    }
    /**
     * 辅助验证
     * @param e
     */
    protected void MaintainAct(ActionEvent e) {
        // TODO Auto-generated method stub

        Client ccs = new Client(this.socket);
        User u=new User();
        u.setId(admintextField.getText());
        u.setPwd(passwordField.getText());
        Message mes=new Message();
        mes.setContent(u.getContent());
        mes.setModuleType(ModuleType.User);
        mes.setMessageType(MessageType.REQ_LOGIN);
        Message res=ccs.sendRequestToServer(mes);
        if(res.getUserType()==1) {
            JOptionPane.showMessageDialog(null, "辅助验证成功", "提示", JOptionPane.WARNING_MESSAGE);
            crf.asstate=true;
            this.dispose();
            mes.setMessageType(MessageType.REQ_LOGOUT);
            res=ccs.sendRequestToServer(mes);
        }
        else{
            JOptionPane.showMessageDialog(null, "辅助验证失败，无法注册为管理员", "错误", JOptionPane.ERROR_MESSAGE);
            crf.asstate=false;
        }
    }

}
