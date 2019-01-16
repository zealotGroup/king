package group.zealot.king.base.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final List<BasicHeader> basicHeaders = new LinkedList<>();

    static {
        basicHeaders.add(new BasicHeader("Content-Type", "text/xml"));
        basicHeaders.add(new BasicHeader("Cache-Control", "no-cache"));
    }

    public static String GET(String url) throws IOException {
        return GET(url, getBasicHeader());
    }

    private static String GET(String url, List<BasicHeader> basicHeaders) throws IOException {
        HttpPost httpPost = initHttpPost(url);
        for (BasicHeader basicHeader : basicHeaders) {
            httpPost.addHeader(basicHeader);
        }
        return execute(httpPost);
    }

    public static String POST(String url, String json) throws IOException {
        return POST(url, json, getBasicHeader());
    }

    private static String POST(String url, String json, List<BasicHeader> basicHeaders) throws IOException {
        HttpPost httpPost = initHttpPost(url);
        httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        for (BasicHeader basicHeader : basicHeaders) {
            httpPost.addHeader(basicHeader);
        }
        return execute(httpPost);
    }

    private static List<BasicHeader> getBasicHeader() {
        return basicHeaders;
    }

    private static HttpGet initHttpGet(String url) {
        return new HttpGet(url);
    }

    private static HttpPost initHttpPost(String url) {
        return new HttpPost(url);
    }

    private static String execute(HttpUriRequest request) throws IOException {
        CloseableHttpResponse response = null;
        String returnStr = null;
        try {
            logger.debug("请求URI:" + request.getURI());
            logger.debug("请求方法:" + request.getMethod());
            logger.debug("请求头:" + JSONObject.toJSONString(request.getAllHeaders()));
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                logger.debug("请求内容:" + JSONObject.toJSONString(((HttpPost) request).getEntity()));
            }
            response = HttpClients.createDefault().execute(request);
            HttpEntity entity = response.getEntity();
            returnStr = EntityUtils.toString(entity);
            StatusLine statusLine = response.getStatusLine();
            logger.debug("请求返回状态码:" + statusLine.getStatusCode());
            logger.debug("请求返回Strnig:" + returnStr);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("response.close()", e);
                }
            }
        }
        return returnStr;
    }
}
