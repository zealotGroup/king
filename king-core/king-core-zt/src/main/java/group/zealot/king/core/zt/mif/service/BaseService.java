package group.zealot.king.core.zt.mif.service;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;

import java.io.Serializable;
import java.util.List;

public interface BaseService<E, P extends Serializable> {
    E getById(P primaryKey);

    E get(E entity);

    void del(P primaryKey, Long userId);

    void recover(P primaryKey, Long userId);

    void realDel(P primaryKey);

    List<E> getList();

    List<E> getList(E entity);

    Page<E> pageQuery(PageRequest<E> pageRequest);

    void formater(List<E> list);

    void formater(E e);
}
