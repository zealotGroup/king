package group.zealot.king.base.util;

import group.zealot.king.base.exception.BaseRuntimeException;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesUtil {
    private static ConcurrentHashMap<String, Properties> CACHE = new ConcurrentHashMap<>();

    public static Properties load(Class<?> clazz, String name) {
        Properties properties = new Properties();
        try {
            properties.load(clazz.getClassLoader().getResourceAsStream(name));
        } catch (IOException e) {
            throw new BaseRuntimeException(e);
        }
        return properties;
    }

    public static void loadCache(Class<?> clazz, String name) {
        CACHE.put(name, load(clazz, name));
    }

    public static <T> T getProperty(String name, Class<T> clazz, String cacheName) {
        return getProperty(name, clazz, getCache(cacheName));
    }

    public static <T> T getProperty(String name, Class<T> clazz, Properties properties) {
        String str = properties.getProperty(name);
        if (str == null) {
            return null;
        }
        if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
            return (T) new Integer(str);
        } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
            return (T) new Long(str);
        } else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
            return (T) new Boolean(str);
        } else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
            return (T) new Short(str);
        } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
            return (T) new Float(str);
        } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
            return (T) new Double(str);
        } else if (clazz.equals(String.class)) {
            return (T) str;
        } else {
            return null;
        }
    }

    public static Properties getCache(String name) {
        return CACHE.get(name);
    }

}
