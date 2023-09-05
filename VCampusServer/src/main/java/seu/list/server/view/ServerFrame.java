package seu.list.server.view;

import seu.list.server.bz.ServerMainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends JFrame {


	private JScrollPane scrollPane;
	public static JTextArea consoleText;

	public ServerFrame() {
		this.setTitle("虚拟校园系统-服务端");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		//背景图片
		JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusServer/Image_new/Server.PNG"));
        Toolkit k = Toolkit.getDefaultToolkit();
		Dimension d = k.getScreenSize();
		setBounds(d.width/2-640, d.height/2-360, 1280, 720);
		backgroundImageLabel.setBounds(0, 0, 1280, 720);
		setSize(1280,760);
        setResizable(false);
        add(backgroundImageLabel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "是否退出？", "退出", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    close();
                    ServerMainFrame.exitbtn();
                }
            }
        });

		//功能按钮配置：
		//得到鼠标的坐标（用于推算对话框应该摆放的坐标）
    /* backgroundImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
            }
        });
*/

		JButton btnNewButton_1 = new JButton("Launch");
        btnNewButton_1.setBounds(137, 139, 369 - 137, 227 - 139);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = ServerMainFrame.launch();
                if (res == 0) {
                    JOptionPane.showMessageDialog(null, "请勿重复启动服务器，或按help获取帮助", "错误", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"启动服务器成功","提示",JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
		btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 18));

		JButton btnNewButton_2 = new JButton("Close");
        btnNewButton_2.setBounds(708, 402, 943 - 708, 494 - 402);
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = ServerMainFrame.closebtn();
                if (res == 0) {
                    JOptionPane.showMessageDialog(null, "还未启动服务器", "错误", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"服务器关闭成功","提示",JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
		btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 18));

		JButton btnNewButton_3 = new JButton("Reboot");
        btnNewButton_3.setBounds(351, 289, 583 - 351, 379 - 289);
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = ServerMainFrame.reboot();
                if (res == 0) {
                    JOptionPane.showMessageDialog(null, "还未启动服务器", "错误", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"重启服务器成功","提示",JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
		btnNewButton_3.setFont(new Font("宋体", Font.PLAIN, 18));

		JButton btnNewButton_4 = new JButton("Exit");
        btnNewButton_4.setBounds(515, 565, 745 - 515, 653 - 565);
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "是否要退出服务器端程序?", "提示", JOptionPane.YES_NO_OPTION);
                if (res == 0) {
                    close();
                    ServerMainFrame.exitbtn();
                }
            }
		});
		btnNewButton_4.setFont(new Font("宋体", Font.PLAIN, 18));

		add(btnNewButton_2);
		add(btnNewButton_1);
		add(btnNewButton_4);
		add(btnNewButton_3);
		//隐藏所有按钮

		btnNewButton_2.setOpaque(false);
		btnNewButton_1.setOpaque(false);
		btnNewButton_3.setOpaque(false);
		btnNewButton_4.setOpaque(false);
    }

	public void close() {
		this.dispose();
	}
}
