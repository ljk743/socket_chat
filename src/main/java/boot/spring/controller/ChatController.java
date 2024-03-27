package boot.spring.controller;

// Import necessary classes
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import boot.spring.service.LoginService;
import boot.spring.service.WebSocketServer;

/**
 * Controller class responsible for handling chat-related web requests.
 * It uses Spring MVC annotations to define routing and dependency injection.
 */
@Controller
public class ChatController {

	// Autowire the LoginService to use its functionalities
	// @Autowired
	// LoginService loginservice;

	/**
	 * Method to fetch online users excluding the current user.
	 * It responds to the "/onlineusers" request path.
	 *
	 * @param currentuser The username of the current user passed as a request parameter.
	 * @return Set<String> A set of usernames representing online users excluding the current user.
	 *
	 * The method achieves this by retrieving the current session pools from the WebSocketServer,
	 * which contains all online users. It then filters out the current user's username and
	 * returns the remaining set of usernames.
	 */
	@RequestMapping("/onlineusers")
	@ResponseBody
	public Set<String> onlineusers(@RequestParam("currentuser") String currentuser) {
		// Retrieve the current online sessions from the WebSocketServer
		ConcurrentHashMap<String, Session> map = WebSocketServer.getSessionPools();
		// Extract the set of usernames (keys of the map)
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		Set<String> nameset = new HashSet<String>();
		while (it.hasNext()) {
			String entry = it.next();
			// Exclude the current user from the set
			if (!entry.equals(currentuser))
				nameset.add(entry);
		}
		// Return the set of online users excluding the current user
		return nameset;
	}
}
