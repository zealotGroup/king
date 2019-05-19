package group.zealot.king.core.zt.dubbo;

import org.apache.dubbo.config.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

//@Configuration
public class DubboConfig {
    @Autowired
    Environment environment;

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(environment.getProperty("dubbo.application.name"));
        applicationConfig.setVersion(environment.getProperty("dubbo.application.version"));
        return applicationConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(environment.getProperty("dubbo.protocol.name"));
        protocolConfig.setPort(environment.getProperty("dubbo.protocol.port", Integer.class));
        return protocolConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(environment.getProperty("dubbo.registry.address"));
        registryConfig.setProtocol(environment.getProperty("dubbo.registry.protocol"));
        registryConfig.setClient("curator");
        registryConfig.setCheck(environment.getProperty("dubbo.registry.check", Boolean.class));
        return registryConfig;
    }

    @Bean
    public ProviderConfig providerConfig(ProtocolConfig protocolConfig) {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setProtocol(protocolConfig);
        providerConfig.setTimeout(environment.getProperty("dubbo.provider.timeout", Integer.class));
        providerConfig.setRetries(environment.getProperty("dubbo.provider.retries", Integer.class));
        providerConfig.setLoadbalance(environment.getProperty("dubbo.provider.loadbalance"));
        return providerConfig;
    }

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(environment.getProperty("dubbo.consumer.check", Boolean.class));
        consumerConfig.setAsync(environment.getProperty("dubbo.consumer.async", Boolean.class));
        return consumerConfig;
    }

}
