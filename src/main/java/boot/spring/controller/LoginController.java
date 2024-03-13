package boot.spring.controller;

import boot.spring.po.User;
import boot.spring.security.JwtUtil;
import boot.spring.service.LoginService;
import boot.spring.utils.PasswordValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {
    @Autowired
    LoginService loginservice;

    @RequestMapping("/loginvalidate")
    public String loginvalidate(@RequestParam("username") String username, @RequestParam("password") String pwd, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        if (username == null || pwd == null) {
            return "loginfail";
        }if (username.length() > 16 || pwd.length() > 40) {
            return "loginfail";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        if (currentUser.isAuthenticated()) {

                // 登录成功，从 Shiro 获取用户信息
                // 假设登录成功后你需要用户的ID存储在会话中
                // 这个逻辑可以根据你实际情况调整
                Long uid = loginservice.getUidbyname(username);
                httpSession.setAttribute("uid", uid);

                // 生成JWT
                String jwt = JwtUtil.createToken(username); // 假设用户名作为JWT的主题
                System.out.println("--------------------------------------------------------------------------------------------------");
                System.out.println(jwt);
                httpSession.setAttribute("jwt", jwt); // 将JWT存储在会话中

                return "chatroom";
        } else {
            System.out.println("--------------------------------------------------------------------------------------------------");
            // 登陆失败
            return "loginfail";
        }
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "login";
    }

    @RequestMapping(value = "/currentuser", method = RequestMethod.GET)
    @ResponseBody
    public User currentuser(HttpSession httpSession) {
        Long uid = (Long) httpSession.getAttribute("uid");
        String name = loginservice.getnamebyid(uid);
        return new User(uid, name);
    }

    @RequestMapping("/reg")
    public String reg() {
        return "reg";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        if (username.length() > 16 || password.length() > 40) {
            return "backendfail";
        }
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
