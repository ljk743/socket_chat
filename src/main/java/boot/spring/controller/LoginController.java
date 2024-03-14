package boot.spring.controller;

import boot.spring.po.User;
import boot.spring.service.LoginService;
import boot.spring.utils.HashTools;
import boot.spring.utils.PasswordValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
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
    private LoginService loginservice;

    @RequestMapping("/loginvalidate")
    public String loginvalidate(@RequestParam("username") String username, @RequestParam("password") String pwd, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        // 检查用户名和密码是否为空
        if (username == null || pwd == null) {
            return "loginfail";
        }

        // 检查用户名和密码的长度是否符合要求
        if (username.length() > 16 || pwd.length() > 40) {
            return "lengthInvalid";
        }

        try {
            String salt = loginservice.getSaltByName(username);
            String hashedPassword = HashTools.hashWithSHA256(pwd, salt);
            UsernamePasswordToken token = new UsernamePasswordToken(username, hashedPassword);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);

            if (currentUser.isAuthenticated()) {
                Long uid = loginservice.getUidbyname(username);
                httpSession.setAttribute("uid", uid);

                return "chatroom";
            } else {
                // 这个分支实际上可能永远不会执行，因为如果认证失败，会直接抛出异常
                return "loginfail";
            }
        } catch (AuthenticationException ae) {
            // 处理身份验证失败的情况
            System.out.println("Login Failed : " + ae.getMessage());
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
            return "lengthInvalid";
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

        String salt = HashTools.generateSalt(); // 生成盐
        String hashedPassword = HashTools.hashWithSHA256(password, salt);
        // 添加新用户（需要在LoginService中实现此方法）
        boolean success = loginservice.addNewUser(username, hashedPassword, salt);
        if (success) {
            return "regOK"; // 如果注册成功，跳转到一个确认页面或登录页面
        } else {
            return "backendfail"; // 如果注册失败，返回注册页面并显示错误消息
        }
    }

}
