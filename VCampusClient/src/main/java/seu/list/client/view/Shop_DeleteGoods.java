package seu.list.client.view;

import seu.list.client.bz.Client;
import seu.list.client.bz.ClientMainFrame;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 类{@code Goods_Addframe}为删除商品界面界面
 * 点击“删除”按钮后会进入到当前界面
 * @author 欧阳瑜
 * @version 1.0
 */
public class Shop_DeleteGoods {
    private Shop_AdminFrame shop;
	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	
	/**
	 * Initialize the contents of the frame.
	 * @param textField 输入商品id
	 */
	public Shop_DeleteGoods(Shop_AdminFrame a) {
		shop=a;
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		textField = new JTextField();
		textField.setColumns(10);
		
		
		JLabel lblNewLabel = new JLabel("请输入要删除的商品的编号：");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delgoods(e);
				close();
			}
		});
		
		JButton btnNewButton_1 = new JButton("取消");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(10, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnNewButton)
							.addGap(115)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1))
					.addGap(106))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(60)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(58))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(2);
	}
		

        /**  
	 * 方法{@code void Delgoods(ActionEvent e)}点击确认后执行，利用当前输入的参数增加商品。
	 */
	protected void Delgoods(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(!textField.getText().matches("[0-9]*")) {
			JOptionPane.showMessageDialog(null, "ID应为正整数！！", "提示", JOptionPane.WARNING_MESSAGE);
			}
		else {
		int tempid=Integer.parseInt(textField.getText());
		Message mes =new Message();
		mes.setData(tempid);
		mes.setMessageType(MessageType.GoodsDelete);
		mes.setModuleType(ModuleType.Shop);
		Client client=new Client(ClientMainFrame.socket);
		
		Message serverResponse = client.sendRequestToServer(mes); 
		int res=(int)serverResponse.getData();
		shop.show();
		}
	}

	protected void close() {
		// TODO 自动生成的方法存根
		frame.setEnabled(true);
		frame.dispose();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
}
