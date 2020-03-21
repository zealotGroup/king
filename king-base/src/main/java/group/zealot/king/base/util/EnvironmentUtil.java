package group.zealot.king.base.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author zealot
 * @date 2020/2/29 17:46
 */
@Component
public class EnvironmentUtil {

    private static Environment environment;

    public static String get(String s) {
        return get(s, String.class);
    }

    public static <T> T get(String s, Class<T> clazz, T value) {
        return environment.getProperty(s, clazz, value);
    }

    public static <T> T get(String s, Class<T> clazz) {
        return environment.getProperty(s, clazz);
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        EnvironmentUtil.environment = environment;
    }
}
