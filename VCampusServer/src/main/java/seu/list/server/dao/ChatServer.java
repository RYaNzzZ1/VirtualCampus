package seu.list.server.dao;

import seu.list.common.Message;
import seu.list.server.db.Chat_DbAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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

    public void execute(){
        con=getConnection();
        mesToClient=new Message();
        switch (this.mesFromClient.getMessageType()){

        }
    }
}
