/**
 * 
 * @version jdk1.8.0
 */
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

public class Dormmaintain extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel maintainLabel;
	private JTextField nametextField;
	private JTextField dormIDtextField;
	private JTextField maintaintextField;
	static Socket socket;
	public DormitoryStudentClient C;
	public Dormitory dorm;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		try {
			Dormmaintain dialog = new Dormmaintain(socket);
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
	public Dormmaintain(DormitoryStudentClient c,Socket socket) {
		C=c;
		setVisible(true);
		setTitle("维修登记");
		setLayout(null);

		// 创建带有背景图片的JLabel
		JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Dormmaintain.PNG"));
		//获取当前屏幕的尺寸（长、宽的值）
		Toolkit k = Toolkit.getDefaultToolkit();
		Dimension d = k.getScreenSize();
		backgroundImageLabel.setBounds(0, 0, 675, 605);
		//将当前窗口设置到屏幕正中央进行显示
		setBounds(d.width / 2 - 675 / 2, d.height / 2 - 605 / 2, 675, 605);
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
		nametextField.setBounds(197,155,375,44);
		add(nametextField);
		nametextField.setOpaque(false);
		nametextField.setBorder(new EmptyBorder(0,0,0,0));

		dormIDtextField = new JTextField();
		dormIDtextField.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		dormIDtextField.setColumns(20);
		dormIDtextField.setBounds(197,252,375,44);
		add(dormIDtextField);
		dormIDtextField.setOpaque(false);
		dormIDtextField.setBorder(new EmptyBorder(0,0,0,0));


		maintaintextField = new JTextField();
		maintaintextField.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		maintaintextField.setColumns(20);
		maintaintextField.setBounds(197,344,375,44);
		add(maintaintextField);
		maintaintextField.setOpaque(false);
		maintaintextField.setBorder(new EmptyBorder(0,0,0,0));

		add(backgroundImageLabel);

		//设置按钮
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		{
			buttonPane = new JPanel();
			{
				okButton = new JButton("确定");
				okButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
				okButton.setBounds(146,454,103,56);
				add(okButton);
				okButton.setOpaque(false);

				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						MaintainAct(e);
						setVisible(false);
					}

				});
			}
			{
				cancelButton = new JButton("取消");
				cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				cancelButton.setActionCommand("Cancel");
				cancelButton.setBounds(430,453,103,56);
				add(cancelButton);
				cancelButton.setOpaque(false);

				cancelButton.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						setVisible(false);
					}
				});
			}
		}
	}
/**
 * 宿舍维修申请登记
 * @param e
 */
	protected void MaintainAct(ActionEvent e) {
		// TODO Auto-generated method stub
		Message mes = new Message();
		mes.setUserType(0);
		mes.setModuleType(ModuleType.Dormitory);
		mes.setMessageType(MessageType.DormMaintain);

		Client client = new Client(ClientMainFrame.socket);
		ArrayList<String> para = new ArrayList<String>();
		para.add(nametextField.getText());
		para.add(dormIDtextField.getText());
		para.add(maintaintextField.getText());
		
		mes.setData(para);
		System.out.println(para);
			Message serverResponse=new Message();
			serverResponse=client.sendRequestToServer(mes);
			dorm=(Dormitory)serverResponse.getData();
			C.updateFrameM(para);
			this.dispose();
	}
}
