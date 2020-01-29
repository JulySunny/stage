package com.gabriel.stage.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: Gabriel
 * @date: 2020/1/17 15:22
 * @description 请求参数工具类
 */
@Slf4j
public class RequestUtil {
    /**
     * 获取请求所有参数
     *
     * @param request the request
     * @return params params
     */
    public static Map<String, Object> getParams(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Enumeration paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();

                String[] paramValues = request.getParameterValues(paramName);
                if (paramValues.length > 0) {
                    String paramValue = paramValues[0];
                    if (paramValue.length() != 0) {
                        map.put(paramName, paramValue);
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取请求所有参数异常:{}",e.getMessage());
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    /**
     * 判断请求方式GET
     *
     * @param request
     * @return
     */
    public static boolean isGet(HttpServletRequest request) {
        return HttpMethod.GET.toString().equalsIgnoreCase(request.getMethod());
    }

    /**
     * 判断请求方式POST
     *
     * @param request
     * @return
     */
    public static boolean isPost(HttpServletRequest request) {
        return HttpMethod.POST.toString().equalsIgnoreCase(request.getMethod());
    }

    /**
     * 判断请求方式PUT
     *
     * @param request
     * @return
     */
    public static boolean isPut(HttpServletRequest request) {
        return HttpMethod.PUT.toString().equalsIgnoreCase(request.getMethod());
    }

    /**
     * 判断请求方式DELETE
     *
     * @param request
     * @return
     */
    public static boolean isDelete(HttpServletRequest request) {
        return HttpMethod.DELETE.toString().equalsIgnoreCase(request.getMethod());
    }

    /**
     * 判断请求方式PATCH
     *
     * @param request
     * @return
     */
    public static boolean isPatch(HttpServletRequest request) {
        return HttpMethod.PATCH.toString().equalsIgnoreCase(request.getMethod());
    }

    /**
     * 判断请求方式TRACE
     *
     * @param request
     * @return
     */
    public static boolean isTrace(HttpServletRequest request) {
        return HttpMethod.TRACE.toString().equalsIgnoreCase(request.getMethod());
    }

    /**
     * 判断请求方式HEAD
     *
     * @param request
     * @return
     */
    public static boolean isHead(HttpServletRequest request) {
        return HttpMethod.HEAD.toString().equalsIgnoreCase(request.getMethod());
    }

    /**
     * 判断请求方式OPTIONS
     *
     * @param request
     * @return
     */
    public static boolean isOptions(HttpServletRequest request) {
        return HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod());
    }
    /**
     * 获取请求
     *
     * @param request
     * @return
     */
    public static String getRequestBody(HttpServletRequest request) throws IOException {
        String requestBody = null;
        if (isContainBody(request)) {
            ServletInputStream inputStream =request.getInputStream();
            if (Objects.nonNull(inputStream)) {
                StringWriter writer = new StringWriter();
                IOUtils.copy(inputStream, writer, StandardCharsets.UTF_8.name());
                requestBody = writer.toString();
            }

        }
        return requestBody;
    }
    /**
     * 获取请求
     *
     * @param request
     * @return
     */
    public static byte[] getByteBody(HttpServletRequest request) {
        byte[] body = new byte[0];
        try {
            body = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            log.error("Error: Get RequestBody byte[] fail," + e);
        }
        return body;
    }

    /**
     * 是否包含请求体
     *
     * @param request
     * @return
     */
    public static boolean isContainBody(HttpServletRequest request) {
        return isPost(request) || isPut(request) || isPatch(request);
    }

}
