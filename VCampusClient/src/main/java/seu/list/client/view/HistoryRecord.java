package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HistoryRecord extends JFrame {
    JFrame frame;
    JTextPane textPane;
    JScrollPane scrollPane,s;
    JTextArea input;
    JLabel nickname;
    JButton back,send;
    private Chatroom t;

    public static void main(String[] args) {

        HistoryRecord d=new HistoryRecord(); //用于测试的主函数
    }
    HistoryRecord()
    {
        //基础配置
        frame = new JFrame("HistoryRecord");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // 创建 JTextPane(聊天记录显示框）
        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("楷体",Font.BOLD,24));
        // 创建文档
        DefaultStyledDocument doc = new DefaultStyledDocument();

        //设置不同行的文字样式
        //黑色斜体小号
        SimpleAttributeSet f1 = new SimpleAttributeSet();
        StyleConstants.setForeground(f1, Color.black);
        StyleConstants.setItalic(f1, true);
        StyleConstants.setFontSize(f1, 18);
        //黑色正常
        SimpleAttributeSet f2 = new SimpleAttributeSet();
        StyleConstants.setForeground(f2 ,Color.black);
        StyleConstants.setFontSize(f2, 24);


        // 创建 JScrollPane 并将 JTextPane 添加到其中
        scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPane.setBounds(0,0,1151-131,608-110);
        scrollPane.setBounds(131,110,1151-131,608-110);
        // 设置文档到 JTextPane
        textPane.setDocument(doc);
        frame.add(scrollPane);
        frame.setVisible(true);

        //透明化处理
        textPane.setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 始终显示垂直滚动条
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        //从后端读取历史记录
        ArrayList<Chat> hisget= new ArrayList<Chat>();
        Message mes = new Message();
        setLayout(null);
        Client client = new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Chat);
        mes.setMessageType(MessageType.ChatHistory);
        //mes.setData(t.uID);
        Message serverResponse = new Message();
        serverResponse = client.sendRequestToServer(mes);
        hisget = (ArrayList<Chat>) serverResponse.getData();

        for (int i = 0; i < hisget.size(); i++) {
            //String s1=nickname.getText();
            //Date now=new Date();
            //DateFormat df=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            //Date date=new Date();
            //new SimpleDateFormat(hisget.get(i).getUID()).format(date);
            String s1=hisget.get(i).getChatText()+" "+hisget.get(i).getUID()+":\n";
            String s2=hisget.get(i).getChatTime()+"\n";
            try {

                doc.insertString(doc.getLength(), s1,f1);
                doc.insertString(doc.getLength(), s2,f2);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }




        //设置背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/HistoryRecord.png"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        frame.setBounds(d.width/2-640, d.height/2-360, 1280,720+30);
        backgroundImageLabel.setBounds(0, 0, 1280,720);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.add(backgroundImageLabel);


        //2.绘制退出按钮
        //得到鼠标的坐标（用于推算对话框应该摆放的坐标）
          /* backgroundImageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();
                    System.out.println("鼠标点击位置：X=" + x + ", Y=" + y);
                }
            });*/



        //5.关闭按钮
        back=new JButton("退出");
        back.setBounds(1180,305,1251-1180,415-305);
        frame.add(back);
        back.setOpaque(false);
        back.addActionListener(event->
        {
            frame.dispose();
        });

    }

}
