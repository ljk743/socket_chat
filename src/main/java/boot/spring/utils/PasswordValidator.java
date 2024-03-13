package boot.spring.utils;

import java.util.regex.Pattern;

public class PasswordValidator {

    // 更新正则表达式规则：
    // 要求密码至少包含一个大写字母、一个小写字母和一个特殊字符(!@#%*&)
    // 且长度不小于八位
    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#%*&])(?=.*[0-9])[A-Za-z!@#%*&0-9]{8,}$";


    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
}
