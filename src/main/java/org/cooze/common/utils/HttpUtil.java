package org.cooze.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author cooze
 * @version 1.0.0
 * @desc
 * @date 2017/3/22
 */
public class HttpUtil {
    private HttpUtil() {
    }

    private static final String charset = "utf-8";
    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    /**
     *
     * @param uri
     * @param params
     * @return
     */
    public static String getRequest(String uri,Map<String,String> params){
        uri = trimUri(uri)+ getParams(params);
        HttpGet httpGet = new HttpGet(uri);
        try(CloseableHttpResponse response = httpclient.execute(httpGet)) {
            String result="";
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(response.getEntity(),charset);

                response.close();
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param uri
     * @param map
     * @return
     * @throws Exception
     */
    public static String postRequest(String uri,Map<String,Object> map ) throws Exception {
        HttpPost httpPost = new HttpPost(trimUri(uri));

        StringEntity strentity = new StringEntity(JSON.toJSONString(map),charset);
        strentity.setContentEncoding(charset);
        strentity.setContentType("application/json");
        httpPost.setEntity(strentity);
        HttpResponse response = httpclient.execute(httpPost);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            HttpEntity resEntity = response.getEntity();
            if(resEntity != null){
                return EntityUtils.toString(resEntity,charset);
            }
        }
        return null;
    }

    /**
     *
     * @param uri
     * @param params
     * @param files
     * @return
     * @throws Exception
     */
    public static String postRequest(String uri, Map<String,Object> params , Map<String,File> files)throws Exception {
        HttpPost httpPost = new HttpPost(trimUri(uri));
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();

        if(files!=null && !files.isEmpty()){
            files.forEach((name,file)->multipartEntityBuilder.addBinaryBody(name,file));
        }

        if(params!=null && !params.isEmpty()){
            params.forEach((name,value)->multipartEntityBuilder.addTextBody(name,value+""));
        }
        httpPost.setEntity(multipartEntityBuilder.build());
        HttpResponse response = httpclient.execute(httpPost);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            HttpEntity resEntity = response.getEntity();
            if(resEntity != null){
                return EntityUtils.toString(resEntity,charset);
            }
        }
        return null;
    }

    /**
     *
     * @param params
     * @return
     */
    private static String getParams(Map<String,String> params){
        if(params==null||params.isEmpty())return null;
        StringBuffer sb = new StringBuffer();
        params.forEach((k,v)->sb.append(k+"="+v+"&"));
        String ps = sb.toString();
        ps = ps.substring(0,ps.length()-1);
        return "/?" + ps;
    }

    private static String trimUri(String uri){
        uri = uri.trim();
        StringBuffer sb = new StringBuffer(uri.trim());
        while(uri.endsWith("/")){
            uri = uri.substring(0,uri.length()-1);
        }
        return uri;
    }
}
