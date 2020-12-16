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

    public static int getInt(String s) {
        return get(s, int.class);
    }

    public static int getInt(String s, int value) {
        return get(s, int.class, value);
    }

    public static long getLong(String s) {
        return get(s, long.class);
    }

    public static long getLong(String s, long value) {
        return get(s, long.class, value);
    }

    public static String getString(String s) {
        return get(s, String.class);
    }

    public static String getString(String s, String value) {
        return get(s, String.class, value);
    }


    public static String get(String s) {
        return get(s, String.class);
    }

    public static <T> T get(String s, Class<T> targetType) {
        if (environment.containsProperty(s)) {
            return environment.getProperty(s, targetType);
        } else {
            return null;
        }
    }

    public static <T> T get(String s, Class<T> targetType, T defaultValue) {
        if (environment.containsProperty(s)) {
            return environment.getProperty(s, targetType, defaultValue);
        } else {
            return null;
        }
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
