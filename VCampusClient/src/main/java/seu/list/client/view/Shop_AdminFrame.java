package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.Goods;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Shop_AdminFrame {
    ArrayList<Goods> GoodsList;
    private JFrame frame;
    private JTextField textField;
    private JTable table;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private JButton btnNewButton_3;
    private JTextField Searchtext;
    private JButton btnNewButton_6;
    JScrollPane scrollPane;

    public Shop_AdminFrame() {
        initialize();
        GoodsList = new ArrayList<Goods>();
        frame.setVisible(true);

    }

    private void initialize() {

        setFrame(new JFrame());
        getFrame().setTitle("管理员视图商店");
        getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        double total = 0.0;
        frame.setLayout(null);

        //绘制背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Shop_Adminframe.PNG"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        frame.setBounds(d.width / 2 - 640, d.height / 2 - 360, 1280, 720);
        backgroundImageLabel.setBounds(0, 0, 1280, 720);
        frame.setSize(1280, 760);
        frame.setResizable(false);

        textField = new JTextField(); //显示营业额的框
        textField.setBounds(672, 155, 575 - 297, 198 - 154);
        textField.setFont(new Font("华文行楷", Font.BOLD, 36));
        textField.setEnabled(false);
        textField.setText(total + "");
        frame.add(textField);
        textField.setOpaque(false);
        textField.setBorder(new EmptyBorder(0, 0, 0, 0));

        //搜索框
        Searchtext = new JTextField("商品名称/编号");
        Searchtext.setBounds(297, 154, 575 - 297, 198 - 154);
        Searchtext.setFont(new Font("华文行楷", Font.BOLD, 36));
        frame.add(Searchtext);
        Searchtext.setOpaque(false);
        Searchtext.setBorder(new EmptyBorder(0, 0, 0, 0));

        //表格
        scrollPane = new JScrollPane();
        scrollPane.setEnabled(false);
        scrollPane.setBounds(261, 224, 1103 - 261, 568 - 224);
        table = new JTable();
        scrollPane.setViewportView(table);
        table.setBackground(Color.WHITE);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);
        table.setBounds(0, 0, 1103 - 261, 568 - 224);
        show();
        getTable().getColumnModel().getColumn(3).setPreferredWidth(79);
        frame.add(scrollPane);


        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Serif", Font.BOLD, 30));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {
                "商品编号", "商品名称", "单价", "库存"};
        for (int i = 0; i < Names.length; i++) {
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


        frame.add(backgroundImageLabel);
        //表格放入带滑动条的容器中
        Shop_StudentFrame.MyCellEditor cellEditor = new Shop_StudentFrame.MyCellEditor(new JTextField());

        table.getColumnModel().getColumn(3).setPreferredWidth(79);
        table.getTableHeader().setReorderingAllowed(false);


        //删除按钮
        btnNewButton = new JButton("");
        btnNewButton.setBounds(115, 372, 220 - 114, 540 - 498 + 8);
        btnNewButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //
                DelGoods(e);
            }
        });
        frame.add(btnNewButton);
        btnNewButton.setOpaque(false);

        //刷新按钮
        btnNewButton_6 = new JButton("");
        btnNewButton_6.setBounds(979, 155, 220 - 114, 540 - 498 + 8);
        btnNewButton_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                show();
            }
        });
        frame.add(btnNewButton_6);
        btnNewButton_6.setOpaque(false);

        //退出按钮
        btnNewButton_1 = new JButton("");
        btnNewButton_1.setBounds(114, 498, 220 - 114, 540 - 498 + 8);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        frame.add(btnNewButton_1);
        btnNewButton_1.setOpaque(false);

        //添加按钮
        JButton btnNewButton_2 = new JButton("");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddGoods(e);
            }
        });
        btnNewButton_2.setBounds(116, 248, 220 - 114, 540 - 498 + 8);
        frame.add(btnNewButton_2);
        btnNewButton_2.setOpaque(false);


        //查寻按钮
        btnNewButton_3 = new JButton("");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchGood(e);
            }
        });
        btnNewButton_3.setBounds(236, 153, 287 - 236, 198 - 154);
        frame.add(btnNewButton_3);
        btnNewButton_3.setOpaque(false);
    }

    /**
     * 方法{@code  void modify()}点击表格修改后触发此函数，完成修改相关的功能，
     */
    protected void modify() {
        // TODO 自动生成的方法存根
        Goods_modifyprice a = new Goods_modifyprice(this);
    }

    /**
     * 方法{@code  void DelGoods(ActionEvent e)}点击“删除”按钮后触发此函数，完成删除相关的功能
     */
    private void DelGoods(ActionEvent e) {
        // TODO 自动生成的方法存根
        Shop_DeleteGoods a = new Shop_DeleteGoods(this);
    }

    /**
     * 方法{@code  void AddGoods(ActionEvent e)}点击“增加”按钮后触发此函数，完成增加相关的功能
     */
    private void AddGoods(ActionEvent e) {
        // TODO 自动生成的方法存根
        Goods_Addframe a = new Goods_Addframe(this);
    }

    /**
     * 方法{@code  void SearchGood(ActionEvent e)}在搜素框输入内容并点击搜索按钮后触发，完成搜素相关功能
     */
    private void SearchGood(ActionEvent e) {
        // TODO 自动生成的方法存根
        Message mes = new Message();
        mes.setData(Searchtext.getText());
        mes.setModuleType(ModuleType.Shop);
        if (((String) Searchtext.getText()).equals("")) {
            show();
            return;
        }
        if (Searchtext.getText().matches("[0-9]*")) {//商品ID查找
            mes.setMessageType(MessageType.GoodsSearch_ID);

            Client client = null;
            client = new Client(ClientMainFrame.socket);
            Message serverResponse = client.sendRequestToServer(mes);
            ArrayList<Goods> res = (ArrayList<Goods>) serverResponse.getData();

            MyCellEditor_double cellEditor_db = new MyCellEditor_double(new JTextField());
            MyCellEditor_int cellEditor = new MyCellEditor_int(new JTextField());

            DefaultTableModel tablemodel;
            tablemodel = new DefaultTableModel(new Object[][]{}, new String[]{
                    "商品编号", "商品名称", "单价", "库存"}) {


                /*
                 * overload the method to change the table's factor
                 */
                boolean[] columnEditables = new boolean[]{
                        false, false, true, true
                };

                @Override
                public boolean isCellEditable(int row, int column) {

                    return columnEditables[column];
                }
            };

            for (int i = 0; i < res.size(); i++) {
                String[] tempgoods = new String[4];
                tempgoods[0] = res.get(i).getGoodsid() + "";
                tempgoods[1] = res.get(i).getGoodsname();
                tempgoods[2] = res.get(i).getGoodsprice() + "";
                tempgoods[3] = res.get(i).getGoodsnumber() + "";
                tablemodel.addRow(tempgoods);
            }
            getTable().setModel(tablemodel);
            //System.out.println("1");
            TableColumn tableColumn = getTable().getColumn("库存");
            tableColumn.setCellEditor(cellEditor);

            TableColumn tableColumnd = getTable().getColumn("单价");
            tableColumnd.setCellEditor(cellEditor_db);


            table.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    // 第一个 和 最后一个 被改变的行（只改变了一行，则两者相同）
                    int firstRow = e.getFirstRow();
                    int lastRow = e.getLastRow();

                    // 被改变的列
                    int type = e.getType();
                    if (type == TableModelEvent.UPDATE) {
                        modify();
                    }
                }
            });
        } else {
            mes.setMessageType(MessageType.GoodsSearch_Name);

            Client client = new Client(ClientMainFrame.socket);
            Message serverResponse = client.sendRequestToServer(mes);
            ArrayList<Goods> res = (ArrayList<Goods>) serverResponse.getData();

            MyCellEditor_double cellEditor_db = new MyCellEditor_double(new JTextField());
            MyCellEditor_int cellEditor = new MyCellEditor_int(new JTextField());

            DefaultTableModel tablemodel;
            tablemodel = new DefaultTableModel(new Object[][]{}, new String[]{
                    "商品编号", "商品名称", "单价", "库存"}) {


                /*
                 * overload the method to change the table's factor
                 */
                boolean[] columnEditables = new boolean[]{
                        false, false, true, true
                };

                @Override
                public boolean isCellEditable(int row, int column) {

                    return columnEditables[column];
                }
            };
            for (int i = 0; i < res.size(); i++) {
                String[] tempgoods = new String[4];
                tempgoods[0] = res.get(i).getGoodsid() + "";
                tempgoods[1] = res.get(i).getGoodsname();
                tempgoods[2] = res.get(i).getGoodsprice() + "";
                tempgoods[3] = res.get(i).getGoodsnumber() + "";
                tablemodel.addRow(tempgoods);
            }
            getTable().setModel(tablemodel);
            TableColumn tableColumn = getTable().getColumn("库存");
            tableColumn.setCellEditor(cellEditor);

            TableColumn tableColumnd = getTable().getColumn("单价");
            tableColumnd.setCellEditor(cellEditor_db);


            table.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    // 第一个 和 最后一个 被改变的行（只改变了一行，则两者相同）
                    int firstRow = e.getFirstRow();
                    int lastRow = e.getLastRow();

                    // 被改变的列
                    int type = e.getType();
                    if (type == TableModelEvent.UPDATE) {
                        modify();
                    }
                }
            });
        }

        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Serif", Font.BOLD, 30));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {
                "商品编号", "商品名称", "单价", "库存"};
        for (int i = 0; i < Names.length; i++) {
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
     * 方法{@code  void get_turnover()}获得营业额并显示在前端
     */
    private void get_turnover() {
        Message mes = new Message();
        mes.setMessageType(MessageType.Goodsgetturnover);
        mes.setModuleType(ModuleType.Shop);
        Client client = new Client(ClientMainFrame.socket);
        Message serverResponse = client.sendRequestToServer(mes);
        Double total = (Double) serverResponse.getData();
        textField.setText(total + "");
    }

    /**
     * 方法{@code  void show()}在表格呈现所有商品相关的信息
     */
    public void show() {
        MyCellEditor_double cellEditor_db = new MyCellEditor_double(new JTextField());
        MyCellEditor_int cellEditor = new MyCellEditor_int(new JTextField());

        Message mes = new Message();
        mes.setMessageType(MessageType.Goodsgetall);
        mes.setModuleType(ModuleType.Shop);
        Client client = new Client(ClientMainFrame.socket);
        Message serverResponse = client.sendRequestToServer(mes);
        GoodsList = (ArrayList<Goods>) serverResponse.getData();


        DefaultTableModel tablemodel;
        tablemodel = new DefaultTableModel(new Object[][]{}, new String[]{
                "商品编号", "商品名称", "单价", "库存"}) {


            /*
             * overload the method to change the table's factor
             */
            boolean[] columnEditables = new boolean[]{
                    false, false, true, true
            };

            @Override
            public boolean isCellEditable(int row, int column) {

                return columnEditables[column];
            }
        };
        for (int i = 0; i < GoodsList.size(); i++) {
            String tempgoods[] = new String[4];
            tempgoods[0] = GoodsList.get(i).getGoodsid() + "";
            tempgoods[1] = GoodsList.get(i).getGoodsname();
            tempgoods[2] = GoodsList.get(i).getGoodsprice() + "";
            tempgoods[3] = GoodsList.get(i).getGoodsnumber() + "";
            tablemodel.addRow(tempgoods);
        }
        getTable().setModel(tablemodel);

        TableColumn tableColumn = getTable().getColumn("库存");
        tableColumn.setCellEditor(cellEditor);

        TableColumn tableColumnd = getTable().getColumn("单价");
        tableColumnd.setCellEditor(cellEditor_db);

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // 第一个 和 最后一个 被改变的行（只改变了一行，则两者相同）
                int firstRow = e.getFirstRow();
                int lastRow = e.getLastRow();
                int column = e.getColumn();
                int type = e.getType();
                if (type == TableModelEvent.UPDATE) {
                    modify();
                }
            }
        });
        get_turnover();
        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Serif", Font.BOLD, 30));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {
                "商品编号", "商品名称", "单价", "库存"};
        for (int i = 0; i < Names.length; i++) {
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

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public static class MyCellEditor_int extends DefaultCellEditor {

        public MyCellEditor_int(JTextField textField) {
            super(textField);
        }

        @Override
        public boolean stopCellEditing() {
            // 获取当前单元格的编辑器组件
            Component comp = getComponent();

            // 获取当前单元格编辑器输入的值
            Object obj = getCellEditorValue();

            // 如果当前单元格编辑器输入的值不是数字，则返回 false（表示数据非法，不允许设置，无法保存）
            if (obj == null || !obj.toString().matches("[0-9]*")) {
                // 数据非法时，设置编辑器组件内的内容颜色为红色
                comp.setForeground(Color.RED);
                return false;
            }

            // 数据合法时，设置编辑器组件内的内容颜色为黑色
            comp.setForeground(Color.BLACK);

            // 合法数据交给父类处理
            return super.stopCellEditing();
        }
    }


    public static class MyCellEditor_double extends DefaultCellEditor {

        public MyCellEditor_double(JTextField textField) {
            super(textField);
        }

        @Override
        public boolean stopCellEditing() {
            // 获取当前单元格的编辑器组件
            Component comp = getComponent();

            // 获取当前单元格编辑器输入的值
            Object obj = getCellEditorValue();

            // 如果当前单元格编辑器输入的值不是double，则返回 false（表示数据非法，不允许设置，无法保存）
            if (obj == null || !obj.toString().matches("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}")) {
                // 数据非法时，设置编辑器组件内的内容颜色为红色
                comp.setForeground(Color.RED);
                return false;
            }

            // 数据合法时，设置编辑器组件内的内容颜色为黑色
            comp.setForeground(Color.BLACK);

            // 合法数据交给父类处理
            return super.stopCellEditing();
        }
    }

}
