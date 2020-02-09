package group.zealot.king.core.zt.dbif.service;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;

import java.io.Serializable;
import java.util.List;

public interface BaseService<E, P extends Serializable> {
    E getById(P p);

    E get(E e);

    void deleteById(P p);

    void delete(E e);

    E insert(E e);

    E update(E e);

    List<E> getList();

    List<E> getList(E e);

    Page<E> pageQuery(PageRequest<E> pageRequest);

    void formater(List<E> list);

    void formater(E e);
}
