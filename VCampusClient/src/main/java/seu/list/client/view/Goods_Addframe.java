package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.Goods;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Goods_Addframe {

    private JFrame frame;
    private JTextField IDtextField;
    private JTextField NametextField;
    private Shop_AdminFrame shop;
    private JTextField PicetextField;
    private JTextField NumbertextField;

    public Goods_Addframe(Shop_AdminFrame a) {
        this.shop = a;
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //绘制背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Goods_Addframe.png"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        frame.setBounds(d.width / 2 - 849 / 2, d.height / 2 - 556 / 2, 849, 585);
        backgroundImageLabel.setBounds(0, 0, 849, 556);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.add(backgroundImageLabel);

        //ID输入框
        IDtextField = new JTextField();
        IDtextField.setFont(new Font("华文行楷", Font.BOLD, 32));
        IDtextField.setBounds(259, 104, 621 - 259, 147 - 104);
        IDtextField.setOpaque(false);
        IDtextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        frame.add(IDtextField);

        //名称输入框
        NametextField = new JTextField();
        NametextField.setFont(new Font("华文行楷", Font.BOLD, 32));
        NametextField.setBounds(259, 180, 621 - 259, 147 - 104);
        frame.add(NametextField);
        NametextField.setOpaque(false);
        NametextField.setBorder(new EmptyBorder(0, 0, 0, 0));

        //单价输入框
        PicetextField = new JTextField();
        PicetextField.setFont(new Font("华文行楷", Font.BOLD, 32));
        PicetextField.setBounds(259, 254, 621 - 259, 147 - 104);
        frame.add(PicetextField);
        PicetextField.setOpaque(false);
        PicetextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        //库存输入款
        NumbertextField = new JTextField();
        NumbertextField.setFont(new Font("华文行楷", Font.BOLD, 32));
        NumbertextField.setBounds(259, 332, 621 - 259, 147 - 104);
        frame.add(NumbertextField);
        NumbertextField.setOpaque(false);
        NumbertextField.setBorder(new EmptyBorder(0, 0, 0, 0));
        frame.add(backgroundImageLabel);
        //确定按钮
        JButton btnNewButton = new JButton("确定");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Addgoods();
                frame.dispose();
            }
        });
        btnNewButton.setBounds(242, 448, 335 - 242, 485 - 448);
        btnNewButton.setOpaque(false);
        frame.add(btnNewButton);

        //取消按钮
        JButton btnNewButton_1 = new JButton("取消");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        btnNewButton_1.setOpaque(false);
        btnNewButton_1.setBounds(506, 446, 335 - 242, 485 - 448);
        frame.add(btnNewButton_1);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /**
     * 方法{@code void Addgoods()}点击确认后执行，利用当前输入的参数增加商品。
     */
    protected void Addgoods() {
        // TODO 自动生成的方法存根
        System.out.println(IDtextField.getText());
        System.out.println(PicetextField.getText());
        System.out.println(NumbertextField.getText());
        System.out.println(shop.getTable().getRowCount());
        for (int i = 0; i < shop.getTable().getRowCount(); i++) {
            if (IDtextField.getText().equals(shop.getTable().getValueAt(i, 0))) {
                JOptionPane.showMessageDialog(null, "ID重复！！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (!IDtextField.getText().matches("[0-9]*")) {
            JOptionPane.showMessageDialog(null, "ID应为正整数！！", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (!PicetextField.getText().matches("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}")) {
            JOptionPane.showMessageDialog(null, "请输入合法的价格！！", "提示", JOptionPane.WARNING_MESSAGE);
        } else if (!NumbertextField.getText().matches("[0-9]*")) {
            JOptionPane.showMessageDialog(null, "请输入合法库存！！", "提示", JOptionPane.WARNING_MESSAGE);
        } else {
            Goods temp = new Goods(Integer.parseInt(IDtextField.getText()), NametextField.getText(),
                    Double.parseDouble(PicetextField.getText()), Integer.parseInt(NumbertextField.getText()));

            Message mes = new Message();
            mes.setData(temp);
            mes.setModuleType(ModuleType.Shop);
            mes.setMessageType(MessageType.GoodsAdd);
            Client client = new Client(ClientMainFrame.socket);

            Message serverResponse = client.sendRequestToServer(mes);
            int res = (int) serverResponse.getData();
            shop.show();
        }
    }
}
