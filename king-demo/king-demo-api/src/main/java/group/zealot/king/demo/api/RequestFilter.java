package group.zealot.king.demo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器
 */
@WebFilter(filterName = "RequestFilter", urlPatterns = "/*")
public class RequestFilter implements Filter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        logger.info("请求URI：" + requestURI);
        if (checkRequestId(request.getParameter("requestId"))) {
            filterChain.doFilter(request, servletResponse);
        } else {
            ResultJson resultJson = ResultJsonFactory.create();
            resultJson.setCode(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value());
            resultJson.setMsg("请求的URI" + request.getRequestURI() + "需要已授权的requestId");
            servletResponse.getWriter().write(resultJson.toJSONString());
        }
    }

    protected boolean checkRequestId(String requestId) {
        // TODO: 2019/2/17 通过redis缓存requestId ，防重复+防攻击
        return true;
    }
}
