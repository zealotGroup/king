package group.zealot.king.core.mybatis;


import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * 内置CURD操作，和分页查询
 *
 * @param <E> 表对应实体
 * @param <P> 表主键
 */
public abstract class BaseDao<E, P extends Serializable> extends SqlSessionDaoSupport {

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    protected abstract String getMapperNamesapce();

    public E getById(P primaryKey) {
        return getSqlSession().selectOne(getMapperNamesapce() + ".getById", primaryKey);
    }

    public E get(E e) {
        return getSqlSession().selectOne(getMapperNamesapce() + ".get", e);
    }

    public void delete(P primaryKey) {
        getSqlSession().delete(getMapperNamesapce() + ".deleteById", primaryKey);
    }

    public void delete(E entity) {
        getSqlSession().delete(getMapperNamesapce() + ".delete", entity);
    }

    public void save(E entity) {
        getSqlSession().insert(getMapperNamesapce() + ".insert", entity);
    }

    public void saveBatchInn(List<E> list) {
        getSqlSession().insert(getMapperNamesapce() + ".insertBatch", list);
    }

    public void saveBatch(List<E> list) {
        for (E entity : list) {
            getSqlSession().insert(getMapperNamesapce() + ".insert", entity);
        }
    }

    public void update(E entity) {
        getSqlSession().update(getMapperNamesapce() + ".update", entity);
    }

    public List<E> getList(E entity) {
        return getSqlSession().selectList(getMapperNamesapce() + ".getList", entity);
    }

    public Page<E> pageQuery(PageRequest<E> pageRequest) {
        return pageQuery(".getList", ".getListCount", pageRequest);
    }

    public int getListCount(E entity) {
        return getSqlSession().selectOne(getMapperNamesapce() + ".getListCount", entity);
    }

    public List<E> getList(E entity, int offset, int limit) {
        return getSqlSession().selectList(getMapperNamesapce() + ".getList", entity, new RowBounds(offset, limit));
    }

    /**
     * rapid分页查询
     *
     * @param statementName
     * @param countStatementName
     * @param pageRequest
     * @return
     */
    protected Page<E> pageQuery(String statementName, String countStatementName,
            PageRequest<E> pageRequest) {
        int totalCount = getSqlSession().selectOne(getMapperNamesapce() + countStatementName, pageRequest.getFilters());
        if (totalCount <= 0) {
            return new Page<>(pageRequest, 0);
        }
        Page<E> page = new Page<>(pageRequest, totalCount);
        int limit = pageRequest.getIDisplayLength() == -1 ? totalCount : pageRequest.getIDisplayLength();
        List<E> list = getSqlSession().selectList(getMapperNamesapce() +
                statementName, pageRequest.getFilters(), new RowBounds(pageRequest.getIDisplayStart(), limit));
        page.setAaData(list);
        return page;
    }
}
