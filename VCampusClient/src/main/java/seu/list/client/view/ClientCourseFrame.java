package seu.list.client.view;


import seu.list.client.driver.Client;
import seu.list.common.Course;
import seu.list.common.Message;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class ClientCourseFrame extends JFrame implements ActionListener {
    final int WIDTH = 1280;
    final int HEIGHT = 720;
    JFrame jFrame = new JFrame();
    JTable jtb1;
    JComboBox jcb;
    JTextField jtf, jtf1;
    JButton jco_Search, jco_Add, jco_Delete, exit;
    Socket socket;
    JScrollPane scrollPane;

    private String userID;

    /**
     * creat the frame
     *
     * @param ID     用户的id
     * @param socket 与服务器通信所用socket
     */
    public ClientCourseFrame(String ID, Socket socket) throws ClassNotFoundException, SQLException, IOException, ClassNotFoundException {
        Tools.setWindowspos(WIDTH, HEIGHT, jFrame);
        userID = ID;
        this.socket = socket;
        Client client = new Client(this.socket);


        //绘制背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/ClientCourseFrame.PNG"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        jFrame.setBounds(d.width / 2 - 640, d.height / 2 - 360, 1280, 720);
        backgroundImageLabel.setBounds(0, 0, 1280, 720);
        jFrame.setSize(1280, 755);
        jFrame.setResizable(false);
        jFrame.setLayout(null);

        //下拉拉列表
        String[] seOp = {"全部", "课程号"};

        jcb = new JComboBox(seOp);
        jcb.setFont(new Font("华为行楷", Font.BOLD, 36));
        jcb.setBounds(450, 148, 200, 197 - 148);
        jFrame.add(jcb);
        jcb.setOpaque(false);
        jcb.setBorder(BorderFactory.createBevelBorder(0));
        jtf1 = new JTextField("请输入课程名称");
        jtf = new JTextField("课程编号");
        jtf.setBounds(671, 148, 991 - 671, 197 - 148);
        jtf.setFont(new Font("华文行楷", Font.BOLD, 36));
        jFrame.add(jtf);
        jtf.setOpaque(false);
        jtf.setBorder(new EmptyBorder(0, 0, 0, 0));


        Object[][] courseinformation = {};
        Object[] courselist = {"学年学期", "课程编号", "专业", "课程", "授课教师", "状态", "类型"};
        DefaultTableModel model;
        model = new DefaultTableModel(courseinformation, courselist);

        Message clientReq = new Message();
        clientReq.setModuleType(ModuleType.Course);
        clientReq.setMessageType("REQ_SHOW_ALL_LESSON");
        Message rec = client.sendRequestToServer(clientReq);
        Vector<String> allCourseContents = rec.getContent();

        Object sigRow[] = new String[7];
        for (int i = 0; i < allCourseContents.size(); ) {
            for (int j = 0; j < 7; ) {
                sigRow[j++] = allCourseContents.get(i++);
            }
            model.addRow(sigRow);
        }

        jtb1 = new JTable();
        jtb1.setModel(model);
        jtb1.setPreferredSize(new Dimension(WIDTH - 100, 2000));
        jtb1.setFont(new Font("微软雅黑", Font.BOLD, 20));
        jtb1.getTableHeader().setPreferredSize(new Dimension(1, 40));
        jtb1.getTableHeader().setFont(new Font("宋体", Font.BOLD, 25));
        jtb1.setRowHeight(50);
        scrollPane = new JScrollPane(jtb1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 200, 500));
        jtb1.setBounds(0, 0, 1155 - 257, 610 - 219);
        scrollPane.setBounds(257, 219, 1155 - 257, 610 - 219);
        jFrame.add(scrollPane);

        //透明化处理
        jtb1.setForeground(Color.BLACK);
        jtb1.setFont(new Font("Serif", Font.BOLD, 28));
        jtb1.setRowHeight(40);                //表格行高
        jtb1.setPreferredScrollableViewportSize(new Dimension(850, 500));
        jtb1.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {"学年学期", "课程编号", "专业", "课程", "授课教师", "状态", "类型"};
        for (int i = 0; i < 7; i++) {
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


        jFrame.add(backgroundImageLabel);

        //4个按钮
        jco_Search = new JButton("搜索");
        jco_Search.setFont(new Font("微软雅黑", Font.BOLD, 20));
        jco_Search.setBounds(1015, 144, 1127 - 1015, 200 - 144);
        jco_Search.addActionListener(this);
        jco_Search.setActionCommand("search");
        jco_Add = new JButton("增加课程");
        jco_Add.setBounds(114, 239, 1127 - 1015, 200 - 144);
        jco_Add.setFont(new Font("微软雅黑", Font.BOLD, 20));
        jco_Add.addActionListener(this);
        jco_Add.setActionCommand("add");
        jco_Delete = new JButton("移除课程");
        jco_Delete.setBounds(115, 379, 1127 - 1015, 200 - 144);
        jco_Delete.setFont(new Font("微软雅黑", Font.BOLD, 20));
        jco_Delete.addActionListener(this);
        jco_Delete.setActionCommand("delete");
        jFrame.setVisible(true);
        exit = new JButton("退出");
        exit.setBounds(113, 518, 1127 - 1015, 200 - 144);
        jFrame.add(exit);
        jFrame.add(jco_Add);
        jFrame.add(jco_Delete);
        jFrame.add(jco_Search);
        exit.setOpaque(false);
        jco_Search.setOpaque(false);
        jco_Delete.setOpaque(false);
        jco_Add.setOpaque(false);
        exit.addActionListener(event -> jFrame.dispose());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Client client = new Client(this.socket);
        if (e.getActionCommand().equals("search")) {
            if (jcb.getSelectedItem().equals("全部")) {
                Message clientReq = new Message();
                clientReq.setModuleType(ModuleType.Course);
                clientReq.setMessageType("REQ_SHOW_ALL_LESSON");
                Message rec = client.sendRequestToServer(clientReq);

                Vector<String> allCourseInfor = rec.getContent();
                int rowNumber = allCourseInfor.size() / 7;
                String[][] allCourseTable = new String[rowNumber][7];
                int storingPlace = 0;
                for (int i = 0; i < rowNumber; i++) {
                    for (int j = 0; j < 7; j++)
                        allCourseTable[i][j] = allCourseInfor.get(storingPlace++);
                }
                jtb1 = new JTable();
                jtb1.setModel(new DefaultTableModel(
                        allCourseTable,
                        new String[]{
                                "学年学期", "课程编号", "专业", "课程", "授课教师", "状态", "类型"
                        }
                ));
                jtb1.getColumnModel().getColumn(0).setPreferredWidth(161);
                jtb1.getColumnModel().getColumn(1).setPreferredWidth(161);
                jtb1.getColumnModel().getColumn(2).setPreferredWidth(161);
                jtb1.getColumnModel().getColumn(3).setPreferredWidth(161);
                jtb1.getColumnModel().getColumn(4).setPreferredWidth(161);
                jtb1.getColumnModel().getColumn(5).setPreferredWidth(161);
                jtb1.getColumnModel().getColumn(6).setPreferredWidth(161);

                jtb1.setPreferredSize(new Dimension(WIDTH - 100, 2000));
                jtb1.setFont(new Font("微软雅黑", Font.BOLD, 20));
                jtb1.getTableHeader().setPreferredSize(new Dimension(1, 40));
                jtb1.getTableHeader().setFont(new Font("宋体", Font.BOLD, 25));
                jtb1.setRowHeight(50);

                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setPreferredSize(new Dimension(WIDTH - 200, 500));
                scrollPane.setViewportView(jtb1);
                //透明化处理
                jtb1.setForeground(Color.BLACK);
                jtb1.setFont(new Font("Serif", Font.BOLD, 28));
                jtb1.setRowHeight(40);                //表格行高
                jtb1.setPreferredScrollableViewportSize(new Dimension(850, 500));
                jtb1.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setOpaque(false);    //设置透明
                String[] Names = {"学年学期", "课程编号", "专业", "课程", "授课教师", "状态", "类型"};
                for (int i = 0; i < 7; i++) {
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


            } else if (jcb.getSelectedItem().equals("课程号")) {
                Message clientReq = new Message();//新建申请用于交换
                clientReq.setModuleType(ModuleType.Course);
                clientReq.setMessageType("REQ_SEARCH_LESSON");//查找课程
                Vector<String> reqContent = new Vector<String>();
                Course c = new Course();
                c.setCourseID(jtf.getText());
                reqContent = c.getContent();
                clientReq.setContent(reqContent);//调用信息存进申请
                Message rec = client.sendRequestToServer(clientReq);
                if (rec.isSeccess()) {
                    Vector<String> allCourseInfor = rec.getContent();
                    int rowNumber = allCourseInfor.size() / 7;
                    String[][] allCourseTable = new String[rowNumber][7];
                    int storingPlace = 0;
                    for (int i = 0; i < rowNumber; i++) {
                        for (int j = 0; j < 7; j++)
                            allCourseTable[i][j] = allCourseInfor.get(storingPlace++);
                    }
                    jtb1 = new JTable();
                    jtb1.setModel(new DefaultTableModel(
                            allCourseTable,
                            new String[]{
                                    "学年学期", "课程编号", "专业", "课程", "授课教师", "状态", "类型"
                            }
                    ));
                    jtb1.getColumnModel().getColumn(0).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(1).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(2).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(3).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(4).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(5).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(6).setPreferredWidth(161);
                    jtb1.setPreferredSize(new Dimension(WIDTH - 100, 2000));
                    jtb1.setFont(new Font("微软雅黑", Font.BOLD, 20));
                    jtb1.getTableHeader().setPreferredSize(new Dimension(1, 40));
                    jtb1.getTableHeader().setFont(new Font("宋体", Font.BOLD, 25));
                    jtb1.setRowHeight(50);

                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setPreferredSize(new Dimension(WIDTH - 200, 500));
                    scrollPane.setViewportView(jtb1);
                    //透明化处理
                    jtb1.setForeground(Color.BLACK);
                    jtb1.setFont(new Font("Serif", Font.BOLD, 28));
                    jtb1.setRowHeight(40);                //表格行高
                    jtb1.setPreferredScrollableViewportSize(new Dimension(850, 500));
                    jtb1.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                    renderer.setOpaque(false);    //设置透明
                    String[] Names = {"学年学期", "课程编号", "专业", "课程", "授课教师", "状态", "类型"};
                    for (int i = 0; i < 7; i++) {
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
                } else {
                    JOptionPane.showMessageDialog(null, "课程不存在，请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
                }

            }

        } else if (e.getActionCommand().equals("add")) {
            CourseInfor courseInfor = new CourseInfor(userID, this.socket, this);
            jFrame.setVisible(false);

        } else if (e.getActionCommand().equals("delete")) {
            //删除课程增加弹窗
            //绘制背景图片
            jFrame.setVisible(false);
            JFrame tem = new JFrame();
            JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/CancelCourse.JPG"));
            Toolkit k = Toolkit.getDefaultToolkit();
            Dimension d = k.getScreenSize();
            tem.setBounds(d.width / 2 - 827 / 2, d.height / 2 - 381 / 2, 827, 378);
            backgroundImageLabel.setBounds(0, 0, 827, 351);
            tem.setResizable(false);
            tem.setLayout(null);
            tem.setVisible(true);

            JTextField jtf = new JTextField();
            jtf.setText("请输入课程名称");
            jtf.setBounds(263, 134, 620 - 263, 186 - 141);
            jtf.setFont(new Font("华文行楷", Font.BOLD, 36));
            tem.add(jtf);
            tem.add(backgroundImageLabel);
            jtf.setOpaque(false);
            jtf.setBorder(new EmptyBorder(0, 0, 0, 0));

            JButton OK = new JButton("OK");
            OK.setBounds(251, 243, 100, 315 - 257);
            tem.add(OK);
            JButton Cancel = new JButton("No");
            Cancel.setBounds(512, 243, 100, 315 - 257);
            tem.add(Cancel);
            OK.setOpaque(false);
            Cancel.setOpaque(false);

            Cancel.addActionListener(event ->
            {
                jFrame.setVisible(true);
                tem.dispose();
            });
            OK.addActionListener(event -> {
                Message clientReq = new Message();
                clientReq.setModuleType(ModuleType.Course);
                clientReq.setMessageType("REQ_REMOVE_LESSON");
                Vector<String> reqContent = new Vector<String>();
                reqContent.setSize(7);
                reqContent.set(2, jtf.getText());
                clientReq.setContent(reqContent);
                Message rec = client.sendRequestToServer(clientReq);

                if (rec.isSeccess()) {
                    clientReq.setMessageType("REQ_SHOW_ALL_LESSON");
                    rec = client.sendRequestToServer(clientReq);

                    Vector<String> allCourseInfor = rec.getContent();
                    int rowNumber = allCourseInfor.size() / 7;
                    String[][] allCourseTable = new String[rowNumber][7];
                    int storingPlace = 0;
                    for (int i = 0; i < rowNumber; i++) {
                        for (int j = 0; j < 7; j++)
                            allCourseTable[i][j] = allCourseInfor.get(storingPlace++);
                    }
                    jtb1 = new JTable();
                    jtb1.setModel(new DefaultTableModel(
                            allCourseTable,
                            new String[]{
                                    "学年学期", "课程编号", "专业", "课程", "授课教师", "状态", "类型"
                            }
                    ));
                    jtb1.getColumnModel().getColumn(0).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(1).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(2).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(3).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(4).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(5).setPreferredWidth(161);
                    jtb1.getColumnModel().getColumn(6).setPreferredWidth(161);

                    jtb1.setPreferredSize(new Dimension(WIDTH - 100, 2000));
                    jtb1.setFont(new Font("微软雅黑", Font.BOLD, 20));
                    jtb1.getTableHeader().setPreferredSize(new Dimension(1, 40));
                    jtb1.getTableHeader().setFont(new Font("宋体", Font.BOLD, 25));
                    jtb1.setRowHeight(50);

                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setPreferredSize(new Dimension(WIDTH - 200, 500));
                    scrollPane.setViewportView(jtb1);
                    //透明化处理
                    jtb1.setForeground(Color.BLACK);
                    jtb1.setFont(new Font("Serif", Font.BOLD, 28));
                    jtb1.setRowHeight(40);                //表格行高
                    jtb1.setPreferredScrollableViewportSize(new Dimension(850, 500));
                    jtb1.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                    renderer.setOpaque(false);    //设置透明
                    String[] Names = {"学年学期", "课程编号", "专业", "课程", "授课教师", "状态", "类型"};
                    for (int i = 0; i < 7; i++) {
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
                }
                jFrame.setVisible(true);
                tem.dispose();

            });
        }
    }
}
