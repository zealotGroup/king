package group.zealot.king.core.db.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author zealot
 * @date 2020/2/7 17:14
 */
@Configuration
@EnableJpaRepositories(basePackages = "group.zealot.king.core.db.jpa")
@EntityScan(basePackages = "group.zealot.king.core.zt.entity")
public class JpaConfig {
}
