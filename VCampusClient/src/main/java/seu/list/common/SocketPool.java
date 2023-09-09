package seu.list.common;

import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketPool implements Serializable {
    public static Map<String, Socket> clientSocketPool = new HashMap<>();

    public static Map<String, Socket> getClientSocketPool() {
        return clientSocketPool;
    }

    public String getuID() {
        return uID;
    }

    public Socket getSocket() {
        return socket;
    }

    private String uID;
    private Socket socket;

    public SocketPool(String uid, Socket s) {
        uID = uid;
        socket = s;
    }
}