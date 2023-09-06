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

public class ClassAdminForDelete extends JFrame {


    private JTable table;
    private JTextField searchdata;
    private JScrollPane scrollPane;
    private DefaultTableModel model1;
    private DefaultTableModel model2;
    private ClassAdminClient CAC = null;
    private Vector<Student> StuAll = null;
    private Vector<ClassManage> ClssAll = null;
    private Vector<Student> StudentTemp = null;
    private Vector<ClassManage> ClassTemp = null;
    private Vector<Integer> StudentIndex = null;
    private Vector<Integer> ClassIndex = null;

    private enum MODEL {
        CLASSDELETE, STUDENTDELTE, CLASSTEMP, STUDENTTEMP
    }

    ;

    private MODEL now = MODEL.STUDENTDELTE;


    public ClassAdminForDelete(final ClassAdminClient cac, Vector<Student> Stu, final Vector<ClassManage> Clss) {
        CAC = cac;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("学生管理界面");
        //背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/ClassAdminForDelete.png"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        setBounds(d.width / 2 - 948 / 2, d.height / 2 - 531 / 2, 948, 555);
        backgroundImageLabel.setBounds(0, 0, 948, 531);
        setResizable(false);
        setLayout(null);

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


        Font f = new Font("华文行楷", Font.BOLD, 36);
        //模式下拉框
        final JComboBox selectmode = new JComboBox();
        selectmode.setFont(new Font("宋体", Font.PLAIN, 18));
        selectmode.addItem("学生");
        selectmode.addItem("班级");
        selectmode.setFont(f);
        selectmode.setBounds(172, 74, 120, 105 - 74 + 13);
        add(selectmode);

        //搜索类别下拉框
        final JComboBox searchbtn = new JComboBox();
        searchbtn.setFont(f);
        searchbtn.setBounds(458, 78, 120, 112 - 78 + 13);
        add(searchbtn);


        //表格

        scrollPane = new JScrollPane();
        //搜索框
        searchdata = new JTextField();
        searchdata.setFont(f);
        searchdata.setBounds(617, 78, 785 - 617, 112 - 78);
        add(searchdata);
        searchdata.setOpaque(false);
        searchdata.setBorder(new EmptyBorder(0, 0, 0, 0));


        table = new JTable();
        table.setBounds(0, 0, 809 - 182, 444 - 128);
        table.setFont(new Font("Adobe 仿宋 Std R", Font.PLAIN, 12));
        scrollPane.setViewportView(table);
        scrollPane.setBounds(182, 128, 809 - 182, 444 - 128);
        add(scrollPane);
        model1 = new DefaultTableModel(new Object[][]{},
                new String[]{"班级", "学号", "姓名", "电话"});
        model2 = new DefaultTableModel(new Object[][]{},
                new String[]{"班级", "老师", "专业"});

        StuAll = Stu;
        ClssAll = Clss;

        if ((selectmode.getSelectedItem().toString()).equalsIgnoreCase("学生")) {
            table.setModel(model1);// student
            table.getColumnModel().getColumn(3).setPreferredWidth(144);
            getStudent();
            now = MODEL.STUDENTDELTE;
            searchbtn.addItem("全部");
            searchbtn.addItem("班级");
            searchbtn.addItem("学号");
            searchbtn.addItem("姓名");
            //透明化处理
            table.setForeground(Color.BLACK);
            table.setFont(new Font("楷体", Font.BOLD, 20));
            table.setRowHeight(73);            //表格行高
            table.setPreferredScrollableViewportSize(new Dimension(850, 500));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            String[] str = {"班级", "学号", "姓名", "电话"};
            renderer.setOpaque(false);    //设置透明
            for (int i = 0; i < str.length; i++) {
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
            now = MODEL.CLASSDELETE;
            searchbtn.addItem("全部");
            searchbtn.addItem("班级");
            searchbtn.addItem("专业");
            searchbtn.addItem("老师");
            //透明化处理
            table.setForeground(Color.BLACK);
            table.setFont(new Font("楷体", Font.BOLD, 20));
            table.setRowHeight(73);            //表格行高
            table.setPreferredScrollableViewportSize(new Dimension(850, 500));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            String[] str = {"班级", "老师", "专业"};
            renderer.setOpaque(false);    //设置透明
            for (int i = 0; i < str.length; i++) {
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

            @SuppressWarnings("unchecked")
            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                if (selectmode.getSelectedIndex() == 0) {
                    // student
                    if (now == MODEL.STUDENTDELTE) {
                        //empty do nothing
                    } else {
                        if (now == MODEL.CLASSDELETE || now == MODEL.CLASSTEMP) {
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
                        while (model2.getRowCount() > 0) {
                            model2.removeRow(model2.getRowCount() - 1);
                            table.setModel(model2);
                        }
                        table.setModel(model1);
                        getStudent();
                        now = MODEL.STUDENTDELTE;
                        searchbtn.removeItemAt(2);
                        searchbtn.removeItemAt(2);
                        searchbtn.addItem("学号");
                        searchbtn.addItem("姓名");
                        System.out.println("Model Change");
                        //透明化处理
                        table.setForeground(Color.BLACK);
                        table.setFont(new Font("楷体", Font.BOLD, 20));
                        table.setRowHeight(73);            //表格行高
                        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
                        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                        String[] str = {"班级", "学号", "姓名", "电话"};
                        renderer.setOpaque(false);    //设置透明
                        for (int i = 0; i < str.length; i++) {
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
                    if (now == MODEL.CLASSDELETE) {
                        //empty body
                    } else {
                        while (model1.getRowCount() > 0) {
                            //System.out.println(table.getRowCount() - 1);
                            model1.removeRow(model1.getRowCount() - 1);
                            table.setModel(model1);
                        }
                        table.setModel(model2);
                        getClass_all();
                        now = MODEL.CLASSDELETE;
                        searchbtn.removeItemAt(2);
                        searchbtn.removeItemAt(2);
                        searchbtn.addItem("专业");
                        searchbtn.addItem("老师");
                        //透明化处理
                        table.setForeground(Color.BLACK);
                        table.setFont(new Font("楷体", Font.BOLD, 20));
                        table.setRowHeight(73);            //表格行高
                        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
                        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                        String[] str = {"班级", "老师", "专业"};
                        renderer.setOpaque(false);    //设置透明
                        for (int i = 0; i < str.length; i++) {
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
                        System.out.println("Model Change");
                    }
                }
            }

        });

        add(backgroundImageLabel);
        //确认按钮
        JButton Commitbtn = new JButton("确定");
        Commitbtn.setFont(new Font("宋体", Font.PLAIN, 18));
        Commitbtn.setBounds(299, 454, 892 - 805, 112 - 78);
        add(Commitbtn);
        Commitbtn.setOpaque(false);
        //返回按钮
        JButton exitbtn = new JButton("返回");
        exitbtn.setFont(new Font("宋体", Font.PLAIN, 18));
        exitbtn.setBounds(583, 454, 892 - 805, 112 - 78);
        add(exitbtn);
        exitbtn.setOpaque(false);
        Commitbtn.addActionListener(new ActionListener() {
            @SuppressWarnings({"unchecked", "unlikely-arg-type"})
            public void actionPerformed(ActionEvent e) {
                //delete
                if (now == MODEL.STUDENTDELTE) {
                    int target = table.getSelectedRow();
                    if (target == -1) {
                        JOptionPane.showMessageDialog(null, "请先选择要进行删除的学生！", "提示", JOptionPane.WARNING_MESSAGE);
                    } else {
                        int classtempforadd = 0;
                        int oldclasssize = 0;
                        Boolean newclass = false;
                        while (classtempforadd < ClssAll.size() && !newclass) {
                            if (ClssAll.get(classtempforadd).getClassID().equals(table.getValueAt(target, 0))) {
                                newclass = true;
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
                        sendData.add(StuAll.get(target).getClassid());
                        mes.setData(sendData);

                        Client client = new Client(ClientMainFrame.socket);

                        Message serverResponse = new Message();
                        serverResponse = client.sendRequestToServer(mes);
                        int res = (int) serverResponse.getData();
                        System.out.println("update class size");

                        //delete studentmanage student here(studentid == StuAll.get(target).getStudentid())
                        mes = null;
                        mes = new Message();
                        mes.setModuleType(ModuleType.Student);
                        mes.setMessageType(MessageType.ClassAdminDelete);
                        sendData = null;
                        sendData = new ArrayList<Object>();
                        sendData.add(0);
                        sendData.add(StuAll.get(target).getStudentid());

                        Student tempstudent = new Student();
                        tempstudent = StuAll.get(target);

                        mes.setData(sendData);

                        client = null;
                        client = new Client(ClientMainFrame.socket);

                        serverResponse = null;
                        serverResponse = new Message();
                        serverResponse = client.sendRequestToServer(mes);
                        res = (int) serverResponse.getData();
                        if (res > 0) {
                            JOptionPane.showMessageDialog(null, "完成删除！", "提示", JOptionPane.WARNING_MESSAGE);
                            model1.removeRow(target);
                            StuAll.remove(target);
                            table.setModel(model1);
                        }
//delete dormitory student here	(studentid == StuAll.get(target).getStudentid())
                        mes = null;
                        mes = new Message();
                        mes.setUserType(1);
                        mes.setModuleType(ModuleType.Dormitory);
                        mes.setMessageType(MessageType.DormDelete);

                        client = null;
                        client = new Client(ClientMainFrame.socket);
                        mes.setData(tempstudent.getStudentid());
                        serverResponse = null;
                        serverResponse = new Message();
                        serverResponse = client.sendRequestToServer(mes);
                        ArrayList<Dormitory> allDormitoryContents = (ArrayList<Dormitory>) serverResponse.getData();
                        System.out.println("dormitory delete confirmed!");
//delete user(id == StuAll.get(target).getStudentid())
                        User user = new User();
                        mes = null;
                        mes = new Message();
                        mes.setModuleType(ModuleType.User);
                        mes.setMessageType(MessageType.REQ_USERDEL);
                        user.setId(tempstudent.getStudentid());
                        user.setAge("");
                        user.setGrade("");
                        user.setMajor(tempstudent.getMajor());
                        user.setMoney("");
                        user.setName(tempstudent.getStudentName());
                        user.setPwd("");
                        user.setRole("0");
                        if (tempstudent.getStudentgender()) {
                            user.setSex("男");
                        } else {
                            user.setSex("女");
                        }
                        mes.setContent(user.getContent());

                        client = null;
                        client = new Client(ClientMainFrame.socket);

                        serverResponse = null;
                        serverResponse = new Message();
                        serverResponse = client.sendRequestToServer(mes);
                        serverResponse.getData();
                    }
                } else if (now == MODEL.CLASSDELETE) {
                    int target = table.getSelectedRow();
                    if (target == -1) {
                        JOptionPane.showMessageDialog(null, "请先选择要进行删除的班级！", "提示", JOptionPane.WARNING_MESSAGE);
                    } else {
                        if (ClssAll.get(target).getClassSize() == 0) {
                            Message mes = new Message();
                            mes.setModuleType(ModuleType.Student);
                            mes.setMessageType(MessageType.ClassDelete);
                            String sendData = null;
                            sendData = ClssAll.get(target).getClassID();
                            mes.setData(sendData);

                            Client client = new Client(ClientMainFrame.socket);

                            Message serverResponse = new Message();
                            serverResponse = client.sendRequestToServer(mes);
                            int res = (int) serverResponse.getData();
                            if (res > 0) {
                                JOptionPane.showMessageDialog(null, "完成删除！", "提示", JOptionPane.WARNING_MESSAGE);
                                model2.removeRow(target);
                                ClssAll.remove(target);
                                table.setModel(model2);
                            }
                        } else {

                            JOptionPane.showMessageDialog(null, "请先处理仍在该班内的学生，确认学生为空后再次进行删除操作！", "提示", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } else if (now == MODEL.STUDENTTEMP) {
                    int target = table.getSelectedRow();
                    if (target == -1) {
                        JOptionPane.showMessageDialog(null, "请先选择要进行删除的学生！", "提示", JOptionPane.WARNING_MESSAGE);
                    } else {
                        int classtempforadd = 0;
                        int oldclasssize = 0;
                        Boolean newclass = false;
                        while (classtempforadd < ClssAll.size() && !newclass) {
                            if (ClssAll.get(classtempforadd).getClassID().equals(model1.getValueAt(target, 0))) {
                                oldclasssize = ClssAll.get(classtempforadd).getClassSize() - 1;
                                System.out.println("delete student from class");
                                newclass = true;
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
                        sendData.add(StudentTemp.get(target).getClassid());
                        mes.setData(sendData);

                        Client client = new Client(ClientMainFrame.socket);

                        Message serverResponse = new Message();
                        serverResponse = client.sendRequestToServer(mes);
                        int res = (int) serverResponse.getData();
                        System.out.println("update class size");


                        mes = null;
                        mes = new Message();
                        mes.setModuleType(ModuleType.Student);
                        mes.setMessageType(MessageType.ClassAdminDelete);
                        sendData = null;
                        sendData = new ArrayList<Object>();
                        sendData.add(0);
                        sendData.add(StudentTemp.get(target).getStudentid());

                        Student tempstudent = new Student();
                        tempstudent = StudentTemp.get(target);

                        mes.setData(sendData);

                        client = null;
                        client = new Client(ClientMainFrame.socket);

                        serverResponse = null;
                        serverResponse = new Message();
                        serverResponse = client.sendRequestToServer(mes);
                        res = (int) serverResponse.getData();
                        if (res > 0) {
                            JOptionPane.showMessageDialog(null, "完成删除！", "提示", JOptionPane.WARNING_MESSAGE);
                            model1.removeRow(target);
                            StudentTemp.remove(target);
                            StuAll.remove(StudentIndex.get(target));
                            table.setModel(model1);
                        }
//delete dormitory student here	(studentid == StudentTemp.get(target).getStudentid())
                        mes = null;
                        mes = new Message();
                        mes.setUserType(1);
                        mes.setModuleType(ModuleType.Dormitory);
                        mes.setMessageType(MessageType.DormDelete);

                        client = null;
                        client = new Client(ClientMainFrame.socket);
                        mes.setData(tempstudent.getStudentid());
                        serverResponse = null;
                        serverResponse = new Message();
                        serverResponse = client.sendRequestToServer(mes);
                        ArrayList<Dormitory> allDormitoryContents = (ArrayList<Dormitory>) serverResponse.getData();
                        System.out.println("dormitory delete confirmed!");
//delete user(id == StudentTemp.get(target).getStudentid())
                        User user = new User();
                        mes = null;
                        mes = new Message();
                        mes.setModuleType(ModuleType.User);
                        mes.setMessageType(MessageType.REQ_USERDEL);
                        user.setId(tempstudent.getStudentid());
                        user.setAge("");
                        user.setGrade("");
                        user.setMajor(tempstudent.getMajor());
                        user.setMoney("");
                        user.setName(tempstudent.getStudentName());
                        user.setPwd("");
                        user.setRole("0");
                        if (tempstudent.getStudentgender()) {
                            user.setSex("男");
                        } else {
                            user.setSex("女");
                        }
                        mes.setContent(user.getContent());

                        client = null;
                        client = new Client(ClientMainFrame.socket);

                        serverResponse = null;
                        serverResponse = new Message();
                        serverResponse = client.sendRequestToServer(mes);
                        serverResponse.getData();
                    }
                } else if (now == MODEL.CLASSTEMP) {
                    int target = table.getSelectedRow();
                    if (target == -1) {
                        JOptionPane.showMessageDialog(null, "请先选择要进行删除的班级！", "提示", JOptionPane.WARNING_MESSAGE);
                    } else {
                        if (ClassTemp.get(target).getClassSize() == 0) {
                            Message mes = new Message();
                            mes.setModuleType(ModuleType.Student);
                            mes.setMessageType(MessageType.ClassDelete);
                            String sendData = null;
                            sendData = ClassTemp.get(target).getClassID();
                            mes.setData(sendData);

                            Client client = new Client(ClientMainFrame.socket);

                            Message serverResponse = new Message();
                            serverResponse = client.sendRequestToServer(mes);
                            int res = (int) serverResponse.getData();
                            if (res > 0) {
                                JOptionPane.showMessageDialog(null, "完成删除！", "提示", JOptionPane.WARNING_MESSAGE);
                                model1.removeRow(target);
                                ClassTemp.remove(target);
                                Clss.remove(ClassIndex.get(target));
                                table.setModel(model2);
                            }
                        } else {

                            JOptionPane.showMessageDialog(null, "请先处理仍在该班内的学生，确认学生为空后再次进行删除操作！", "提示", JOptionPane.WARNING_MESSAGE);
                        }

                    }
                }//end
                table.setEnabled(true);
            }
        });

        scrollPane.setViewportView(table);

        JButton btnNewButton = new JButton("查找");
        btnNewButton.setFont(new Font("宋体", Font.PLAIN, 18));
        btnNewButton.setBounds(805, 78, 892 - 805, 112 - 78);
        add(btnNewButton);
        btnNewButton.setOpaque(false);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                table.setEnabled(false);
                if (now == MODEL.STUDENTDELTE || now == MODEL.STUDENTTEMP) {
                    //search student
                    switch (searchbtn.getSelectedIndex()) {
                        case 0: {
                            //all
                            UpdateTable();
                            now = MODEL.STUDENTDELTE;
                        }
                        break;
                        case 1: {
                            //class
                            now = MODEL.STUDENTTEMP;

                            StudentTemp = null;
                            StudentTemp = new Vector<Student>();
                            StudentIndex = null;
                            StudentIndex = new Vector<Integer>();
                            int i_search = 0;
                            String sch = searchdata.getText();
                            while (i_search < StuAll.size()) {
                                //System.out.println(i_search);
                                String test = StuAll.get(i_search).getClassid();
                                test.replaceAll("\\p{C}", "");
                                sch.replaceAll("\\p{C}", "");
                                //System.out.println(test);
                                //System.out.println(sch);
                                //System.out.println(test.equals(sch));
                                if (test.equals(sch)) {
                                    StudentTemp.add(StuAll.get(i_search));
                                    StudentIndex.add(i_search);
                                }
                                i_search++;
                            }
                            UpdateStudent(StudentTemp);
                            System.out.println("model change commit");
                        }
                        break;
                        case 2: {
                            //student id
                            now = MODEL.STUDENTTEMP;
                            StudentTemp = null;
                            StudentTemp = new Vector<Student>();
                            StudentIndex = null;
                            StudentIndex = new Vector<Integer>();
                            int i_search = 0;
                            String sch = searchdata.getText();
                            while (i_search < StuAll.size()) {
                                String test = StuAll.get(i_search).getStudentid();
                                test.replaceAll("\\p{C}", "");
                                sch.replaceAll("\\p{C}", "");
                                if (test.equals(sch)) {
                                    StudentTemp.add(StuAll.get(i_search));
                                    StudentIndex.add(i_search);
                                }
                                i_search++;
                            }
                            UpdateStudent(StudentTemp);
                            System.out.println("model change commit");
                        }
                        break;
                        case 3: {
                            //student name
                            now = MODEL.STUDENTTEMP;
                            StudentTemp = null;
                            StudentTemp = new Vector<Student>();
                            StudentIndex = null;
                            StudentIndex = new Vector<Integer>();
                            int i_search = 0;
                            String sch = searchdata.getText();
                            while (i_search < StuAll.size()) {
                                String test = StuAll.get(i_search).getStudentName();
                                test.replaceAll("\\p{C}", "");
                                sch.replaceAll("\\p{C}", "");
                                if (test.equals(sch)) {
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
                    table.setFont(new Font("楷体", Font.BOLD, 20));
                    table.setRowHeight(73);            //表格行高
                    table.setPreferredScrollableViewportSize(new Dimension(850, 500));
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                    String[] str = {"班级", "学号", "姓名", "电话"};
                    renderer.setOpaque(false);    //设置透明
                    for (int i = 0; i < str.length; i++) {
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
                    switch (searchbtn.getSelectedIndex()) {
                        case 0: {
                            //all
                            UpdateTable();
                            now = MODEL.CLASSDELETE;
                        }
                        break;
                        case 1: {
                            //class
                            now = MODEL.CLASSTEMP;
                            ClassTemp = null;
                            ClassTemp = new Vector<ClassManage>();
                            ClassIndex = null;
                            ClassIndex = new Vector<Integer>();
                            int i_search = 0;
                            String sch = searchdata.getText();
                            while (i_search < ClssAll.size()) {
                                String test = ClssAll.get(i_search).getClassID();
                                test.replaceAll("\\p{C}", "");
                                sch.replaceAll("\\p{C}", "");
                                System.out.println(test);
                                System.out.println(sch);
                                System.out.println(test.equals(sch));
                                if (test.equals(sch)) {
                                    ClassTemp.add(ClssAll.get(i_search));
                                    ClassIndex.add(i_search);
                                }
                                i_search++;
                            }
                            UpdateClass(ClassTemp);
                            System.out.println("model change commit");
                        }
                        break;
                        case 2: {
                            //major
                            now = MODEL.CLASSTEMP;
                            ClassTemp = null;
                            ClassTemp = new Vector<ClassManage>();
                            ClassIndex = null;
                            ClassIndex = new Vector<Integer>();
                            int i_search = 0;
                            String sch = searchdata.getText();
                            while (i_search < ClssAll.size()) {
                                String test = ClssAll.get(i_search).getMajor();
                                test.replaceAll("\\p{C}", "");
                                sch.replaceAll("\\p{C}", "");
                                if (test.equals(sch)) {
                                    ClassTemp.add(ClssAll.get(i_search));
                                    ClassIndex.add(i_search);
                                }
                                i_search++;
                            }
                            UpdateClass(ClassTemp);
                            System.out.println("model change commit");
                        }
                        break;
                        case 3: {
                            //teacher
                            now = MODEL.CLASSTEMP;
                            ClassTemp = null;
                            ClassTemp = new Vector<ClassManage>();
                            ClassIndex = null;
                            ClassIndex = new Vector<Integer>();
                            int i_search = 0;
                            String sch = searchdata.getText();
                            while (i_search < ClssAll.size()) {
                                String test = ClssAll.get(i_search).getTeacherID();
                                test.replaceAll("\\p{C}", "");
                                sch.replaceAll("\\p{C}", "");
                                if (test.equals(sch)) {
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
                    //透明化处理
                    table.setForeground(Color.BLACK);
                    table.setFont(new Font("楷体", Font.BOLD, 20));
                    table.setRowHeight(73);            //表格行高
                    table.setPreferredScrollableViewportSize(new Dimension(850, 500));
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                    String[] str = {"班级", "老师", "专业"};
                    renderer.setOpaque(false);    //设置透明
                    for (int i = 0; i < str.length; i++) {
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
    }

    private void UpdateTable() {
        if (now == MODEL.STUDENTDELTE) {
            // student
            while (model1.getRowCount() > 0) {
                //System.out.println(table.getRowCount() - 1);
                model1.removeRow(model1.getRowCount() - 1);
                table.setModel(model1);
            }
            table.setModel(model1);
            getStudent();
            now = MODEL.STUDENTDELTE;
            //透明化处理
            table.setForeground(Color.BLACK);
            table.setFont(new Font("楷体", Font.BOLD, 20));
            table.setRowHeight(73);            //表格行高
            table.setPreferredScrollableViewportSize(new Dimension(850, 500));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            String[] str = {"班级", "学号", "姓名", "电话"};
            renderer.setOpaque(false);    //设置透明
            for (int i = 0; i < str.length; i++) {
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
        } else {// class modify
            while (model2.getRowCount() > 0) {
                //System.out.println(table.getRowCount() - 1);
                model2.removeRow(model2.getRowCount() - 1);
                table.setModel(model2);
            }
            table.setModel(model2);
            getClass_all();
            now = MODEL.CLASSDELETE;
            //透明化处理
            table.setForeground(Color.BLACK);
            table.setFont(new Font("楷体", Font.BOLD, 20));
            table.setRowHeight(73);            //表格行高
            table.setPreferredScrollableViewportSize(new Dimension(850, 500));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            String[] str = {"班级", "老师", "专业"};
            renderer.setOpaque(false);    //设置透明
            for (int i = 0; i < str.length; i++) {
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
        while (model1.getRowCount() > 0) {
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
        table.setFont(new Font("楷体", Font.BOLD, 20));
        table.setRowHeight(73);            //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        String[] str = {"班级", "学号", "姓名", "电话"};
        renderer.setOpaque(false);    //设置透明
        for (int i = 0; i < str.length; i++) {
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
        while (model2.getRowCount() > 0) {
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
        table.setFont(new Font("楷体", Font.BOLD, 20));
        table.setRowHeight(73);            //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        String[] str = {"班级", "老师", "专业"};
        renderer.setOpaque(false);    //设置透明
        for (int i = 0; i < str.length; i++) {
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
        // System.out.println(get);
        String[] arr = new String[3];
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
        table.setFont(new Font("楷体", Font.BOLD, 20));
        table.setRowHeight(73);            //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        String[] str = {"班级", "老师", "专业"};
        renderer.setOpaque(false);    //设置透明
        for (int i = 0; i < str.length; i++) {
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


    @SuppressWarnings("unchecked")
    private void finalupdate() {
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
