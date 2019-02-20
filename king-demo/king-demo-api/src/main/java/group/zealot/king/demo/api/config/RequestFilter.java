package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.zt.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器
 * 1、校验是否需要认证
 * 2、校验是否需要requestId，防重复提交
 */
@WebFilter(filterName = "RequestFilter", urlPatterns = "/*")
public class RequestFilter implements Filter {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String REQUEST_ID_NAME = "requestId";
    public static final String TOKEN_NAME = "token";
    public static final String REQUEST_ID_KEY_PREFIX = "api:requestId:";
    public static final String TOKEN_KEY_PREFIX = "api:token:";
    public static final long REQUEST_ID_TIMEOUT = 30 * 60;//30min

    private final int NOT_NEED_LOGIN = 1;
    private final int NEED_LOGIN = 2;
    private final int NEED_REQUEST_ID = 3;


    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        logger.info("请求URI：" + requestURI);
        switch (choose(requestURI)) {
            case NOT_NEED_LOGIN:
                filterChain.doFilter(request, servletResponse);
                break;
            case NEED_LOGIN:
                if (checkToken(request)) {
                    filterChain.doFilter(request, servletResponse);
                } else {
                    ResultJson resultJson = ResultJsonFactory.create();
                    resultJson.setCode(ServiceCode.NEED_LOGIN.code());
                    resultJson.setMsg("请求的URI" + request.getRequestURI() + "需要先认证");
                    servletResponse.setCharacterEncoding("UTF-8");
                    servletResponse.setContentType("application/json;charset=UTF-8");
                    servletResponse.getWriter().write(JSONObject.toJSONString(resultJson.result()));
                }
                break;
            case NEED_REQUEST_ID:
                if (checkRequestId(request.getHeader(REQUEST_ID_NAME))) {
                    filterChain.doFilter(request, servletResponse);
                } else {
                    ResultJson resultJson = ResultJsonFactory.create();
                    resultJson.setCode(ServiceCode.NEED_REQUEST_ID.code());
                    resultJson.setMsg("请求的URI" + request.getRequestURI() + "需要已授权的 " + REQUEST_ID_NAME);
                    servletResponse.setCharacterEncoding("UTF-8");
                    servletResponse.setContentType("application/json;charset=UTF-8");
                    servletResponse.getWriter().write(JSONObject.toJSONString(resultJson.result()));
                }
                break;
            default:
                logger.error("未分类URI:" + requestURI);
                break;
        }
    }

    private int choose(String requestURI) {
        if ("/".equals(requestURI) || "/requestId/createAndGet".equals(requestURI)) {
            return NOT_NEED_LOGIN;
        } else if ("/login/login".equals(requestURI) || requestURI.contains("add") || requestURI.contains("del") || requestURI.contains("update")) {
            return NEED_REQUEST_ID;
        } else {
            return NEED_LOGIN;
        }
    }

    private boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtil.isEmpty(token)) {
            return false;
        } else {
            ValueOperations<String, String> valueOperations = redisUtil.valueOperations();
            token = valueOperations.get(TOKEN_KEY_PREFIX + token);
            if (StringUtil.isEmpty(token)) {
                return false;
            } else {
                request.setAttribute("token", token);
                return true;
            }
        }
    }

    private boolean checkRequestId(String requestId) {
        return redisUtil.valueOperations().get(REQUEST_ID_KEY_PREFIX + requestId) != null;
    }

}
