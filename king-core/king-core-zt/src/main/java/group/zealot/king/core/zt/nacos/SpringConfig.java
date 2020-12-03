package group.zealot.king.core.zt.nacos;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * @author zealot
 * @date 2020/4/17 21:58
 */
@Configuration
@NacosPropertySource(dataId = "king", autoRefreshed = true, type = ConfigType.YAML)
public class SpringConfig {
}
