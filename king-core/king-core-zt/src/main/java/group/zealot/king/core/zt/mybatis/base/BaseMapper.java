package group.zealot.king.core.zt.mybatis.base;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<E, P extends Serializable> {
    E getById(P primaryKey);

    E get(E e);

    int deleteById(P primaryKey);

    int delete(E entity);

    int insert(E entity);

    int insertBatchInn(List<E> list);

    int insertBatch(List<E> list);

    int update(E entity);

    List<E> getList(E entity);

    Page<E> pageQuery(PageRequest<E> pageRequest);

    int getListCount(E entity);

    List<E> getList(E entity, int offset, int limit);

    /**
     * rapid分页查询
     *
     * @param statementName
     * @param countStatementName
     * @param pageRequest
     * @return
     */
    Page<E> pageQuery(String statementName, String countStatementName, PageRequest<E> pageRequest);
}
