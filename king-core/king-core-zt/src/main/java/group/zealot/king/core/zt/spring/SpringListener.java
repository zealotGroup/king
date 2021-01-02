package group.zealot.king.core.zt.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zealotTL
 * @date 2020-05-27 10:49
 */
public abstract class SpringListener {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected ClassScan classScan;

    abstract protected int getOrder();//数字越小越优先执行

    abstract protected void exec();
}
