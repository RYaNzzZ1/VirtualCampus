package seu.list.server.view;

import seu.list.server.bz.ServerMainFrame;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Vector;

/* *
 * 类{@codeServerFrame}用于生成服务器端的界面 <br>
 * 静态数据成员: {@code contentOane}, 类型: {@code JPanel}, 服务器端界面 <br>
 * 支持对服务器的操作 <br>
 * 重定向控制台输出到GUI界面显示 <br>
 * @author 柳多荣 吴慕陶
 * @version 1.0
 * @see java.swing.*
 * @see java.awt.*
 * @see Vector
 */

public class ServerFrame extends JFrame {


	private JScrollPane scrollPane;
	public static JTextArea consoleText;

	/* *
	 * 服务端界面的初始化 <br>
	 * 重定向控制台输出到GUI界面显示
	 * @author 柳多荣 吴慕陶
	 * @version 1.0
	 * @see java.swing.*
	 * @see java.awt.*;
	 * @see Vector
	 */
	public ServerFrame() {
		this.setTitle("虚拟校园系统-服务端");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		consoleText = new JTextArea();
		consoleText.setEditable(false);
		consoleText.setFont(new Font("楷体", Font.BOLD+Font.PLAIN+Font.ITALIC, 20));
		consoleText.setBounds(127, 194, 1154-127, 636-194);
		//consoleText.setCaretPosition(consoleText.getText().length());
		consoleText.setOpaque(false);
		scrollPane = new JScrollPane(consoleText);
		scrollPane.setBounds(127, 194, 1154-127, 636-194);
		//scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		add(scrollPane);
		//背景图片
		JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusServer/Image_new/ServerFrame.jpg"));
		Toolkit k = Toolkit.getDefaultToolkit();
		Dimension d = k.getScreenSize();
		setBounds(d.width/2-640, d.height/2-360, 1280, 720);
		backgroundImageLabel.setBounds(0, 0, 1280, 720);
		setSize(1280,760);
		add(backgroundImageLabel);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "是否退出？", "退出", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(result == JOptionPane.OK_OPTION) {
					close();
					ServerMainFrame.exitbtn();
				}
			}
		});

		//功能按钮配置：
		//得到鼠标的坐标（用于推算对话框应该摆放的坐标）
     /*backgroundImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
            }
        });*/
		JButton btnNewButton = new JButton("Help");
		btnNewButton.setBounds(124, 127, 232-124, 165-127);
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 18));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "launch -- 启动服务器\nclose -- 关闭服务器\nreboot -- 重启服务器\ngetall -- 打印所有客户端信息\nexit -- 退出服务端程序", "帮助", JOptionPane.INFORMATION_MESSAGE);
				//System.out.println("Hello");
			}
		});

		JButton btnNewButton_1 = new JButton("Launch");
		btnNewButton_1.setBounds(309, 129, 232-124, 165-127);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int res = ServerMainFrame.launch();
				if(res == 0) {
					JOptionPane.showMessageDialog(null, "请勿重复启动服务器，或按help获取帮助", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 18));

		JButton btnNewButton_2 = new JButton("Close");
		btnNewButton_2.setBounds(491, 129, 232-124, 165-127);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int res = ServerMainFrame.closebtn();
				if(res == 0) {
					JOptionPane.showMessageDialog(null, "还未启动服务器，或按help获取帮助", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 18));

		JButton btnNewButton_3 = new JButton("Reboot");
		btnNewButton_3.setBounds(675, 129, 232-124, 165-127);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int res = ServerMainFrame.reboot();
				if(res == 0) {
					JOptionPane.showMessageDialog(null, "还未启动服务器，或按help获取帮助", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton_3.setFont(new Font("宋体", Font.PLAIN, 18));

		JButton btnNewButton_4 = new JButton("Exit");
		btnNewButton_4.setBounds(1036, 129, 232-124, 165-127);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.showConfirmDialog(null, "是否要退出服务器端程序?", "提示", JOptionPane.YES_NO_OPTION);
				if(res == 0) {
					close();
					ServerMainFrame.exitbtn();
				}else {
					JOptionPane.showMessageDialog(null, "按help获取帮助", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnNewButton_4.setFont(new Font("宋体", Font.PLAIN, 18));
		JButton btnNewButton_5 = new JButton("GetAll");
		btnNewButton_5.setBounds(861, 129, 232-124, 165-127);
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<String> res = new Vector<String>();
				res = ServerMainFrame.getall();
				if(res.size() == 0) {
					JOptionPane.showMessageDialog(null, "目前没有客户端连接到服务器", "错误", JOptionPane.ERROR_MESSAGE);
				}else {
					String info = null;
					info = "目前连接到服务器上的客户端：";
					int ivector = 0;
					while(ivector<res.size()) {
						info = info + "\n";
						info = info + res.get(ivector) + ": " + res.get(ivector + 1);
						ivector += 2;
					}
					JOptionPane.showMessageDialog(null, info, "信息", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnNewButton_5.setFont(new Font("宋体", Font.PLAIN, 18));


		add(btnNewButton_2);
		add(btnNewButton);
		add(btnNewButton_1);
		add(btnNewButton_5);
		add(btnNewButton_4);
		add(btnNewButton_3);
		//隐藏所有按钮

		btnNewButton_2.setOpaque(false);
		btnNewButton.setOpaque(false);
		btnNewButton_1.setOpaque(false);
		btnNewButton_3.setOpaque(false);
		btnNewButton_4.setOpaque(false);
		btnNewButton_5.setOpaque(false);






		OutputStream textAreaStream = new OutputStream() {
			public void write(int b) throws IOException {
				consoleText.append(String.valueOf((char)b));
				consoleText.setCaretPosition(consoleText.getText().length());
			}

			public void write(byte b[]) throws IOException {
				consoleText.append(new String(b));
				consoleText.setCaretPosition(consoleText.getText().length());
			}

			public void write(byte b[], int off, int len) throws IOException {
				consoleText.append(new String(b, off, len));
				consoleText.setCaretPosition(consoleText.getText().length());
			}
		};
		PrintStream myOut = new PrintStream(textAreaStream);
		System.setOut(myOut);
		System.setErr(myOut);

		System.out.println("欢迎使用虚拟校园系统-服务端");
		System.out.println("点击Help获取服务端使用帮助");
		consoleText.setCaretPosition(consoleText.getText().length());
	}

	/**
	 * 关闭本界面
	 * @author 柳多荣
	 * @version 1.0
	 */
	public void close() {
		this.dispose();
	}
}
