package group.zealot.king.base.util;

import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static int connectionRequestTimeout = EnvironmentUtil.get("httpUtil.connectionRequestTimeout", int.class, 30000);
    private static int socketTimeout = EnvironmentUtil.get("httpUtil.socketTimeout", int.class, 30000);
    private static int connectTimeout = EnvironmentUtil.get("httpUtil.connectTimeout", int.class, 30000);
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(connectionRequestTimeout)
            .setSocketTimeout(socketTimeout)
            .setConnectTimeout(connectTimeout)
            .build();

    public static String get(String url) {
        return get(url, null);
    }

    public static String get(String url, Map<String, String> header) {
        HttpGet httpGet = new HttpGet(getURI(url));
        if (header != null) {
            for (String str : header.keySet()) {
                httpGet.addHeader(str, header.get(str));
            }
        }
        httpGet.setConfig(requestConfig);
        return sendHttp(httpGet);
    }


    public static String post(String url, List<BasicNameValuePair> parameters) {
        return post(url, parameters, null);
    }

    public static String post(String url, List<BasicNameValuePair> parameters, Map<String, String> header) {
        HttpPost httpPost = new HttpPost(getURI(url));
        HttpEntity entity = new UrlEncodedFormEntity(parameters, Charset.forName("UTF-8"));
        httpPost.setEntity(entity);
        if (header != null) {
            for (String str : header.keySet()) {
                httpPost.addHeader(str, header.get(str));
            }
        }
        httpPost.setConfig(requestConfig);
        return sendHttp(httpPost);
    }

    public static String post(String url, String json) {
        return post(url, json, null);
    }

    public static String post(String url, String json, Map<String, String> header) {
        HttpPost httpPost = new HttpPost(getURI(url));
        HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);
        if (header != null) {
            for (String str : header.keySet()) {
                httpPost.addHeader(str, header.get(str));
            }
        }
        httpPost.setConfig(requestConfig);
        return sendHttp(httpPost);
    }

    public static String put(String url) {
        return put(url, null);
    }

    public static String put(String url, Map<String, String> header) {
        HttpPut httpPut = new HttpPut(getURI(url));
        if (header != null) {
            for (String str : header.keySet()) {
                httpPut.addHeader(str, header.get(str));
            }
        }
        httpPut.setConfig(requestConfig);
        return sendHttp(httpPut);
    }

    public static String delete(String url) {
        return delete(url, null);
    }

    public static String delete(String url, Map<String, String> header) {
        HttpDelete httpDelete = new HttpDelete(getURI(url));
        if (header != null) {
            for (String str : header.keySet()) {
                httpDelete.addHeader(str, header.get(str));
            }
        }
        httpDelete.setConfig(requestConfig);
        return sendHttp(httpDelete);
    }

    public static String sendHttp(HttpUriRequest obj) {
        CloseableHttpResponse response = sendHttpRequest(obj);
        String res;
        try {
            res = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            logger.error("EntityUtils.toString(response.getEntity()) 异常", e);
            throw new BaseRuntimeException(ServiceCode.REQUEST_HTTP_ERROR);
        }
        return res;
    }

    public static CloseableHttpResponse sendHttpRequest(HttpUriRequest obj) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response;
        try {
            response = httpclient.execute(obj);
        } catch (IOException e) {
            logger.error("httpclient.execute(obj) 异常", e);
            throw new BaseRuntimeException(ServiceCode.REQUEST_HTTP_ERROR);
        }
        return response;
    }

    private static String getURI(String url) {
        if (url.trim().startsWith("http")) {
            return url.trim();
        } else {
            return "http://" + url.trim();
        }
    }
}
