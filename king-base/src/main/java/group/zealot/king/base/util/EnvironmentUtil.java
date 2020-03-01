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

    public static <T> T get(String s, Class<T> clazz) {
        return environment.getProperty(s, clazz);
    }

    public static String get(String s) {
        return environment.getProperty(s, String.class);
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        EnvironmentUtil.environment = environment;
    }
}
