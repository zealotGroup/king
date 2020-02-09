package group.zealot.king.core.db.mybatis.core.base;


import group.zealot.king.base.exception.BaseRuntimeException;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;


/**
 * 精简调用getSqlSession()方法，和增加默认配置getMapperNamesapce
 */
public class ZtSqlSessionDao extends SqlSessionDaoSupport {

    protected String getMapperNamesapce() {
        return this.getClass().getName();
    }

    /**
     * 两种形式
     * String var1
     * String var1, Object var2
     *
     * @param id  sql语句id，不包含namespace（采用默认getMapperNamesapce()）
     * @param obj
     */
    protected <E> E selectOne(String id, Object... obj) {
        if (obj.length == 0) {
            return getSqlSession().selectOne(getMapperNamesapce() + "." + id);
        } else if (obj.length == 1) {
            return getSqlSession().selectOne(getMapperNamesapce() + "." + id, obj[0]);
        } else {
            throw new BaseRuntimeException("可变长参数obj异常");
        }
    }

    /**
     * 三种形式
     * String var1
     * String var1, Object var2
     * String var1, Object var2, RowBounds var3
     *
     * @param id  sql语句id，不包含namespace（采用默认getMapperNamesapce()）
     * @param obj
     */
    protected <E> List<E> selectList(String id, Object... obj) {
        if (obj.length == 0) {
            return getSqlSession().selectList(getMapperNamesapce() + "." + id);
        } else if (obj.length == 1) {
            return getSqlSession().selectList(getMapperNamesapce() + "." + id, obj[0]);
        } else if (obj.length == 2 && obj[1] != null && obj[1] instanceof RowBounds) {
            return getSqlSession().selectList(getMapperNamesapce() + "." + id, obj[0], (RowBounds) obj[1]);
        } else {
            throw new BaseRuntimeException("可变长参数obj异常");
        }
    }

    /**
     * @param id  sql语句id，不包含namespace（采用默认getMapperNamesapce()）
     * @param obj
     */
    protected int insert(String id, Object obj) {
        return getSqlSession().insert(getMapperNamesapce() + "." + id, obj);
    }

    /**
     * @param id  sql语句id，不包含namespace（采用默认getMapperNamesapce()）
     * @param obj
     */
    protected int update(String id, Object obj) {
        return getSqlSession().update(getMapperNamesapce() + "." + id, obj);
    }

    /**
     * @param id  sql语句id，不包含namespace（采用默认getMapperNamesapce()）
     * @param obj
     */
    protected int delete(String id, Object obj) {
        return getSqlSession().delete(getMapperNamesapce() + "." + id, obj);
    }
}
