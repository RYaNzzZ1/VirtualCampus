package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ClassAdminForModify extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField searchdata;
	private DefaultTableModel model1;
	private DefaultTableModel model2;
	private ClassAdminClient CAC = null;
	private Vector<Student> StuAll = null;
	private Vector<ClassManage> ClssAll = null;
	private Vector<Student> StudentTemp = null;
	private Vector<ClassManage> ClassTemp = null;
	private Vector<Integer> StudentIndex = null;
	private Vector<Integer> ClassIndex = null;
	private JLabel lblNewLabel_1;
	private  JScrollPane scrollPane;

	private enum MODEL {
		ClASSMODIFY, STUDENTMODIFY, CLASSTEMP, STUDENTTEMP
	};

	private MODEL now = MODEL.STUDENTMODIFY;
	public ClassAdminForModify(final ClassAdminClient cac, Vector<Student> Stu, Vector<ClassManage> Clss) {
		CAC = cac;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("学生管理界面");
		//背景图片
		JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/ClassAdminForModify.png"));
		Toolkit k = Toolkit.getDefaultToolkit();
		Dimension d = k.getScreenSize();
		setBounds(d.width/2-949/2, d.height/2-534/2,949,555);
		backgroundImageLabel.setBounds(0, 0, 949,534);
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
        });
*/

		Font f=new Font("华文行楷",Font.BOLD,24);
		final JComboBox selectmode = new JComboBox();
		selectmode.setFont(f);
		selectmode.addItem("学生");
		selectmode.addItem("班级");
		selectmode.setBounds(165,75,150,108-75);
		add(selectmode);


		final JComboBox searchbtn = new JComboBox();
		searchbtn.setFont(f);
		searchbtn.setBounds(457,80,130,112-77);
		add(searchbtn);

		scrollPane = new JScrollPane();
		searchdata = new JTextField();
		searchdata.setFont(f);
		searchdata.setBounds(618,77,785-618,112-77);
		add(searchdata);
		searchdata.setOpaque(false);
		searchdata.setBorder(new EmptyBorder(0,0,0,0));

		table = new JTable();
		table.setFont(new Font("Adobe 仿宋 Std R", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setBounds(0,0,811-182,443-126);
		scrollPane.setBounds(182,126,811-182,443-126);
		add(scrollPane);

		model1 = new DefaultTableModel(new Object[][] {},
				new String[] { "班级", "学号", "姓名", "电话" });
		model2 = new DefaultTableModel(new Object[][] {},
				new String[] { "班级", "老师", "专业" });

		StuAll = Stu;
		ClssAll = Clss;
		if ((selectmode.getSelectedItem().toString()).equalsIgnoreCase("学生")) {
			table.setModel(model1);// student
			table.getColumnModel().getColumn(3).setPreferredWidth(144);
			getStudent();
			now = MODEL.STUDENTMODIFY;
			searchbtn.addItem("全部");
			searchbtn.addItem("班级");
			searchbtn.addItem("学号");
			searchbtn.addItem("姓名");
			//透明化处理
			table.setForeground(Color.BLACK);
			table.setFont(new Font("华文行楷", Font.BOLD, 20));
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
			table.setModel(model2);// class
			getClass_all();
			now = MODEL.ClASSMODIFY;
			searchbtn.addItem("全部");
			searchbtn.addItem("班级");
			searchbtn.addItem("专业");
			searchbtn.addItem("老师");
			//透明化处理
			table.setForeground(Color.BLACK);
			table.setFont(new Font("华文行楷", Font.BOLD, 20));
			table.setRowHeight(73);			//表格行高
			table.setPreferredScrollableViewportSize(new Dimension(850, 500));
			table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			String []str= {"班级", "老师","专业"};
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
				if (selectmode.getSelectedIndex() == 0) {
					// student
					if(now == MODEL.STUDENTMODIFY) {
						//empty do nothing
					}else {
						while(table.getRowCount() > 0) {
							model2.removeRow(table.getRowCount() - 1);
							table.setModel(model2);
						}
						table.setModel(model1);
						getStudent();
						now = MODEL.STUDENTMODIFY;
						searchbtn.removeItemAt(2);
						searchbtn.removeItemAt(2);
						searchbtn.addItem("学号");
						searchbtn.addItem("姓名");
						System.out.println("Model Change");
						//透明化处理
						table.setForeground(Color.BLACK);
						table.setFont(new Font("华文行楷", Font.BOLD, 20));
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
					}
				} else {
					// class modify
					if(now == MODEL.ClASSMODIFY) {
						//empty body
					}else {
						while(table.getRowCount() > 0) {
							//System.out.println(table.getRowCount() - 1);
							model1.removeRow(table.getRowCount() - 1);
							table.setModel(model1);
						}
						table.setModel(model2);
						getClass_all();
						now = MODEL.ClASSMODIFY;
						searchbtn.removeItemAt(2);
						searchbtn.removeItemAt(2);
						searchbtn.addItem("专业");
						searchbtn.addItem("老师");
						System.out.println("Model Change");
						//透明化处理
						table.setForeground(Color.BLACK);
						table.setFont(new Font("华文行楷", Font.BOLD, 20));
						table.setRowHeight(73);			//表格行高
						table.setPreferredScrollableViewportSize(new Dimension(850, 500));
						table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
						DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
						String []str= {"班级", "老师","专业"};
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
			}

		});
		add (backgroundImageLabel);
		JButton Commitbtn = new JButton("确定");
		Commitbtn.setFont(new Font("宋体", Font.PLAIN, 18));
		Commitbtn.setBounds(300,453,86,111-77);
		add(Commitbtn);
		Commitbtn.setOpaque(false);

		JButton exitbtn = new JButton("返回");
		exitbtn.setFont(new Font("宋体", Font.PLAIN, 18));
		exitbtn.setBounds(585,453,86,111-77);
		add(exitbtn);
		exitbtn.setOpaque(false);
		Commitbtn.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				Boolean CommitFlag = true;
				if (now == MODEL.STUDENTMODIFY) {
					// 班级，学号， 姓名，电话
					int rownow = table.getRowCount();
					int i_row = 0;
					Boolean Modified = false;
					while (i_row < rownow && CommitFlag) {
						// check edited student
						Student stunow = new Student();
						stunow.setClassid(table.getValueAt(i_row, 0).toString());
						stunow.setStudentid(table.getValueAt(i_row, 1).toString());
						stunow.setStudentName(table.getValueAt(i_row, 2).toString());
						stunow.setStudentphone(table.getValueAt(i_row, 3).toString());

						String newclssid = stunow.getClassid().replaceAll("\\p{C}", "");
						String newid = stunow.getStudentid().replaceAll("\\p{C}", "");
						String newname = stunow.getStudentName().replaceAll("\\p{C}", "");
						String newphone = stunow.getStudentphone().replaceAll("\\p{C}", "");

						String oldclssid = StuAll.get(i_row).getClassid().replaceAll("\\p{C}", "");
						String oldid = StuAll.get(i_row).getStudentid().replaceAll("\\p{C}", "");
						String oldname = StuAll.get(i_row).getStudentName().replaceAll("\\p{C}", "");
						String oldphone = StuAll.get(i_row).getStudentphone().replaceAll("\\p{C}", "");

						//System.out.println(newname);
						if(!newid.equals(oldid)) {
							int studenttemp = 0;
							Boolean okay = true;
							while(studenttemp < StuAll.size()) {
								String tempid = StuAll.get(studenttemp).getStudentid();
								tempid.replaceAll("\\p{C}", "");
								if(tempid.equals(newid)) {
									okay = false;
									break;
								}
								studenttemp++;
							}
							if(!okay) {
								JOptionPane.showMessageDialog(null, "已存在该学号的学生！", "提示", JOptionPane.WARNING_MESSAGE);
								CommitFlag = false;
								//update table
								UpdateStudent(StuAll);
								break;
							}
						}
						if(!newclssid.equals(oldclssid)) {
							int classtempforadd = 0;
							int newclasssize = 0;
							Boolean newclass = false;
							while(classtempforadd < ClssAll.size() && !newclass) {
								String tempclss = ClssAll.get(classtempforadd).getClassID();
								tempclss.replaceAll("\\p{C}", "");
								if(tempclss.equals(newclssid)) {
									newclass = true;
									newclasssize = ClssAll.get(classtempforadd).getClassSize() + 1;
									ClssAll.get(classtempforadd).setClassSize(newclasssize);
								}
								classtempforadd++;
							}
							if(!newclass) {
								CommitFlag = false;
								JOptionPane.showMessageDialog(null, "不存在该班级！", "提示", JOptionPane.WARNING_MESSAGE);
								//update table
								UpdateStudent(StuAll);
								break;
							}else {
								Message mes = new Message();
								mes.setModuleType(ModuleType.Student);
								mes.setMessageType(MessageType.ClassUpdate);
								List<Object> sendData = new ArrayList<Object>();
								sendData.add(4);
								sendData.add(newclasssize);
								sendData.add(oldclssid);
								mes.setData(sendData);

								Client client = new Client(ClientMainFrame.socket);

								Message serverResponse = new Message();
								serverResponse = client.sendRequestToServer(mes);
								int res = (int) serverResponse.getData();
								System.out.println("update class size");
							}
						}

						if(!newclssid.equals(oldclssid)) {
							int classtempforadd = 0;
							int oldclasssize = 0;
							Boolean oldclass = false;
							while(classtempforadd < ClssAll.size() &&!oldclass) {
								String tempclss = ClssAll.get(classtempforadd).getClassID();
								tempclss.replaceAll("\\p{C}", "");
								if(tempclss.equals(newclssid)) {
									oldclass = true;
									oldclasssize = ClssAll.get(classtempforadd).getClassSize() - 1;
									ClssAll.get(classtempforadd).setClassSize(oldclasssize);
								}
								classtempforadd++;
							}
							Message mes = new Message();
							mes.setModuleType(ModuleType.Student);
							mes.setMessageType(MessageType.ClassUpdate);
							List<Object> sendData = new ArrayList<Object>();
							sendData.add(4);
							sendData.add(oldclasssize);
							sendData.add(oldclssid);
							mes.setData(sendData);

							Client client = new Client(ClientMainFrame.socket);

							Message serverResponse = new Message();
							serverResponse = client.sendRequestToServer(mes);
							int res = (int) serverResponse.getData();
							System.out.println("update class size");
						}

						if (!newclssid.equals(oldclssid)
								|| !newname.equals(oldname)
								|| !newphone.equals(oldphone)
								|| !newid.equals(oldid)) {
							Modified = true;
//modify studentmanage student
							Message mes = new Message();
							mes.setModuleType(ModuleType.Student);
							mes.setMessageType(MessageType.ClassAdminUpdate);
							List<Object> sendData = new ArrayList<Object>();
							sendData.add(10);// type -- update classid + studentid + studentname + studentphone
							sendData.add(newclssid);// new data classid
							//System.out.println(newclssid);
							sendData.add(newid);// new data
							//System.out.println(newid);
							sendData.add(newname);
							//System.out.println(newname);
							sendData.add(newphone);
							//System.out.println(newphone);
							sendData.add(oldid);//anchor
							//System.out.println(oldid);
							mes.setData(sendData);

							Client client = new Client(ClientMainFrame.socket);

							Message serverResponse = new Message();
							serverResponse = client.sendRequestToServer(mes);
							int res = (int) serverResponse.getData();
							table.setValueAt(newclssid, i_row, 0);// update table
							table.setValueAt(newid, i_row, 1);
							table.setValueAt(newname, i_row, 2);
							table.setValueAt(newphone, i_row, 3);
							StuAll.get(i_row).setClassid(newclssid);// update vector
							StuAll.get(i_row).setStudentid(newid);
							StuAll.get(i_row).setStudentName(newname);
							StuAll.get(i_row).setStudentphone(newphone);
							System.out.println("Student information is updated at Client!");

							if(!newid.equals(oldid)) {
//update dormitory student here(update oldid with newid)
								Dormitory c=new Dormitory();
								c.setuserID(newid);
								c.setDormitoryID("");
								c.setStudentBunkID(0);
								c.setDormitoryScore(0);
								c.setWater(0);
								c.setElectricity(0);
								c.setDormitoryMaintain("");
								c.setStudentExchange("");
								mes = null;
								mes = new Message();
								mes.setModuleType(ModuleType.Dormitory);
								mes.setMessageType(MessageType.DormUpdate);
								Vector<String> tempdata = new Vector<String>();
								tempdata.add(oldid);
								tempdata.add(newid);
								mes.setContent(tempdata);

								client = null;
								client = new Client(ClientMainFrame.socket);

								serverResponse = null;
								serverResponse = new Message();
								serverResponse = client.sendRequestToServer(mes);
								System.out.println("update user dormitory confirmed!");
//update user here(update oldid with newid)
								User user = new User();
								mes = null;
								mes = new Message();
								mes.setModuleType(ModuleType.User);
								mes.setMessageType(MessageType.REQ_USERUPDATE);
								user.setId(oldid);
								user.setAge("");
								user.setGrade("");
								user.setMajor("");
								user.setMoney("");
								user.setName(oldname);
								user.setPwd("");
								user.setRole("0");
								user.setSex("男");
								mes.setContent(user.getContent());
								tempdata = null;
								tempdata = new Vector<String>();
								tempdata.add(oldid);
								tempdata.add(newid);
								mes.setData(tempdata);

								client = null;
								client = new Client(ClientMainFrame.socket);

								serverResponse = null;
								serverResponse = new Message();
								serverResponse = client.sendRequestToServer(mes);
								System.out.println("update user confirmed!");
							}//end of update of other table
						}
						i_row++;
					}//end of while
					if(Modified) {
						//update
						UpdateStudent(StuAll);
						JOptionPane.showMessageDialog(null, "成功保存修改！", "提示", JOptionPane.WARNING_MESSAGE);
					}
				} else if(now == MODEL.ClASSMODIFY){
					// 班级， 老师， 专业
					int rownow = table.getRowCount();
					int i_row = 0;
					int input = 1;
					Boolean hint = false;
					Boolean Modified = false;
					while (i_row < rownow) {
						// check edited student
						ClassManage clssnow = new ClassManage();
						clssnow.setClassID(table.getValueAt(i_row, 0).toString());
						clssnow.setTeacherID(table.getValueAt(i_row, 1).toString());
						clssnow.setMajor(table.getValueAt(i_row, 2).toString());

						String newclssid = clssnow.getClassID();
						newclssid.replaceAll("\\p{C}", "");
						String newteacher = clssnow.getTeacherID();
						newteacher.replaceAll("\\p{C}", "");
						String newmajor = clssnow.getMajor();
						newmajor.replaceAll("\\p{C}", "");

						String oldclssid = ClssAll.get(i_row).getClassID().replaceAll("\\p{C}", "");
						String oldteacher = ClssAll.get(i_row).getTeacherID().replaceAll("\\p{C}", "");
						String oldmajor = ClssAll.get(i_row).getMajor().replaceAll("\\p{C}", "");

						if(!newclssid.equals(oldclssid)
								|| !newteacher.equals(oldteacher)
								|| !newmajor.equals(oldmajor)) {
							if(!newclssid.equals(oldclssid)) {
								int clsstemp = 0;
								Boolean okay = true;
								while(clsstemp < ClssAll.size()) {
									String temp = ClssAll.get(clsstemp).getClassID();
									temp.replaceAll("\\p{C}", "");
									if(temp.equals(newclssid)) {
										okay = false;
										break;
									}
									clsstemp++;
								}
								if(!okay) {
									JOptionPane.showMessageDialog(null, "已存在该班级！", "提示", JOptionPane.WARNING_MESSAGE);
									CommitFlag = false;
									//update table
									UpdateClass(ClssAll);
									break;
								}
							}
							if(!hint) {
								input = JOptionPane.showConfirmDialog(null, "该操作同时会更改该班学生的信息，请您确认执行该操作", "提示",JOptionPane.YES_NO_OPTION);
								hint = true;
							}
							if(input == 0) {
								//yes--update Class and Student
								Modified = true;

								if(!newmajor.equals(oldmajor)) {
									Message mes = new Message();
									mes.setModuleType(ModuleType.Student);
									mes.setMessageType(MessageType.ClassAdminUpdate);
									List<Object> sendData = new ArrayList<Object>();
									sendData.add(13);// type -- update major with classid
									sendData.add(newmajor);
									sendData.add(oldclssid);
									mes.setData(sendData);

									Client client = new Client(ClientMainFrame.socket);

									Message serverResponse = new Message();
									serverResponse = client.sendRequestToServer(mes);
									int res = (int) serverResponse.getData();

									table.setValueAt(newmajor, i_row, 2);
									ClssAll.get(i_row).setMajor(newmajor);
								}

								if(!newteacher.equals(oldteacher)) {
									Message mes = new Message();
									mes.setModuleType(ModuleType.Student);
									mes.setMessageType(MessageType.ClassAdminUpdate);
									List<Object> sendData = new ArrayList<Object>();
									sendData.add(12);// type -- update teacher with classid
									sendData.add(newteacher);
									sendData.add(oldclssid);
									mes.setData(sendData);

									Client client = new Client(ClientMainFrame.socket);

									Message serverResponse = new Message();
									serverResponse = client.sendRequestToServer(mes);
									int res = (int) serverResponse.getData();

									table.setValueAt(newteacher, i_row, 1);
									ClssAll.get(i_row).setTeacherID(newteacher);
								}

								if(!newclssid.equals(oldclssid)) {
									Message mes = new Message();
									mes.setModuleType(ModuleType.Student);
									mes.setMessageType(MessageType.ClassAdminUpdate);
									List<Object> sendData = new ArrayList<Object>();
									sendData.add(11);// type -- update classid with classid
									sendData.add(newclssid);
									sendData.add(oldclssid);
									mes.setData(sendData);

									Client client = new Client(ClientMainFrame.socket);

									Message serverResponse = new Message();
									serverResponse = client.sendRequestToServer(mes);
									int res = (int) serverResponse.getData();

									table.setValueAt(newclssid, i_row, 0);
									ClssAll.get(i_row).setClassID(newclssid);
								}

								Message mes = new Message();
								mes.setModuleType(ModuleType.Student);
								mes.setMessageType(MessageType.ClassUpdate);
								List<Object> sendData = new ArrayList<Object>();
								sendData.add(3);// type -- all
								sendData.add(newteacher);
								sendData.add(newmajor);
								sendData.add(newclssid);
								sendData.add(oldclssid);
								mes.setData(sendData);

								Client client = new Client(ClientMainFrame.socket);

								Message serverResponse = new Message();
								serverResponse = client.sendRequestToServer(mes);
								int res = (int) serverResponse.getData();

								table.setValueAt(newclssid, i_row, 0);
								table.setValueAt(newteacher, i_row, 1);
								table.setValueAt(newmajor, i_row, 2);
								ClssAll.get(i_row).setClassID(newclssid);
								ClssAll.get(i_row).setTeacherID(newteacher);
								ClssAll.get(i_row).setMajor(newmajor);
							}//end of if
							else {
								//no
								break;
							}//end of else
						}//end of ifelse
						i_row++;
					}//end of while
					if(Modified) {
						//update table
						UpdateClass(ClssAll);
						JOptionPane.showMessageDialog(null, "成功保存修改！", "提示", JOptionPane.WARNING_MESSAGE);
					}

					Message mes = new Message();
					mes.setModuleType(ModuleType.Student);
					mes.setMessageType(MessageType.ClassAdminGetAll);
					List<Object> sendData = new ArrayList<Object>();
					mes.setData(sendData);

					Client client = new Client(ClientMainFrame.socket);

					Message serverResponse = new Message();
					serverResponse = client.sendRequestToServer(mes);
					StuAll = (Vector<Student>) serverResponse.getData();
				}//end of if else of all
				else if(now == MODEL.STUDENTTEMP) {
					// 班级，学号， 姓名，电话
					int rownow = table.getRowCount();
					int i_row = 0;
					Boolean Modified = false;
					while (i_row < rownow && CommitFlag) {
						// check edited student
						Student stunow = new Student();
						stunow.setClassid(table.getValueAt(i_row, 0).toString());
						stunow.setStudentid(table.getValueAt(i_row, 1).toString());
						stunow.setStudentName(table.getValueAt(i_row, 2).toString());
						stunow.setStudentphone(table.getValueAt(i_row, 3).toString());

						String newclssid = stunow.getClassid().replaceAll("\\p{C}", "");
						String newid = stunow.getStudentid().replaceAll("\\p{C}", "");
						String newname = stunow.getStudentName().replaceAll("\\p{C}", "");
						String newphone = stunow.getStudentphone().replaceAll("\\p{C}", "");

						String oldclssid = StudentTemp.get(i_row).getClassid().replaceAll("\\p{C}", "");
						String oldid = StudentTemp.get(i_row).getStudentid().replaceAll("\\p{C}", "");
						String oldname = StudentTemp.get(i_row).getStudentName().replaceAll("\\p{C}", "");
						String oldphone = StudentTemp.get(i_row).getStudentphone().replaceAll("\\p{C}", "");

						if(!newid.equals(oldid)) {
							int studenttemp = 0;
							Boolean okay = true;
							while(studenttemp < StuAll.size()) {
								String tempid = StuAll.get(studenttemp).getStudentid();
								tempid.replaceAll("\\p{C}", "");
								if(tempid.equals(newid)) {
									okay = false;
									break;
								}
								studenttemp++;
							}
							if(!okay) {
								JOptionPane.showMessageDialog(null, "已存在该学号的学生！", "提示", JOptionPane.WARNING_MESSAGE);
								UpdateStudent(StudentTemp);
								CommitFlag = false;
								break;
							}
						}
						if(!newclssid.equals(oldclssid)) {
							int classtempforadd = 0;
							int newclasssize = 0;
							Boolean newclass = false;
							while(classtempforadd < ClssAll.size() && !newclass) {
								String tempclss = ClssAll.get(classtempforadd).getClassID();
								tempclss.replaceAll("\\p{C}", "");
								if(tempclss.equals(newclssid)) {
									newclass = true;
									newclasssize = ClssAll.get(classtempforadd).getClassSize() + 1;
									ClssAll.get(classtempforadd).setClassSize(newclasssize);
								}
								classtempforadd++;
							}
							if(!newclass) {
								CommitFlag = false;
								UpdateStudent(StudentTemp);
								JOptionPane.showMessageDialog(null, "不存在该班级！", "提示", JOptionPane.WARNING_MESSAGE);
								break;
							}else {
								Message mes = new Message();
								mes.setModuleType(ModuleType.Student);
								mes.setMessageType(MessageType.ClassUpdate);
								List<Object> sendData = new ArrayList<Object>();
								sendData.add(4);
								sendData.add(newclasssize);
								sendData.add(oldclssid);
								mes.setData(sendData);

								Client client = new Client(ClientMainFrame.socket);

								Message serverResponse = new Message();
								serverResponse = client.sendRequestToServer(mes);
								int res = (int) serverResponse.getData();
								System.out.println("update class size");
							}
						}

						if(!newclssid.equals(oldclssid)) {
							int classtempforadd = 0;
							int oldclasssize = 0;
							Boolean oldclass = false;
							while(classtempforadd < ClssAll.size() &&!oldclass) {
								String tempclss = ClssAll.get(classtempforadd).getClassID();
								tempclss.replaceAll("\\p{C}", "");
								if(tempclss.equals(newclssid)) {
									oldclass = true;
									oldclasssize = ClssAll.get(classtempforadd).getClassSize() - 1;
									ClssAll.get(classtempforadd).setClassSize(oldclasssize);
								}
								classtempforadd++;
							}
							Message mes = new Message();
							mes.setModuleType(ModuleType.Student);
							mes.setMessageType(MessageType.ClassUpdate);
							List<Object> sendData = new ArrayList<Object>();
							sendData.add(4);
							sendData.add(oldclasssize);
							sendData.add(oldclssid);
							mes.setData(sendData);

							Client client = new Client(ClientMainFrame.socket);

							Message serverResponse = new Message();
							serverResponse = client.sendRequestToServer(mes);
							int res = (int) serverResponse.getData();
							System.out.println("update class size");
						}

						if (!newclssid.equals(oldclssid)
								|| !newname.equals(oldname)
								|| !newphone.equals(oldphone)
								|| !newid.equals(oldid)) {
							Modified = true;

							Message mes = new Message();
							mes.setModuleType(ModuleType.Student);
							mes.setMessageType(MessageType.ClassAdminUpdate);
							List<Object> sendData = new ArrayList<Object>();
							sendData.add(10);// type -- update classid + studentid + studentname + studentphone
							sendData.add(newclssid);// new data classid
							//System.out.println(newclssid);
							sendData.add(newid);// new data
							//System.out.println(newid);
							sendData.add(newname);
							//System.out.println(newname);
							sendData.add(newphone);
							//System.out.println(newphone);
							sendData.add(oldid);//anchor
							//System.out.println(oldid);
							mes.setData(sendData);

							Client client = new Client(ClientMainFrame.socket);

							Message serverResponse = new Message();
							serverResponse = client.sendRequestToServer(mes);
							int res = (int) serverResponse.getData();
							table.setValueAt(newclssid, i_row, 0);// update table
							table.setValueAt(newid, i_row, 1);
							table.setValueAt(newname, i_row, 2);
							table.setValueAt(newphone, i_row, 3);
							StudentTemp.get(i_row).setClassid(newclssid);// update vector
							StudentTemp.get(i_row).setStudentid(newid);
							StudentTemp.get(i_row).setStudentName(newname);
							StudentTemp.get(i_row).setStudentphone(newphone);
							StuAll.get(StudentIndex.get(i_row)).setClassid(newclssid);
							StuAll.get(StudentIndex.get(i_row)).setStudentid(newid);
							StuAll.get(StudentIndex.get(i_row)).setStudentName(newname);
							StuAll.get(StudentIndex.get(i_row)).setStudentphone(newphone);
							System.out.println("Student information is updated at Client!");

							if (!newid.equals(oldid)) {
//update dormitory student here(update oldid with newid)
								Dormitory c=new Dormitory();
								c.setuserID(newid);
								c.setDormitoryID("");
								c.setStudentBunkID(0);
								c.setDormitoryScore(0);
								c.setWater(0);
								c.setElectricity(0);
								c.setDormitoryMaintain("");
								c.setStudentExchange("");
								mes = null;
								mes = new Message();
								mes.setModuleType(ModuleType.Dormitory);
								mes.setMessageType(MessageType.DormUpdate);
								Vector<String> tempdata = new Vector<String>();
								tempdata.add(oldid);
								tempdata.add(newid);
								mes.setContent(tempdata);

								client = null;
								client = new Client(ClientMainFrame.socket);

								serverResponse = null;
								serverResponse = new Message();
								serverResponse = client.sendRequestToServer(mes);
								System.out.println("update user dormitory confirmed!");
//update user here(update oldid with newid)
								User user = new User();
								mes = null;
								mes = new Message();
								mes.setModuleType(ModuleType.User);
								mes.setMessageType(MessageType.REQ_USERUPDATE);
								user.setId(oldid);
								user.setAge("");
								user.setGrade("");
								user.setMajor("");
								user.setMoney("");
								user.setName(oldname);
								user.setPwd("");
								user.setRole("0");
								user.setSex("男");
								mes.setContent(user.getContent());
								tempdata = null;
								tempdata = new Vector<String>();
								tempdata.add(oldid);
								tempdata.add(newid);
								mes.setData(tempdata);

								client = null;
								client = new Client(ClientMainFrame.socket);

								serverResponse = null;
								serverResponse = new Message();
								serverResponse = client.sendRequestToServer(mes);
								System.out.println("update user confirmed!");
							}//end of update of other table
						}
						i_row++;
					}//end of while
					if(Modified) {
						UpdateStudent(StudentTemp);
						JOptionPane.showMessageDialog(null, "成功保存修改！", "提示", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					// 班级， 老师， 专业
					int rownow = table.getRowCount();
					int i_row = 0;
					int input = 1;
					Boolean hint = false;
					Boolean Modified = false;
					while (i_row < rownow) {
						// check edited student
						ClassManage clssnow = new ClassManage();
						clssnow.setClassID(table.getValueAt(i_row, 0).toString());
						clssnow.setTeacherID(table.getValueAt(i_row, 1).toString());
						clssnow.setMajor(table.getValueAt(i_row, 2).toString());

						String newclssid = clssnow.getClassID();
						newclssid.replaceAll("\\p{C}", "");
						String newteacher = clssnow.getTeacherID();
						newteacher.replaceAll("\\p{C}", "");
						String newmajor = clssnow.getMajor();
						newmajor.replaceAll("\\p{C}", "");

						String oldclssid = ClassTemp.get(i_row).getClassID().replaceAll("\\p{C}", "");
						String oldteacher = ClassTemp.get(i_row).getTeacherID().replaceAll("\\p{C}", "");
						String oldmajor = ClassTemp.get(i_row).getMajor().replaceAll("\\p{C}", "");

						if(!newclssid.equals(oldclssid)
								|| !newteacher.equals(oldteacher)
								|| !newmajor.equals(oldmajor)) {
							if(!newclssid.equals(oldclssid)) {
								int clsstemp = 0;
								Boolean okay = true;
								while(clsstemp < ClssAll.size()) {
									String temp = ClssAll.get(clsstemp).getClassID();
									temp.replaceAll("\\p{C}", "");
									if(temp.equals(newclssid)) {
										okay = false;
										break;
									}
									clsstemp++;
								}
								if(!okay) {
									JOptionPane.showMessageDialog(null, "已存在该班级！", "提示", JOptionPane.WARNING_MESSAGE);
									UpdateClass(ClassTemp);
									CommitFlag = false;
									break;
								}
							}
							if(!hint) {
								input = JOptionPane.showConfirmDialog(null, "该操作同时会更改该班学生的信息，请您确认执行该操作", "提示",JOptionPane.YES_NO_OPTION);
								hint = true;
							}
							if(input == 0) {
								//yes--update Class and Student
								Modified = true;

								if(!newmajor.equals(oldmajor)) {
									Message mes = new Message();
									mes.setModuleType(ModuleType.Student);
									mes.setMessageType(MessageType.ClassAdminUpdate);
									List<Object> sendData = new ArrayList();
									sendData.add(13);// type -- update major with classid
									sendData.add(newmajor);
									sendData.add(oldclssid);
									mes.setData(sendData);

									Client client = new Client(ClientMainFrame.socket);

									Message serverResponse = new Message();
									serverResponse = client.sendRequestToServer(mes);
									int res = (int) serverResponse.getData();

									table.setValueAt(newmajor, i_row, 2);
									ClssAll.get(i_row).setMajor(newmajor);
								}

								if(!newteacher.equals(oldteacher)) {
									Message mes = new Message();
									mes.setModuleType(ModuleType.Student);
									mes.setMessageType(MessageType.ClassAdminUpdate);
									List<Object> sendData = new ArrayList<Object>();
									sendData.add(12);// type -- update teacher with classid
									sendData.add(newteacher);
									sendData.add(oldclssid);
									mes.setData(sendData);

									Client client = new Client(ClientMainFrame.socket);

									Message serverResponse = new Message();
									serverResponse = client.sendRequestToServer(mes);
									int res = (int) serverResponse.getData();

									table.setValueAt(newteacher, i_row, 1);
									ClssAll.get(i_row).setTeacherID(newteacher);
								}

								if(!newclssid.equals(oldclssid)) {
									Message mes = new Message();
									mes.setModuleType(ModuleType.Student);
									mes.setMessageType(MessageType.ClassAdminUpdate);
									List<Object> sendData = new ArrayList<Object>();
									sendData.add(11);// type -- update classid with classid
									sendData.add(newclssid);
									sendData.add(oldclssid);
									mes.setData(sendData);

									Client client = new Client(ClientMainFrame.socket);

									Message serverResponse = new Message();
									serverResponse = client.sendRequestToServer(mes);
									int res = (int) serverResponse.getData();

									table.setValueAt(newclssid, i_row, 0);
									ClssAll.get(i_row).setClassID(newclssid);
								}

								Message mes = new Message();
								mes.setModuleType(ModuleType.Student);
								mes.setMessageType(MessageType.ClassUpdate);
								List<Object> sendData = new ArrayList<Object>();
								sendData.add(3);// type -- all
								sendData.add(newteacher);
								sendData.add(newmajor);
								sendData.add(newclssid);
								sendData.add(oldclssid);
								mes.setData(sendData);

								Client client = new Client(ClientMainFrame.socket);

								Message serverResponse = new Message();
								serverResponse = client.sendRequestToServer(mes);
								int res = (int) serverResponse.getData();

								table.setValueAt(newclssid, i_row, 0);
								table.setValueAt(newteacher, i_row, 1);
								table.setValueAt(newmajor, i_row, 2);
								ClassTemp.get(i_row).setClassID(newclssid);
								ClassTemp.get(i_row).setTeacherID(newteacher);
								ClassTemp.get(i_row).setMajor(newmajor);
								ClssAll.get(ClassIndex.get(i_row)).setClassID(newclssid);
								ClssAll.get(ClassIndex.get(i_row)).setTeacherID(newteacher);
								ClssAll.get(ClassIndex.get(i_row)).setMajor(newmajor);
							}//end of if
							else {
								//no
								break;
							}//end of else
						}//end of ifelse
						i_row++;
					}//end of while
					if(Modified) {
						UpdateClass(ClassTemp);
						JOptionPane.showMessageDialog(null, "成功保存修改！", "提示", JOptionPane.WARNING_MESSAGE);
					}

					Message mes = new Message();
					mes.setModuleType(ModuleType.Student);
					mes.setMessageType(MessageType.ClassAdminGetAll);
					List<Object> sendData = new ArrayList<Object>();
					mes.setData(sendData);

					Client client = new Client(ClientMainFrame.socket);

					Message serverResponse = new Message();
					serverResponse = client.sendRequestToServer(mes);
					StuAll = (Vector<Student>) serverResponse.getData();
				}
//				UpdateTable();
				table.setEnabled(true);
			}
		});

		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		JButton btnNewButton = new JButton("查找");
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 18));
		btnNewButton.setBounds(804,77,86,112-77);
		add(btnNewButton);
		btnNewButton.setOpaque(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(now == MODEL.STUDENTMODIFY || now == MODEL.STUDENTTEMP) {
					//search student
					switch(searchbtn.getSelectedIndex()) {
						case 0:
						{
							//all
							UpdateTable();
							now = MODEL.STUDENTMODIFY;
						}
						break;
						case 1:
						{
							//class
							now = MODEL.STUDENTTEMP;

							StudentTemp = null;
							StudentTemp = new Vector<Student>();
							StudentIndex = null;
							StudentIndex = new Vector<Integer>();
							int i_search = 0;
							String sch = searchdata.getText();
							while(i_search < StuAll.size()) {
								//System.out.println(i_search);
								String test = StuAll.get(i_search).getClassid();
								test.replaceAll("\\p{C}", "");
								sch.replaceAll("\\p{C}", "");
								//System.out.println(test);
								//System.out.println(sch);
								//System.out.println(test.equals(sch));
								if(test.equals(sch)) {
									StudentTemp.add(StuAll.get(i_search));
									StudentIndex.add(i_search);
								}
								i_search++;
							}
							UpdateStudent(StudentTemp);
							System.out.println("model change commit");
						}
						break;
						case 2:
						{
							//student id
							now = MODEL.STUDENTTEMP;
							StudentTemp = null;
							StudentTemp = new Vector<Student>();
							StudentIndex = null;
							StudentIndex = new Vector<Integer>();
							int i_search = 0;
							String sch = searchdata.getText();
							while(i_search < StuAll.size()) {
								String test = StuAll.get(i_search).getStudentid();
								test.replaceAll("\\p{C}", "");
								sch.replaceAll("\\p{C}", "");
								if(test.equals(sch)) {
									StudentTemp.add(StuAll.get(i_search));
									StudentIndex.add(i_search);
								}
								i_search++;
							}
							UpdateStudent(StudentTemp);
							System.out.println("model change commit");
						}
						break;
						case 3:
						{
							//student name
							now = MODEL.STUDENTTEMP;
							StudentTemp = null;
							StudentTemp = new Vector<Student>();
							StudentIndex = null;
							StudentIndex = new Vector<Integer>();
							int i_search = 0;
							String sch = searchdata.getText();
							while(i_search < StuAll.size()) {
								String test = StuAll.get(i_search).getStudentName();
								test.replaceAll("\\p{C}", "");
								sch.replaceAll("\\p{C}", "");
								if(test.equals(sch)) {
									StudentTemp.add(StuAll.get(i_search));
									StudentIndex.add(i_search);
								}
								i_search++;
							}
							UpdateStudent(StudentTemp);
							System.out.println("model change commit");
						}
						break;
						default:
							break;
					}
					//透明化处理
					table.setForeground(Color.BLACK);
					table.setFont(new Font("华文行楷", Font.BOLD, 20));
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
				}//end of student searching
				else {
					//search class
					switch(searchbtn.getSelectedIndex()) {
						case 0:
						{
							//all
							UpdateTable();
							now = MODEL.ClASSMODIFY;
						}
						break;
						case 1:
						{
							//class
							now = MODEL.CLASSTEMP;
							ClassTemp = null;
							ClassTemp = new Vector<ClassManage>();
							ClassIndex = null;
							ClassIndex = new Vector<Integer>();
							int i_search = 0;
							String sch = searchdata.getText();
							while(i_search < ClssAll.size()) {
								String test = ClssAll.get(i_search).getClassID();
								test.replaceAll("\\p{C}", "");
								sch.replaceAll("\\p{C}", "");
								System.out.println(test);
								System.out.println(sch);
								System.out.println(test.equals(sch));
								if(test.equals(sch)) {
									ClassTemp.add(ClssAll.get(i_search));
									ClassIndex.add(i_search);
								}
								i_search++;
							}
							UpdateClass(ClassTemp);
							System.out.println("model change commit");
						}
						break;
						case 2:
						{
							//major
							now = MODEL.CLASSTEMP;
							ClassTemp = null;
							ClassTemp = new Vector<ClassManage>();
							ClassIndex = null;
							ClassIndex = new Vector<Integer>();
							int i_search = 0;
							String sch = searchdata.getText();
							while(i_search < ClssAll.size()) {
								String test = ClssAll.get(i_search).getMajor();
								test.replaceAll("\\p{C}", "");
								sch.replaceAll("\\p{C}", "");
								if(test.equals(sch)) {
									ClassTemp.add(ClssAll.get(i_search));
									ClassIndex.add(i_search);
								}
								i_search++;
							}
							UpdateClass(ClassTemp);
							System.out.println("model change commit");
						}
						break;
						case 3:
						{
							//teacher
							now = MODEL.CLASSTEMP;
							ClassTemp = null;
							ClassTemp = new Vector<ClassManage>();
							ClassIndex = null;
							ClassIndex = new Vector<Integer>();
							int i_search = 0;
							String sch = searchdata.getText();
							while(i_search < ClssAll.size()) {
								String test = ClssAll.get(i_search).getTeacherID();
								test.replaceAll("\\p{C}", "");
								sch.replaceAll("\\p{C}", "");
								if(test.equals(sch)) {
									ClassTemp.add(ClssAll.get(i_search));
									ClassIndex.add(i_search);
								}
								i_search++;
							}
							UpdateClass(ClassTemp);
							System.out.println("model change commit");
						}
						break;
						default:
							break;
					}
				}//end of class searching
				table.setEnabled(true);
			}
		});



		exitbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cac.setEnabled(true);
				close();
			}
		});

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	private void UpdateTable() {
		if(now == MODEL.STUDENTMODIFY ) {
			// student
			while(model1.getRowCount() > 0) {
				//System.out.println(table.getRowCount() - 1);
				model1.removeRow(model1.getRowCount() - 1);
				table.setModel(model1);
			}
			table.setModel(model1);
			getStudent();
			now = MODEL.STUDENTMODIFY;
			//透明化处理
			table.setForeground(Color.BLACK);
			table.setFont(new Font("华文行楷", Font.BOLD, 20));
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
		}else {// class modify
			while(model2.getRowCount() > 0) {
				//System.out.println(table.getRowCount() - 1);
				model2.removeRow(model2.getRowCount() - 1);
				table.setModel(model2);
			}
			table.setModel(model2);
			getClass_all();
			now = MODEL.ClASSMODIFY;
			//透明化处理
			table.setForeground(Color.BLACK);
			table.setFont(new Font("华文行楷", Font.BOLD, 20));
			table.setRowHeight(73);			//表格行高
			table.setPreferredScrollableViewportSize(new Dimension(850, 500));
			table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			String []str= {"班级", "老师","专业"};
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

	private void UpdateStudent(Vector<Student> temp) {
		// student
		while(model1.getRowCount() > 0) {
			//System.out.println(table.getRowCount() - 1);
			model1.removeRow(model1.getRowCount() - 1);
			table.setModel(model1);
		}
		table.setModel(model1);

		String[] arr = new String[4];
		for (int i = 0; i < temp.size(); i++) {
			// 班级 学号 姓名 电话
			arr[0] = temp.get(i).getClassid();
			arr[1] = temp.get(i).getStudentid();
			arr[2] = temp.get(i).getStudentName();
			arr[3] = temp.get(i).getStudentphone();

			model1.addRow(arr);
			table.setModel(model1);
		}
		//透明化处理
		table.setForeground(Color.BLACK);
		table.setFont(new Font("华文行楷", Font.BOLD, 20));
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
	}

	private void UpdateClass(Vector<ClassManage> temp) {
		while(model2.getRowCount() > 0) {
			//System.out.println(table.getRowCount() - 1);
			model2.removeRow(model2.getRowCount() - 1);
			table.setModel(model2);
		}
		table.setModel(model2);
		String[] arr = new String[3];
		for (int i = 0; i < temp.size(); i++) {
			// 班级， 老师， 专业
			arr[0] = temp.get(i).getClassID();
			arr[1] = temp.get(i).getTeacherID();
			arr[2] = temp.get(i).getMajor();

			model2.addRow(arr);
			table.setModel(model2);
		}
		//透明化处理
		table.setForeground(Color.BLACK);
		table.setFont(new Font("华文行楷", Font.BOLD, 20));
		table.setRowHeight(73);			//表格行高
		table.setPreferredScrollableViewportSize(new Dimension(850, 500));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		String []str= {"班级", "老师","专业"};
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

	public void getClass_all() {
		// String get = serverresponse.getData().toString();
		System.out.println("get class");
		String[] arr = new String[3];
		if(ClssAll == null) {
			System.out.println("None of Class!");
		}
		for (int i = 0; i < ClssAll.size(); i++) {
			// 班级， 老师， 专业
			arr[0] = ClssAll.get(i).getClassID();
			arr[1] = ClssAll.get(i).getTeacherID();
			arr[2] = ClssAll.get(i).getMajor();

			model2.addRow(arr);
			table.setModel(model2);
		}
		//透明化处理
		table.setForeground(Color.BLACK);
		table.setFont(new Font("华文行楷", Font.BOLD, 20));
		table.setRowHeight(73);			//表格行高
		table.setPreferredScrollableViewportSize(new Dimension(850, 500));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		String []str= {"班级", "老师","专业"};
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

	private void setStudent(ClassManage classtemp) {
		Message mes = new Message();
		mes.setModuleType(ModuleType.Student);
		mes.setMessageType(MessageType.ClassAdminUpdate);
		List<Object> sendData = new ArrayList<Object>() ;
		sendData.add(14);//teacher, major, class
		sendData.add(classtemp.getTeacherID());
		//System.out.println(classtemp.getTeacherID());
		//System.out.println(classtemp.getMajor());
		//System.out.println(classtemp.getClassID());
		sendData.add(classtemp.getMajor());
		sendData.add(classtemp.getClassID());
		mes.setData(sendData);

		Client client = new Client(ClientMainFrame.socket);

		Message serverResponse = new Message();
		serverResponse = client.sendRequestToServer(mes);
		@SuppressWarnings("unused")
		int res = (int) serverResponse.getData();
	}

	@SuppressWarnings("unchecked")
	private void finalupdate() {
		int i_clss = 0;
		while(i_clss < ClssAll.size()) {
			setStudent(ClssAll.get(i_clss));
			i_clss++;
		}
		Message mes = new Message();
		mes.setModuleType(ModuleType.Student);
		mes.setMessageType(MessageType.ClassAdminGetAll);
		List<Object> sendData = new ArrayList<Object>();
		mes.setData(sendData);

		Client client = new Client(ClientMainFrame.socket);

		Message serverResponse = new Message();
		serverResponse = client.sendRequestToServer(mes);
		StuAll = (Vector<Student>) serverResponse.getData();
	}

	public void getStudent() {
		String[] arr = new String[4];
		for (int i = 0; i < StuAll.size(); i++) {
			// 班级 学号 姓名 电话
			arr[0] = StuAll.get(i).getClassid();
			arr[1] = StuAll.get(i).getStudentid();
			arr[2] = StuAll.get(i).getStudentName();
			arr[3] = StuAll.get(i).getStudentphone();

			model1.addRow(arr);
			table.setModel(model1);
		}
	}



	void close() {
		finalupdate();
		CAC.setEnabled(true);
		CAC.updateFrame(StuAll, ClssAll);
		this.dispose();
	}
}
