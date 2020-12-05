package group.zealot.king.base.util;

import group.zealot.king.base.Constants;
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
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
    private static final int CONNECTION_REQUEST_TIMEOUT = EnvironmentUtil.get("timeout.http.connect-request", 30000);
    private static final int SOCKET_TIMEOUT = EnvironmentUtil.get("timeout.http.socket", 30000);
    private static final int CONNECT_TIMEOUT = EnvironmentUtil.get("timeout.http.connect", 30000);
    private static RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
            .setSocketTimeout(SOCKET_TIMEOUT)
            .setConnectTimeout(CONNECT_TIMEOUT)
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
        httpGet.setConfig(REQUEST_CONFIG);
        return sendHttp(httpGet);
    }


    public static String post(String url, List<BasicNameValuePair> parameters) {
        return post(url, parameters, null);
    }

    public static String post(String url, List<BasicNameValuePair> parameters, Map<String, String> header) {
        HttpPost httpPost = new HttpPost(getURI(url));
        HttpEntity entity = new UrlEncodedFormEntity(parameters, Constants.UTF8);
        httpPost.setEntity(entity);
        if (header != null) {
            for (String str : header.keySet()) {
                httpPost.addHeader(str, header.get(str));
            }
        }
        httpPost.setConfig(REQUEST_CONFIG);
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
        httpPost.setConfig(REQUEST_CONFIG);
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
        httpPut.setConfig(REQUEST_CONFIG);
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
        httpDelete.setConfig(REQUEST_CONFIG);
        return sendHttp(httpDelete);
    }

    public static String sendHttp(HttpUriRequest obj) {
        CloseableHttpResponse response = sendHttpRequest(obj);
        String res;
        try {
            res = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LOGGER.error("EntityUtils.toString(response.getEntity()) 异常", e);
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
            LOGGER.error("httpclient.execute(obj) 异常", e);
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
