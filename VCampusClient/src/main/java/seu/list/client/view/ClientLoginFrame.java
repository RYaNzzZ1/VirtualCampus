package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;

public class ClientLoginFrame extends JFrame implements ActionListener {

    private JButton jb_login,jb_register;
    private JTextField jtf_user,jtf_verify;
    private JPasswordField jpf_password;
    static int flag =0;
    VerifyCode vcode=new VerifyCode();
    private Socket socket;
    /**
     * create the frame
     * @param socket 与服务端进行通信
     */
    public ClientLoginFrame(Socket socket){

        this.socket=socket;

        //设置背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/ClientLoginFrame.PNG"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        setBounds(d.width/2-640, d.height/2-360, 1280, 720);
        backgroundImageLabel.setBounds(0, 0, 1280, 720);
        setSize(1280,760);
        setTitle("VCampus");

        setLayout(null);
        backgroundImageLabel.setOpaque(false);



        //账号，密码，验证码的输入框
        Font f = new Font("华文行楷", Font.BOLD, 36);
        jtf_user = new JTextField();
        jtf_verify = new JTextField();
        jpf_password = new JPasswordField();
        jtf_user.setBounds(808,272,1098-803+1,326-274+2);
        jtf_verify.setBounds(808,451,1098-803+1,326-274+1);
        jpf_password.setBounds(808,367,1098-803+1,326-274+1);
        jtf_user.setFont(f);
        Font f1 = new Font( "",Font.BOLD,36);
        jtf_verify.setFont(f1);
        jtf_verify.setOpaque(false);
        jpf_password.setOpaque(false);
        jpf_password.setFont(f1);
        jtf_user.setOpaque(false);

        jtf_user.setOpaque(false); //文字框设为透明背景
        jtf_verify.setOpaque(false);
        jpf_password.setOpaque(false);

        jtf_user.setBorder(new EmptyBorder(0,0,0,0));
        jtf_verify.setBorder(new EmptyBorder(0,0,0,0));
        jpf_password.setBorder(new EmptyBorder(0,0,0,0));
        add(jtf_user);
        add(jtf_verify);
        add(jpf_password);
        vcode.setBounds(1112,452,105,47);
        add(vcode);
        add(backgroundImageLabel);




        //登录按钮
        jb_login = new JButton("登录按钮");
        //注册按钮
        jb_register= new JButton("注册按钮");
        //设置位置
        jb_login.setBounds(747,541,857-747,593-541);
        jb_register.setBounds(942,541,857-747,593-541);
        //添加到窗口
        add(jb_login);
        add(jb_register);

        //添加监听器（登录和注册）
        jb_login.addActionListener(this);
        jb_login.setActionCommand("jb_login");

        jb_register.addActionListener(this);
        jb_register.setActionCommand("jb_register");
        jb_register.setOpaque(false);
        jb_login.setOpaque(false);


        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if(jtf_verify.getText()==null) {
            flag = 0;
        }else if(vcode.getCode()==null) {
            flag=1;
        }else if(vcode.getCode().equals(jtf_verify.getText())) {
            flag=1;
        }else {
            flag=0;
        }
        //最大化最小化以及关闭操作
        if (arg0.getActionCommand().equals("Closed")) {
            this.dispose();
            ClientMainFrame.close();
        }
        else if (arg0.getActionCommand().equals("Minimized")) {
            this.setExtendedState(ICONIFIED);
        }
        else if (arg0.getActionCommand().equals("Maximized"))
        {
            boolean isMax = false;
            if (!isMax) {
                this.setExtendedState(MAXIMIZED_BOTH);
                isMax = true;
            } else {
                this.setExtendedState(NORMAL);
                isMax = false;
            }
        }

        //注册界面
        if(arg0.getActionCommand().equals("jb_register")){
            ClientRegisterFrame crf = new ClientRegisterFrame(this.socket);
            //this.dispose();
        }

        else if(arg0.getActionCommand().equals("jb_login"))
        {
            //登录界面
            if(flag==0)
            {
                JOptionPane.showMessageDialog(null, "验证码错误");
                vcode.nextCode();//自动切换到下一个
            }
            if(flag==1)
            {
                //登录界面
                Client ccs = new Client(this.socket);
                User u=new User();
                u.setPwd(jpf_password.getText());
                u.setId(jtf_user.getText());
                Message mes=new Message();
                mes.setContent(u.getContent());
                mes.setModuleType(ModuleType.User);
                mes.setMessageType(MessageType.REQ_LOGIN);
                Message res=ccs.sendRequestToServer(mes);
                int sign=res.getUserType();
                u = (User)res.getData();

                if(sign==0||sign==1||sign==30||sign==31) {
                    if(sign>=30){//重复登录，之前的下线
                        sign-=30;
                        JOptionPane.showMessageDialog(null,"重复登录，之前的连接已断开","提示",JOptionPane.WARNING_MESSAGE);
                    }
                    this.setVisible(false);
                    MainMenu csf = new MainMenu(sign, u.getId(), u.getPwd(), u.getName(), u.getMoney(), this.socket);
                    csf.setVisible(true);
                }
                else if(sign==2) {
                    JOptionPane.showMessageDialog(null,"用户名或密码错误！","错误",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(null,"用户名或密码错误！","错误",JOptionPane.ERROR_MESSAGE);
                }


            }
        }

    }
}
