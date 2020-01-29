package com.gabriel.stage.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Gabriel
 * @date: 2019/12/3 22:49
 * @description 获取请求设备工具类
 */
public class AgentUserUtil {
    private static String pattern = "^Mozilla/\\d\\.\\d\\s+\\(+.+?\\)";
    private static String pattern2 = "\\(+.+?\\)";
    private static Pattern r = Pattern.compile(pattern);
    private static Pattern r2 = Pattern.compile(pattern2);

    public static String getDeviceInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent==null){
            return null;
        }
        return getDeviceInfo(userAgent);
    }

    private static String getDeviceInfo(String userAgent) {
        String result=null;
        try {
            Matcher m = r.matcher(userAgent);
            if (m.find()) {
                result = m.group(0);
            }
            Matcher m2 = r2.matcher(result);
            if (m2.find()) {
                result = m2.group(0);
            }
            result = result.replace("(", "");
            result = result.replace(")", "");

        } catch (Exception e) {
            return userAgent;
        }
        return filterDeviceInfo(result);
    }

    public static String filterDeviceInfo(String result) {
        if (StringUtils.isEmpty(result)) {
            return null;
        }
        result = result.replace(" U;", "");
        result = result.replace(" zh-cn;", "");
        return result;
    }
}
