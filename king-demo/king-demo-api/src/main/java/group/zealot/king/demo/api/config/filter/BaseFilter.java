package group.zealot.king.demo.api.config.filter;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import group.zealot.king.core.shiro.exception.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public abstract class BaseFilter implements Filter {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException {
        if (filter(servletRequest, servletResponse)) {
            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (Exception e) {
                if (e.getCause() instanceof ShiroException) {
                    logger.warn(e.getMessage(), e);
                    throwException(ServiceCode.NEED_LOGIN, "请重新登录", (HttpServletResponse) servletResponse);
                } else {
                    logger.error(e.getMessage(), e);
                    throwException(ServiceCode.EXCEPTION_RUNNTIME, "运行时异常", (HttpServletResponse) servletResponse);
                }
            }
        }
    }

    protected abstract boolean verification(HttpServletRequest request, HttpServletResponse response) throws IOException;

    private boolean filter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            return verification(request, response);
        } else {
            try {
                logger.error("Error ServletRequest,Just allowed HttpServletRequest");
                servletResponse.getWriter().println("Error ServletRequest,Just allowed HttpServletRequest");
            } catch (IOException e) {
                logger.error("servletResponse.getWriter().println(\"Error ServletRequest,Just allowed HttpServletRequest\");", e);
            }
        }
        return false;
    }

    protected void throwException(ServiceCode serviceCode, String message, HttpServletResponse response) throws IOException {
        response.setStatus(serviceCode.code());
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", serviceCode.code());
        jsonObject.put("msg", message);
        jsonObject.putIfAbsent("time", LocalDateTime.now().toString());
        writer.println(jsonObject.toJSONString());
    }
}
