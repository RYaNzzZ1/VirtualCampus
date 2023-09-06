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

public class DormApply extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JButton commitButton;
    private JButton divestButton;
    private JButton cancelButton;


    private JRadioButton exchangeButton;
    private JRadioButton maintainButton;

    private static JTable table;
    private static JScrollPane scrollPane;

    private JPanel buttonPane;
    private JTextField DeuserIDField;
    private JCheckBox[] jCheckBoxes;

    DormitoryAdminClient C;
    public ArrayList<Dormitory> allDormitoryContents;
    private int dormSize;
    private ArrayList<Dormitory> dormitoriesWithApply;

    public DormApply(DormitoryAdminClient c, Socket socket) {
        C = c;
        setVisible(true);
        setTitle("申请管理");
        setLayout(null);

        applyShow();

//        //添加图标

        // 创建带有背景图片的JLabel
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Dormapply.png"));
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        backgroundImageLabel.setBounds(0, 0, 845, 639);
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 845 / 2, d.height / 2 - 639 / 2, 845, 639);
        backgroundImageLabel.setOpaque(false); // 设置背景透明
        setVisible(true);

        setResizable(false); //阻止用户拖拽改变窗口的大小


        exchangeButton = new JRadioButton("审核调换申请");    //创建JRadioButton对象
        exchangeButton.setFont(new Font("华文行楷", Font.BOLD, 24));
        exchangeButton.setBounds(50, 355, 180, 40);
        add(exchangeButton);

        maintainButton = new JRadioButton("审核维修申请");    //创建JRadioButton对象
        maintainButton.setFont(new Font("华文行楷", Font.BOLD, 24));
        maintainButton.setBounds(50, 446, 180, 40);
        add(maintainButton);

        ButtonGroup group = new ButtonGroup();
        //添加JRadioButton到ButtonGroup中
        group.add(exchangeButton);
        group.add(maintainButton);

        add(backgroundImageLabel);

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        {
            buttonPane = new JPanel();
            {
                commitButton = new JButton("提交");
                commitButton.setActionCommand("OK");
                getRootPane().setDefaultButton(commitButton);
                commitButton.setBounds(126, 519, 100, 50);
                add(commitButton);
                commitButton.setOpaque(false);
                commitButton.addActionListener(e -> {
                    System.out.println("提交一点击");
                    // TODO Auto-generated method stub
                    applyCommitAct(e);
                    setVisible(false);
                });
            }
            {
                divestButton = new JButton("否决");
                divestButton.setActionCommand("OK");
                divestButton.setBounds(367, 515, 100, 50);
                add(divestButton);
                divestButton.setOpaque(false);
                divestButton.addActionListener(e -> {
                    applyDivestAct(e);
                    // TODO Auto-generated method stub
                    setVisible(false);
                });
            }
            {
                cancelButton = new JButton("取消");
                cancelButton.setActionCommand("Cancel");
                cancelButton.setBounds(610, 510, 100, 50);
                add(cancelButton);
                cancelButton.setOpaque(false);
                cancelButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        setVisible(false);
                    }

                });
            }
        }
    }

    private void applyShow() {
        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormApplyShow);
        Client client = new Client(ClientMainFrame.socket);
        Message serverResponse = client.sendRequestToServer(mes);
        dormitoriesWithApply = (ArrayList<Dormitory>) serverResponse.getData();
        dormSize = dormitoriesWithApply.size();
        jCheckBoxes = new JCheckBox[dormSize];

        JPanel panel = new JPanel();

        for (int i = 0; i < dormSize; i++) {
            System.out.println("bbbbb");
            jCheckBoxes[i] = new JCheckBox();
            System.out.println("aaaaaa");
            jCheckBoxes[i].setBounds(253, 140 + 40 * i, 20, 20);
            add(jCheckBoxes[i]);
        }


        //展示
        //显示宿舍信息的表格
        scrollPane = new JScrollPane();
        scrollPane.setEnabled(false);
        scrollPane.setBounds(280, 99, 526, 404);
        //表格放入带滑动条的容器中
        table = new JTable();
        table.setBackground(Color.WHITE);
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setRowHeight(25);
        table.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "学号", "宿舍号", "调换申请", "维修申请"
                }
        ) {
            boolean[] columnEditables = new boolean[]{false, false, false, false};

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });

        table.setBounds(0, 0, 526, 404);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(183);
        table.getColumnModel().getColumn(3).setPreferredWidth(183);
        scrollPane.setViewportView(table);


        Object[][] dorminformation = {};
        Object[] dormlist = {"学号", "宿舍号", "调换申请", "维修申请"};
        DefaultTableModel model;
        model = new DefaultTableModel(dorminformation, dormlist);

        System.out.println("bbbbb");

        for (int i = 0; i < dormitoriesWithApply.size(); i++) {
            String[] arr = new String[4];
            arr[0] = dormitoriesWithApply.get(i).getuserID();
            arr[1] = dormitoriesWithApply.get(i).getDormitoryID();
            arr[2] = dormitoriesWithApply.get(i).getStudentExchange();
            arr[3] = dormitoriesWithApply.get(i).getDormitoryMaintain();

            model.addRow(arr);
            table.setModel(model);
        }


        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("华文行楷", Font.PLAIN, 22));
        //table.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 5));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {"学号", "宿舍号", "调换申请", "维修申请"};
        for (int i = 0; i < 4; i++) {
            //System.out.println(Names[i]);
            //System.out.println(table.getColumn(Names[i]));
            table.getColumn(Names[i]).setCellRenderer(renderer);//单格渲染
            TableColumn column = table.getTableHeader().getColumnModel().getColumn(i);
            column.setHeaderRenderer(renderer);//表头渲染
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(183);
        table.getColumnModel().getColumn(3).setPreferredWidth(183);
        table.setOpaque(false);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBorder(BorderFactory.createBevelBorder(0));
        scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.getColumnHeader().setOpaque(false);

        add(scrollPane);
    }

    private void applyDivestAct(ActionEvent e) {
        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormDivestApply);

        String applyChoose = null;
        if (exchangeButton.isSelected()) {
            applyChoose = "ExchangeApply";
        } else if (maintainButton.isSelected()) {
            applyChoose = "MaintainApply";
        }
        if (applyChoose == null)
            return;
        ArrayList<String> para = new ArrayList<>();
        for (int i = 0; i < jCheckBoxes.length; i++) {
            if (jCheckBoxes[i].isSelected()) {
                String uID = dormitoriesWithApply.get(i).getuserID();
                para.add(applyChoose);
                para.add(uID);
            }
        }
        mes.setData(para);
        Client client = new Client(ClientMainFrame.socket);
        System.out.println("adsdhgifho");
        Message serverResponse = client.sendRequestToServer(mes);

        C.setEnabled(true);

        this.dispose();
        JOptionPane.showMessageDialog(null, "完成", "提示", JOptionPane.WARNING_MESSAGE);
    }

    protected void applyCommitAct(ActionEvent e) {
        String applyChoose = null;
        if (exchangeButton.isSelected()) {
            System.out.println("ex 已选");
            applyChoose = "ExchangeApply";
        } else if (maintainButton.isSelected()) {
            System.out.println("maintian 已选");
            applyChoose = "MaintainApply";
        }
        if (applyChoose == null) {
            System.out.println("apply c 为空");
            return;
        }
        ArrayList<String> para = new ArrayList<>();
        for (int i = 0; i < jCheckBoxes.length; i++) {
            if (jCheckBoxes[i].isSelected()) {
                System.out.println("jcheckbox" + i + "被选");
                String uID = dormitoriesWithApply.get(i).getuserID();
                System.out.println(i + "的uID:" + uID);
                para.add(applyChoose);
                para.add(uID);
            }
        }
        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormCommitApply);
        mes.setData(para);

        Client client = new Client(ClientMainFrame.socket);
        Message serverResponse = client.sendRequestToServer(mes);

        C.setEnabled(true);

        this.dispose();
        JOptionPane.showMessageDialog(null, "完成", "提示", JOptionPane.WARNING_MESSAGE);
    }
}


