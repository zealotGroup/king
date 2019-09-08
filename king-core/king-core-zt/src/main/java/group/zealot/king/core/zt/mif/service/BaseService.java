package group.zealot.king.core.zt.mif.service;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.zt.mif.entity.BaseEntity;

import java.util.List;

public interface BaseService<E extends BaseEntity> {
    E getById(Long primaryKey);

    E get(E entity);

    E add(E e, Long userId);

    E update(E e, Long userId);

    void del(Long primaryKey, Long userId);

    void recover(Long primaryKey, Long userId);

    void realDel(Long primaryKey);

    List<E> getList();

    List<E> getList(E entity);

    Page<E> pageQuery(PageRequest<E> pageRequest);

    void formater(List<E> list);

    void formater(E e);
}
