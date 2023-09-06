package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.common.Course;
import seu.list.common.Message;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class CourseInfor extends JDialog implements ActionListener {
    private JTextField Semester;
    private JTextField CourseID;
    private JTextField CourseMajor;
    private JTextField CourseName;
    private JTextField teacherID;
    private JTextField CourseState;
    private JTextField CourseType;
    private Socket socket;
    private String userID;

    public CourseInfor(String ID, Socket socket, ClientCourseFrame tem) {
        userID = ID;
        this.socket = socket;
        tem.dispose();

        Client client = new Client(this.socket);
        //绘制背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/CourseInfor.png"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        setBounds(d.width / 2 - 846 / 2, d.height / 2 - 589 / 2, 846, 610);
        backgroundImageLabel.setBounds(0, 0, 846, 589);
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
        });*/


        setTitle("添加课程");
        Font f = new Font("华文楷体", Font.BOLD, 36);

        CourseID = new JTextField();
        CourseID.setFont(f);
        CourseID.setBounds(168, 133, 407 - 168, 175 - 133);
        add(CourseID);
        CourseID.setOpaque(false);
        CourseID.setBorder(new EmptyBorder(0, 0, 0, 0));

        CourseName = new JTextField();
        CourseName.setFont(f);
        CourseName.setBounds(504, 133, 407 - 168, 175 - 133);
        add(CourseName);
        CourseName.setOpaque(false);
        CourseName.setBorder(new EmptyBorder(0, 0, 0, 0));


        CourseMajor = new JTextField();
        CourseMajor.setFont(f);
        CourseMajor.setBounds(168, 218, 407 - 168, 175 - 133);
        add(CourseMajor);
        CourseMajor.setOpaque(false);
        CourseMajor.setBorder(new EmptyBorder(0, 0, 0, 0));


        teacherID = new JTextField();
        teacherID.setFont(f);
        teacherID.setBounds(504, 219, 407 - 168, 175 - 133);
        add(teacherID);
        teacherID.setOpaque(false);
        teacherID.setBorder(new EmptyBorder(0, 0, 0, 0));


        Semester = new JTextField();
        Semester.setFont(f);
        Semester.setBounds(168, 403, 407 - 168, 175 - 133);
        add(Semester);
        Semester.setOpaque(false);
        Semester.setBorder(new EmptyBorder(0, 0, 0, 0));


        CourseState = new JTextField();
        CourseState.setFont(f);
        CourseState.setBounds(168, 302, 407 - 168, 175 - 133);
        add(CourseState);
        CourseState.setOpaque(false);
        CourseState.setBorder(new EmptyBorder(0, 0, 0, 0));


        CourseType = new JTextField();
        CourseType.setFont(f);
        CourseType.setBounds(504, 303, 407 - 168, 175 - 133);
        add(CourseType);
        CourseType.setOpaque(false);
        CourseType.setBorder(new EmptyBorder(0, 0, 0, 0));

        add(backgroundImageLabel);

        JButton confirmButtom = new JButton("确定");
        confirmButtom.setFont(new Font("微软雅黑", Font.BOLD, 20));
        confirmButtom.setBounds(253, 502, 352 - 253, 50);
        confirmButtom.addActionListener(this);
        confirmButtom.setActionCommand("confirm");
        add(confirmButtom);
        confirmButtom.setOpaque(false);


        JButton exit = new JButton("退出");
        exit.setBounds(519, 502, 100, 50);
        add(exit);
        exit.setOpaque(false);
        exit.addActionListener(event ->
        {
            try {
                this.dispose();
                ClientCourseFrame ccf = new ClientCourseFrame(userID, this.socket);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "confirm") {
            Client client = new Client(this.socket);
            Course course = new Course();
            course.setCourseID(CourseID.getText());
            course.setCourseName(CourseName.getText());
            course.setCourseMajor(CourseMajor.getText());
            course.setTeacherID(teacherID.getText());
            course.setCourseState(CourseState.getText());
            course.setSemester(Semester.getText());
            course.setCourseType(CourseType.getText());
            Message clientReq = new Message();
            clientReq.setModuleType(ModuleType.Course);
            clientReq.setMessageType("REQ_ADD_LESSON");
            clientReq.setContent(course.getContent());
            Message rec = client.sendRequestToServer(clientReq);
            if (rec.isSeccess()) {
                try {
                    ClientCourseFrame ccf = new ClientCourseFrame(userID, this.socket);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "课程id不可重复，请重新填写", "错误", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
