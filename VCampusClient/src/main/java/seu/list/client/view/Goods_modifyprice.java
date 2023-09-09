package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Goods_modifyprice {

    private JFrame frame;
    private Shop_AdminFrame shop;

    public Goods_modifyprice(Shop_AdminFrame a) {
        shop = a;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();

        //绘制背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Goodsmotify.png"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        frame.setBounds(d.width / 2 - 693 / 2, d.height / 2 - 384 / 2, 693, 408);
        backgroundImageLabel.setBounds(0, 0, 693, 384);
        frame.setResizable(false);
        frame.add(backgroundImageLabel);
        frame.setLayout(null);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JButton btnNewButton = new JButton("确定");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Modify(e);
                close();
            }
        });
        btnNewButton.setOpaque(false);
        btnNewButton.setBounds(158, 283, 251 - 158 + 5, 323 - 283 + 10);
        frame.add(btnNewButton);
        JButton btnNewButton_1 = new JButton("取消");
        btnNewButton_1.setOpaque(false);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shop.show();
                close();
            }
        });
        btnNewButton_1.setBounds(419, 281, 251 - 158 + 5, 323 - 283 + 10);
        frame.add(btnNewButton_1);


        frame.setVisible(true);
    }

    protected void Modify(ActionEvent e) {
        // TODO 自动生成的方法存根
        Message mes = new Message();
        mes.setMessageType(MessageType.ModifyGoods);
        mes.setModuleType(ModuleType.Shop);
        Client client = new Client(ClientMainFrame.socket);
        ArrayList<String> args = new ArrayList<String>();
        for (int i = 0; i < shop.getTable().getRowCount(); i++) {
            args.add(shop.getTable().getValueAt(i, 0) + "");
            args.add(shop.getTable().getValueAt(i, 2) + "");
            args.add(shop.getTable().getValueAt(i, 3) + "");
        }
        mes.setData(args);
        Message serverResponse = client.sendRequestToServer(mes);
        close();
    }

    protected void close() {
        // TODO 自动生成的方法存根
        frame.setEnabled(true);
        frame.dispose();
    }
}
