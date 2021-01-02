package group.zealot.king.core.zt.spring;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zealotTL
 * @date 2020-05-27 10:47
 */
@Component
public class AfterApplicationReady implements ApplicationListener<ApplicationReadyEvent> {
    public static AtomicBoolean IS_FINISHED = new AtomicBoolean(false);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        Map<String, SpringListener> map = applicationContext.getBeansOfType(SpringListener.class);
        List<SpringListener> list = new ArrayList<>();
        map.forEach((key, value) -> list.add(value));
        //list 按order排序
        list.sort(Comparator.comparingInt(SpringListener::getOrder));
        //执行方法
        list.forEach(SpringListener::exec);
        IS_FINISHED.set(true);
    }
}
