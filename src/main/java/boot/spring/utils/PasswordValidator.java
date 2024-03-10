package boot.spring.utils;

import java.util.regex.Pattern;

public class PasswordValidator {

    // 正则表达式规则，要求密码包含至少一个大写字母和一个特殊字符(!@#%*&)
    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[!@#%*&]).+$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
}
