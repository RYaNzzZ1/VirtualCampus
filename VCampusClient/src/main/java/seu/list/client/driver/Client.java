package seu.list.client.driver;

import seu.list.common.Message;
import seu.list.common.MessageType;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
    }

    public Message sendRequestToServer(Message clientRequest) {
        try {
            // 向服务端发送数据
            ObjectOutputStream request = new ObjectOutputStream(socket.getOutputStream());
            request.writeObject(clientRequest);
            request.flush();

            // 下线通知不需要等待回应
            if (clientRequest.isOffline()) {
                return null;
            }

            Message messageReturn;//messageReturn,要改
            while (true) { // 等待服务器回应数据
                ObjectInputStream response = new ObjectInputStream(socket.getInputStream());
                messageReturn = (Message) response.readObject();
                if (messageReturn.getMessageType().equals(MessageType.operFeedback)) {
                    break;
                }
            }
            return messageReturn; // 把收到的数据返回给客户端
        } catch (SocketException se) {
            JOptionPane.showMessageDialog(null, "网络连接错误，请重新启动客户端或联系服务器管理员", "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
