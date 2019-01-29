package group.zealot.king.core.mybatis;

import org.junit.Test;


public class TestBaseDao extends BaseDao<Object, Integer> {

    /**
     * 测试类继承BaseDao 后，能否正常获取getMapperNamesapce()
     */
    @Test
    public void getMapperNamesapceTest() {
        System.out.println(new TestBaseDao().getMapperNamesapce());
    }
}
