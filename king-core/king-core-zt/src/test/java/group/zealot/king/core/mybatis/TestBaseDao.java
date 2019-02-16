package group.zealot.king.core.mybatis;

import group.zealot.king.core.zt.mybatis.base.BaseDao;
import group.zealot.king.core.zt.mybatis.system.dao.SysIdDao;
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
