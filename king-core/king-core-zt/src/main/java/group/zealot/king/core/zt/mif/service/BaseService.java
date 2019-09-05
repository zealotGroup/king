package group.zealot.king.core.zt.mif.service;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.zt.mif.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface BaseService<E extends BaseEntity, P extends Serializable> {
    E getById(P primaryKey);

    E get(E entity);

    E add(E e);

    E update(E e);

    void del(P primaryKey, Long userId);

    void recover(P primaryKey, Long userId);

    void realDel(P primaryKey);

    List<E> getList();

    List<E> getList(E entity);

    Page<E> pageQuery(PageRequest<E> pageRequest);

    void formater(List<E> list);

    void formater(E e);
}
