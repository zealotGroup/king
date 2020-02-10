package group.zealot.king.test;

import group.zealot.king.core.db.jpa.Repositorys;
import group.zealot.king.demo.api.Run;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author zealot
 * @date 2019/11/16 12:05
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = {Run.class})
public class MainTest {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ApplicationContext context;

    @Test
    public void studyJpa() {
        Object o = context.getBeansOfType(JpaRepository.class);
        System.out.println(Repositorys.sysUserRepository == null ? "null" : " not null");
    }

}
