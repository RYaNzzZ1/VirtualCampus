/**
 * @author 周楚翘
 * @version jdk1.8.0
 */
package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.Dormitory;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;

public class DormitoryAdminClient extends JFrame {

    private JPanel contentPane;
    private JTextField searchField;
    private JTable table;
    //static Socket socket;
    //private static Socket socket;
    private Socket socket;
    private JScrollPane scrollPane;
    public int k = 0;
    public ArrayList<Dormitory> Dorm = new ArrayList<Dormitory>();

    public DormitoryAdminClient(Socket socket) {
        this.socket = socket;
        setFont(new Font("微软雅黑", Font.BOLD, 12));
        setTitle("宿舍-管理员");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        //添加图标
//		Image image=new ImageIcon("VCampusClient/src/main/resources/image/xiaobiao.jpg").getImage();
//		setIconImage(image);

        // 创建带有背景图片的JLabel
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/DormitoryAdminClient.PNG"));
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        backgroundImageLabel.setBounds(0, 0, 1280, 720);
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 1280 / 2, d.height / 2 - 720 / 2, 1280, 720);
        backgroundImageLabel.setOpaque(false); // 设置背景透明
        setVisible(true);

        setResizable(false); //阻止用户拖拽改变窗口的大小


        //显示宿舍信息的表格
        scrollPane = new JScrollPane();
        scrollPane.setEnabled(false);
        scrollPane.setBounds(250, 218, 922, 364);

        //表格放入带滑动条的容器中
        table = new JTable();
        table.setBackground(Color.WHITE);
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setRowHeight(25);

        //table.setFont(new Font("华文行楷", Font.BOLD, 15));
        table.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "学号", "宿舍", "床位", "卫生评分", "水费", "电费", "调换申请", "维修申请"
                }
        ) {
            boolean[] columnEditables = new boolean[]{
                    false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(3).setPreferredWidth(79);
        table.setBounds(0, 0, 922, 364);
        table.getTableHeader().setReorderingAllowed(false);

        //final TableModel tableModel=table.getModel();

        scrollPane.setViewportView(table);


        Object[][] dorminformation = {};
        Object[] dormlist = {"学号", "宿舍", "床位", "卫生评分", "水费", "电费", "调换申请", "维修申请"};
        DefaultTableModel model;
        model = new DefaultTableModel(dorminformation, dormlist);


        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        //table.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 5));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {"学号", "宿舍", "床位", "卫生评分", "水费", "电费", "调换申请", "维修申请"};
        for (int i = 0; i < 8; i++) {
            //System.out.println(Names[i]);
            //System.out.println(table.getColumn(Names[i]));
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

        add(scrollPane);

        //设置搜索文本框
        searchField = new JTextField();
        searchField.setFont(new Font("微软雅黑", Font.PLAIN, 28));
        searchField.setText("请输入学号");
        searchField.setColumns(20);
        searchField.setBounds(690, 150, 286, 50);
        add(searchField);
        searchField.setOpaque(false);
        searchField.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(backgroundImageLabel);

        //设置搜索按钮
        JButton searchButton = new JButton("搜索");
        searchButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        searchButton.setBounds(990, 150, 120, 50);
        add(searchButton);
        searchButton.setOpaque(false);

        searchButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                SearchAct(e);
            }
        });


        //设置增加按钮
        JButton addButton = new JButton("增加");
        addButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        addButton.setBounds(104, 223, 120, 50);
        add(addButton);
        addButton.setOpaque(false);

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                AddAct(e);
            }
        });

        //设置删除按钮
        JButton deleteButton = new JButton("删除");
        deleteButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        deleteButton.setBounds(104, 320, 120, 50);
        add(deleteButton);
        deleteButton.setOpaque(false);

        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                DeleteAct(e);
            }
        });

        //设置申请按钮
        JButton applyButton = new JButton("申请");
        applyButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        applyButton.setBounds(105, 513, 120, 50);
        add(applyButton);
        applyButton.setOpaque(false);
        applyButton.addActionListener(this::applyAct);


        //设置修改按钮
        JButton modifyNewButton = new JButton("修改");
        modifyNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        modifyNewButton.setBounds(104, 420, 120, 50);
        add(modifyNewButton);
        modifyNewButton.setOpaque(false);

        // TODO Auto-generated method stub
        modifyNewButton.addActionListener(this::ModifyAct);

        //设置完成按钮
        JButton exitNewButton_1 = new JButton("完成");
        exitNewButton_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        exitNewButton_1.setBounds(104, 515, 120, 50);
        add(exitNewButton_1);
        exitNewButton_1.setOpaque(false);

        exitNewButton_1.addActionListener(e -> {
            // TODO Auto-generated method stub
            setVisible(false);
        });


        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormAdShow);

        Client client = new Client(this.socket);
        Message rec = client.sendRequestToServer(mes);
        ArrayList<Dormitory> allDormitoryContents = (ArrayList<Dormitory>) rec.getData();
        Dorm = allDormitoryContents;

        Object[] sigRow = new String[8];
        for (int i = 0; i < allDormitoryContents.size(); i++) {
            String[] arr = new String[8];
            arr[0] = allDormitoryContents.get(i).getuserID();
            arr[1] = allDormitoryContents.get(i).getDormitoryID();
            arr[2] = String.valueOf(allDormitoryContents.get(i).getStudentBunkID());
            arr[3] = String.valueOf(allDormitoryContents.get(i).getDormitoryScore());
            arr[4] = String.valueOf(allDormitoryContents.get(i).getWater());
            arr[5] = String.valueOf(allDormitoryContents.get(i).getElectricity());
            arr[6] = allDormitoryContents.get(i).getStudentExchange();
            arr[7] = allDormitoryContents.get(i).getDormitoryMaintain();

            model.addRow(arr);
            table.setModel(model);
        }

        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明

        for (int i = 0; i < 8; i++) {
            //System.out.println(Names[i]);
            //System.out.println(table.getColumn(Names[i]));
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

    /**
     * 显示修改界面
     *
     * @param e
     */
    protected void ModifyAct(ActionEvent e) {
        // TODO Auto-generated method stub
        Dormmodify Modify = new Dormmodify(this, ClientMainFrame.socket);
        Modify.setVisible(true);
    }


    protected void applyAct(ActionEvent e) {
        DormApply apply = new DormApply(this, ClientMainFrame.socket);
        apply.setVisible(true);
    }

    /**
     * 显示添加界面
     *
     * @param e
     */
    protected void AddAct(ActionEvent e) {
        // TODO Auto-generated method stub
        Dormadd add = new Dormadd(this, ClientMainFrame.socket);
        add.setVisible(true);
    }

    /**
     * 显示删除界面
     *
     * @param e
     */
    protected void DeleteAct(ActionEvent e) {
        // TODO Auto-generated method stub
        Dormdelete delete = new Dormdelete(this, ClientMainFrame.socket);
        delete.setVisible(true);
    }

    /**
     * 查找宿舍
     *
     * @param e
     */
    protected void SearchAct(ActionEvent e) {
        // TODO Auto-generated method stub
        Object[][] dorminformation = {};
        Object[] dormlist = {"学号", "宿舍", "床位", "卫生评分", "水费", "电费", "调换申请", "维修申请"};
        DefaultTableModel model;
        model = new DefaultTableModel(dorminformation, dormlist);

        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormSearch);
        mes.setData(searchField.getText());
        System.out.println(searchField.getText());
        Client client = new Client(ClientMainFrame.socket);

        Message rec = client.sendRequestToServer(mes);
        ArrayList<Dormitory> allDormitoryContents = (ArrayList<Dormitory>) rec.getData();
        if (allDormitoryContents.size() == 0) {
            JOptionPane.showMessageDialog(null, "非法查询！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Dorm = allDormitoryContents;
        System.out.println(allDormitoryContents);
        System.out.println(allDormitoryContents.size());
        Object sigRow[] = new String[8];
        for (int i = 0; i < allDormitoryContents.size(); i++) {
            String[] arr = new String[8];
            arr[0] = allDormitoryContents.get(i).getuserID();
            arr[1] = allDormitoryContents.get(i).getDormitoryID();
            arr[2] = String.valueOf(allDormitoryContents.get(i).getStudentBunkID());
            arr[3] = String.valueOf(allDormitoryContents.get(i).getDormitoryScore());
            arr[4] = String.valueOf(allDormitoryContents.get(i).getWater());
            arr[5] = String.valueOf(allDormitoryContents.get(i).getElectricity());
            arr[6] = allDormitoryContents.get(i).getStudentExchange();
            arr[7] = allDormitoryContents.get(i).getDormitoryMaintain();

            model.addRow(arr);
            table.setModel(model);
        }
    }

    /**
     * 更新添加宿舍后列表
     *
     * @param temp
     */
    public void updateFrame(Dormitory temp) {
        // TODO Auto-generated method stub
        Object[][] dorminformation = {};
        Object[] dormlist = {"学号", "宿舍", "床位", "卫生评分", "水费", "电费", "调换申请", "维修申请"};
        DefaultTableModel model;
        for (int i = 0; i < Dorm.size(); i++)
            if (Dorm.get(i).getuserID().equals(temp.getuserID())) {
                JOptionPane.showMessageDialog(null, "非法添加！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
        Dorm.add(temp);
        model = new DefaultTableModel(dorminformation, dormlist);
        System.out.println(Dorm);
        Object[] sigRow = new String[8];
        for (int i = 0; i < Dorm.size(); i++) {
            String[] arr = new String[8];
            arr[0] = Dorm.get(i).getuserID();
            arr[1] = Dorm.get(i).getDormitoryID();
            arr[2] = String.valueOf(Dorm.get(i).getStudentBunkID());
            arr[3] = String.valueOf(Dorm.get(i).getDormitoryScore());
            arr[4] = String.valueOf(Dorm.get(i).getWater());
            arr[5] = String.valueOf(Dorm.get(i).getElectricity());
            arr[6] = Dorm.get(i).getStudentExchange();
            arr[7] = Dorm.get(i).getDormitoryMaintain();

            model.addRow(arr);
            table.setModel(model);
        }
    }

    /**
     * 更新删除宿舍后列表
     *
     * @param userID
     */
    public void updateFrameD(String userID) {
        // TODO Auto-generated method stub
        boolean flag = false;
        for (int i = 0; i < Dorm.size(); i++)
            if (Dorm.get(i).getuserID().equals(userID)) {
                Dorm.remove(i);
                flag = true;
            }
        if (!flag) {
            JOptionPane.showMessageDialog(null, "非法删除！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object[][] dorminformation = {};
        Object[] dormlist = {"学号", "宿舍", "床位", "卫生评分", "水费", "电费", "调换申请", "维修申请"};
        DefaultTableModel model;
        model = new DefaultTableModel(dorminformation, dormlist);
        System.out.println(Dorm);
        Object sigRow[] = new String[8];
        for (int i = 0; i < Dorm.size(); i++) {
            String[] arr = new String[8];
            arr[0] = Dorm.get(i).getuserID();
            arr[1] = Dorm.get(i).getDormitoryID();
            arr[2] = String.valueOf(Dorm.get(i).getStudentBunkID());
            arr[3] = String.valueOf(Dorm.get(i).getDormitoryScore());
            arr[4] = String.valueOf(Dorm.get(i).getWater());
            arr[5] = String.valueOf(Dorm.get(i).getElectricity());
            arr[6] = Dorm.get(i).getStudentExchange();
            arr[7] = Dorm.get(i).getDormitoryMaintain();

            model.addRow(arr);
            table.setModel(model);
        }
    }

    /**
     * 更新修改宿舍后列表
     *
     * @param para
     */
    public void updateFrameM(ArrayList<String> para) {
        // TODO Auto-generated method stub
        boolean flag = false;
        String userID = para.get(0);
        String usertype = para.get(1);
        String temp = para.get(2);
        for (int i = 0; i < Dorm.size(); i++)
            if (Dorm.get(i).getuserID().equals(userID)) {
                flag = true;
                if ("宿舍".equals(usertype)) {
                    Dorm.get(i).setDormitoryID(temp);
                }
                if ("卫生评分".equals(usertype)) {
                    Dorm.get(i).setDormitoryScore(Integer.parseInt(temp));
                }
                if ("水费".equals(usertype)) {
                    Dorm.get(i).setWater(Integer.parseInt(temp));
                }
                if ("电费".equals(usertype)) {
                    Dorm.get(i).setElectricity(Integer.parseInt(temp));
                }
                if ("宿舍维修".equals(usertype)) {
                    Dorm.get(i).setDormitoryMaintain(temp);
                }
                if ("宿舍调换".equals(usertype)) {
                    Dorm.get(i).setStudentExchange(temp);
                }
            }
        if (!flag) {
            JOptionPane.showMessageDialog(null, "非法修改！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        System.out.println(Dorm);
        Object[][] dorminformation = {};
        Object[] dormlist = {"学号", "宿舍", "床位", "卫生评分", "水费", "电费", "调换申请", "维修申请"};
        DefaultTableModel model;
        model = new DefaultTableModel(dorminformation, dormlist);
        System.out.println(Dorm);
        Object sigRow[] = new String[8];
        for (int i = 0; i < Dorm.size(); i++) {
            String[] arr = new String[8];
            arr[0] = Dorm.get(i).getuserID();
            arr[1] = Dorm.get(i).getDormitoryID();
            arr[2] = String.valueOf(Dorm.get(i).getStudentBunkID());
            arr[3] = String.valueOf(Dorm.get(i).getDormitoryScore());
            arr[4] = String.valueOf(Dorm.get(i).getWater());
            arr[5] = String.valueOf(Dorm.get(i).getElectricity());
            arr[6] = Dorm.get(i).getStudentExchange();
            arr[7] = Dorm.get(i).getDormitoryMaintain();

            model.addRow(arr);
            table.setModel(model);
        }
    }
}
