package boot.spring.controller;

import boot.spring.po.User;
import boot.spring.service.LoginService;
import boot.spring.service.RedisCodeService;
import boot.spring.utils.EmailValidator;
import boot.spring.utils.HashTools;
import boot.spring.utils.PasswordValidator;
import boot.spring.utils.UsernameValidator;
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

// Defines the controller class for handling user login, logout, and registration within a Spring Boot application.
@Controller
public class LoginController {

    // Injects the LoginService to use its methods for login and registration operations.
    @Autowired
    private LoginService loginservice;

    // Injects the RedisCodeService to use its methods for you verification code.
    @Autowired
    private RedisCodeService redisCodeService;

    // Handles the login validation process by checking username, password, and email against certain rules.
    @RequestMapping("/loginvalidate")
    public String loginvalidate(@RequestParam("username") String username, @RequestParam("password") String pwd, @RequestParam("email") String email, @RequestParam("code") String code, HttpSession httpSession, RedirectAttributes redirectAttributes) {

        // Checks if the username or password is null and returns a failure view if so.
        if (username == null || pwd == null) {
            return "commonfail";
        }

        // Validates the username with custom rules and checks its length, returning a failure view if it doesn't meet the criteria.
        if (!UsernameValidator.isValidUsername(username) || username.length() > 16) {
            return "usernamefail";
        }

        // Validates the password with custom rules and checks its length, returning a failure view if it doesn't meet the criteria.
        if (!PasswordValidator.isValid(pwd) || pwd.length() > 40) {
            return "passwdfail"; // Returns a view indicating password validation failure.
        }

        // Validates the email with custom rules and checks its length, returning a failure view if it doesn't meet the criteria.
        if(!EmailValidator.isValidEmail(email) || email.length() > 254){
            return "emailfail";
        }
        if(!code.equals(redisCodeService.getVerificationCode(username, email))){
            return "wrongCode";
        }
        try {
            // Attempts to authenticate the user with the provided credentials.
            String salt = loginservice.getSaltByName(username);
            String hashedPassword = HashTools.hashWithSHA256(pwd, salt);
            UsernamePasswordToken token = new UsernamePasswordToken(username, hashedPassword);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);

            // Checks if the current user is authenticated and sets session attributes accordingly.
            if (currentUser.isAuthenticated()) {
                Long uid = loginservice.getUidbyname(username);
                httpSession.setAttribute("uid", uid);

                return "chatroom"; // Directs the user to the chatroom view if authentication is successful.
            } else {
                // This branch might never execute because if authentication fails, an exception is thrown.
                return "loginfail";
            }
        } catch (AuthenticationException ae) {
            // Handles the case where authentication fails.
            System.out.println("Login Failed : " + ae.getMessage());
            return "loginfail";
        }
    }

    // Directs to the login view.
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    // Handles the logout process by invalidating the session and redirecting to the login view.
    @RequestMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "login";
    }

    // Provides the current logged-in user's details.
    @RequestMapping(value = "/currentuser", method = RequestMethod.GET)
    @ResponseBody
    public User currentuser(HttpSession httpSession) {
        Long uid = (Long) httpSession.getAttribute("uid");
        String name = loginservice.getnamebyid(uid);
        return new User(uid, name); // Returns a User object with the current user's ID and name.
    }

    // Directs to the registration view.
    @RequestMapping("/reg")
    public String reg() {
        return "reg";
    }

    // Handles the user registration process, including validation of input and creation of new user.
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email, @RequestParam("code") String code, HttpSession session) {

        // Checks if any of the registration fields are null and returns a failure view if so.
        if (username == null || password == null || email == null){
            return "commonfail";
        }

        // Validates the username and checks its length, returning a failure view if it doesn't meet the criteria.
        if (!UsernameValidator.isValidUsername(username) || username.length() > 16) {
            return "usernamefail";
        }

        // Validates the password and checks its length, returning a failure view if it doesn't meet the criteria.
        if (!PasswordValidator.isValid(password) || password.length() > 40) {
            return "passwdfail"; // Returns a view indicating password validation failure.
        }

        // Validates the email and checks its length, returning a failure view if it doesn't meet the criteria.
        if(!EmailValidator.isValidEmail(email) || email.length() > 254){
            return "emailfail";
        }
        // Checks if the username already exists, indicating failure if so.
        boolean userExists = loginservice.checkUserExists(username);
        if (userExists) {
            return "alreadyexists"; // Indicates that the username is already taken.
        }

        // Generates a salt and hashes the password for storage.
        String salt = HashTools.generateSalt(); // Generates a new salt.
        String hashedPassword = HashTools.hashWithSHA256(password, salt);

        // Check the Verification code.
        if(!redisCodeService.getVerificationCode(username, email).equals(code)){
            return "wrongCode";
        }

        // Attempts to add a new user with the provided details.
        boolean success = loginservice.addNewUser(username, hashedPassword, salt, email);
        if (success) {
            return "regOK"; // Directs to a success view if registration is successful.
        } else {
            return "backendfail"; // Returns a failure view if the registration process fails.
        }
    }

}
