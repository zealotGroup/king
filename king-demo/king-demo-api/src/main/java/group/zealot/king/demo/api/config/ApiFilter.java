package group.zealot.king.demo.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = "/*", filterName = "apiFilter")
public class ApiFilter implements Filter {
    public static final String token_header = "token";

    private Logger logger = LoggerFactory.getLogger(getClass());
    private List<String> paths = new ArrayList<>();
    private List<String> methods = new ArrayList<>();
    private List<String> blackStr = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("init apiFilter");
        //白名单地址
        paths.add("/");
        //允许请求的方法
        methods.add("POST");
        methods.add("GET");
        //黑名单字符
        blackStr.add("INSERT");
        blackStr.add("UPDATE");
        blackStr.add("DELETE");
        blackStr.add("");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        if (filter(servletRequest, servletResponse)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    protected boolean filter(ServletRequest servletRequest, ServletResponse servletResponse) {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            try {
                return verification(request, response);
            } catch (IOException e) {
                logger.error("verification", e);
                return false;
            }
        } else {
            try {
                servletResponse.getWriter().println("Error ServletRequest,Just allowed HttpServletRequest");
            } catch (IOException e) {
                logger.error("servletResponse.getWriter().println(\"Error ServletRequest,Just allowed HttpServletRequest\");", e);
            }
            return false;
        }
    }

    protected boolean verification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();
        String method = request.getMethod();
        String remoteHost = request.getRemoteHost();
        logger.debug("servletPath[" + servletPath + "] method[" + method + "] remoteHost[" + remoteHost + "]");


        if (!methods.contains(method)) {
            response.getWriter().println("不允许的请求类型:" + method);
            response.setStatus(202);
            return false;
        } else {
            if (LoginUtil.getSysUser(request) != null) {
                LoginUtil.flushExp(request);
                return true;
            } else {
                if (!paths.contains(servletPath)) {
                    response.getWriter().println("未认证用户，不允许访问此路径");
                    response.setStatus(202);
                    return false;
                } else {
                    return true;
                }

            }
        }
    }
}
