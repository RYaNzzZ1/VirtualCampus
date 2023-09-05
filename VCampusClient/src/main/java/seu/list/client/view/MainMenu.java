package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainMenu extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private final String cmdClass = "CMD_CLASS";
	private final String cmdLib = "CMD_LIB";
	private final String cmdCourse = "CMD_COURSE";
	private final String cmdDorm = "CMD_DORM";
	private final String cmdShop = "CMD_SHOP";
	private final String cmdClose = "CMD_CLOSE";

	private String uID;
	private String pwd;
	private String name;
	private String money;
	private int userType;
	private Socket socket;
	String type ;

	JLabel l1,l2;
	private JTextField timeField;

	private JLabel nameLabel;

	/**
	 * Create the frame.
	 */

	public MainMenu(int sign, String uID, String pwd, String name, String money, Socket socket) {
		//设置背景图片
		JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/MainMenu.PNG"));
		Toolkit k = Toolkit.getDefaultToolkit();
		Dimension d = k.getScreenSize();
		setBounds(d.width/2-640, d.height/2-360, 1280, 720);
		backgroundImageLabel.setBounds(0, 0, 1280, 720);
		setSize(1280,760);
		setResizable(false);
		setLayout(null);

		this.userType=sign;
		this.uID=uID;
		this.pwd=pwd;
		this.name = name;
		this.money = money;
		this.socket=socket;


		//学生端用户读取student列表进行name\money的设置
		if(this.userType == 0) {
			Vector<Student> StuAll = new Vector<Student>();
			Message mes = new Message();
			mes.setModuleType(ModuleType.Student);
			mes.setMessageType(MessageType.ClassAdminGetAll);
			List<Object> sendData = new ArrayList<Object>();
			mes.setData(sendData);
			Client client = new Client(ClientMainFrame.socket);
			Message serverResponse = new Message();
			serverResponse = client.sendRequestToServer(mes);
			StuAll = (Vector<Student>) serverResponse.getData();
			Student thisStu = new Student();
			int studenttemp = 0;

			while(studenttemp < StuAll.size()) {
				String tempid = StuAll.get(studenttemp).getStudentid();
				uID.replaceAll("\\p{C}", "");
				tempid.replaceAll("\\p{C}", "");
				if(tempid.equals(uID)) {
					thisStu = StuAll.get(studenttemp);
					break;
				}
				studenttemp++;
			}
			this.name = thisStu.getStudentName();
			//设置余额的小数点显示
			DecimalFormat df = new DecimalFormat("0.00");
			this.money = "" + df.format(thisStu.getStudentcredit());
		}
		//结束有关学生列表的操作

		/*addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "是否退出？", "退出", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(result == JOptionPane.OK_OPTION) {
					ClientMainFrame.close();
				}
			}
		});*/
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		type="";

		if(this.userType == 0) {
			type = "学生";
		}else {
			type = "管理员";
		}

		//显示信息：
		l1=new JLabel("Welcome "+name+"!");
		l1.setFont(new Font("华文行楷",Font.BOLD+Font.ITALIC,30));
		l1.setOpaque(false);
		l1.setBounds(250,113,774-250,155-113);
		l1.setEnabled(false);
		add(l1);

		l2=new JLabel("身份："+type+"   账户余额："+this.money);
		l2.setFont(new Font("华文行楷",Font.BOLD+Font.ITALIC,30));
		l2.setOpaque(false);
		l2.setBounds(250,175,774-250,155-113);
		l2.setEnabled(false);
		add(l2);


		add(backgroundImageLabel);
		//五大模块
		JButton classButton = new JButton("学籍管理");//学籍管理
		classButton.setBounds(79,441,419-79,533-441);
		add(classButton);
		classButton.setOpaque(false);
		classButton.addActionListener(this);
		classButton.setActionCommand(this.cmdClass);

		JButton libraryButton = new JButton("图书馆");//图书馆
		libraryButton.setBounds(152,292,447-152,381-292);
		libraryButton.setOpaque(false);
		add(libraryButton);
		libraryButton.addActionListener(this);
		libraryButton.setActionCommand(this.cmdLib);

		JButton courseButton = new JButton("选课");//选课
		courseButton.setBounds(515,273,750-515,357-273);
		courseButton.setOpaque(false);
		add(courseButton);
		courseButton.addActionListener(this);
		courseButton.setActionCommand(this.cmdCourse);

		JButton dormButton = new JButton("宿舍");//宿舍
		dormButton.setOpaque(false);
		dormButton.setBounds(567,424,813-567,510-424);
		add(dormButton);
		dormButton.addActionListener(this);
		dormButton.setActionCommand(this.cmdDorm);

		JButton shopButton = new JButton("商店");//商店
		shopButton.setBounds(281,577,509-281,663-577);
		shopButton.setOpaque(false);
		add(shopButton);
		shopButton.addActionListener(this);
		shopButton.setActionCommand(this.cmdShop);

		//退出按钮
		JButton exitButton = new JButton("EXit");//退出
		exitButton.setOpaque(false);
		exitButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
		exitButton.setBounds(1082,575,1213-1082,627-575);
		add(exitButton);
		exitButton.addActionListener(this);
		exitButton.setActionCommand(this.cmdClose);

		nameLabel = new JLabel("姓名：" + this.name); // 姓名
		nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
		nameLabel.setBounds(10, 158, 164, 39);






	}


	@Override
	public void actionPerformed(ActionEvent e) {
		MainMenu tempmenu = this;
		try {
			if(e.getActionCommand().equals(this.cmdCourse)){ // 选课
				if(userType==0){
					ClientStuCourseFrame s=new ClientStuCourseFrame(uID,socket);
					//s.setVisible(true);
				}else{
					ClientTeacherFrame s=new ClientTeacherFrame(uID,socket);
					//s.setVisible(true);
				}
			}else if(e.getActionCommand().equals(this.cmdClass)) { // 学籍
				if(userType == 0) {
					ClassStudentClient classStu = new ClassStudentClient(this.uID, this.pwd, tempmenu);
					classStu.setVisible(true);
				}else {
					ClassAdminClient classAdmin = new ClassAdminClient();
					classAdmin.setVisible(true);
				}
			}else if(e.getActionCommand().equals(this.cmdLib)) { //图书馆
				if(userType == 0) {
					LibraryStu libStu = new LibraryStu(this.uID);
					libStu.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					libStu.setVisible(true);
				}else {
					LibraryManage libMgr = new LibraryManage();
					libMgr.setVisible(true);
				}
			}else if(e.getActionCommand().equals(this.cmdDorm)) { // 宿舍
				if(userType == 0) {
					DormitoryStudentClient dormStu = new DormitoryStudentClient(this.uID, this.socket);
					dormStu.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					dormStu.setVisible(true);
				}else {
					DormitoryAdminClient dormAdmin = new DormitoryAdminClient(ClientMainFrame.socket);
					dormAdmin.setVisible(true);
				}
			}else if(e.getActionCommand().equals(this.cmdShop)) { //商店
				if(userType == 0) {
					Shop_StudentFrame shopStu = new Shop_StudentFrame(this.uID, this.pwd, tempmenu);
					//shopStu.setVisible(true);
				}else {
					Shop_AdminFrame shopAdmin = new Shop_AdminFrame();
					//shopAdmin.setVisible(true);
				}
			}else { // 退出
				int result = JOptionPane.showConfirmDialog(null, "是否退出？", "退出", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(result == JOptionPane.OK_OPTION) {
					this.dispose();

					Client ccs = new Client(this.socket);
					User u=new User();
					u.setId(this.uID);
					Message mes=new Message();
					mes.setContent(u.getContent());
					mes.setModuleType(ModuleType.User);
					mes.setMessageType(MessageType.REQ_LOGOUT);
					Message res=ccs.sendRequestToServer(mes);

					ClientMainFrame.back();
				}
			}
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void set(String newName, Double newMoney) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.money = "" + df.format(newMoney);
		this.name = newName;
		l1.setText("Welcome "+name+"!");
		l2.setText("身份："+type+"   账户余额："+this.money);
	}

	public void set(Double newMoney) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.money = "" + df.format(Double.parseDouble(money) - newMoney);
		l2.setText("身份："+type+"   账户余额："+money);
	}
}
