package group.zealot.king.core.db.mybatis.base;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;


/**
 * 内置CURD操作，和分页查询
 *
 * @param <E> 表对应实体
 * @param <P> 表主键
 */
public class BaseDaoImpl<E, P extends Serializable> extends ZtSqlSessionDao implements BaseDao<E, P> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public E getById(P primaryKey) {
        return selectOne("getById", primaryKey);
    }

    public E get(E entity) {
        return selectOne("get", entity);
    }

    public int deleteById(P primaryKey) {
        return delete("deleteById", primaryKey);
    }

    public int delete(E entity) {
        return delete("delete", entity);
    }

    public int insert(E entity) {
        return insert("insert", entity);
    }

    public int insertBatchInn(List<E> list) {
        return insert("insertBatch", list);
    }

    public int insertBatch(List<E> list) {
        int count = 0;
        for (E entity : list) {
            count += insert(entity);
        }
        return count;
    }

    public int update(E entity) {
        return insert("update", entity);
    }

    public List<E> getList() {
        return getList(null);
    }

    public List<E> getList(E entity) {
        return selectList("getList", entity);
    }

    public List<E> getList(E entity, int offset, int limit) {
        return selectList("getList", entity, new RowBounds(offset, limit));
    }

    public int getListCount(E entity) {
        return selectOne("getListCount", entity);
    }

    public Page<E> pageQuery(PageRequest<E> pageRequest) {
        return pageQuery("getList", "getListCount", pageRequest);
    }

    /**
     * rapid分页查询
     *
     * @param statementName
     * @param countStatementName
     * @param pageRequest
     * @return
     */
    public Page<E> pageQuery(String statementName, String countStatementName, PageRequest<E> pageRequest) {
        int totalCount = selectOne(countStatementName, pageRequest.getFilters());
        if (totalCount <= 0) {
            return new Page<>();
        }
        Page<E> page = new Page<>(totalCount);

        int limit = pageRequest.getLimit() == -1 ? totalCount : pageRequest.getLimit();
        int offset = (pageRequest.getPage() - 1) * limit;

        if (offset <= totalCount) {
            List<E> list = selectList(
                    statementName, pageRequest.getFilters(), new RowBounds(offset, limit));
            page.setList(list);
        }
        return page;
    }
}
