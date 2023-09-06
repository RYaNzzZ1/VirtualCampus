package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ClassAdminForAdd extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model1;
	private DefaultTableModel model2;
	private Vector<Student> StuAll = null;
	private Vector<ClassManage> ClssAll = null;

	private enum MODEL {
		ClASSADD, STUDENTADD
	};
	private MODEL now = MODEL.STUDENTADD;
	private ClassAdminClient CAC = null;
	private JLabel lblNewLabel_1;

	public ClassAdminForAdd(final ClassAdminClient cac, Vector<Student> Stu, Vector<ClassManage> Clss) {
		CAC = cac;
		StuAll = Stu;
		ClssAll = Clss;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setTitle("学生管理界面");
		JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/ClassAdminForAdd.png"));
		Toolkit k = Toolkit.getDefaultToolkit();
		Dimension d = k.getScreenSize();
		setBounds(d.width/2-845/2, d.height/2-290, 845,605);
		backgroundImageLabel.setBounds(0, 0, 845,580);
		setResizable(false);
		setLayout(null);

		//2.绘制退出按钮
		//得到鼠标的坐标（用于推算对话框应该摆放的坐标）
   /*  backgroundImageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
			}
        });*/

		final JComboBox selectmode = new JComboBox();
		selectmode.addItem("学生");
		selectmode.addItem("班级");;
		selectmode.setBounds(309,132,477-309,170-132);
		add(selectmode);
		selectmode.setFont(new Font("华文行楷",Font.BOLD,36));
		JScrollPane scrollPane = new JScrollPane();
		table = new JTable();
		model1 = new DefaultTableModel(new Object[][] {{null, null, null, null}},
				new String[] { "班级", "学号", "姓名", "电话" });
		model2 = new DefaultTableModel(new Object[][] {{null, null, null, null}},
				new String[] { "班级", "老师", "专业" });
		if((selectmode.getSelectedItem().toString()).equalsIgnoreCase("学生")) {
			table.setModel(model1);
			now = MODEL.STUDENTADD;
			//透明化处理
			table.setForeground(Color.BLACK);
			table.setFont(new Font("楷体", Font.BOLD, 20));
			table.setRowHeight(73);			//表格行高
			table.setPreferredScrollableViewportSize(new Dimension(850, 500));
			table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			String []str= {"班级", "学号", "姓名", "电话"};
			renderer.setOpaque(false);    //设置透明
			for(int i=0;i<str.length;i++){
				table.getColumn(str[i]).setCellRenderer(renderer);//单格渲染
				TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
				column.setHeaderRenderer(renderer);//表头渲染
			}
			table.setOpaque(false);
			table.getTableHeader().setOpaque(false);
			table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
			scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			scrollPane.setColumnHeaderView(table.getTableHeader());
			scrollPane.getColumnHeader().setOpaque(false);


		} else {
			table.setModel(model2);
			now = MODEL.ClASSADD;
			//透明化处理
			table.setForeground(Color.BLACK);
			table.setFont(new Font("楷体", Font.BOLD, 20));
			table.setRowHeight(73);    			//表格行高
			table.setPreferredScrollableViewportSize(new Dimension(850, 500));
			table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			String []str= { "班级", "老师", "专业" };
			renderer.setOpaque(false);    //设置透明
			for(int i=0;i<str.length;i++){
				table.getColumn(str[i]).setCellRenderer(renderer);//单格渲染
				TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
				column.setHeaderRenderer(renderer);//表头渲染
			}
			table.setOpaque(false);
			table.getTableHeader().setOpaque(false);
			table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
			scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
			scrollPane.setOpaque(false);
			scrollPane.getViewport().setOpaque(false);
			scrollPane.setColumnHeaderView(table.getTableHeader());
			scrollPane.getColumnHeader().setOpaque(false);
		}

		selectmode.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if((selectmode.getSelectedItem().toString()).equalsIgnoreCase("学生")) {
					table.setModel(model1);
					now = MODEL.STUDENTADD;

					//透明化处理
					table.setForeground(Color.BLACK);
					table.setFont(new Font("楷体", Font.BOLD, 20));
					table.setRowHeight(73);   			//表格行高
					table.setPreferredScrollableViewportSize(new Dimension(850, 500));
					table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
					DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
					String []str= {"班级", "学号", "姓名", "电话"};
					renderer.setOpaque(false);    //设置透明
					for(int i=0;i<str.length;i++){
						table.getColumn(str[i]).setCellRenderer(renderer);//单格渲染
						TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
						column.setHeaderRenderer(renderer);//表头渲染
					}
					table.setOpaque(false);
					table.getTableHeader().setOpaque(false);
					table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
					scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
					scrollPane.setOpaque(false);
					scrollPane.getViewport().setOpaque(false);
					scrollPane.setColumnHeaderView(table.getTableHeader());
					scrollPane.getColumnHeader().setOpaque(false);


				} else {
					table.setModel(model2);
					now = MODEL.ClASSADD;
					//透明化处理
					table.setForeground(Color.BLACK);
					table.setFont(new Font("楷体", Font.BOLD, 20));
					table.setRowHeight(73);   			//表格行高
					table.setPreferredScrollableViewportSize(new Dimension(850, 500));
					table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
					DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
					String []str= { "班级", "老师", "专业" };
					renderer.setOpaque(false);    //设置透明
					for(int i=0;i<str.length;i++){
						table.getColumn(str[i]).setCellRenderer(renderer);//单格渲染
						TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
						column.setHeaderRenderer(renderer);//表头渲染
					}
					table.setOpaque(false);
					table.getTableHeader().setOpaque(false);
					table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
					scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
					scrollPane.setOpaque(false);
					scrollPane.getViewport().setOpaque(false);
					scrollPane.setColumnHeaderView(table.getTableHeader());
					scrollPane.getColumnHeader().setOpaque(false);
				}
			}

		});
		table.setBounds(0,0,748-134,370-241);
		scrollPane.setBounds(134,241,748-134,370-241);
		scrollPane.setViewportView(table);
		add(backgroundImageLabel);
		add(scrollPane);


		add(backgroundImageLabel);
		//两个按钮
		JButton Commitbtn = new JButton("确定");
		Commitbtn.setBounds(263,425,364-263,478-425);
		add(Commitbtn);
		JButton exitbtn = new JButton("返回");
		exitbtn.setBounds(527,425,364-263,478-425);
		add(exitbtn);

		Commitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean Modified = false;
				if((selectmode.getSelectedItem().toString()).equalsIgnoreCase("学生")) {
					//班级，学号， 姓名，电话
					int clssid = 0;
					if(table.getValueAt(0, 0) == null ||
							table.getValueAt(0, 1) == null ||
							table.getValueAt(0, 2) == null ||
							table.getValueAt(0, 3) == null) {
						JOptionPane.showMessageDialog(null, "请完善学生信息！", "提示", JOptionPane.WARNING_MESSAGE);
					} else {
						Student stu = new Student();
						stu.setClassid(table.getValueAt(0, 0).toString());
						stu.setStudentid(table.getValueAt(0, 1).toString());
						stu.setStudentName(table.getValueAt(0, 2).toString());
						stu.setStudentphone(table.getValueAt(0, 3).toString());
						String newclssid = stu.getClassid().replaceAll("\\p{C}", "");
						String newid = stu.getStudentid().replaceAll("\\p{C}", "");
						String newname = stu.getStudentName().replaceAll("\\p{C}", "");
						String newphone = stu.getStudentphone().replaceAll("\\p{C}", "");

						if(!newphone.matches("^1\\d{10}$")) {
							JOptionPane.showMessageDialog(null, "请正确填写电话号码（11位）！", "提示", JOptionPane.WARNING_MESSAGE);
						}else {

							//add
							Boolean flag = true;

							//check the id is unique
							int i = 0;
							while(i < StuAll.size()) {
								String tempid = StuAll.get(i).getStudentid();
								tempid.replaceAll("\\p{C}", "");
								if(tempid.equals(newid)) {
									flag = false;
									break;
								}
								i++;
							}

							if(flag) {
								flag = false;
								i = 0;
								int oldclasssize = 0;
								//check the class exist
								while(i < ClssAll.size()) {
									String tempclss = ClssAll.get(i).getClassID();
									tempclss.replaceAll("\\p{C}", "");
									if(tempclss.equals(newclssid)) {
										oldclasssize= ClssAll.get(i).getClassSize() + 1;
										ClssAll.get(i).setClassSize(oldclasssize);
										flag = true;
										clssid = i;
										break;
									}
									i++;
								}

								if(flag) {
									Message mes = new Message();
									mes.setModuleType(ModuleType.Student);
									mes.setMessageType(MessageType.ClassUpdate);
									List<Object> sendData = new ArrayList<Object>();
									sendData.add(4);
									sendData.add(oldclasssize);
									sendData.add(stu.getClassid());
									mes.setData(sendData);

									Client client = new Client(ClientMainFrame.socket);

									Message serverResponse = new Message();
									serverResponse = client.sendRequestToServer(mes);
									int res = (int) serverResponse.getData();
									System.out.println("update class size");


									//add student here
									Modified = true;
									mes = null;
									mes = new Message();
									mes.setModuleType(ModuleType.Student);
									mes.setMessageType(MessageType.ClassAdminAdd);
									stu.setMajor(ClssAll.get(clssid).getMajor());
									stu.setTeacherid(ClssAll.get(clssid).getTeacherID());
									mes.setData(stu);

									client = null;
									client = new Client(ClientMainFrame.socket);

									serverResponse = null;
									serverResponse = new Message();
									serverResponse = client.sendRequestToServer(mes);
									res = (int)serverResponse.getData();
									System.out.println("Add Student Confirmed!");

									StuAll.add(stu);

									clear();
								}else {
									JOptionPane.showMessageDialog(null, "不存在该班级！", "提示", JOptionPane.WARNING_MESSAGE);
								}

							}else {
								JOptionPane.showMessageDialog(null, "该学号的学生已经存在！", "提示", JOptionPane.WARNING_MESSAGE);
							}

						}
					}
					//透明化处理
					table.setForeground(Color.BLACK);
					table.setFont(new Font("楷体", Font.BOLD, 20));
					table.setRowHeight(73);  			//表格行高
					table.setPreferredScrollableViewportSize(new Dimension(850, 500));
					table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
					DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
					renderer.setOpaque(false);    //设置透明
					String []Names={ "班级", "学号", "姓名", "电话" };
					for(int i=0;i<4;i++){
						table.getColumn(Names[i]).setCellRenderer(renderer);//单格渲染
						TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
						column.setHeaderRenderer(renderer);//表头渲染
					}
					table.setOpaque(false);
					table.getTableHeader().setOpaque(false);
					table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
					scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
					scrollPane.setOpaque(false);
					scrollPane.getViewport().setOpaque(false);
					scrollPane.setColumnHeaderView(table.getTableHeader());
					scrollPane.getColumnHeader().setOpaque(false);
				} else {
					//班级， 老师， 专业
					if(table.getValueAt(0, 0) == null ||
							table.getValueAt(0, 1) == null ||
							table.getValueAt(0,  2) == null) {
						JOptionPane.showMessageDialog(null, "请完善班级信息！", "提示", JOptionPane.WARNING_MESSAGE);
					}else {
						ClassManage clss = new ClassManage();
						clss.setClassID(table.getValueAt(0, 0).toString());
						clss.setMajor(table.getValueAt(0, 2).toString());
						clss.setTeacherID(table.getValueAt(0, 1).toString());
						clss.setClassSize(0);
						String newclssid = clss.getClassID();
						newclssid.replaceAll("\\p{C}", "");
						String newteacher = clss.getTeacherID();
						newteacher.replaceAll("\\p{C}", "");
						String newmajor = clss.getMajor();
						newmajor.replaceAll("\\p{C}", "");


						//add
						Boolean flag = true;

						//check the id is unique
						int i = 0;
						while(i < ClssAll.size()) {
							String temp = ClssAll.get(i).getClassID();
							temp.replaceAll("\\p{C}", "");
							if(temp.equals(newclssid)) {
								flag = false;
								break;
							}
							i++;
						}

						if(flag) {
							Modified = true;
							Message mes = new Message();
							mes.setModuleType(ModuleType.Student);
							mes.setMessageType(MessageType.ClassAdd);
							mes.setData(clss);

							Client client = new Client(ClientMainFrame.socket);

							Message serverResponse = new Message();
							serverResponse = client.sendRequestToServer(mes);
							int res = (int)serverResponse.getData();
							System.out.println("Add Class Confirmed!");

							ClssAll.add(clss);
							clear();
						}else {
							JOptionPane.showMessageDialog(null, "该班级已经存在！", "提示", JOptionPane.WARNING_MESSAGE);
						}


					}
					//透明化处理
					table.setForeground(Color.BLACK);
					table.setFont(new Font("楷体", Font.BOLD, 20));
					table.setRowHeight(73);    			//表格行高
					table.setPreferredScrollableViewportSize(new Dimension(850, 500));
					table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
					DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
					renderer.setOpaque(false);    //设置透明
					String []Names={ "班级", "老师", "专业" };
					for(int i=0;i<3;i++){
						table.getColumn(Names[i]).setCellRenderer(renderer);//单格渲染
						TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
						column.setHeaderRenderer(renderer);//表头渲染
					}
					table.setOpaque(false);
					table.getTableHeader().setOpaque(false);
					table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
					scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
					scrollPane.setOpaque(false);
					scrollPane.getViewport().setOpaque(false);
					scrollPane.setColumnHeaderView(table.getTableHeader());
					scrollPane.getColumnHeader().setOpaque(false);
				}
				if(Modified) {
					JOptionPane.showMessageDialog(null, "增加成功", "提示", JOptionPane.WARNING_MESSAGE);
				}
				table.setEnabled(true);
			}
		});



		exitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cac.setEnabled(true);
				close();
			}
		});
		exitbtn.setOpaque(false);
		Commitbtn.setOpaque(false);
	}

	void clear() {
		if(now == MODEL.STUDENTADD) {
			table.setModel(model1);
			table.setValueAt("", 0, 0);
			table.setValueAt("", 0, 1);
			table.setValueAt("", 0, 2);
			table.setValueAt("", 0, 3);
		} else {
			table.setModel(model2);
			table.setValueAt("", 0, 0);
			table.setValueAt("", 0, 1);
			table.setValueAt("", 0, 2);
		}
	}

	void close() {
		CAC.setEnabled(true);
		CAC.updateFrame(StuAll, ClssAll);
		this.dispose();
	}
}
