package org.cooze.common.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author cooze
 * @version 1.0.0
 * @desc
 * @date 2017/3/22
 */
public class WebUtil {
    private WebUtil() {
    }


    private static String readJsonParamsFromRequest(HttpServletRequest request){
        StringBuffer requetsBody = new StringBuffer();
        if (request instanceof DefaultMultipartHttpServletRequest) {//Multipart data
        } else {//read json string from request body.
            byte[] b = new byte[1024];
            try {
                int len;
                try {
                    while((len = request.getInputStream().read(b)) != -1){
                        requetsBody.append(new String(b, 0, len));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return requetsBody.toString();
    }


    public static Map<String,String> parseRequestParams(HttpServletRequest request){
        return parseRequestParams(request,null);
    }


    public static Map<String,String> parseRequestParams(HttpServletRequest request, MultipartHttpServletRequest mrequest){

        String paramStr = readJsonParamsFromRequest(request);
        Map<String,Object> requestParams = null;
        if(paramStr!=null||!paramStr.isEmpty()){
            requestParams = JSON.toJavaObject(JSON.parseObject(paramStr),Map.class);
        }

        Enumeration<String> requestparamsNames =  request.getParameterNames();
        if(requestParams==null)requestParams = new HashMap<>();
        while (requestparamsNames.hasMoreElements()){
            String name = requestparamsNames.nextElement();
            requestParams.put(name,request.getParameter(name));
        }
        if(mrequest!=null){
            Enumeration<String> mrequestParameterNames = mrequest.getParameterNames();
            while (mrequestParameterNames.hasMoreElements()){
                String name = mrequestParameterNames.nextElement();
                requestParams.put(name,mrequest.getParameter(name));
            }
        }
        Set<String> keys = requestParams.keySet();
        Map<String,String> map = new HashMap<>();
        for (String key:keys) {
            map.put(key,requestParams.get(key)+"");
        }
        return map;
    }

    /**
     * 该方法的作用是清除键值Map中的空键值对
     * @param params 参数
     */
    public static void cleanParams(Map<String,String> params){
        if(params == null)return ;
        Map<String,String> temp = new HashMap<>();
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        for(Map.Entry<String, String> entry :entrySet){
            temp.put(entry.getKey(),entry.getValue().toString());
        }
    }
}
