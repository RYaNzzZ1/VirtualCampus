package seu.list.client.view;

import seu.list.client.bz.Client;
import seu.list.client.bz.ClientMainFrame;
import seu.list.common.Dormitory;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;

public class DormApply extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JButton commitButton;
    private JButton divestButton;
    private JButton cancelButton;

    private JPanel buttonPane;
    private JTextField DeuserIDField;

    DormitoryAdminClient C;
    public ArrayList<Dormitory> allDormitoryContents;

    public DormApply(DormitoryAdminClient c, Socket socket) {
        C = c;
        setVisible(true);
        setTitle("申请管理");
        setBounds(100, 100, 391, 283);

        applyShow();

        //添加图标
        Image image = new ImageIcon("VCampusClient/src/main/resources/image/xiaobiao.jpg").getImage();
        setIconImage(image);

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel lblNewLabel = new JLabel("申请管理");
        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        JLabel lblNewLabel_1 = new JLabel("学号：");
        lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        DeuserIDField = new JTextField();
        DeuserIDField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        DeuserIDField.setColumns(10);
        GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
        gl_contentPanel.setHorizontalGroup(
                gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup()
                                .addGap(76)
                                .addComponent(lblNewLabel_1)
                                .addGap(18)
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel)
                                        .addComponent(DeuserIDField, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(95, Short.MAX_VALUE))
        );
        gl_contentPanel.setVerticalGroup(
                gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup()
                                .addGap(23)
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lblNewLabel_1)
                                        .addGroup(gl_contentPanel.createSequentialGroup()
                                                .addComponent(lblNewLabel)
                                                .addGap(46)
                                                .addComponent(DeuserIDField, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(66, Short.MAX_VALUE))
        );
        contentPanel.setLayout(gl_contentPanel);
        {
            buttonPane = new JPanel();
            {
                commitButton = new JButton("提交");
                commitButton.setActionCommand("OK");
                getRootPane().setDefaultButton(commitButton);
                commitButton.addActionListener(e -> {
                    // TODO Auto-generated method stub
                    applyCommitAct(e);
                    setVisible(false);
                });
            }
            {
                divestButton = new JButton("否决");
                divestButton.setActionCommand("OK");
                divestButton.addActionListener(e -> {
                    applyDivestAct(e);
                    // TODO Auto-generated method stub
                    setVisible(false);
                });
            }
            {
                cancelButton = new JButton("取消");
                cancelButton.setActionCommand("Cancel");
                cancelButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        setVisible(false);
                    }

                });
            }
            GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
            gl_buttonPane.setHorizontalGroup(
                    gl_buttonPane.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_buttonPane.createSequentialGroup()
                                    .addGap(66)
                                    .addComponent(commitButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                    .addGap(71)
                                    .addComponent(divestButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                    .addGap(71)
                                    .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                    .addGap(126))
            );
            gl_buttonPane.setVerticalGroup(
                    gl_buttonPane.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_buttonPane.createSequentialGroup()
                                    .addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(commitButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(divestButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(15, Short.MAX_VALUE))
            );
            buttonPane.setLayout(gl_buttonPane);
        }
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(contentPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                                        .addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 375, Short.MAX_VALUE))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(buttonPane, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                .addGap(12))
        );
        getContentPane().setLayout(groupLayout);

        //居中显示
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
    }

    private static void applyShow() {
        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormApplyShow);
        Client client = new Client(ClientMainFrame.socket);
        Message serverResponse = client.sendRequestToServer(mes);
        ArrayList<Dormitory> dormitoriesWithApply = (ArrayList<Dormitory>) serverResponse.getData();
        //展示
    }

    private void applyDivestAct(ActionEvent e) {
        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormDivestApply);

        Client client = new Client(ClientMainFrame.socket);
        System.out.println("adsdhgifho");
        Message serverResponse = client.sendRequestToServer(mes);

        serverResponse = client.sendRequestToServer(mes);

        C.setEnabled(true);

        this.dispose();
        JOptionPane.showMessageDialog(null, "完成", "提示", JOptionPane.WARNING_MESSAGE);
    }

    protected void applyCommitAct(ActionEvent e) {
        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormCommitApply);

        Client client = new Client(ClientMainFrame.socket);
        Message serverResponse = client.sendRequestToServer(mes);

        serverResponse = client.sendRequestToServer(mes);

        C.setEnabled(true);

        this.dispose();
        JOptionPane.showMessageDialog(null, "完成", "提示", JOptionPane.WARNING_MESSAGE);
    }


}


