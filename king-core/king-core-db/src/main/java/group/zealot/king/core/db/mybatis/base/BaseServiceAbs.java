package group.zealot.king.core.db.mybatis.base;

import group.zealot.king.base.Constants;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.zt.mif.entity.BaseEntity;
import group.zealot.king.core.zt.mif.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public abstract class BaseServiceAbs<E extends BaseEntity, P extends Serializable> implements BaseService<E, P> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract BaseDao<E, P> getBaseDao();

    public E getById(P primaryKey) {
        return getBaseDao().getById(primaryKey);
    }

    public E get(E entity) {
        return getBaseDao().get(entity);
    }


    public E add(E e) {
        getBaseDao().insert(e);
        return e;
    }

    public E update(E e) {
        getBaseDao().update(e);
        return e;
    }

    public void del(P primaryKey, Long userId) {
        E e = getE(primaryKey);
        e.setLastUpdateTime(LocalDateTime.now());
        e.setLastUpdateUserId(userId);
        e.setIsDelete(Constants.DELETE_Y);
        getBaseDao().update(e);
    }

    public void recover(P primaryKey, Long userId) {
        E e = getE(primaryKey);
        e.setLastUpdateTime(LocalDateTime.now());
        e.setLastUpdateUserId(userId);
        e.setIsDelete(Constants.DELETE_N);
        getBaseDao().update(e);
    }

    public void realDel(P primaryKey) {
        getBaseDao().deleteById(primaryKey);
    }

    public List<E> getList() {
        return getBaseDao().getList();
    }

    public List<E> getList(E entity) {
        return getBaseDao().getList(entity);
    }

    public Page<E> pageQuery(PageRequest<E> pageRequest) {
        return getBaseDao().pageQuery(pageRequest);
    }

    public void formater(List<E> list) {
        list.forEach(entity -> formater(entity));
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    public void formater(E entity) {
    }

    abstract protected E getE(P primaryKey);
}
