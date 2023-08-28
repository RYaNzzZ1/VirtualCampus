package seu.list.client.view;

import seu.list.client.bz.Client;
import seu.list.client.bz.ClientMainFrame;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;
import seu.list.common.Student;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ClassStudentClient extends JFrame {


	private JPanel contentPane;
	private JTextField name;
	private JTextField studentid;
	private JTextField major;
	private JTextField classid;
	private JTextField teacherid;
	private JTextField phone;
	private JTextField credit;
	private JTextField origion;
	private Double Money;
	private String Name;
	private String PWD;
	private String ID;
	private Student thisStu = null;
	private String statusmy = null;
	private JLabel lblNewLabel_1;
	private JLabel userimage = null;
	private MainMenu Mainmenu = null;

	@SuppressWarnings("unchecked")
	public ClassStudentClient(String id, String pwd, MainMenu mainmenu) {
		this.Mainmenu = mainmenu;

		//1.设置背景图片
		JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/ClassStudentClient.png"));
		Toolkit k = Toolkit.getDefaultToolkit();
		Dimension d = k.getScreenSize();
		setBounds(d.width/2-441, d.height/2-635/2, 882, 670);
		backgroundImageLabel.setBounds(0, 0, 882, 635);
		setResizable(false);
		setLayout(null);


		//2.绘制退出按钮
		//得到鼠标的坐标（用于推算对话框应该摆放的坐标）
    /* backgroundImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
			}
        });*/



		PWD = pwd;
		ID = id;
		setTitle("学生个人信息管理界面");
		this.setDefaultCloseOperation(2);  //关闭按钮




		//下面代码无需改动
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
		
		thisStu = new Student();
		
		int studenttemp = 0;
		while(studenttemp < StuAll.size()) {
			String tempid = StuAll.get(studenttemp).getStudentid();
			id.replaceAll("\\p{C}", "");
			tempid.replaceAll("\\p{C}", "");
			if(tempid.equals(id)) {
				thisStu = StuAll.get(studenttemp);
				break;
			}
			studenttemp++;
		}


		//提示标签


		//信息输入框（显示框）
		name = new JTextField();  //姓名
		name.setText("null");
		name.setEditable(false);
		name.setFont(new Font("华文行楷", Font.PLAIN, 24));
		name.setBounds(545,153,783-545,128-91);
		name.setText(thisStu.getStudentName());
		Name = thisStu.getStudentName();
		add(name);
		name.setOpaque(false);
		name.setBorder(new EmptyBorder(0,0,0,0));
		
		studentid = new JTextField();      //学号
		studentid.setEditable(false);
		studentid.setFont(new Font("华文行楷", Font.PLAIN, 24));
		studentid.setBounds(545,91,783-545,128-91);
		studentid.setText("null");
		studentid.setText(id);
		add(studentid);
		studentid.setOpaque(false);
		studentid.setBorder(new EmptyBorder(0,0,0,0));
		
		major = new JTextField();  //专业
		major.setText("null");
		major.setEditable(false);
		major.setFont(new Font("华文行楷", Font.PLAIN, 24));
		major.setBounds(545,328,783-545,128-91);
		major.setText(thisStu.getMajor());
		add(major);
		major.setOpaque(false);
		major.setBorder(new EmptyBorder(0,0,0,0));
		
		classid = new JTextField();   //班级
		classid.setText("null");
		classid.setEditable(false);
		classid.setFont(new Font("华文行楷", Font.PLAIN, 24));
		classid.setBounds(545,382,783-545,128-91);
		classid.setText(thisStu.getClassid());
		add(classid);
		classid.setOpaque(false);
		classid.setBorder(new EmptyBorder(0,0,0,0));
		
		teacherid = new JTextField();   //老师
		teacherid.setText("null");
		teacherid.setEditable(false);
		teacherid.setFont(new Font("华文行楷", Font.PLAIN, 24));
		teacherid.setBounds(169,444,783-545,128-91);
		teacherid.setText(thisStu.getTeacherid());
		add(teacherid);
		teacherid.setOpaque(false);
		teacherid.setBorder(new EmptyBorder(0,0,0,0));
		
		phone = new JTextField();  //联系电话
		phone.setText("null");
		phone.setEditable(false);
		phone.setFont(new Font("华文行楷", Font.PLAIN, 24));
		phone.setBounds(545,438,783-545,128-91);
		phone.setText(thisStu.getStudentphone());
		add(phone);
		phone.setOpaque(false);
		phone.setBorder(new EmptyBorder(0,0,0,0));



		DecimalFormat df = new DecimalFormat("0.00");
		credit = new JTextField();   //账户余额
		credit.setEditable(false);
		credit.setFont(new Font("华文行楷", Font.PLAIN, 24));
		credit.setBounds(545,502,783-545,128-91);
		credit.setText("" + df.format(thisStu.getStudentcredit()));
		Money = thisStu.getStudentcredit();
		add(credit);
		credit.setOpaque(false);
		credit.setBorder(new EmptyBorder(0,0,0,0));

		origion = new JTextField(); //籍贯
		origion.setText("null");
		origion.setEditable(false);
		origion.setFont(new Font("华文行楷", Font.PLAIN, 24));
		origion.setBounds(545,267,783-545,128-91);
		origion.setText(thisStu.getStudentorigion());
		origion.setOpaque(false);
		origion.setBorder(new EmptyBorder(0,0,0,0));

		
		final JComboBox gender = new JComboBox();  //男女的下拉框
		gender.setEnabled(false);
		gender.setFont(new Font("华文行楷", Font.PLAIN, 24));
		gender.addItem("男");
		gender.addItem("女");
		gender.setBounds(544,199,783-545,128-91);
		add(gender);
		if(thisStu.getStudentgender() == false) {
			gender.setSelectedIndex(1);
		}
		gender.setOpaque(false);
		
		gender.setToolTipText("");
		
		final JComboBox status = new JComboBox();  //政治面貌的下拉框
		status.setEnabled(false);
		status.setFont(new Font("宋体", Font.PLAIN, 16));
		status.addItem("群众");
		status.addItem("共青团员");
		status.addItem("中共预备党员");
		status.addItem("中共党员");
		status.addItem("民革党员");
		status.addItem("民盟盟员");
		status.addItem("民建会员");
		status.addItem("民进会员");
		status.addItem("农工党党员");
		status.addItem("致公党党员");
		status.addItem("九三学社社员");
		status.addItem("台盟盟员");
		status.addItem("无党派人士");
		status.setBounds(169,504,783-545,128-91);
		status.setOpaque(false);
		//下面的代码用于显示默认的政治面貌
		if(thisStu.getStudentstatus() != null) {
			statusmy = thisStu.getStudentstatus().replaceAll("\\p{C}", "");
			if(statusmy.length() != 0) {
				switch(statusmy) {
				case "群众":
				{
					status.setSelectedIndex(0);
				}
				break;
				case "共青团员":
				{
					status.setSelectedIndex(1);
				}
				break;
				case "中共预备党员":
				{
					status.setSelectedIndex(2);
				}
				break;
				case "中共党员":
				{
					status.setSelectedIndex(3);
				}
				break;
				case "民革党员":
				{
					status.setSelectedIndex(4);
				}
				break;
				case "民盟盟员":
				{
					status.setSelectedIndex(5);
				}
				break;
				case "民建会员":
				{
					status.setSelectedIndex(6);
				}
				break;
				case "民进会员":
				{
					status.setSelectedIndex(7);
				}
				break;
				case "农工党党员":
				{
					status.setSelectedIndex(8);
				}
				break;
				case "致公党党员":
				{
					status.setSelectedIndex(9);
				}
				break;
				case "九三学社社员":
				{
					status.setSelectedIndex(10);
				}
				break;
				case "台盟盟员":
				{
					status.setSelectedIndex(11);
				}
				break;
				case "无党派人士":
				{
					status.setSelectedIndex(12);
				}
				break;
				default:
					break;
				}
			}
		}
		add(status);
		add(backgroundImageLabel);//这行代码出现在按钮之前，TextField之后


		//修改按钮
		final JButton modifybutton = new JButton("修改");
		modifybutton.setFont(new Font("新宋体", Font.PLAIN, 20));
		modifybutton.setBounds(404,579,262-166,608-579);
		add(modifybutton);
		modifybutton.setOpaque(false);
		//充值按钮
		JButton investbutton = new JButton("充值");
		investbutton.setFont(new Font("宋体", Font.PLAIN, 20));
		investbutton.setBounds(166,579,262-166,608-579);
		add(investbutton);
		investbutton.setOpaque(false);

		//修改按钮监听事件
		modifybutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(modifybutton.getText().compareTo("修改") == 0) {

					System.out.println(statusmy);
					System.out.println(statusmy.equals("共青团员"));
					
					modifybutton.setText("保存");
					name.setEditable(true);
					
					studentid.setEditable(false);
					major.setEditable(false);
					classid.setEditable(false);
					teacherid.setEditable(false);
					origion.setEditable(true);
					phone.setEditable(true);
					status.setEnabled(true);
					gender.setEnabled(true);
				}
				else {
					if(name.getText().trim().equals("")||
							origion.getText().trim().equals("")||
							phone.getText().trim().equals("")) {
						JOptionPane.showMessageDialog(null, "请完善学生信息！", "提示", JOptionPane.WARNING_MESSAGE);
					}
					else {
						Student temp = new Student();
						temp.setClassid(classid.getText());
						temp.setMajor(major.getText());
						if(gender.getSelectedIndex() == 0) {
							temp.setStudentgender(true);//boy
						}else{
							temp.setStudentgender(false);//girl
						}
						temp.setStudentid(studentid.getText());
						temp.setStudentName(name.getText());
						temp.setStudentorigion(origion.getText());
						temp.setStudentphone(phone.getText());//remember to check
						temp.setTeacherid(teacherid.getText());
						switch (gender.getSelectedIndex()) {
						case 0:
							temp.setStudentgender(true);
							break;
						case 1:
							temp.setStudentgender(false);
							break;
						default:
							break;
						}
						switch(status.getSelectedIndex()) {
						case 0:
							temp.setStudentstatus("群众");
							break;
						case 1:
							temp.setStudentstatus("共青团员");
							break;
						case 2:
							temp.setStudentstatus("中共预备党员");
							break;
						case 3:
							temp.setStudentstatus("中共党员");
							break;
						case 4:
							temp.setStudentstatus("民革党员");
							break;
						case 5:
							temp.setStudentstatus("民盟盟员");
							break;
						case 6:
							temp.setStudentstatus("民建会员");
							break;
						case 7:
							temp.setStudentstatus("民进会员");
							break;
						case 8:
							temp.setStudentstatus("农工党党员");
							break;
						case 9:
							temp.setStudentstatus("致公党党员");
							break;
						case 10:
							temp.setStudentstatus("九三学社社员");
							break;
						case 11:
							temp.setStudentstatus("台盟盟员");
							break;
						case 12:
							temp.setStudentstatus("无党派人士");
							break;
						default:
							break;

						}
						
						if(temp.getStudentphone().length()!=11) {
							JOptionPane.showMessageDialog(null, "请正确填写电话号码（11位）！", "提示", JOptionPane.WARNING_MESSAGE);
						}else {
							save(temp);
							modifybutton.setText("修改");
							name.setEditable(false);
							studentid.setEditable(false);
							major.setEditable(false);
							classid.setEditable(false);
							teacherid.setEditable(false);
							origion.setEditable(false);
							phone.setEditable(false);
							status.setEnabled(false);
							gender.setEnabled(false);
						}
					}
				}
			}
		});

		//充值按钮监听事件
		investbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setinvestframe();  //单独一个框架
			}
		});


		//退出按钮
		JButton exitbutton = new JButton("退出");
		exitbutton.setBounds(655,575,262-166,608-579);
		add(exitbutton);
		exitbutton.setOpaque(false);

		//退出按钮监听事件
		exitbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(modifybutton.getText().compareTo("修改") == 0) {
					close();
				}
				else {    //不保存直接退出
					//exit without saving
					int input = JOptionPane.showConfirmDialog(null, "确认放弃该操作吗？", "提示",JOptionPane.YES_NO_OPTION);
					if(input == 0)
					{
						modifybutton.setText("修改");
						name.setEditable(false);
						studentid.setEditable(false);
						major.setEditable(false);
						classid.setEditable(false);
						teacherid.setEditable(false);
						origion.setEditable(false);
						phone.setEditable(false);
						status.setEnabled(false);
						gender.setEnabled(false);
					}
				}
			}
		});
		exitbutton.setFont(new Font("新宋体", Font.PLAIN, 20));

		
		/*
		contentPane=new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(248)
							.addComponent(lblNewLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(77, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblNewLabel_5)
											.addGap(38)
											.addComponent(teacherid, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblNewLabel_4)
											.addGap(38)
											.addComponent(classid, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)))
									.addGap(37))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(userimage, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
									.addGap(74)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_8)
								.addComponent(lblNewLabel_9)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel_10, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(credit, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblNewLabel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_6, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_7, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGap(59)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(studentid)
										.addComponent(gender, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
										.addComponent(name, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
										.addComponent(origion, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(4)
													.addComponent(phone, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
												.addComponent(major)
												.addComponent(lblNewLabel_11, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(4)
													.addComponent(status, 0, 171, Short.MAX_VALUE)))
											.addPreferredGap(ComponentPlacement.RELATED))))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(42)
							.addComponent(modifybutton, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addGap(155)
							.addComponent(investbutton, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addGap(159)
							.addComponent(exitbutton, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
					.addGap(44))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(33)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(studentid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_6)
								.addComponent(gender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_7)
								.addComponent(origion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_3)
								.addComponent(major, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(14)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_8)
								.addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18))
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(userimage, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
							.addGap(44)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_4)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(classid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_9)
							.addComponent(phone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_5)
						.addComponent(teacherid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_10)
							.addComponent(credit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_11)))
					.addGap(24)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(modifybutton)
						.addComponent(investbutton)
						.addComponent(exitbutton))
					.addGap(43))
		);
		contentPane.setLayout(gl_contentPane);

		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setIcon(new ImageIcon("VCampusClient/src/main/resources/image/student3.png"));
//		lblNewLabel_1.setIcon(new ImageIcon(this.getClass().getResource("/resources/image/student3.png")));
		lblNewLabel_1.setBounds(0, 0, 700, 623);
		this.getContentPane().add(lblNewLabel_1);*/
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(2);
	}


	//保存修改之后的信息
	void save(Student temp) {
		Message mes = new Message();
		mes.setModuleType(ModuleType.Student);
		mes.setMessageType(MessageType.ClassAdminUpdate);
		List<Object> sendData = new ArrayList();
		sendData.add(15);//name, origin, phone, status, gender ---- id
		sendData.add(temp.getStudentName());
		sendData.add(temp.getStudentorigion());
		sendData.add(temp.getStudentphone());
		sendData.add(temp.getStudentstatus());
		sendData.add(temp.getStudentgender());
		sendData.add(temp.getStudentid());
		mes.setData(sendData);

		Client client = new Client(ClientMainFrame.socket);

		Message serverResponse = new Message();
		serverResponse = client.sendRequestToServer(mes);
		int res = (int) serverResponse.getData();
		
		this.Name = temp.getStudentName();
	}
	public void setinvestframe() {
		this.setEnabled(false);
		this.setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
		ClassStudentForInvest frame = new ClassStudentForInvest(this, Money, ID, PWD);
		frame.setVisible(true);
	}
	//更新
	public void update(Double temp) {
		DecimalFormat df = new DecimalFormat("0.00");
		credit.setText("" + df.format(temp));
		Money = temp;
	}

   //关闭
	void close() {
		this.dispose();
		Mainmenu.set(Name, Money);
	}
}
