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

/**
 * 类{@code Goods_Addframe}为删除商品界面界面
 * 点击“删除”按钮后会进入到当前界面
 *
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
     *
     * @param
     */
    public Shop_DeleteGoods(Shop_AdminFrame a) {
        shop = a;
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);


        //绘制背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Shop_DeleteGoods.png"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        frame.setBounds(d.width / 2 - 424, d.height / 2 - 427 / 2, 848, 456);
        backgroundImageLabel.setBounds(0, 0, 848, 427);
        frame.setResizable(false);

        //2.绘制退出按钮
        //得到鼠标的坐标（用于推算对话框应该摆放的坐标）
     /*backgroundImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
			}
        });*/
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(null);


        textField = new JTextField();
        textField.setBounds(268, 149, 634 - 268, 188 - 149 + 7);
        textField.setFont(new Font("华文行楷", Font.BOLD, 32));
        frame.add(textField);
        textField.setOpaque(false);
        textField.setBorder(new EmptyBorder(0, 0, 0, 0));
        frame.add(backgroundImageLabel);


        JButton btnNewButton = new JButton("确定");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Delgoods(e);
                close();
            }
        });
        frame.add(btnNewButton);
        btnNewButton.setOpaque(false);

        btnNewButton.setBounds(257, 276, 100, 40);

        JButton btnNewButton_1 = new JButton("取消");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        btnNewButton_1.setBounds(525, 276, 100, 40);
        btnNewButton_1.setOpaque(false);
        frame.add(btnNewButton_1);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


    }


    /**
     * 方法{@code void Delgoods(ActionEvent e)}点击确认后执行，利用当前输入的参数增加商品。
     */
    protected void Delgoods(ActionEvent e) {
        // TODO 自动生成的方法存根
        if (!textField.getText().matches("[0-9]*")) {
            JOptionPane.showMessageDialog(null, "ID应为正整数！！", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            int tempid = Integer.parseInt(textField.getText());
            Message mes = new Message();
            mes.setData(tempid);
            mes.setMessageType(MessageType.GoodsDelete);
            mes.setModuleType(ModuleType.Shop);
            Client client = new Client(ClientMainFrame.socket);

            Message serverResponse = client.sendRequestToServer(mes);
            int res = (int) serverResponse.getData();
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
