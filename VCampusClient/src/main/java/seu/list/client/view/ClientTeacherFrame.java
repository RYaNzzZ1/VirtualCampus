package seu.list.client.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientTeacherFrame extends JFrame implements ActionListener {
    JButton courseManage, Quit;
    String number;
    Socket socket;//传送数据

    public ClientTeacherFrame(String number, Socket socket) throws ClassNotFoundException, SQLException, IOException, ClassNotFoundException {
        this.socket = socket;
        this.number = number;

        //绘制背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/ClientTeacherFrame.PNG"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        setBounds(d.width / 2 - 640, d.height / 2 - 360, 1280, 720);
        backgroundImageLabel.setBounds(0, 0, 1280, 720);
        setSize(1280, 760);
        setResizable(false);
        setLayout(null);
        //2.绘制退出按钮

        add(backgroundImageLabel);
        //两个按钮
        courseManage = new JButton("课程信息管理");
        courseManage.addActionListener(this);
        Quit = new JButton("退出");
        Quit.addActionListener(this);

        courseManage.setBounds(294, 303, 628 - 294, 389 - 303);
        Quit.setBounds(780, 477, 991 - 780, 562 - 477);
        add(courseManage);
        add(Quit);
        courseManage.setOpaque(false);
        Quit.setOpaque(false);
        this.setTitle("教师系统");
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if (arg0.getSource() == courseManage) {
            try {
                this.setVisible(false);
                ClientCourseFrame ccf = new ClientCourseFrame(this.number, this.socket);

            } catch (ClassNotFoundException | SQLException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (arg0.getSource() == Quit) {
            this.dispose();
        }

    }
}

