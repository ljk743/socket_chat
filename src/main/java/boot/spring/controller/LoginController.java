package boot.spring.controller;

import javax.servlet.http.HttpSession;

import boot.spring.utils.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import boot.spring.po.User;
import boot.spring.service.LoginService;



@Controller
public class LoginController {
	@Autowired
	LoginService loginservice;
	
	@RequestMapping("/loginvalidate")
	public String loginvalidate(@RequestParam("username") String username,@RequestParam("password") String pwd,HttpSession httpSession){
		if(username==null)
			return "login";
		String realpwd=loginservice.getpwdbyname(username);
		if(realpwd!=null&&pwd.equals(realpwd))
		{
			long uid=loginservice.getUidbyname(username);
			httpSession.setAttribute("uid", uid);
			return "chatroom";
		}else
			return "loginfail";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession httpSession){
		httpSession.invalidate();
		return "login";
	}
	
	@RequestMapping(value="/currentuser",method = RequestMethod.GET)
	@ResponseBody
	public User currentuser(HttpSession httpSession){
		Long uid = (Long)httpSession.getAttribute("uid");
		String name = loginservice.getnamebyid(uid);
		return new User(uid, name);
	}

	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String register(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
		// 验证密码复杂度
		if (!PasswordValidator.isValid(password)) {
			return "passwdfail"; // 返回注册失败的视图
		}

		// 检查用户名是否已存在（需要在LoginService中实现此方法）
		boolean userExists = loginservice.checkUserExists(username);
		if (userExists) {
			return "alreadyexists"; // 用户名已存在，返回失败视图
		}

		// 添加新用户（需要在LoginService中实现此方法）
		boolean success = loginservice.addNewUser(username, password);
		if (success) {
			return "regOK"; // 如果注册成功，跳转到一个确认页面或登录页面
		} else {
			return "backendfail"; // 如果注册失败，返回注册页面并显示错误消息
		}
	}

}
