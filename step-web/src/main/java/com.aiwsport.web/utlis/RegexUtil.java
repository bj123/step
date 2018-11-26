package com.aiwsport.web.utlis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangjian9
 * @date Created on 2018/3/27
 */
public class RegexUtil {
    public static final String isIp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    public static final Pattern isChinese = Pattern.compile("[\u4e00-\u9fa5]");
    public static final String isNumber = "[0-9]*";
    public static final String isDecimal = "";
    public static final Pattern isHost = Pattern.compile("^[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]", Pattern.CASE_INSENSITIVE);


    /**
     * 判断是否是正确的IP地址段，ip之间用逗号分隔
     *
     * @param ips
     * @return boolean true,通过，false，没通过
     */
    public static boolean isIps(String ips) {
        String[] ipList = ips.split(",");
        if (ipList == null || ipList.length == 0) {
            return false;
        }
        for (String ip : ipList) {
            if (!isIp(ip)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是正确的IP地址
     *
     * @param ip
     * @return boolean true,通过，false，没通过
     */
    public static boolean isIp(String ip) {
        if (null == ip || "".equals(ip))
            return false;
        return ip.matches(isIp);
    }

    /**
     * 判断是否含有中文，仅适合中国汉字，不包括标点
     *
     * @param text
     * @return boolean true,通过，false，没通过
     */
    public static boolean isChinese(String text) {
        if (null == text || "".equals(text))
            return false;
        Matcher m = isChinese.matcher(text);
        return m.find();
    }

    /**
     * 判断是否正整数
     *
     * @param number 数字
     * @return boolean true,通过，false，没通过
     */
    public static boolean isNumber(String number) {
        if (null == number || "".equals(number))
            return false;
        String regex = "^[+]{0,1}(\\d+)$";
        return number.matches(regex);
    }

    /**
     * 判断几位小数(正数)
     *
     * @param decimal 数字
     * @param count   小数位数
     * @return boolean true,通过，false，没通过
     */
    public static boolean isDecimal(String decimal, int count) {
        if (null == decimal || "".equals(decimal))
            return false;
        String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + count + "})?$";
        return decimal.matches(regex);
    }

    /**
     * 判断字符串是否域名
     *
     * @param url
     * @return
     */
    public static boolean isHost(String url) {
        return isHost.matcher(url).matches();
    }

    /**
     * 判断是否是boolean值
     *
     * @param value
     * @return
     */
    public static boolean isBoolean(String value) {
        return "false".equals(value) || "true".equals(value);
    }

    public static void main(String[] args){
        System.out.println(isHost("i.api.weibo.com"));
    }
}