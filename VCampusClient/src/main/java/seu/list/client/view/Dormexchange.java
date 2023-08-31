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

public class Dormexchange extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JButton okButton;
    private JButton cancelButton;
    private JPanel buttonPane;
    private JTextField nametextField;
    private JTextField dormIDtextField;
    private JTextField exchangetextField_1;
    static Socket socket;
    public DormitoryStudentClient C;
    public Dormitory dorm;

    /**
     * Launch the application.
     */
	/*
	public static void main(String[] args) {
		try {
			Dormexchange dialog = new Dormexchange(socket);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
    /**
     * Create the dialog.
     */
    public Dormexchange(DormitoryStudentClient c,Socket socket) {
        C = c;
        setVisible(true);
        setTitle("宿舍调换");
        setLayout(null);

        // 创建带有背景图片的JLabel
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Dormexchange.PNG"));
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        backgroundImageLabel.setBounds(0, 0, 678, 605);
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 678 / 2, d.height / 2 - 605 / 2, 678, 605);
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
        nametextField = new JTextField();
        nametextField.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        nametextField.setColumns(20);
        nametextField.setBounds(202,163,377,45);
        add(nametextField);
        nametextField.setOpaque(false);
        nametextField.setBorder(new EmptyBorder(0,0,0,0));

        dormIDtextField = new JTextField();
        dormIDtextField.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        dormIDtextField.setColumns(20);
        dormIDtextField.setBounds(202,260,377,45);
        add(dormIDtextField);
        dormIDtextField.setOpaque(false);
        dormIDtextField.setBorder(new EmptyBorder(0,0,0,0));

        exchangetextField_1 = new JTextField();
        exchangetextField_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        exchangetextField_1.setColumns(20);
        exchangetextField_1.setBounds(202,355,377,45);
        add(exchangetextField_1);
        exchangetextField_1.setOpaque(false);
        exchangetextField_1.setBorder(new EmptyBorder(0,0,0,0));

        add(backgroundImageLabel);

        //设置按钮
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        {
            buttonPane = new JPanel();
            {
                okButton = new JButton("确定");
                okButton.setActionCommand("OK");
                getRootPane().setDefaultButton(okButton);
                okButton.setBounds(145,463,103,56);
                add(okButton);
                okButton.setOpaque(false);

                okButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        ExchangeAct(e);
                        setVisible(false);
                    }
                });
            }
            {
                cancelButton = new JButton("取消");
                cancelButton.setActionCommand("Cancel");
                cancelButton.setBounds(434,463,103,56);
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
     * 宿舍调换申请
     *
     * @param e
     */
    protected void ExchangeAct(ActionEvent e) {
        // TODO Auto-generated method stub
        Message mes = new Message();
        mes.setUserType(0);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormExcange);
        Client client = new Client(ClientMainFrame.socket);
        ArrayList<String> para = new ArrayList<String>();
        para.add(nametextField.getText());
        para.add(dormIDtextField.getText());
        para.add(exchangetextField_1.getText());

        mes.setData(para);
        System.out.println(para);
        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);
        dorm = (Dormitory) serverResponse.getData();
        C.updateFrameE(para);
        this.dispose();
    }

}
