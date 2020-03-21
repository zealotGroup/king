package group.zealot.king.demo.api.config.filter;

import group.zealot.king.base.ServiceCode;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = "/*", filterName = "apiFilter")
public class ApiFilter extends BaseFilter {
    private final List<String> whiteMethods = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("init apiFilter");
        //允许请求的方法
        whiteMethods.add("POST");
        whiteMethods.add("GET");
    }

    @Override
    protected boolean verification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String method = request.getMethod();
        if (!match(whiteMethods, method)) {
            throwException(ServiceCode.EXCEPTION, "方法不允许", response);
            return false;
        }
        return true;
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
