package seu.list.common;

import java.util.Vector;

public class Chat implements java.io.Serializable {
    private String uID;
    private String NickName;
    private String ChatText;
    private String ChatTime;

    public Chat() {
    }

    public Chat(Chat chat) {
        this.uID = chat.getUID();
        this.NickName = chat.getNickName();
        this.ChatText = chat.getChatText();
        this.ChatTime = chat.getChatTime();
    }

    /**
     * 获取
     *
     * @return uID
     */
    public String getUID() {
        return uID;
    }

    /**
     * 设置
     *
     * @param uID
     */
    public void setUID(String uID) {
        this.uID = uID;
    }

    /**
     * 获取
     *
     * @return NickName
     */
    public String getNickName() {
        return NickName;
    }

    /**
     * 设置
     *
     * @param NickName
     */
    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    /**
     * 获取
     *
     * @return ChatText
     */
    public String getChatText() {
        return ChatText;
    }

    /**
     * 设置
     *
     * @param ChatText
     */
    public void setChatText(String ChatText) {
        this.ChatText = ChatText;
    }

    /**
     * 获取
     *
     * @return ChatTime
     */
    public String getChatTime() {
        return ChatTime;
    }

    /**
     * 设置
     *
     * @param ChatTime
     */
    public void setChatTime(String ChatTime) {
        this.ChatTime = ChatTime;
    }

    public Vector<String> getContent() {
        Vector<String> chatContents = new Vector<String>();
        chatContents.add(uID);
        chatContents.add(NickName);
        chatContents.add(ChatText);
        chatContents.add(ChatTime);
        return chatContents;
    }
}

