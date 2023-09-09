/**
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;

public class Dormmodify extends JDialog {

    static DormitoryAdminClient C;
    private final JPanel contentPanel = new JPanel();
    public ArrayList<Dormitory> allDormitoryContents;
    //static Socket socket;
    JComboBox modifyt = new JComboBox();
    private JPanel buttonPane;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel lblNewLabel;
    private JTextField userIDField;
    private JTextField modifyField_1;

    /**
     * Launch the application.
     */
	/*
	public static void main(String[] args) {
		try {
			Dormmodify dialog = new Dormmodify(C,socket);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

    /**
     * Create the dialog.
     */
    public Dormmodify(DormitoryAdminClient c, Socket socket) {
        C = c;
        //this.socket=socket;
        setTitle("修改信息");
        setVisible(true);

//		//添加图标
//		Image image=new ImageIcon("VCampusClient/src/main/resources/image/xiaobiao.jpg").getImage();
//		setIconImage(image);
        setLayout(null);

        // 创建带有背景图片的JLabel
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Dormmodify.PNG"));
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        backgroundImageLabel.setBounds(0, 0, 847, 585);
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 847 / 2, d.height / 2 - 585 / 2, 847, 585);
        backgroundImageLabel.setOpaque(false); // 设置背景透明
        setVisible(true);

        setResizable(false); //阻止用户拖拽改变窗口的大小

        //2.绘制退出按钮
        //得到鼠标的坐标（用于推算对话框应该摆放的坐标）
//		backgroundImageLabel.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int x = e.getX();
//				int y = e.getY();
//				System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
//			}
//		});

        userIDField = new JTextField();
        userIDField.setFont(new Font("华文行楷", Font.PLAIN, 24));
        userIDField.setColumns(20);
        userIDField.setBounds(303, 143, 366, 43);
        add(userIDField);
        userIDField.setOpaque(false);
        userIDField.setBorder(new EmptyBorder(0, 0, 0, 0));

        modifyt.setModel(new DefaultComboBoxModel(new String[]{"宿舍", "卫生评分", "水费", "电费", "宿舍维修", "宿舍调换"}));
        modifyt.setFont(new Font("华文行楷", Font.PLAIN, 24));
        modifyt.setBounds(303, 225, 366, 43);
        add(modifyt);
        modifyt.setOpaque(false);
        modifyt.setBorder(new EmptyBorder(0, 0, 0, 0));
        modifyt.setRenderer(new DefaultListCellRenderer() {
             private static final long serialVersionUID = 6424750174292826127L;

            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                JComponent result = (JComponent) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
                result.setOpaque(false);
                return result;
            }
        });

//		JLabel lblNewLabel_2 = new JLabel("修改项目：");
//		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
//
//		JLabel lblNewLabel_3 = new JLabel("修改内容：");
//		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        modifyField_1 = new JTextField();
        modifyField_1.setFont(new Font("华文行楷", Font.PLAIN, 24));
        modifyField_1.setColumns(20);
        modifyField_1.setBounds(303, 315, 366, 43);
        add(modifyField_1);
        modifyField_1.setOpaque(false);
        modifyField_1.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(backgroundImageLabel);

        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        {
            buttonPane = new JPanel();
            {
                okButton = new JButton("确定");
                okButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                okButton.setActionCommand("OK");
                getRootPane().setDefaultButton(okButton);
                okButton.setBounds(221, 430, 103, 56);
                add(okButton);
                okButton.setOpaque(false);
                okButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        ModifyAct(e);
                        setVisible(false);
                    }

                });
            }
            {
                cancelButton = new JButton("取消");
                cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
                cancelButton.setActionCommand("Cancel");
                cancelButton.setBounds(487, 430, 103, 56);
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

    /**
     * 宿舍信息修改
     * @param e
     */
    protected void ModifyAct(ActionEvent e) {
        // TODO Auto-generated method stub
        Message mes = new Message();
        mes.setUserType(1);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormModify);
		/*
		try {
			socket = new Socket(ClientMainFrame.socket);
		}catch (IOException e1) {
			e1.printStackTrace();
		}*/

        Client client = new Client(ClientMainFrame.socket); // 不需要新建socket，客户端一启动就建立好了
        ArrayList<String> para = new ArrayList<String>();
        para.add(userIDField.getText());
        String usertype = (String) modifyt.getSelectedItem();
        if ("宿舍".equals(usertype)) para.add("宿舍");
        if ("卫生评分".equals(usertype)) para.add("卫生评分");
        if ("水费".equals(usertype)) para.add("水费");
        if ("电费".equals(usertype)) para.add("电费");
        if ("宿舍调换".equals(usertype)) para.add("宿舍调换");
        if ("宿舍维修".equals(usertype)) para.add("宿舍维修");
        para.add(modifyField_1.getText());

        mes.setData(para);
        System.out.println(para);
        System.out.println("!!!!!!!!!!!!!!");
        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);
        allDormitoryContents = (ArrayList<Dormitory>) serverResponse.getData();
        System.out.println(allDormitoryContents);
        C.updateFrameM(para);
        this.dispose();
    }

}


