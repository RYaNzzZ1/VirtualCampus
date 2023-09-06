package seu.list.server.dao;

import com.sun.security.ntlm.NTLMException;
import seu.list.client.driver.Client;
import seu.list.common.Chat;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.common.ModuleType;
import seu.list.server.db.Chat_DbAccess;
import seu.list.server.driver.ServerClientThreadMgr;
import seu.list.server.driver.ServerSocketThread;



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

    public ChatServer(Message mesFromClient) {
        super();
        this.mesFromClient = mesFromClient;
    }

    public void execute() throws SQLException, NTLMException {
        con = getConnection();
        mesToClient = new Message();
        switch (this.mesFromClient.getMessageType()) {
            case MessageType.ChatShow:
                break;
            case MessageType.ChatSend:
                Chat chat = (Chat)this.mesFromClient.getData();
                this.mesToClient.setData(chatBroadcast(chat));

        }
    }

    private Message[] chatBroadcast(Chat chat) throws SQLException {
        Map<String, ServerSocketThread> threadPool = ServerClientThreadMgr.getPool();
        ArrayList<String> onlineID = getChatOnline(chat.getUID());
        Message[] messageResponse=new Message[onlineID.size()];
        for (int i = 0; i < onlineID.size(); i++) {
            Message messageBroadcast = new Message();
            messageBroadcast.setModuleType(ModuleType.Chat);
            messageBroadcast.setMessageType(MessageType.ChatShow);
            messageBroadcast.setData(chat);
            Client client=new Client(threadPool.get(onlineID.get(i)).getClientSocket());
            messageResponse[i]=client.sendRequestToServer(messageBroadcast);
        }
        return messageResponse;
    }

    private ArrayList<String> getChatOnline(String excludeID) throws SQLException {
        rs = s.executeQuery("select uID from tb_User where ChatStatus=" + "'1'");
        ArrayList<String> uID = new ArrayList<>();
        while (rs.next()) {
            uID.add(rs.getString(1));
        }
        uID.remove(excludeID);
        return uID;
    }
}
