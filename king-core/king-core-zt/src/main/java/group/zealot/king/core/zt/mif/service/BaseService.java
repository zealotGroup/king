package group.zealot.king.core.zt.mif.service;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;

import java.io.Serializable;
import java.util.List;

public interface BaseService<E, P extends Serializable> {
    E getById(P primaryKey);

    E get(E entity);

    int deleteById(P primaryKey);

    int delete(E entity);

    int insert(E entity);

    int update(E entity);

    List<E> getList(E entity);

    Page<E> pageQuery(PageRequest<E> pageRequest);
}
