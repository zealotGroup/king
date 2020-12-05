package group.zealot.king.core.shiro;

import group.zealot.king.base.util.EnvironmentUtil;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.shiro.cache.ShiroRedisCache;
import group.zealot.king.core.shiro.filter.AuthFilter;
import group.zealot.king.core.shiro.realms.BaseShiroRealm;
import group.zealot.king.core.shiro.sessionManager.ShiroSessionManager;
import group.zealot.king.core.zt.redis.RedisUtil;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.*;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.Filter;

@Configuration
public class ShiroConfig {
    public static final Duration timeout = Duration.ofMinutes(1L);//30min
    public static final Long sessionTimeout = timeout.getSeconds() * 1000;
    public static final Long sessionInterval = timeout.getSeconds() * 1000;//检查session过期的调度间隔
    public static final int cookieTimeout = (int) timeout.getSeconds();
    public static final String cookieName = "ShiroSessionId";

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
    public Cookie cookie() {
        SimpleCookie simpleCookie = new SimpleCookie(cookieName);
        simpleCookie.setMaxAge(cookieTimeout);
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new SimpleSessionFactory();
    }

    @Bean
    public SessionValidationScheduler sessionValidationScheduler(ShiroSessionManager shiroSessionManager) {
        ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
        sessionValidationScheduler.setInterval(sessionInterval);
        sessionValidationScheduler.setSessionManager(shiroSessionManager);
        return sessionValidationScheduler;
    }

    //配置核心安全事务管理器
    @Bean
    public SecurityManager securityManager(Authenticator authenticator, ShiroSessionManager shiroSessionManager, CacheManager cacheManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setAuthenticator(authenticator);
        defaultWebSecurityManager.setSessionManager(shiroSessionManager);
        defaultWebSecurityManager.setCacheManager(cacheManager);
        return defaultWebSecurityManager;
    }

    @Bean
    public Authenticator authenticator(ConfigurableApplicationContext context) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        ArrayList<Realm> realms = new ArrayList<>();
        context.getBeansOfType(BaseShiroRealm.class).forEach((key, baseShiroRealm) -> realms.add(baseShiroRealm));
        authenticator.setRealms(realms);
        return authenticator;
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
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager, Environment environment) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc", new AuthFilter());
        bean.setFilters(filters);
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/oauth/login");
        bean.setSuccessUrl("/");

        //配置访问权限 anon匿名
        int i = 0;
        List<String> shiroList = new ArrayList<>();
        while (!StringUtil.isEmpty(environment.getProperty("shiro.filter" + "[" + i + "]"))) {
            shiroList.add(environment.getProperty("shiro.filter" + "[" + i + "]"));
            i++;
        }

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        for (String str : shiroList) {
            String[] strs = str.split(",");
            filterChainDefinitionMap.put(strs[0], strs[1]);
        }
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }
}
