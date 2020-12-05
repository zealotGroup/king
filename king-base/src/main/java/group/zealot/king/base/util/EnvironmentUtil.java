package group.zealot.king.base.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zealot
 * @date 2020/2/29 17:46
 */
@Component
public class EnvironmentUtil {

    private static Environment environment;

    public static <T> T get(String s) {
        return get(s, null);
    }

    public static <T> T get(String s, T defaultValue) {
        T t;
        if (environment.containsProperty(s)) {
            t = (T) environment.getProperty(s);
        } else {
            t = defaultValue;
        }
        return t;
    }

    public static List<String> getList(String s) {
        int i = 0;
        String str;
        List<String> list = new ArrayList<>();
        while (!StringUtil.isEmpty(str = environment.getProperty(s + "[" + i + "]"))) {
            list.add(str);
            i++;
        }
        return list;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        EnvironmentUtil.environment = environment;
    }
}
