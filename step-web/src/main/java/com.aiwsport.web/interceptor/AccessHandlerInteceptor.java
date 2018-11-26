package com.aiwsport.web.interceptor;

import com.aiwsport.core.StepServerException;
import com.aiwsport.core.StepServerExceptionFactor;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class AccessHandlerInteceptor implements HandlerInterceptor {

    private static Logger logger = LogManager.getLogger();

    /**
     * controller 执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        authSign(request, "y21gsdi35zas0921ksjxu3la5noiwns5ak821#2*ds+");
        return true;
    }

    /**
     * controller 执行之后，且页面渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    /**
     * 页面渲染之后调用，一般用于资源清理操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {


    }

    //进行签名限制
    //签名规则 md5(base64(params) + appsecret)
    protected void authSign(HttpServletRequest request, String secret) {
        String paramString = getQueryString(request);
        String param_sign = request.getParameter("sign");
        commonAuthSign(paramString,param_sign,secret,request.getRequestURI());
    }

    protected void commonAuthSign(String paramString,String param_sign,String secret, String uri){
        String sign = DigestUtils.md5Hex(URLEncoder.encode(paramString.replaceAll(" ", "")) + secret);
        if (!sign.equals(param_sign)) {
            logger.warn("paramString: " + paramString +
                    ",param sign: " + param_sign + ",sign: " + sign + ", uri: " + uri);
            throw new StepServerException(StepServerExceptionFactor.SIGN_IS_ERROR);
        }
    }

    public static String getQueryString(HttpServletRequest request) {
        SortedMap<String, String[]> params = getSortedParams(request);
        return getQueryStringByMap(params);
    }

    private static SortedMap<String, String[]> getSortedParams(HttpServletRequest request) {
        SortedMap<String, String[]> map = new TreeMap<String, String[]>();
        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap == null) {
            return map;
        }
        for (Object e : paramMap.entrySet()) {
            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) e;
            String name = entry.getKey();
            map.put(name, entry.getValue());
        }
        return map;
    }

    public static String getQueryStringByMap(SortedMap sortedMap) {
        boolean first = true;
        StringBuilder strbuf = new StringBuilder();
        for (Object e : sortedMap.entrySet()) {
            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) e;
            String name = entry.getKey();
            if ("sign".equals(name)) {
                continue;
            }
            String[] sValues = entry.getValue();
            String sValue = "";
            for (int i = 0; i < sValues.length; i++) {
                sValue = sValues[i];
                if (first == true) {
                    //第一个参数
                    first = false;
                    strbuf.append(name).append("=").append(sValue);
                } else if (first == false) {
                    strbuf.append("&").append(name).append("=").append(sValue);
                }
            }
        }
        return strbuf.toString();
    }

}
