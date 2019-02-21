package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.zt.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;

import static group.zealot.king.demo.api.config.ResultFulSession.SESSIONID_NAME;
import static group.zealot.king.demo.api.controller.RequestIdController.REDIS_PREFIX_REQUEST_ID;
import static group.zealot.king.demo.api.controller.RequestIdController.REQUEST_ID_NAME;

/**
 * 过滤器
 * 1、校验是否需要认证
 * 2、校验是否需要requestId，防重复提交
 */
@WebFilter(filterName = "RequestFilter", urlPatterns = "/*")
public class RequestFilter implements Filter {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final int NOT_NEED_LOGIN = 1;
    private final int NEED_LOGIN = 2;
    private final int NEED_REQUEST_ID = 3;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ResultFulSession resultFulSession;

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
                if (checkSession(request)) {
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
                if (checkRequestId(request.getParameter(REQUEST_ID_NAME))) {
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
        if ("/".equals(requestURI)
                || "/requestId/createAndGet".equals(requestURI)
                || "/login/login".equals(requestURI)) {
            return NOT_NEED_LOGIN;
        } /*else if ("/login/login".equals(requestURI)
                || requestURI.contains("add")
                || requestURI.contains("del") ||
                requestURI.contains("update")) {
            return NEED_REQUEST_ID;
        } */else {
            return NEED_LOGIN;
        }
    }


    /**
     * 验证session，成功并续约
     */
    private boolean checkSession(HttpServletRequest request) {
        String sessionId = request.getHeader(SESSIONID_NAME);
        if (StringUtil.isEmpty(sessionId)) {
            return false;
        } else {
            boolean fg = resultFulSession.getSessionSysUser(sessionId) != null;
            if (fg) {
                //续约
                try {
                    resultFulSession.updateSessionSysUserTimeout(sessionId);
                } catch (Exception e) {
                    logger.error("sessionId:" + sessionId + "续约异常", e);
                }
            }
            return fg;
        }
    }

    /**
     * 验证requestId ，成功并删除
     */
    private boolean checkRequestId(String requestId) {
        Object value = redisUtil.valueOperations().get(REDIS_PREFIX_REQUEST_ID + requestId);
        if (value instanceof Boolean) {
            if ((Boolean) value) {
                try {
                    Boolean fg = redisUtil.valueOperations().setIfAbsent(REDIS_PREFIX_REQUEST_ID + requestId, false, Duration.ZERO);
                    if (!fg) {
                        throw new BaseRuntimeException("redis setIfAbsent " + REDIS_PREFIX_REQUEST_ID + requestId + " 异常");
                    }
                } catch (Exception e) {
                    logger.error("requestId:" + requestId + "删除异常", e);
                }
                return true;
            }
        }
        return false;
    }

}
