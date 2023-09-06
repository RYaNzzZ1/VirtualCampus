/**
 * 
 * @version jdk1.8.0
 */
package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;

public class DormitoryStudentClient extends JFrame {

    private JPanel contentPane;
    static Socket socket;
    private ArrayList<Dormitory> Dorm=new ArrayList<Dormitory>();
    JLabel UserIDLabel,DormIDLabel,BunkIDLabel_1,ScoreLabel_2,WaterLabel_3, ElectricityLabel_4,ExchangeLabel_5,MaintainLabel_6;
    public Dormitory dorm=new Dormitory();
    /**
     * Launch the application.
     */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DormitoryStudentClient frame = new DormitoryStudentClient(socket);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

    /**
     * Create the frame.
     */
    public DormitoryStudentClient(String userID,Socket socket) {
        this.socket=socket;
        setFont(new Font("微软雅黑", Font.BOLD, 12));
        setTitle("宿舍-学生");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

//		//添加图标
//		Image image=new ImageIcon("VCampusClient/src/main/resources/image/xiaobiao.jpg").getImage();
//		setIconImage(image);

        // 创建带有背景图片的JLabel
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/DormitoryStudentClient.PNG"));
        //获取当前屏幕的尺寸（长、宽的值）
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        backgroundImageLabel.setBounds(0, 0, 846, 592);
        //将当前窗口设置到屏幕正中央进行显示
        setBounds(d.width / 2 - 846 / 2, d.height / 2 - 592 / 2, 846, 592);
        backgroundImageLabel.setOpaque(false); // 设置背景透明
        setVisible(true);


        setResizable(false); //阻止用户拖拽改变窗口的大小

        //2.绘制退出按钮
        //得到鼠标的坐标（用于推算对话框应该摆放的坐标）
//     backgroundImageLabel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int x = e.getX();
//                int y = e.getY();
//                System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
//            }
//        });

        //设置信息文本
        JLabel userIDLabel_1 = new JLabel("学号：");
        userIDLabel_1.setFont(new Font("华文行楷", Font.PLAIN, 28));
        userIDLabel_1.setForeground(Color.white);
        userIDLabel_1.setBounds(150,170,140,30);
        add(userIDLabel_1);

        JLabel dormIDLabel_2 = new JLabel("宿舍：");
        dormIDLabel_2.setFont(new Font("华文行楷", Font.PLAIN, 28));
        dormIDLabel_2.setForeground(Color.white);
        dormIDLabel_2.setBounds(150,230,140,30);
        add(dormIDLabel_2);

        JLabel bunkIDLabel = new JLabel("床位：");
        bunkIDLabel.setFont(new Font("华文行楷", Font.PLAIN, 28));
        bunkIDLabel.setForeground(Color.white);
        bunkIDLabel.setBounds(150,290,140,30);
        add(bunkIDLabel);

        JLabel scoreLabel = new JLabel("卫生评分：");
        scoreLabel.setFont(new Font("华文行楷", Font.PLAIN, 28));
        scoreLabel.setForeground(Color.white);
        scoreLabel.setBounds(150,350,140,30);
        add(scoreLabel);

        JLabel waterLabel = new JLabel("水费：");
        waterLabel.setFont(new Font("华文行楷", Font.PLAIN, 28));
        waterLabel.setForeground(Color.white);
        waterLabel.setBounds(440,170,140,30);
        add(waterLabel);

        JLabel electricityLabel = new JLabel("电费：");
        electricityLabel.setFont(new Font("华文行楷", Font.PLAIN, 28));
        electricityLabel.setForeground(Color.white);
        electricityLabel.setBounds(440,230,140,30);
        add(electricityLabel);

        JLabel exchangeLabel = new JLabel("调换申请：");
        exchangeLabel.setFont(new Font("华文行楷", Font.PLAIN, 28));
        exchangeLabel.setForeground(Color.white);
        exchangeLabel.setBounds(440,290,140,30);
        add(exchangeLabel);

        JLabel maintainLabel = new JLabel("维修申请：");
        maintainLabel.setFont(new Font("华文行楷", Font.PLAIN, 28));
        maintainLabel.setForeground(Color.white);
        maintainLabel.setBounds(440,350,140,30);
        add(maintainLabel);

        UserIDLabel = new JLabel("");
        UserIDLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        UserIDLabel.setForeground(Color.white);
        UserIDLabel.setBounds(283,175,130,20);
        add(UserIDLabel);
        UserIDLabel.setOpaque(false);
        UserIDLabel.setBorder(new EmptyBorder(0,0,0,0));

        DormIDLabel = new JLabel("");
        DormIDLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        DormIDLabel.setForeground(Color.white);
        DormIDLabel.setBounds(283,235,130,20);
        add(DormIDLabel);
        DormIDLabel.setOpaque(false);
        DormIDLabel.setBorder(new EmptyBorder(0,0,0,0));

        BunkIDLabel_1 = new JLabel("");
        BunkIDLabel_1.setFont(new Font("微软雅黑", Font.BOLD, 24));
        BunkIDLabel_1.setForeground(Color.white);
        BunkIDLabel_1.setBounds(283,294,130,20);
        add(BunkIDLabel_1);
        BunkIDLabel_1.setOpaque(false);
        BunkIDLabel_1.setBorder(new EmptyBorder(0,0,0,0));

        ScoreLabel_2 = new JLabel("");
        ScoreLabel_2.setFont(new Font("微软雅黑", Font.BOLD, 24));
        ScoreLabel_2.setForeground(Color.white);
        ScoreLabel_2.setBounds(283,356,130,20);
        add(ScoreLabel_2);
        ScoreLabel_2.setOpaque(false);
        ScoreLabel_2.setBorder(new EmptyBorder(0,0,0,0));

        WaterLabel_3 = new JLabel("");
        WaterLabel_3.setFont(new Font("微软雅黑", Font.BOLD, 24));
        WaterLabel_3.setForeground(Color.white);
        WaterLabel_3.setBounds(575,175,130,20);
        add(WaterLabel_3);
        WaterLabel_3.setOpaque(false);
        WaterLabel_3.setBorder(new EmptyBorder(0,0,0,0));

        ElectricityLabel_4 = new JLabel("");
        ElectricityLabel_4.setFont(new Font("微软雅黑", Font.BOLD, 24));
        ElectricityLabel_4.setForeground(Color.white);
        ElectricityLabel_4.setBounds(575,235,130,20);
        add(ElectricityLabel_4);
        ElectricityLabel_4.setOpaque(false);
        ElectricityLabel_4.setBorder(new EmptyBorder(0,0,0,0));

        ExchangeLabel_5 = new JLabel("");
        ExchangeLabel_5.setFont(new Font("微软雅黑", Font.BOLD, 24));
        ExchangeLabel_5.setForeground(Color.white);
        ExchangeLabel_5.setBounds(575,292,130,25);
        add(ExchangeLabel_5);
        ExchangeLabel_5.setOpaque(false);
        ExchangeLabel_5.setBorder(new EmptyBorder(0,0,0,0));

        MaintainLabel_6 = new JLabel("");
        MaintainLabel_6.setFont(new Font("微软雅黑", Font.BOLD, 24));
        MaintainLabel_6.setForeground(Color.white);
        MaintainLabel_6.setBounds(575,351,130,25);
        add(MaintainLabel_6);
        MaintainLabel_6.setOpaque(false);
        MaintainLabel_6.setBorder(new EmptyBorder(0,0,0,0));

        add(backgroundImageLabel);

        //设置按钮
        JButton maintainButton = new JButton("维修登记");
        //maintainButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        maintainButton.setBounds(142,447,160,53);
        add(maintainButton);
        maintainButton.setOpaque(false);

        maintainButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                MaintainAct(e);

            }

        });

        JButton exchangeButton = new JButton("宿舍调换申请");
        //exchangeButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        exchangeButton.setBounds(342,447,217,53);
        add(exchangeButton);
        exchangeButton.setOpaque(false);

        exchangeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                ExchangeAct(e);
            }
        });

        JButton exitButton = new JButton("退出");
        //exitButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        exitButton.setBounds(596,447,98,53);
        add(exitButton);
        exitButton.setOpaque(false);

        exitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                setVisible(false);
            }
        });

        Message mes = new Message();
        mes.setUserType(0);
        mes.setModuleType(ModuleType.Dormitory);
        mes.setMessageType(MessageType.DormStShow);
        mes.setData(userID);
        Client client = new Client(ClientMainFrame.socket);

        new Message();
        Message rec;
        rec=client.sendRequestToServer(mes);
        dorm =(Dormitory) rec.getData();
        System.out.println(dorm);
        UserIDLabel.setText(dorm.getuserID());
        DormIDLabel.setText(dorm.getDormitoryID());
        BunkIDLabel_1.setText(String.valueOf(dorm.getStudentBunkID()));
        ScoreLabel_2.setText(String.valueOf(dorm.getDormitoryScore()));
        WaterLabel_3.setText(String.valueOf(dorm.getWater()));
        ElectricityLabel_4.setText(String.valueOf(dorm.getElectricity()));
        MaintainLabel_6.setText(dorm.getDormitoryMaintain());
        ExchangeLabel_5.setText(dorm.getStudentExchange());

        System.out.println(dorm);
        setVisible(true);
        validate();
    }
    /**
     * 显示宿舍调换申请界面
     * @param e
     */
    protected void ExchangeAct(ActionEvent e) {
        // TODO Auto-generated method stub
        Dormexchange Exchange = new Dormexchange(this,socket);
        Exchange.setVisible(true);
    }
    /**
     * 显示宿舍维修申请界面
     * @param e
     */
    protected void MaintainAct(ActionEvent e) {
        // TODO Auto-generated method stub
        Dormmaintain Maintain = new Dormmaintain(this,socket);
        Maintain.setVisible(true);
    }
    /**
     * 更新宿舍调换后界面
     * @param para
     */
    public void updateFrameE(ArrayList<String> para) {
        // TODO Auto-generated method stub
        if(!para.get(0).equals(dorm.getuserID())||!para.get(1).equals(dorm.getDormitoryID())) {
            JOptionPane.showMessageDialog(null, "非法修改！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        dorm.setDormitoryMaintain(para.get(2));
        System.out.println(dorm);
        UserIDLabel.setText(dorm.getuserID());
        DormIDLabel.setText(dorm.getDormitoryID());
        BunkIDLabel_1.setText(String.valueOf(dorm.getStudentBunkID()));
        ScoreLabel_2.setText(String.valueOf(dorm.getDormitoryScore()));
        WaterLabel_3.setText(String.valueOf(dorm.getWater()));
        ElectricityLabel_4.setText(String.valueOf(dorm.getElectricity()));
        MaintainLabel_6.setText(dorm.getDormitoryMaintain());
        ExchangeLabel_5.setText(dorm.getStudentExchange());
    }
    /**
     * 更新维修申请后信息
     * @param para
     */
    public void updateFrameM(ArrayList<String> para) {
        // TODO Auto-generated method stub
        if(!para.get(0).equals(dorm.getuserID())||!para.get(1).equals(dorm.getDormitoryID())) {
            JOptionPane.showMessageDialog(null, "非法修改！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        dorm.setStudentExchange(para.get(2));
        System.out.println(dorm);
        UserIDLabel.setText(dorm.getuserID());
        DormIDLabel.setText(dorm.getDormitoryID());
        BunkIDLabel_1.setText(String.valueOf(dorm.getStudentBunkID()));
        ScoreLabel_2.setText(String.valueOf(dorm.getDormitoryScore()));
        WaterLabel_3.setText(String.valueOf(dorm.getWater()));
        ElectricityLabel_4.setText(String.valueOf(dorm.getElectricity()));
        MaintainLabel_6.setText(dorm.getDormitoryMaintain());
        ExchangeLabel_5.setText(dorm.getStudentExchange());
    }
}

