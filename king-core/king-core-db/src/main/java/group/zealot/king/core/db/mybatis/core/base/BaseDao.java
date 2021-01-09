package group.zealot.king.core.db.mybatis.core.base;

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
public class BaseDao<E, P extends Serializable> extends ZtSqlSessionDao implements BaseMapper<E, P> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public E getById(P p) {
        return selectOne("getById", p);
    }

    public E get(E e) {
        return selectOne("get", e);
    }

    public void deleteById(P p) {
        delete("deleteById", p);
    }

    public void delete(E e) {
        delete("delete", e);
    }

    public E insert(E e) {
        insert("insert", e);
        return e;
    }

    public E update(E e) {
        insert("update", e);
        return e;
    }

    public void insertBatchInn(List<E> list) {
        insert("insertBatch", list);
    }

    public void insertBatch(List<E> list) {
        for (E entity : list) {
            insert(entity);
        }
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
