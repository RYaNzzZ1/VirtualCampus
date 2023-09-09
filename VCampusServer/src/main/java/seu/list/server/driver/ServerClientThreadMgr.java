package seu.list.server.driver;

import seu.list.common.Message;
import seu.list.common.User;

import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * 类{@code ServerClientThreadMgr}用于管理服务器上的客户端线程池 <br>
 * 静态数据成员: {@code clientThreadPool}, 类型: {@code Map}, 客户端线程池 <br>
 * 支持对线程池的增删改查
 *
 * @version 1.0
 * @see Map
 */
public class ServerClientThreadMgr {
    private static final Map<String, ServerSocketThread> clientThreadPool = new HashMap<String, ServerSocketThread>();//线程池
    private static final Map<String, String> allocation = new HashMap<String, String>();//用户ID与线程ID
    private static final Map<String, String> anonymity = new HashMap<String, String>();//用户ID与匿名
    private static final Map<String, Socket> uIDSocketPool = new HashMap<String, Socket>();//用户ID与匿名




    //存储客户端socket

    /**
     * 绑定线程和对应的用户ID，通过用户ID查询对应线程
     *
     * @param id  需要添加的客户端线程ID
     * @param uid 需要添加的用户ID
     * @return null
     * @author 谢睿沣
     * @version 1.0
     * @see Map#put(Object, Object)
     */
    public synchronized static void bind(String uid, String id) {
        System.out.println("用户:" + uid + "绑定至" + "线程" + id);
        allocation.put(uid, id);
        uIDSocketPool.put(uid,clientThreadPool.get(id).getClientSocket());
    }

    public synchronized static void unbind(String uid) {
        System.out.println("用户:" + uid + "与" + "线程" + allocation.get(uid) + "解绑");
        allocation.remove(uid);
        uIDSocketPool.remove(uid);
    }

    public static Map<String, Socket> getUIDSocketPool() {
        return uIDSocketPool;
    }

    public synchronized static void unbindbyid(String id) {
        for (String key : allocation.keySet()) {
            if (allocation.get(key).equals(id)) {
                unbind(key);
                User user = new User();
                user.setId(key);
                user.changeState(0);
                break;
            }
        }
    }

    public synchronized static boolean contain(String ano) {
        return anonymity.containsValue(ano);
    }

    public synchronized static void bindano(String uid, String ano) {
        anonymity.put(uid, ano);
    }

    public synchronized static void unbindano(String id) {
        anonymity.remove(id);
    }

    public static void unbindanobyid(String id) {
        for (String key : allocation.keySet()) {
            if (allocation.get(key).equals(id)) {
                unbindano(key);
                break;
            }
        }
    }

    public synchronized static ServerSocketThread getPreThread(String uid) {
        String preid = allocation.get(uid);
        ServerSocketThread pres = clientThreadPool.get(preid);
        return pres;
    }

    public synchronized static String getId(String uid) {
        return allocation.get(uid);
    }

    /**
     * 添加一个新的客户端线程到线程池中，不可重复，重复会覆盖原有线程
     *
     * @param id              需要添加的客户端线程ID
     * @param clientThreadSrv 需要添加的客户端线程
     * @return 如果id对应的线程存在，则覆盖并返回原线程，如果不存在，则返回{@code null}
     * @version 1.0
     * @see Map#put(Object, Object)
     */
    public synchronized static ServerSocketThread add(String id, ServerSocketThread clientThreadSrv) {
        return clientThreadPool.put(id, clientThreadSrv);
    }

    /**
     * 根据输入的客户端线程ID删除对应的客户端线程
     *
     * @param id 需要删除的客户端线程ID
     * @return 被删除的客户端线程，若该ID对应的线程不存在，则返回{@code null}
     * @version 1.0
     * @see Map#remove(Object)
     */
    public synchronized static ServerSocketThread remove(String id) {
        return clientThreadPool.remove(id);
    }


    public synchronized static ServerSocketThread get(String id) {
        ServerSocketThread ret = clientThreadPool.get(id);
        return ret;
    }

    /**
     * 获取整个客户端线程池
     *
     * @return 客户端线程池
     * @version 1.0
     */
    public synchronized static Map<String, ServerSocketThread> getPool() {
        return clientThreadPool;
    }

    /**
     * 清空整个客户端线程池，会关闭所有客户端线程
     *
     * @version 1.0
     */
    public synchronized static void clear() {
        Iterator<Map.Entry<String, ServerSocketThread>> entries = clientThreadPool.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, ServerSocketThread> entry = entries.next();
            ServerSocketThread thd = entry.getValue();

            thd.close();
        }
        clientThreadPool.clear();
    }

    /**
     * 对所有客户端发送消息
     *
     * @param mes 对所有客户端发送的消息
     * @version 1.0
     */
    public synchronized static void sendMesToAll(Message mes) {
        Iterator<Map.Entry<String, ServerSocketThread>> entries = clientThreadPool.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, ServerSocketThread> entry = entries.next();
            ServerSocketThread thd = entry.getValue();
            thd.sendMesToClient(mes);
        }
    }

    /**
     * 打印目前连接到服务器上的所有客户端的信息
     *
     * @version 1.0
     */
    public synchronized static void printAllClient() {
        if (clientThreadPool.isEmpty()) {
            System.out.println("目前没有客户端连接到服务器！");
            return;
        }

        Iterator<Map.Entry<String, ServerSocketThread>> entries = clientThreadPool.entrySet().iterator();
        System.out.println("目前连接到服务器上的客户端: ");
        while (entries.hasNext()) {
            Map.Entry<String, ServerSocketThread> entry = entries.next();
            ServerSocketThread thd = entry.getValue();
            System.out.println("客户端线程ID: " + thd.getCliThdID() + ", ip地址: " + thd.getIP());
        }
    }

    /**
     * 返回目前连接到服务器上的所有客户端的信息
     *
     * @return 目前连接到服务器上客户端的ID+ip地址，不存在则返回{@code null}
     * @version 1.0
     */
    public synchronized static Vector<String> getAll() {
        Vector<String> res = new Vector<String>();
        if (clientThreadPool.isEmpty()) {
            System.out.println("目前没有客户端连接到服务器！");
            return res;
        }

        Iterator<Map.Entry<String, ServerSocketThread>> entries = clientThreadPool.entrySet().iterator();
        System.out.println("目前连接到服务器上的客户端: ");
        while (entries.hasNext()) {
            Map.Entry<String, ServerSocketThread> entry = entries.next();
            ServerSocketThread thd = entry.getValue();
            res.add(thd.getCliThdID());
            res.add(thd.getIP());
        }
        return res;
    }


}
