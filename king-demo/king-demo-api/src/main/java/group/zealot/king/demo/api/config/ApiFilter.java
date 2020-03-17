package group.zealot.king.demo.api.config;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.ServiceCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = "/*", filterName = "apiFilter")
public class ApiFilter implements Filter {
    public static final String token_header = "auth-token";
    public static final ThreadLocal<String> tokenLocal = new ThreadLocal<>();

    private Logger logger = LoggerFactory.getLogger(getClass());
    private List<String> whitePaths = new ArrayList<>();
    private List<String> methods = new ArrayList<>();
    private List<String> blackStr = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("init apiFilter");
        //白名单地址
        whitePaths.add("/");
        whitePaths.add("/login");
        whitePaths.add("/admin/picture/getPicture");
        // --微信--
        whitePaths.add("^/oauth/.+");
        whitePaths.add("/checkToken");

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
            response.setHeader("Access-Control-Allow-Origin", "*");
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
        tokenLocal.set(request.getHeader(token_header));

        if (!methods.contains(method)) {
            doBaseRuntimeException(ServiceCode.REQUEST_METHOD_NOT_ALLOWED, "不允许的请求类型:" + method, response);
            return false;
        } else {
            if (LoginUtil.isLogin()) {
                LoginUtil.flushExp();
                return true;
            } else {
                if (!match(whitePaths, servletPath)) {
                    doBaseRuntimeException(ServiceCode.NEED_LOGIN, "不允许访问此路径:" + servletPath, response);
                    return false;
                } else {
                    return true;
                }

            }
        }
    }

    private void doBaseRuntimeException(ServiceCode serviceCode, String message, HttpServletResponse response) throws IOException {
        response.setStatus(serviceCode.code());
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", serviceCode.code());
        jsonObject.put("msg", message);
        jsonObject.putIfAbsent("time", LocalDateTime.now().toString());
        writer.println(jsonObject.toJSONString());
    }

    private boolean match(List<String> regexList, String str) {
        for (String regex : regexList) {
            if (Pattern.matches(regex, str)) {
                return true;
            }
        }
        return false;
    }
}
