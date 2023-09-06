package seu.list.server.dao;

import com.sun.security.ntlm.NTLMException;
import seu.list.client.driver.Client;
import seu.list.common.*;
import seu.list.server.db.Chat_DbAccess;
import seu.list.server.db.SqlHelper;
import seu.list.server.driver.ServerClientThreadMgr;
import seu.list.server.driver.ServerSocketThread;


import java.sql.*;
import java.util.*;

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
            case MessageType.CRE_ANONYMOUS:{
                Random random=new Random();
                // 随机生成一个姓名，长度为 2 到 6 个字母
                int nameLength = random.nextInt(5) + 2;
                String ano;
                StringBuilder name ;
                //匿名不重复
                do {
                    name =new StringBuilder();
                    for (int j = 0; j < nameLength; j++) {
                        char letter = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
                        name.append(letter);
                    }
                    ano=name.toString();
                }while(ServerClientThreadMgr.contain(ano));

                ServerClientThreadMgr.bindano(uid,ano);
                mesToClient.setData(ano);
                break;
            }
            case MessageType.DES_ANONYMOUS:{
                //退出登录时清除匿名
                ServerClientThreadMgr.unbindano(uid);
                break;
            }
            case MessageType.ChatShow:
                break;
            case MessageType.ChatSend: {
                Chat chat = (Chat) this.mesFromClient.getData();
                this.sendChat(chat);
                this.mesToClient.setData(this.chatBroadcast(chat));
                break;
            }
        }
            case MessageType.ChatHistory: {
                System.out.println("serving CHAT_HISTORY");
                System.out.println("grabbing.....");
                Vector<String> sigChatContent = new Vector<String>();
                Vector<String> allChatContent = new Vector<String>();
                List<Chat> allChat = new LinkedList<Chat>();
                allChat = this.getAllChat();
                Iterator<Chat> iteAllChat = allChat.iterator();
                while (iteAllChat.hasNext()) {
                    sigChatContent = iteAllChat.next().getContent();
                    for (int i = 0; i <= 3; i++) {
                        allChatContent.add(sigChatContent.get(i));
                    }
                }
                System.out.println(allChatContent);
                this.mesToClient.setContent(allChatContent);
                System.out.println("CHAT_HISTORY finished");
                break;
            }
        }
    }
        private void sendChat(Chat chat) {//匿名，内容，时间，uID
        String sql="insert into tb_Chat(NickName,ChatText,ChatTime,uID) values(?,?,?,?)";
        String[] paras = new String[4];
        paras[0]=chat.getNickName();
        paras[1]=chat.getChatText();
        paras[2]=chat.getChatTime();
        paras[3]=chat.getUID();

        new SqlHelper().sqlUpdate(sql,paras);
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

    public List<Chat> getAllChat()
    {
        String sql = "select * from tb_Chat";
        return new SqlHelper().sqlChatQuery(sql, new String[]{});
    }

}
