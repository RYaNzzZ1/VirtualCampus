/**
 * @author 周楚翘
 * @version jdk1.8.0
 */
package seu.list.client.view;

import seu.list.client.bz.Client;
import seu.list.client.bz.ClientMainFrame;
import seu.list.common.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Dormdelete extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JButton okButton;
    private JButton cancelButton;
    private JPanel buttonPane;
    private JTextField DeuserIDField;
    static Socket socket;
    DormitoryAdminClient C;
    public ArrayList<Dormitory> allDormitoryContents;

    /**
     * Create the dialog.
     */
    public Dormdelete(DormitoryAdminClient c,Socket socket) {
        C = c;
        setVisible(true);
        setTitle("删除宿舍");
        setLayout(null);


        // 创建带有背景图片的JLabel
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Dormdelete.PNG"));
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        backgroundImageLabel.setBounds(0, 0, 874, 519);
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 874 / 2, d.height / 2 - 519 / 2, 874, 519);
        backgroundImageLabel.setOpaque(false); // 设置背景透明
        setVisible(true);

        setResizable(false); //阻止用户拖拽改变窗口的大小

        //2.绘制退出按钮
        //得到鼠标的坐标（用于推算对话框应该摆放的坐标）
//		backgroundImageLabel.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int x = e.getX();
//				int y = e.getY();
//				System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
//			}
//		});

        //设置输入框
        DeuserIDField = new JTextField();
        DeuserIDField.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        DeuserIDField.setColumns(20);
        DeuserIDField.setBounds(278,231,365,44);
        add(DeuserIDField);
        DeuserIDField.setOpaque(false);
        DeuserIDField.setBorder(new EmptyBorder(0,0,0,0));

        add(backgroundImageLabel);

        //设置按钮
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        {
            buttonPane = new JPanel();
            {
                okButton = new JButton("确定");
                okButton.setActionCommand("OK");
                getRootPane().setDefaultButton(okButton);
                okButton.setBounds(266,354,102,56);
                add(okButton);
                okButton.setOpaque(false);
                okButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        DeleteAct(e);
                        setVisible(false);
                    }

                });
            }
            {
                cancelButton = new JButton("取消");
                cancelButton.setActionCommand("Cancel");
                cancelButton.setBounds(540,352,102,56);
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
     * 完成删除宿舍
     * @param
     */
    protected void DeleteAct(ActionEvent e) {
        // TODO Auto-generated method stub
        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormDelete);

        Client client = new Client(ClientMainFrame.socket);
        System.out.println(DeuserIDField.getText());
        mes.setData(DeuserIDField.getText());
        Message serverResponse=new Message();

        serverResponse=client.sendRequestToServer(mes);

        allDormitoryContents = (ArrayList<Dormitory>) serverResponse.getData();
        System.out.println(allDormitoryContents);
        C.setEnabled(true);
        C.updateFrameD(DeuserIDField.getText().toString());
        this.dispose();
        JOptionPane.showMessageDialog(null,"完成","提示",JOptionPane.WARNING_MESSAGE);
    }

}

