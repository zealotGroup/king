package group.zealot.king.core.mybatis;

import group.zealot.king.core.db.mybatis.base.BaseDao;
import group.zealot.king.core.db.mybatis.system.dao.SysIdDao;
import org.junit.Test;


public class TestBaseDao extends BaseDao<Object, Integer> {

    @Override
    protected String getMapperNamesapce() {
        return SysIdDao.class.getName();
    }

    /**
     * 测试类继承BaseDao 后，能否正常获取getMapperNamesapce()
     */
    @Test
    public void getMapperNamesapceTest() {
        System.out.println(new TestBaseDao().getMapperNamesapce());
    }

}
