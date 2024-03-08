package boot.spring.controller;

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


import boot.spring.po.User;
import boot.spring.service.LoginService;
import boot.spring.service.WebSocketServer;

@Controller
public class ChatController {

	@Autowired
	LoginService loginservice;
	

	//方法onlineusers接受一个请求参数currentuser（当前用户），并返回一个用户名称集合。
	// 这个方法首先从WebSocketServer获取当前所有在线用户的会话池（一个ConcurrentHashMap），
	// 然后从中删除当前用户的名称，最后返回剩余的在线用户名称集合。
	@RequestMapping("/onlineusers")
	@ResponseBody
	public Set<String> onlineusers(@RequestParam("currentuser") String currentuser) {
		ConcurrentHashMap<String, Session> map = WebSocketServer.getSessionPools();
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		Set<String> nameset = new HashSet<String>();
		while (it.hasNext()) {
			String entry = it.next();
			if (!entry.equals(currentuser))
				nameset.add(entry);
		}
		return nameset;
	}


}
