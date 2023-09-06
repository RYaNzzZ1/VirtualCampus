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
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Shop_StudentFrame {

    private JFrame frame;
    private JTextField textField;
    private JTable table;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private JTextField SearchText;
    private JButton btnNewButton_2;
    private JScrollPane scrollPane;

    //总价
    private double sum = 0.0;
    private String id = null;
    private String PWD = null;

    private MainMenu Mainmenu = null;

    public Shop_StudentFrame(String id, String PWD, MainMenu mainmenu) {
        this.Mainmenu = mainmenu;
        this.id = id;
        this.PWD = PWD;
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        sum = 0.0;
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 532);
        //设置背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Shop_StudentFrame.PNG"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        frame.setBounds(d.width / 2 - 640, d.height / 2 - 360, 1280, 720);
        backgroundImageLabel.setBounds(0, 0, 1280, 720);
        frame.setSize(1280, 760 - 3 - 3);
        frame.setTitle("校园超市");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        //主frame

        //金额显示框
        textField = new JTextField();
        textField.setBounds(723 + 2, 157, 627 - 348, 199 - 157);
        textField.setFont(new Font("华文行楷", Font.BOLD, 36));
        textField.setEnabled(false);
        textField.setText(sum + "");
        frame.add(textField);
        textField.setBorder(new EmptyBorder(0, 0, 0, 0));
        textField.setOpaque(false);


        //搜索框
        SearchText = new JTextField("请输入商品名称");
        SearchText.setBounds(348, 157, 627 - 348, 199 - 157);
        SearchText.setFont(new Font("华文行楷", Font.BOLD, 36));
        frame.add(SearchText);
        SearchText.setBorder(new EmptyBorder(0, 0, 0, 0));
        SearchText.setOpaque(false);


        //显示商品的表格
        scrollPane = new JScrollPane();
        scrollPane.setEnabled(false);
        scrollPane.setBounds(265, 225, 1100 - 265 + 10, 566 - 260 + 15 + 15 + 10);

        //表格放入带滑动条的容器中
        table = new JTable();
        table.setBackground(Color.WHITE);
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setRowHeight(25);
        show();
        table.getColumnModel().getColumn(3).setPreferredWidth(79);
        table.setBounds(0, 0, 1100 - 265 + 10, 566 - 260 + 15 + 15 + 10);
        MyCellEditor cellEditor = new MyCellEditor(new JTextField());
        TableColumn tableColumn = table.getColumn("购买数量");
        tableColumn.setCellEditor(cellEditor);//确保输入合法
        table.getColumnModel().getColumn(3).setPreferredWidth(79);
        table.getTableHeader().setReorderingAllowed(false);
        final TableModel tableModel = table.getModel();
        //表格显示商品信息


        tableModel.addTableModelListener(new TableModelListener() {
                                             @Override
                                             public void tableChanged(TableModelEvent e) {
                                                 int firstRow = e.getFirstRow();
                                                 int lastRow = e.getLastRow();
                                                 int column = e.getColumn();
                                                 int type = e.getType();
                                                 if (type == TableModelEvent.UPDATE) {
                                                     sum = 0.0;
                                                     if (column == 4) {
                                                         for (int row = 0; row < table.getRowCount(); row++) {
                                                             Object tempnumber = tableModel.getValueAt(row, 4);
                                                             Object tempprice = tableModel.getValueAt(row, 2);
                                                             double tem = Double.parseDouble((String) tempprice);
                                                             int tem1 = Integer.parseInt((String) tempnumber);
                                                             sum += tem * tem1;

                                                         }
                                                         textField.setText(sum + "");
                                                     } else return;
                                                 }
                                             }
                                         }//表格增加监听，修改信息时需确认
        );

        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Serif", Font.BOLD, 28));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {
                "商品编号", "商品名称", "单价", "库存", "购买数量"};
        for (int i = 0; i < 5; i++) {
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


        frame.add(scrollPane);
        frame.add(backgroundImageLabel);
        //结结账按钮
        btnNewButton = new JButton("");
        btnNewButton.setBounds(112, 316, 216 - 113 + 8, 356 - 316);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setPayFrame();
            }
        });
        frame.add(btnNewButton);
        btnNewButton.setOpaque(false);
        //购买界面确认需输入密码

        //退出按钮
        btnNewButton_1 = new JButton("");
        //btnNewButton_1.setBounds(10, 308, 60, 25);
        btnNewButton_1.setBounds(112, 462, 216 - 113 + 8, 356 - 316);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        btnNewButton_1.setOpaque(false);
        btnNewButton_1.setFont(new Font("微软雅黑", Font.BOLD, 20));
        //退出
        frame.add(btnNewButton_1);


        //搜索按钮
        btnNewButton_2 = new JButton("");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchGood(e);
            }
        });
        btnNewButton_2.setOpaque(false);

        btnNewButton_2.setBounds(285, 156, 338 - 285, 199 - 157);
        frame.add(btnNewButton_2);


        //重写关闭事件和窗口居中
        //frame.setDefaultCloseOperation(2);
        //frame.setLocationRelativeTo(null);
    }

    /**
     * 将商品信息显示到表格中
     */

    public void show() {
        Message mes = new Message();
        mes.setMessageType(MessageType.Goodsgetall);
        mes.setModuleType(ModuleType.Shop);
        Client client = new Client(ClientMainFrame.socket);
        Message serverResponse = client.sendRequestToServer(mes);
        ArrayList<Goods> Goodslist = (ArrayList<Goods>) serverResponse.getData();
        DefaultTableModel tablemodel;

        tablemodel = new DefaultTableModel(new Object[][]{}, new String[]{
                "商品编号", "商品名称", "单价", "库存", "购买数量"}) {


            boolean[] columnEditables = new boolean[]{
                    false, false, false, false, true
            };

            @Override
            public boolean isCellEditable(int row, int column) {

                return columnEditables[column];
            }
        };
        for (int i = 0; i < Goodslist.size(); i++) {
            String tempgoods[] = new String[5];
            tempgoods[0] = Goodslist.get(i).getGoodsid() + "";
            tempgoods[1] = Goodslist.get(i).getGoodsname();
            tempgoods[2] = Goodslist.get(i).getGoodsprice() + "";
            tempgoods[3] = Goodslist.get(i).getGoodsnumber() + "";
            tempgoods[4] = "0";
            tablemodel.addRow(tempgoods);
        }

        MyCellEditor cellEditor = new MyCellEditor(new JTextField());

        table.setModel(tablemodel);
        final TableModel tableModel = table.getModel();
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // 第一个 和 最后一个 被改变的行（只改变了一行，则两者相同）
                int firstRow = e.getFirstRow();
                int lastRow = e.getLastRow();

                // 被改变的列
                int column = e.getColumn();
                int type = e.getType();
                if (type == TableModelEvent.UPDATE) {
//		        	double t=0.0;
                    sum = 0.0;
                    if (column == 4) {
                        for (int row = 0; row < table.getRowCount(); row++) {
                            Object tempnumber = tableModel.getValueAt(row, 4);
                            Object tempprice = tableModel.getValueAt(row, 2);
                            double tem = Double.parseDouble((String) tempprice);
                            int tem1 = Integer.parseInt((String) tempnumber);
//		        			t+=tem*tem1;
                            sum += tem * tem1;

                        }
//		        		 textField.setText(t+"");
                        textField.setText(sum + "");
                    } else return;
                }
            }
        });
        TableColumn tableColumn = table.getColumn("购买数量");
        tableColumn.setCellEditor(cellEditor);//确保输入合法

        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Serif", Font.BOLD, 28));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {
                "商品编号", "商品名称", "单价", "库存", "购买数量"};
        for (int i = 0; i < 5; i++) {
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
     * 查询操作
     *
     * @param e 事件
     */
    private void SearchGood(ActionEvent e) {
        // TODO 自动生成的方法存根
        Message mes = new Message();
        mes.setData(SearchText.getText());
        mes.setModuleType(ModuleType.Shop);
        if (((String) SearchText.getText()).equals("")) {
            show();
            return;
        }
        if (SearchText.getText().matches("[0-9]*")) {//商品ID查找
            mes.setMessageType(MessageType.GoodsSearch_ID);

            Client client = new Client(ClientMainFrame.socket);
            Message serverResponse = client.sendRequestToServer(mes);
            ArrayList<Goods> res = (ArrayList<Goods>) serverResponse.getData();

            DefaultTableModel tablemodel;
            tablemodel = new DefaultTableModel(new Object[][]{}, new String[]{
                    "商品编号", "商品名称", "单价", "库存", "购买数量"}) {


                /*
                 * overload the method to change the table's factor
                 */
                boolean[] columnEditables = new boolean[]{
                        false, false, false, false, true
                };

                @Override
                public boolean isCellEditable(int row, int column) {

                    return columnEditables[column];
                }
            };

            for (int i = 0; i < res.size(); i++) {
                String tempgoods[] = new String[5];
                tempgoods[0] = res.get(i).getGoodsid() + "";
                tempgoods[1] = res.get(i).getGoodsname();
                tempgoods[2] = res.get(i).getGoodsprice() + "";
                tempgoods[3] = res.get(i).getGoodsnumber() + "";
                tempgoods[4] = "0";
                tablemodel.addRow(tempgoods);
            }
            MyCellEditor cellEditor = new MyCellEditor(new JTextField());

            table.setModel(tablemodel);

            final TableModel tableModel = table.getModel();
            tableModel.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    // 第一个 和 最后一个 被改变的行（只改变了一行，则两者相同）
                    int firstRow = e.getFirstRow();
                    int lastRow = e.getLastRow();

                    // 被改变的列
                    int column = e.getColumn();
                    int type = e.getType();
                    if (type == TableModelEvent.UPDATE) {
//		        	double t=0.0;
                        sum = 0.0;
                        if (column == 4) {
                            for (int row = 0; row < table.getRowCount(); row++) {
                                Object tempnumber = tableModel.getValueAt(row, 4);
                                Object tempprice = tableModel.getValueAt(row, 2);
                                double tem = Double.parseDouble((String) tempprice);
                                int tem1 = Integer.parseInt((String) tempnumber);
//		        			t+=tem*tem1;
                                sum += tem * tem1;

                            }
//		        		 textField.setText(t+"");
                            textField.setText(sum + "");
                        } else return;
                    }
                }
            });
            TableColumn tableColumn = table.getColumn("购买数量");
            tableColumn.setCellEditor(cellEditor);//确保输入合法


        } else {
            mes.setMessageType(MessageType.GoodsSearch_Name);

            Client client = new Client(ClientMainFrame.socket);
            Message serverResponse = client.sendRequestToServer(mes);
            ArrayList<Goods> res = (ArrayList<Goods>) serverResponse.getData();

            DefaultTableModel tablemodel;
            tablemodel = new DefaultTableModel(new Object[][]{}, new String[]{
                    "商品编号", "商品名称", "单价", "库存", "购买数量"}) {


                /*
                 * overload the method to change the table's factor
                 */
                boolean[] columnEditables = new boolean[]{
                        false, false, false, false, true
                };

                @Override
                public boolean isCellEditable(int row, int column) {

                    return columnEditables[column];
                }
            };
            for (int i = 0; i < res.size(); i++) {
                String tempgoods[] = new String[5];
                tempgoods[0] = res.get(i).getGoodsid() + "";
                tempgoods[1] = res.get(i).getGoodsname();
                tempgoods[2] = res.get(i).getGoodsprice() + "";
                tempgoods[3] = res.get(i).getGoodsnumber() + "";
                tempgoods[4] = "0";
                tablemodel.addRow(tempgoods);
            }
            MyCellEditor cellEditor = new MyCellEditor(new JTextField());

            table.setModel(tablemodel);

            final TableModel tableModel = table.getModel();
            tableModel.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    // 第一个 和 最后一个 被改变的行（只改变了一行，则两者相同）
                    int firstRow = e.getFirstRow();
                    int lastRow = e.getLastRow();

                    // 被改变的列
                    int column = e.getColumn();
                    int type = e.getType();
                    if (type == TableModelEvent.UPDATE) {
//			        	double t=0.0;
                        sum = 0.0;
                        if (column == 4) {
                            for (int row = 0; row < table.getRowCount(); row++) {
                                Object tempnumber = tableModel.getValueAt(row, 4);
                                Object tempprice = tableModel.getValueAt(row, 2);
                                double tem = Double.parseDouble((String) tempprice);
                                int tem1 = Integer.parseInt((String) tempnumber);
//			        			t+=tem*tem1;
                                sum += tem * tem1;

                            }
//			        		 textField.setText(t+"");
                            textField.setText(sum + "");
                        } else return;
                    }
                }
            });
            TableColumn tableColumn = table.getColumn("购买数量");
            tableColumn.setCellEditor(cellEditor);//确保输入合法


        }
        //透明化处理
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Serif", Font.BOLD, 28));
        table.setRowHeight(40);                //表格行高
        table.setPreferredScrollableViewportSize(new Dimension(850, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setOpaque(false);    //设置透明
        String[] Names = {
                "商品编号", "商品名称", "单价", "库存", "购买数量"};
        for (int i = 0; i < 5; i++) {
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

    private JTable getTable() {
        return null;
    }

    /**
     * 购买操作
     */
    protected void buy() {
        Mainmenu.set(sum);
        Message mes = new Message();
        mes.setModuleType(ModuleType.Shop);
        mes.setMessageType(MessageType.Buy);
        ArrayList<String> bgoods = new ArrayList<String>();
        for (int i = 0; i < table.getRowCount(); i++) {
            String id = (String) table.getValueAt(i, 0);
            String number = (String) table.getValueAt(i, 4);
            int temp = Integer.parseInt(number);
            if (temp != 0) {
                bgoods.add(id);
                bgoods.add(number);
            }
        }
        mes.setData(bgoods);
        mes.setModuleType(ModuleType.Shop);
        Client client = new Client(ClientMainFrame.socket);

        Message serverResponse = client.sendRequestToServer(mes);
        //int res=(int)serverResponse.getData();
        textField.setText("0.0");
    }

    /**
     * 购买按钮的响应
     */
    void setPayFrame() {
        frame.setEnabled(false);
        frame.setModalExclusionType(ModalExclusionType.NO_EXCLUDE);
        Shop_StudentForPay newframe = new Shop_StudentForPay(this, frame, sum, id, PWD);
        newframe.setVisible(true);
    }

    /**
     * 保证表格改动时数据合法
     */
    public static class MyCellEditor extends DefaultCellEditor {

        public MyCellEditor(JTextField textField) {
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
}
