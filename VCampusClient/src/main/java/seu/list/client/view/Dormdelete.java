/**
 * @author 周楚翘
 * @version jdk1.8.0
 */
package seu.list.client.view;

import seu.list.client.bz.Client;
import seu.list.common.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		C=c;
		setVisible(true);
		setTitle("删除宿舍");
		setBounds(100, 100, 391, 283);
		//添加图标
		Image image=new ImageIcon("VCampusClient/src/main/resources/image/xiaobiao.jpg").getImage();
		setIconImage(image);
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		JLabel lblNewLabel = new JLabel("删除宿舍");
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
				okButton = new JButton("确定");
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener()
						{

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
						.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
						.addGap(71)
						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
						.addGap(126))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
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
/**
 * 完成删除宿舍
 * @param e点击事件
 */
	protected void DeleteAct(ActionEvent e) {
		// TODO Auto-generated method stub
		Message mes = new Message();
		mes.setUserType(1);
		mes.setModuleType(ModuleType.Dormitory);
		mes.setMessageType(MessageType.DormDelete);
		try {
			socket = new Socket(IConstant.SERVER_ADDRESS,IConstant.SERVER_PORT);
		}catch (IOException e1) {
			e1.printStackTrace();
		}
		Client client = new Client(socket);
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

