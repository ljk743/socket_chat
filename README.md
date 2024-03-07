# socket_chat
#
The traditional HTTP protocol generally realizes half-duplex communication by sending a request to the server and pulling data. The disadvantage is that it is difficult for the server to send messages directly to the browser. Therefore, websocket protocol came into being, which can be used to actively send arbitrary data (PUSH) to the connected browser. This project is based on Spring platform and integrates websocket protocol to realize the function of a simple web chat room. The main features are as follows:

1. Including chat room login and logout functions. When logging in, the browser automatically initiates a websocket connection to the server, and automatically disconnects when logging out.

2. After logging in, users can view the online user list in the chat room, and we always record the current online user list on the server through a hashmap;

3. The logged-in user can click on an online other user and send him a message, which is first submitted to the server and then forwarded to the other end user through the server;

4. Support the function of sending messages in groups. When in use, the server will send the received messages in groups to all users currently online;

5. Add the functions of online reminder and offline reminder for friends, and automatically notify all other online people when friends are online or offline. Don't refresh the page to see the real-time online user list.

**Env Requirements:**
- JDK >= 11 (Tested)
- Maven >= 3.8.1 (Tested)
- Mysql8 (Tested)

#
**To run the project on tomcat server:**
- Use terminal to enter the current directory
- execute **mvn clean install**
- Enter **/target**
- Copy socket_chat-0.0.1-SNAPSHOT.war to **your tomcat basedir/webapps**
- Start your tomcat server
- The url to login : **http://localhost:8080/login**