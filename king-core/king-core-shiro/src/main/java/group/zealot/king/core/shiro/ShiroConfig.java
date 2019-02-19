package group.zealot.king.core.shiro;

import group.zealot.king.core.shiro.cache.ShiroRedisCache;
import group.zealot.king.core.shiro.realm.ShiroRealm;
import group.zealot.king.core.zt.redis.RedisUtil;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.*;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.LinkedHashMap;
import javax.servlet.Filter;

@Configuration
public class ShiroConfig {
    @Bean
    public CacheManager cacheManager(RedisUtil redisUtil) {
        return new AbstractCacheManager() {
            @Override
            protected Cache createCache(String cacheName) throws CacheException {
                return new ShiroRedisCache(redisUtil, cacheName);
            }
        };
    }

    @Bean
    public SessionFactory sessionFactory() {
        SessionFactory sessionFactory = new SimpleSessionFactory();
        return sessionFactory;
    }

    @Bean
    public SessionManager sessionManager(@Lazy SessionValidationScheduler sessionValidationScheduler, SessionFactory sessionFactory) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Cookie cookie = new SimpleCookie("ShiroSessionId");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 60 * 1000);//30min 过期 -1关闭浏览器过期
        sessionManager.setSessionIdCookie(cookie);
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);//30min 过期
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
        sessionManager.setSessionFactory(sessionFactory);
        return sessionManager;
    }

    @Bean
    public SessionValidationScheduler sessionValidationScheduler(@Lazy SessionManager sessionManager) {
        ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
        sessionValidationScheduler.setInterval(30 * 60 * 1000);
        sessionValidationScheduler.setSessionManager((ValidatingSessionManager) sessionManager);
        return sessionValidationScheduler;
    }

    //配置核心安全事务管理器
    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm, SessionManager sessionManager, CacheManager cacheManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(shiroRealm);
        defaultWebSecurityManager.setSessionManager(sessionManager);
        defaultWebSecurityManager.setCacheManager(cacheManager);
        return defaultWebSecurityManager;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    /**
     * anon:所有url都都可以匿名访问
     * authc: 需要认证才能进行访问
     * user:配置记住我或认证通过可以访问
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc", new FormAuthenticationFilter());
        bean.setFilters(filters);
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/login/login");
        bean.setSuccessUrl("/");

        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login/login", "anon"); //表示可以匿名访问
        filterChainDefinitionMap.put("/login/logout", "logout");
        filterChainDefinitionMap.put("/", "user");
        filterChainDefinitionMap.put("/**", "user");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }
}
