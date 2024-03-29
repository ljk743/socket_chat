package boot.spring.service;

import boot.spring.po.Message;
import boot.spring.utils.AESUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// Declaring this class as a WebSocket server endpoint, listening on the path "/webSocket/{username}".
@ServerEndpoint("/webSocket/{username}")
@Component // Marks this class as a component class for component scanning.
public class WebSocketServer {
    // Static variable to keep track of the current number of online connections. It is thread-safe.
    private static final AtomicInteger onlineNum = new AtomicInteger();

    // Thread-safe ConcurrentHashMap to store WebSocketServer objects corresponding to each client.
    private static final ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    // Method to increment the online count atomically.
    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    // Method to decrement the online count atomically.
    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

    // Getter for the online number AtomicInteger.
    public static AtomicInteger getOnlineNumber() {
        return onlineNum;
    }

    // Getter for the session pools ConcurrentHashMap.
    public static ConcurrentHashMap<String, Session> getSessionPools() {
        return sessionPools;
    }

    // Method to send a message to a session.
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) { // Synchronizing on the session object to ensure thread safety.
                System.out.println("Sending data: " + message);
                session.getBasicRemote().sendText(message); // Sending the message to the client.
            }
        }
    }

    // Method to send a message to a specific user by username.
    public void sendInfo(String userName, String message) {
        Session session = sessionPools.get(userName); // Retrieving the session corresponding to the username.
        try {
            sendMessage(session, message); // Sending the message to the session.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to broadcast a message to all clients.
    public void broadcast(String message) {
        for (Session session : sessionPools.values()) { // Iterating through all sessions.
            try {
                sendMessage(session, message); // Sending the message to each session.
            } catch (Exception e) {
                e.printStackTrace(); // Handling any exceptions.
                continue;
            }
        }
    }

    // Method called when a new connection is established.
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "username") String userName) {
        sessionPools.put(userName, session); // Adding the session to the session pool.
        addOnlineCount(); // Incrementing the online count.
        System.out.println(userName + " joined webSocket! Current number of people is " + onlineNum);
        // Broadcasting an online message.
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setTo("0");
        msg.setText(userName);
        broadcast(JSON.toJSONString(msg, true)); // Broadcasting the message as a JSON string.
    }

    // Method called when a connection is closed.
    @OnClose
    public void onClose(@PathParam(value = "username") String userName) {
        sessionPools.remove(userName); // Removing the session from the session pool.
        subOnlineCount(); // Decrementing the online count.
        System.out.println(userName + " disconnected from webSocket! Current number of people is " + onlineNum);
        // Broadcasting an offline message.
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setTo("-2");
        msg.setText(userName);
        broadcast(JSON.toJSONString(msg, true)); // Broadcasting the message as a JSON string.
    }

    // Method called when a message is received from a client.
    @OnMessage
    public void onMessage(String message) throws Exception {
        System.out.println("Server received: " + message);
        String emsg = AESUtil.encrypt(message);
        Message msg = JSON.parseObject(message, Message.class); // Parsing the message from JSON to a Message object.
        msg.setDate(new Date());
        if (msg.getTo().equals("-1")) { // Checking if the message is meant for broadcasting.
            broadcast(JSON.toJSONString(msg, true)); // Broadcasting the message.
        } else {
            sendInfo(msg.getTo(), JSON.toJSONString(msg, true)); // Sending the message to a specific user.
        }
    }

    // Method called when an error occurs.
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("An error occurred");
        throwable.printStackTrace(); // Printing the stack trace of the throwable.
    }
}