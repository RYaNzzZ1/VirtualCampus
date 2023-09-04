package seu.list.client.view;

import seu.list.client.bz.Client;
import seu.list.client.bz.ClientMainFrame;
import seu.list.common.Dormitory;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
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
        setBounds(100, 100, 391, 283);

        applyShow();

        //添加图标
        Image image = new ImageIcon("VCampusClient/src/main/resources/image/xiaobiao.jpg").getImage();
        setIconImage(image);

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JLabel lblNewLabel = new JLabel("申请管理");
        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        JLabel lblNewLabel_1 = new JLabel("学号：");
        lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        DeuserIDField = new JTextField();
        DeuserIDField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        DeuserIDField.setColumns(10);
        GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
        gl_contentPanel.setHorizontalGroup(
                gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup()
                                .addGap(76)
                                .addComponent(lblNewLabel_1)
                                .addGap(18)
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel)
                                        .addComponent(DeuserIDField, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(95, Short.MAX_VALUE))
        );
        gl_contentPanel.setVerticalGroup(
                gl_contentPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPanel.createSequentialGroup()
                                .addGap(23)
                                .addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(lblNewLabel_1)
                                        .addGroup(gl_contentPanel.createSequentialGroup()
                                                .addComponent(lblNewLabel)
                                                .addGap(46)
                                                .addComponent(DeuserIDField, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(66, Short.MAX_VALUE))
        );
        contentPanel.setLayout(gl_contentPanel);
        {
            buttonPane = new JPanel();
            {
                commitButton = new JButton("提交");
                commitButton.setActionCommand("OK");
                getRootPane().setDefaultButton(commitButton);
                commitButton.addActionListener(e -> {
                    // TODO Auto-generated method stub
                    applyCommitAct(e);
                    setVisible(false);
                });
            }
            {
                divestButton = new JButton("否决");
                divestButton.setActionCommand("OK");
                divestButton.addActionListener(e -> {
                    applyDivestAct(e);
                    // TODO Auto-generated method stub
                    setVisible(false);
                });
            }
            {
                cancelButton = new JButton("取消");
                cancelButton.setActionCommand("Cancel");
                cancelButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        setVisible(false);
                    }

                });
            }
            GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
            gl_buttonPane.setHorizontalGroup(
                    gl_buttonPane.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_buttonPane.createSequentialGroup()
                                    .addGap(66)
                                    .addComponent(commitButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                    .addGap(71)
                                    .addComponent(divestButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                    .addGap(71)
                                    .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                    .addGap(126))
            );
            gl_buttonPane.setVerticalGroup(
                    gl_buttonPane.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_buttonPane.createSequentialGroup()
                                    .addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(commitButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(divestButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(15, Short.MAX_VALUE))
            );
            buttonPane.setLayout(gl_buttonPane);
        }
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(contentPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                                        .addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 375, Short.MAX_VALUE))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(buttonPane, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                .addGap(12))
        );
        getContentPane().setLayout(groupLayout);

        //居中显示
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
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

        //展示
        //显示宿舍信息的表格
        scrollPane = new JScrollPane();
        scrollPane.setEnabled(false);
        scrollPane.setBounds(330, 100, 470, 400);
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
                        "学号", "宿舍号", "维修申请"
                }
        ) {
            boolean[] columnEditables = new boolean[]{
                    false, false, false
            };

            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        // table.getColumnModel().getColumn(3).setPreferredWidth(79);
        table.setBounds(0, 0, 470, 400);
        table.getTableHeader().setReorderingAllowed(false);

        scrollPane.setViewportView(table);


        Object[][] dorminformation = {};
        Object[] dormlist = {"学号", "宿舍号", "维修申请"};
        DefaultTableModel model;
        model = new DefaultTableModel(dorminformation, dormlist);


        System.out.println("bbbbb");

        for (int i = 0; i < dormitoriesWithApply.size(); i++) {
            String[] arr = new String[3];
            arr[0] = dormitoriesWithApply.get(i).getuserID();
            arr[1] = dormitoriesWithApply.get(i).getDormitoryID();
            arr[2] = dormitoriesWithApply.get(i).getDormitoryMaintain();

            model.addRow(arr);
            table.setModel(model);
        }

        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        //table.getTableHeader().setFont(new Font("微软雅黑", Font.PLAIN, 5));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {"学号", "宿舍号", "维修申请"};
        for (int i = 0; i < 3; i++) {
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
        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormCommitApply);

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
        Message serverResponse = client.sendRequestToServer(mes);

        C.setEnabled(true);

        this.dispose();
        JOptionPane.showMessageDialog(null, "完成", "提示", JOptionPane.WARNING_MESSAGE);
    }
}


