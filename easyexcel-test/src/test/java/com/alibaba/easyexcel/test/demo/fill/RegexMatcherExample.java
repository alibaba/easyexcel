package com.alibaba.easyexcel.test.demo.fill;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcherExample {
    public static void main(String[] args) {
        String regex = ".*m100.*";
        String input = "http://www.baidu.com/images/m100-1.3.jpg";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            System.out.println("字符串匹配正则表达式");
        } else {
            System.out.println("字符串不匹配正则表达式");
        }

        String content = "I am noob " +
            "from runoob.com.";

        String pattern1 = ".*runoob.*";

        boolean isMatch = Pattern.matches(pattern1, content);
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);

    }
}
