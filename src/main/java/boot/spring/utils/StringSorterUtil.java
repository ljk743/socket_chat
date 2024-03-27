package boot.spring.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StringSorterUtil {

    /**
     * 对字符串列表进行排序，排序基于字符串最后13位数字的大小。
     *
     * @param strings 要排序的字符串列表
     * @return 排序后的字符串列表
     */
    public static List<String> sortStringsByLastDigits(List<String> strings) {
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 提取并解析最后13位数字
                long num1 = Long.parseLong(o1.substring(o1.length() - 13));
                long num2 = Long.parseLong(o2.substring(o2.length() - 13));
                return Long.compare(num2, num1);
            }
        });
        List<String> modifiedStrings = new ArrayList<>();
        for (String s : strings) {
            modifiedStrings.add(s.substring(0, s.length() - 13));
        }

        return modifiedStrings; // 返回修改后的列表
    }
}


