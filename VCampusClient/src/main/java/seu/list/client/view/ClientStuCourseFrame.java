package seu.list.client.view;
//学生选课界面

import seu.list.client.bz.Client;
import seu.list.common.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Vector;


public class ClientStuCourseFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	final int WIDTH=1280;
	final int HEIGHT=750;
	JFrame jframe=new JFrame();
	JButton jb1,jb2,jb3,jb4,jb5;
	JTextField jtf1,jtf2;

	JTable jtb1;
	JScrollPane scrollPane;
	Socket socket;//传送数据
	private String userID;
	/**
	 */

	Font f1=new Font("华文行楷",Font.PLAIN+Font.BOLD,30);

	/**
	 * create the frame
	 * @param number 用户id
	 * @param socket 与服务端保持通信
	 */
	public ClientStuCourseFrame(String number, Socket socket) throws ClassNotFoundException, SQLException,IOException, ClassNotFoundException {

		Tools.setWindowspos(WIDTH,HEIGHT,jframe);
		this.socket=socket;
		Client client =new Client(socket);
		userID=number;
		jframe.setTitle("选课管理系统");
		jframe.setLayout(null);

		//绘制背景图片
		JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/ClientStuCourseFrame.PNG"));
		Toolkit k = Toolkit.getDefaultToolkit();
		Dimension d = k.getScreenSize();
		setBounds(d.width/2-640, d.height/2-360, 1280, 720);
		backgroundImageLabel.setBounds(0, 0, 1280, 720);
		jframe.setSize(1280,760);
		jframe.setResizable(false);
		jframe.setLayout(null);

		//2.绘制退出按钮
		//得到鼠标的坐标（用于推算对话框应该摆放的坐标）
     /*backgroundImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
			}
        });
*/

		jtf1=new JTextField();
		jtf1.setBounds(670,149,991-670,194-149+3);
		jtf1.setFont(f1);
		jframe.add(jtf1);
		jtf1.setOpaque(false);
		jtf1.setBorder(new EmptyBorder(0,0,0,0));
//		"课程编号","学年学期","课程","专业","授课教师","状态","类型"
		Object[][] courseinformation= {};
		jtf2=new JTextField();
		jtf2.setFont(f1);
		jtf2.setOpaque(false);
		jtf2.setBorder(new EmptyBorder(0,0,0,0));

		//设置表格
		Object[] courselist = {"学年学期","课程编号","专业","课程","授课教师","状态","类型"};
		DefaultTableModel model;
		model = new DefaultTableModel(courseinformation, courselist);
		Message mes = new Message();
		mes.setUserType(0);
		mes.setModuleType(ModuleType.Course);
		mes.setMessageType(MessageType.REQ_SHOW_ALL_LESSON);
		Message rec=new Message();
		rec=client.sendRequestToServer(mes);
		System.out.println(rec.getContent());
		Vector<String> allCourseContents =  rec.getContent();
		System.out.println(allCourseContents.size());
		Object sigRow[] = new  String[7];
		for(int i=0;i<allCourseContents.size();) {
			for(int j=0;j<7;j++) {
				sigRow[j]=allCourseContents.get(i);
				i++;
			}
			model.addRow(sigRow);
		}


		jtb1=new JTable();
		jtb1.setModel(model);
		scrollPane = new JScrollPane(jtb1);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(WIDTH-100,500));
		jtb1.setPreferredSize(new Dimension(WIDTH-100,2000));
		jtb1.setFont(new Font("微软雅黑",Font.BOLD,20));
		jtb1.getTableHeader().setPreferredSize(new Dimension(1, 40));
		jtb1.getTableHeader().setFont(new Font("宋体",Font.BOLD,25));
		jtb1.setRowHeight(50);
		jtb1.setBounds(0,0,1145-289,610-222);
		scrollPane.setBounds(289,222,1145-289,610-222);
		jtb1.getTableHeader().setReorderingAllowed(false);
		jtb1.setEnabled(false);

		//透明化处理
		jtb1.setForeground(Color.BLACK);
		jtb1.setFont(new Font("Serif", Font.BOLD, 28));
		jtb1.setRowHeight(40);                //表格行高
		jtb1.setPreferredScrollableViewportSize(new Dimension(850, 500));
		jtb1.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setOpaque(false);    //设置透明
		String []Names={"学年学期","课程编号","专业","课程","授课教师","状态","类型"};
		for(int i=0;i<7;i++){
			jtb1.getColumn(Names[i]).setCellRenderer(renderer);//单格渲染
			TableColumn column = jtb1.getTableHeader().getColumnModel().getColumn(i);
			column.setHeaderRenderer(renderer);//表头渲染
		}
		jtb1.setOpaque(false);
		jtb1.getTableHeader().setOpaque(false);
		jtb1.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
		scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setColumnHeaderView(jtb1.getTableHeader());
		scrollPane.getColumnHeader().setOpaque(false);


		jframe.add(scrollPane);
		jframe.add(backgroundImageLabel);
		//下面是按钮，上面是输入框
		jb1=new JButton("选择");
		jb1.setFont(f1);
		jb1.setBounds(135,228,1127-1015,195-149+10);
		jframe.add(jb1);


		jb4=new JButton("退课");
		jb4.setFont(f1);
		jb4.setBounds(136,336,1127-1015,195-149+10);
		jframe.add(jb4);


		jb2=new JButton("已选课程查询");
		jb2.setFont(f1);
		jb2.setBounds(134,439,1026-779,642-592);
		jframe.add(jb2);

		jb3=new JButton("课程查询");
		jb3.setBounds(1015,149,1127-1015,195-149);
		jframe.add(jb3);
		jb3.setFont(f1);

		jb5=new JButton("退出");
		jb5.setBounds(134,548,1127-1015,195-149);
		jframe.add(jb5);
		jb5.setFont(f1);
		jb5.addActionListener(event->
		{
			jframe.dispose();
		});
		jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


		jb1.addActionListener(this);
		jb1.setActionCommand("choose");
		jb2.addActionListener(this);
		jb2.setActionCommand("chozen");
		jb3.addActionListener(this);
		jb3.setActionCommand("check");
		jb4.addActionListener(this);
		jb4.setActionCommand("cancel");
		jframe.setVisible(true);
		jb1.setOpaque(false);
		jb2.setOpaque(false);
		jb3.setOpaque(false);
		jb4.setOpaque(false);
		jb5.setOpaque(false);

	}

	public void display(Message rec){
		Vector<String> allCourseInfor = rec.getContent();
		int rowNumber = allCourseInfor.size()/7;
		String[][] allCourseTable = new String[rowNumber][7];
		int storingPlace = 0;
		for(int i=0;i<rowNumber;i++) {
			for(int j=0;j<7;j++)
				allCourseTable[i][j] = allCourseInfor.get(storingPlace++);
		}
		jtb1 = new JTable();
		jtb1.setModel(new DefaultTableModel(
				allCourseTable,
				new String[] {
						"学年学期","课程编号","专业","课程","授课教师","状态","类型"
				}
		));
		jtb1.getColumnModel().getColumn(0).setPreferredWidth(161);
		jtb1.getColumnModel().getColumn(1).setPreferredWidth(161);
		jtb1.getColumnModel().getColumn(2).setPreferredWidth(161);
		jtb1.getColumnModel().getColumn(3).setPreferredWidth(161);
		jtb1.getColumnModel().getColumn(4).setPreferredWidth(161);
		jtb1.getColumnModel().getColumn(5).setPreferredWidth(161);
		jtb1.getColumnModel().getColumn(6).setPreferredWidth(161);
		scrollPane.setViewportView(jtb1);
		jtb1.setEnabled(false);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(WIDTH-100,500));
		jtb1.setPreferredSize(new Dimension(WIDTH-100,2000));
		jtb1.setFont(new Font("微软雅黑",Font.BOLD,20));
		jtb1.getTableHeader().setPreferredSize(new Dimension(1, 40));
		jtb1.getTableHeader().setFont(new Font("宋体",Font.BOLD,25));
		jtb1.setRowHeight(50);

		//透明化处理
		jtb1.setForeground(Color.BLACK);
		jtb1.setFont(new Font("Serif", Font.BOLD, 28));
		jtb1.setRowHeight(40);                //表格行高
		jtb1.setPreferredScrollableViewportSize(new Dimension(850, 500));
		jtb1.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setOpaque(false);    //设置透明
		String []Names={"学年学期","课程编号","专业","课程","授课教师","状态","类型"};
		for(int i=0;i<7;i++){
			jtb1.getColumn(Names[i]).setCellRenderer(renderer);//单格渲染
			TableColumn column = jtb1.getTableHeader().getColumnModel().getColumn(i);
			column.setHeaderRenderer(renderer);//表头渲染
		}
		jtb1.setOpaque(false);
		jtb1.getTableHeader().setOpaque(false);
		jtb1.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
		scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setColumnHeaderView(jtb1.getTableHeader());
		scrollPane.getColumnHeader().setOpaque(false);
		//this.setVisible(false);
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Client client =new Client(this.socket);
		if(e.getActionCommand() == "choose") { //选课按钮响应事件
			jframe.setVisible(false);
			JFrame tem=new JFrame();
			tem.setTitle("选课");
			tem.setLayout(null);

			//绘制背景图片
			JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/StudentSelectCourse.jpg"));
			Toolkit k = Toolkit.getDefaultToolkit();
			Dimension d = k.getScreenSize();
			tem.setBounds(d.width/2-825/2, d.height/2-415/2, 825,415+25);
			backgroundImageLabel.setBounds(0, 0, 825,415);
			tem.setResizable(false);
			tem.setLayout(null);
			tem.setVisible(true);


			//2.绘制退出按钮
			//得到鼠标的坐标（用于推算对话框应该摆放的坐标）
     /*backgroundImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
			}
        });
*/

			jtf2.setBounds(260,154,616-260,197-154);
			tem.add(jtf2);

			tem.add(backgroundImageLabel);
			jtf2.setText("");
			JButton OK=new JButton("确定");
			OK.setBounds(250,269,348-250,325-269);
			tem.add(OK);
			JButton Cancel=new JButton("取消");
			Cancel.setBounds(510,270,348-250,325-269);
			tem.add(Cancel);
			OK.setOpaque(false);
			Cancel.setOpaque(false);
			OK.addActionListener(event->
			{
				if(!Objects.equals(jtf2.getText(), "")) {
					Message clientReq = new Message();//新建申请用于交换
					Vector<String> reqContent = new Vector<String>();
					reqContent.add(jtf2.getText());
					reqContent.add(userID);
					clientReq.setContent(reqContent);
					clientReq.setModuleType(ModuleType.Course);
					clientReq.setMessageType("REQ_STU_ADD_LESSON");
					Message rec = client.sendRequestToServer(clientReq);
					if (rec.isSeccess()) {
						clientReq.setMessageType("REQ_SHOW_ALL_LESSON");
						rec = client.sendRequestToServer(clientReq);
						JOptionPane.showMessageDialog(null,"选课成功！","提示",JOptionPane.PLAIN_MESSAGE);


						display(rec);
						jframe.setVisible(true);
						tem.dispose();

					} else {
						JOptionPane.showMessageDialog(null, "课程已添加或不存在", "错误", JOptionPane.ERROR_MESSAGE);
					}
					jtf2.setText("");
				} else{
					JOptionPane.showMessageDialog(null, "课程号不能为空", "错误", JOptionPane.ERROR_MESSAGE);
					Message clientReq = new Message();//新建申请用于交换
					User user = new User();
					user.setId(userID);
					clientReq.setContent(user.getContent());
					clientReq.setModuleType(ModuleType.Course);
					clientReq.setMessageType("REQ_SHOW_ALL_LESSON");
					Message rec = client.sendRequestToServer(clientReq);

					display(rec);
				}
			});

			Cancel.addActionListener(event->
			{
				jframe.setVisible(true);
				tem.dispose();
			});



		} else if(e.getActionCommand() == "chozen") {
			//this.setVisible(false);
			Message clientReq = new Message();//新建申请用于交换
			User user = new User();
			user.setId(userID);
			clientReq.setContent(user.getContent());
			clientReq.setModuleType(ModuleType.Course);
			clientReq.setMessageType("REQ_STU_ALL_CHOOOSE");
			Message rec=client.sendRequestToServer(clientReq);

			display(rec);
		} else if(e.getActionCommand() == "check") {
			if(!Objects.equals(jtf1.getText(), "")) {
				Message clientReq = new Message();//新建申请用于交换
				clientReq.setModuleType(ModuleType.Course);
				clientReq.setMessageType("REQ_SEARCH_LESSON");//查找课程
				Vector<String> reqContent = new Vector<String>();
				Course c = new Course();
				c.setCourseID(jtf1.getText());
				reqContent = c.getContent();
				clientReq.setContent(reqContent);//调用信息存进申请
				Message rec = client.sendRequestToServer(clientReq);
				System.out.println(rec.getContent());
				//通信

				display(rec);
			} else

			{
				//设置表格
				Object[][] courseinformation= {};
				Object[] courselist = {"学年学期","课程编号","专业","课程","授课教师","状态","类型"};
				DefaultTableModel model;
				model = new DefaultTableModel(courseinformation, courselist);
				Message mes = new Message();
				mes.setUserType(0);
				mes.setModuleType(ModuleType.Course);
				mes.setMessageType(MessageType.REQ_SHOW_ALL_LESSON);
				Message rec=new Message();
				rec=client.sendRequestToServer(mes);
				System.out.println(rec.getContent());
				Vector<String> allCourseContents =  rec.getContent();
				System.out.println(allCourseContents.size());
				Object sigRow[] = new  String[7];
				for(int i=0;i<allCourseContents.size();) {
					for(int j=0;j<7;j++) {
						sigRow[j]=allCourseContents.get(i);
						i++;
					}
					model.addRow(sigRow);
				}
				jtb1.setModel(model);

				//透明化处理
				jtb1.setForeground(Color.BLACK);
				jtb1.setFont(new Font("Serif", Font.BOLD, 28));
				jtb1.setRowHeight(40);                //表格行高
				jtb1.setPreferredScrollableViewportSize(new Dimension(850, 500));
				jtb1.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setOpaque(false);    //设置透明
				String []Names={"学年学期","课程编号","专业","课程","授课教师","状态","类型"};
				for(int i=0;i<7;i++){
					jtb1.getColumn(Names[i]).setCellRenderer(renderer);//单格渲染
					TableColumn column = jtb1.getTableHeader().getColumnModel().getColumn(i);
					column.setHeaderRenderer(renderer);//表头渲染
				}
				jtb1.setOpaque(false);
				jtb1.getTableHeader().setOpaque(false);
				jtb1.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
				scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
				scrollPane.setOpaque(false);
				scrollPane.getViewport().setOpaque(false);
				scrollPane.setColumnHeaderView(jtb1.getTableHeader());
				scrollPane.getColumnHeader().setOpaque(false);
				//this.setVisible(false);
				this.setLocationRelativeTo(null);
			}
			jtf1.setText("");
		} else if(e.getActionCommand() == "cancel") {
			jframe.setVisible(false);
			JFrame tem=new JFrame();
			tem.setTitle("选课");
			tem.setLayout(null);

			//绘制背景图片
			JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/StudentCancekCourse.jpg"));
			Toolkit k = Toolkit.getDefaultToolkit();
			Dimension d = k.getScreenSize();
			tem.setBounds(d.width/2-827/2, d.height/2-381/2, 827,381+25);
			backgroundImageLabel.setBounds(0, 0, 827,381);
			tem.setResizable(false);
			tem.setLayout(null);
			tem.setVisible(true);


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

			jtf2.setBounds(263,147,620-263,189-147);
			tem.add(jtf2);
			tem.add(backgroundImageLabel);

			jtf2.setText("");
			JButton OK=new JButton("确定");
			OK.setBounds(253,262,351-253,316-262);
			tem.add(OK);
			JButton Cancel=new JButton("取消");
			Cancel.setBounds(513,260,351-253,316-262);
			tem.add(Cancel);
			OK.setOpaque(false);
			Cancel.setOpaque(false);

			OK.addActionListener(event->
			{
				if(!Objects.equals(jtf2.getText(), "")){
					Message clientReq = new Message();
					Vector<String> content = new Vector<String>();
					content.add(jtf2.getText());//课ID
					content.add(userID);//人ID
					clientReq.setContent(content);
					clientReq.setModuleType(ModuleType.Course);
					clientReq.setMessageType("REQ_STU_REMOVE_LESSON");
					Message rec=client.sendRequestToServer(clientReq);

					clientReq.setModuleType(ModuleType.Course);
					clientReq.setMessageType("REQ_STU_ALL_CHOOOSE");
					User user =new User();
					user.setId(userID);
					clientReq.setContent(user.getContent());
					rec=client.sendRequestToServer(clientReq);
					JOptionPane.showMessageDialog(null,"退课成功！","提示",JOptionPane.PLAIN_MESSAGE);

					jframe.setVisible(true);
					tem.dispose();


					display(rec);
				} else {
					JOptionPane.showMessageDialog(null, "课程号不能为空", "错误", JOptionPane.ERROR_MESSAGE);
					Message clientReq = new Message();//新建申请用于交换
					User user = new User();
					user.setId(userID);
					clientReq.setContent(user.getContent());
					clientReq.setModuleType(ModuleType.Course);
					clientReq.setMessageType("REQ_SHOW_ALL_LESSON");
					Message rec = client.sendRequestToServer(clientReq);

					display(rec);

				}

			});

			Cancel.addActionListener(event->
			{
				jframe.setVisible(true);
				tem.dispose();
			});
			jtf2.setText("");
		}
	}
}

