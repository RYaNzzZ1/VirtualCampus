package seu.list.server.dao;


import seu.list.client.driver.Client;
import seu.list.common.Chat;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.NickName;
import seu.list.server.db.Chat_DbAccess;
import seu.list.server.db.SqlHelper;
import seu.list.server.driver.ServerClientThreadMgr;
import seu.list.server.driver.ServerSocketThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class ChatServer extends Chat_DbAccess {
    Connection con = null;
    Statement s = null;
    ResultSet rs = null;
    ArrayList<Chat> chat = new ArrayList<Chat>();
    private Message mesFromClient; // 从客户端收到的数据
    private Message mesToClient;

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//字母表

    public ChatServer(Message mesFromClient) {
        super();
        this.mesFromClient = mesFromClient;
    }

    public void execute() throws SQLException, IOException {
        con = getConnection();
        mesToClient = new Message();
        String uid = mesFromClient.getUserId();
        switch (this.mesFromClient.getMessageType()) {
            case MessageType.ChatLogin:
                setChatLogin(mesFromClient.getData().toString());
                break;
            case MessageType.ChatLogout:
                setChatLogout(mesFromClient.getData().toString());
                break;
            case MessageType.CRE_ANONYMOUS:
                String ano;
                //匿名不重复
                do {
                    ano = NickName.generateName();
                } while (ServerClientThreadMgr.contain(ano));
                ServerClientThreadMgr.bindano(uid, ano);
                mesToClient.setData(ano);
                break;
            case MessageType.DES_ANONYMOUS: {
                //退出登录时清除匿名
                ServerClientThreadMgr.unbindano(uid);
                break;
            }
            case MessageType.ChatShow:
                ArrayList<Chat> allChats = this.getAllChat();
                this.mesToClient.setData(allChats);
                break;
            case MessageType.ChatSend:
                Chat chat = (Chat) this.mesFromClient.getData();
                this.sendChat(chat);
                System.out.println("send结束");
                this.chatBroadcast(chat);
                break;
            case MessageType.ChatHistory:
                ArrayList<Chat> historyChat = this.getAllChat();
                this.mesToClient.setData(historyChat);
                break;
        }
    }

    private void sendChat(Chat chat) {//匿名，内容，时间，uID
        String sql = "insert into tb_Chat(NickName,ChatText,ChatTime,uID) values(?,?,?,?)";
        String[] paras = new String[4];
        paras[0] = chat.getNickName();
        paras[1] = chat.getChatText();
        paras[2] = chat.getChatTime();
        paras[3] = chat.getUID();
        new SqlHelper().sqlUpdate(sql, paras);
    }

    private void chatBroadcast(Chat chat) throws SQLException, IOException {
        Map<String, Socket> clientSocketPool = ServerClientThreadMgr.getUIDSocketPool();
        System.out.println("aa");
        for (Map.Entry<String, Socket> entry : clientSocketPool.entrySet()) {
            System.out.println("键:"+entry.getKey()+" 值："+entry.getValue());
        }
        System.out.println("bb");
        ArrayList<String> onlineID = getChatOnline(chat.getUID());
        Message[] messageResponse = new Message[onlineID.size()];
        if (onlineID.size() > 0) {
            for (int i = 0; i < onlineID.size(); i++) {
                Message messageBroadcast = new Message();
                messageBroadcast.setData(chat);
                //messageBroadcast.setMessageType(MessageType.operFeedback);
                //messageBroadcast.setLastOperState(true);
                Socket serverToClientSocket = clientSocketPool.get(onlineID.get(i));
                ObjectOutputStream response = new ObjectOutputStream(serverToClientSocket.getOutputStream());
                response.writeObject(messageBroadcast); // 这里统一发回数据给客户端
                response.flush();
                System.out.println("其他聊天室的socket：" + clientSocketPool.get(onlineID.get(i)));
            }
        }
    }

    private ArrayList<String> getChatOnline(String excludeID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("select uID from tb_User where uChatStatus=" + "'1'");
        ArrayList<String> uID = new ArrayList<>();
        while (rs.next()) {
            uID.add(rs.getString(1));
        }
        uID.remove(excludeID);
        return uID;
    }

    public ArrayList<Chat> getAllChat() {
        String sql = "select * from tb_Chat";
        return (ArrayList<Chat>) new SqlHelper().sqlChatQuery(sql, new String[]{});
    }

    public void setChatLogin(String uID) throws SQLException {
        s = con.createStatement();
        s.executeUpdate("update tb_User set uChatStatus=" + "'1'" + "where uID='" + uID + "'");
    }

    public void setChatLogout(String uID) throws SQLException {
        s = con.createStatement();
        s.executeUpdate("update tb_User set uChatStatus=" + "'0'" + "where uID='" + uID + "'");
    }

    public Message getMesToClient() {
        return this.mesToClient;
    }
}

