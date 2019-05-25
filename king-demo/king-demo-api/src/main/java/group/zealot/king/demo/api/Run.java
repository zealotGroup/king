package group.zealot.king.demo.api;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import group.zealot.king.core.shiro.realm.ShiroService;
import group.zealot.king.core.zt.dubbo.DubboUtil;
import group.zealot.king.core.zt.spring.SpringUtil;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(scanBasePackages = "group.zealot.king")
@ServletComponentScan
@EnableDubbo
public class Run {
    private static Logger logger = LoggerFactory.getLogger(Run.class);

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);    //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
        return new HttpMessageConverters(converter);
    }

    /**
     * java -jar api.jar --server.port=8080
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringUtil.setApplicationContext(SpringApplication.run(Run.class, args));
//        initDubboService(SpringUtil.getApplicationContext());
    }

    /**
     * 分布式 使用dubbo 注册、获取 服务时调用
     */
    private static void initDubboService(ApplicationContext applicationContext) {
        logger.info("启动 initDubboService");
        applicationContext.getBean(DubboUtil.class).registReference();
        //重新导入ShiroService 依赖【有依赖是dubbo service】
        applicationContext.getBean(SpringUtil.class).autowireBean(applicationContext.getBean(ShiroService.class));
    }
}
