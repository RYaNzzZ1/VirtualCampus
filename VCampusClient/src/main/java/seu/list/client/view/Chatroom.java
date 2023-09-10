package seu.list.client.view;

import seu.list.client.driver.Client;
import seu.list.client.driver.ClientMainFrame;
import seu.list.common.Chat;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Chatroom {
    public String uID;
    JFrame frame;
    JTextPane textPane;
    JScrollPane scrollPane, s;
    JTextArea input;
    JLabel nickname;
    String name;
    JButton back, send, his;
    int hisn;

    boolean timerJdg;

    public Chatroom(String number, Socket socket) {
        this.uID = number;
        //1.基础配置
        frame = new JFrame("ChatRoom");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // 2.创建 JTextPane(聊天记录显示框）
        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("楷体", Font.BOLD, 24));
        // 2.1创建文档
        DefaultStyledDocument doc = new DefaultStyledDocument();

        //2.2添加一些文本记录

        //2.2.1设置不同行的文字样式
        //黑色斜体小号
        SimpleAttributeSet f1 = new SimpleAttributeSet();
        StyleConstants.setForeground(f1, Color.black);
        StyleConstants.setItalic(f1, true);
        StyleConstants.setFontSize(f1, 18);
        //黑色正常
        SimpleAttributeSet f2 = new SimpleAttributeSet();
        StyleConstants.setForeground(f2, Color.black);
        StyleConstants.setFontSize(f2, 24);

        //蓝色斜体小号
        SimpleAttributeSet f3 = new SimpleAttributeSet();
        StyleConstants.setForeground(f3, Color.blue);
        StyleConstants.setItalic(f3, true);
        StyleConstants.setFontSize(f3, 18);
        //蓝色正常
        SimpleAttributeSet f4 = new SimpleAttributeSet();
        StyleConstants.setForeground(f4, Color.blue);
        StyleConstants.setFontSize(f4, 24);

        // 2.3创建 JScrollPane 并将 JTextPane 添加到其中
        scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPane.setBounds(0, 0, 959 - 111, 509 - 115);
        scrollPane.setBounds(111, 115, 959 - 111, 509 - 115);
        // 2.4设置文档到 JTextPane
        textPane.setDocument(doc);
        frame.add(scrollPane);
        frame.setVisible(true);

        //2.5透明化处理
        textPane.setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 始终显示垂直滚动条
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        //3.消息输入框
        input = new JTextArea();
        input.setBounds(0, 0, 958 - 113, 673 - 546);
        s = new JScrollPane(input);
        s.setBounds(113, 546, 958 - 113, 673 - 546);
        frame.add(s);
        //透明处理
        input.setOpaque(false);
        s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        s.getVerticalScrollBar().setOpaque(false);//滚动条设置透明
        s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // 始终显示垂直滚动条
        s.setOpaque(false);
        s.getViewport().setOpaque(false);
        input.setFont(new Font("楷体", Font.BOLD, 24));

        //从后端获取马甲
        Message mes = new Message();
        //setLayout(null);
        Client client = new Client(ClientMainFrame.socket);
        mes.setModuleType(ModuleType.Chat);
        mes.setMessageType(MessageType.CRE_ANONYMOUS);
        //mes.setData(t.uID);
        Message sResponse = new Message();
        sResponse = client.sendRequestToServer(mes);
        name = sResponse.getData().toString();

        //马甲显示框：
        nickname = new JLabel(name);
        nickname.setBounds(1010, 294, 1236 - 1010, 345 - 294);
        nickname.setFont(new Font("华文行楷", Font.BOLD, 30));
        frame.add(nickname);

        //4.设置背景图片
        JLabel backgroundImageLabel = new JLabel(new ImageIcon("VCampusClient/Image/Chatroom.png"));
        Toolkit k = Toolkit.getDefaultToolkit();
        Dimension d = k.getScreenSize();
        frame.setBounds(d.width / 2 - 640, d.height / 2 - 360, 1280, 720 + 30);
        backgroundImageLabel.setBounds(0, 0, 1280, 720);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.add(backgroundImageLabel);

        //5.关闭按钮
        back = new JButton("退出");
        back.setBounds(1042, 611 + 5, 1153 - 1039 + 10, 588 - 536 + 10);
        frame.add(back);
        back.setOpaque(false);
        back.addActionListener(event -> {
            timerJdg = true;
            frame.dispose();
        });


        //定时刷新
        ArrayList<Chat> hisget2 = new ArrayList<Chat>();
        Message mes2 = new Message();
        //setLayout(null);
        Client client2 = new Client(ClientMainFrame.socket);
        mes2.setModuleType(ModuleType.Chat);
        mes2.setMessageType(MessageType.ChatHistory);
        Message serverResponse2 = new Message();
        serverResponse2 = client2.sendRequestToServer(mes2);
        hisget2 = (ArrayList<Chat>) serverResponse2.getData();
        hisn = hisget2.size();


        Timer timer = new Timer();
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                if (timerJdg) {
                    timer.cancel();
                }
                ArrayList<Chat> hisget3 = new ArrayList<Chat>();
                Message mes3 = new Message();
                Client client3 = new Client(ClientMainFrame.socket);
                mes3.setModuleType(ModuleType.Chat);
                mes3.setMessageType(MessageType.ChatHistory);
                Message serverResponse3 = new Message();
                serverResponse3 = client3.sendRequestToServer(mes3);
                hisget3 = (ArrayList<Chat>) serverResponse3.getData();


                if (hisn != hisget3.size()) {
                    int c = hisget3.size() - hisn;
                    System.out.println(c);
                    hisn = hisget3.size();
                    for (int i = c; i > 0; i--) {
                        String s3 = hisget3.get(hisget3.size() - i).getChatText() + " " + hisget3.get(hisget3.size() - i).getUID() + ":\n";
                        String s4 = hisget3.get(hisget3.size() - i).getChatTime() + "\n";
                        try {
                            doc.insertString(doc.getLength(), s3, f1);
                            doc.insertString(doc.getLength(), s4, f2);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        timerJdg = false;
        timer.schedule(timertask, 0, 1000);

        //6.发送按钮
        send = new JButton("发送");
        send.setOpaque(false);
        send.setBounds(1039, 536, 1153 - 1039 + 10 + 7, 588 - 536 + 10);
        frame.add(send);
        send.addActionListener(event ->
        {
            String text = input.getText();

            // 使用 trim 方法移除首尾空白字符，然后检查是否为空
            if (text.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "不能发送空的消息", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                String s1 = nickname.getText();
                Date now = new Date();
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                s1 = s1 + df.format(now) + ":\n";
                String s2 = input.getText() + "\n";
                try {

                    doc.insertString(doc.getLength(), s1, f3);
                    doc.insertString(doc.getLength(), s2, f4);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }

                //发送消息到后端
                Chat chat = new Chat();
                chat.setUID(uID);
                chat.setChatText(text);
                chat.setNickName(name);
                chat.setChatTime(df.format(now).toString());
                Message mes1 = new Message();
                Client client1 = new Client(ClientMainFrame.socket);
                mes1.setModuleType(ModuleType.Chat);
                mes1.setMessageType(MessageType.ChatSend);
                mes1.setData(chat);
                Message serverResponse = new Message();
                serverResponse = client1.sendRequestToServer(mes1);

                hisn += 1;

                input.setText("");
            }

        });

        //7.历史记录按钮
        his = new JButton("历史记录");
        his.setBounds(1006, 378, 1250 - 1006, 437 - 378);
        frame.add(his);
        his.setOpaque(false);
        his.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Historyshow(e);
            }
        });

    }

    //显示历史记录界面
    private void Historyshow(ActionEvent e) {
        HistoryRecord d1 = new HistoryRecord();
    }

}
