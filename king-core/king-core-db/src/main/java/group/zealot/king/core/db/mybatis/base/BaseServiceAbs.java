package group.zealot.king.core.db.mybatis.base;

import group.zealot.king.base.Constants;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.zt.mif.entity.BaseEntity;
import group.zealot.king.core.zt.mif.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public abstract class BaseServiceAbs<E extends BaseEntity> implements BaseService<E> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract BaseDao<E, Long> getBaseDao();

    public E getById(Long primaryKey) {
        return getBaseDao().getById(primaryKey);
    }

    public E get(E entity) {
        return getBaseDao().get(entity);
    }


    public E add(E entity, Long userId) {
        entity = beforAdd(entity, userId);
        entity.setCreateTime(LocalDateTime.now());
        entity.setCreateUserId(userId);
        entity.setIsDelete(Constants.DELETE_N);
        getBaseDao().insert(entity);
        return afterAdd(entity, userId);
    }

    public E update(E entity, Long userId) {
        entity = beforUpdate(entity, userId);
        entity.setLastUpdateTime(LocalDateTime.now());
        entity.setLastUpdateUserId(userId);
        getBaseDao().update(entity);
        return afterUpdate(entity, userId);
    }

    public void del(Long primaryKey, Long userId) {
        beforDel(primaryKey, userId);
        E entity = getE(primaryKey);
        entity.setLastUpdateTime(LocalDateTime.now());
        entity.setLastUpdateUserId(userId);
        entity.setIsDelete(Constants.DELETE_Y);
        getBaseDao().update(entity);
        afterDel(primaryKey, userId);
    }

    public void recover(Long primaryKey, Long userId) {
        beforRecover(primaryKey, userId);
        E entity = getE(primaryKey);
        entity.setLastUpdateTime(LocalDateTime.now());
        entity.setLastUpdateUserId(userId);
        entity.setIsDelete(Constants.DELETE_N);
        getBaseDao().update(entity);
        afterRecover(primaryKey, userId);
    }

    public void realDel(Long primaryKey) {
        beforRealDel(primaryKey);
        getBaseDao().deleteById(primaryKey);
        afterRealDel(primaryKey);
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

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    protected E beforAdd(E entity, Long userId) {
        return entity;
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    protected E afterAdd(E entity, Long userId) {
        return entity;
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    protected E beforUpdate(E entity, Long userId) {
        return entity;
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    protected E afterUpdate(E entity, Long userId) {
        return entity;
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    protected void beforRecover(Long primaryKey, Long userId) {
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    protected void afterRecover(Long primaryKey, Long userId) {
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    protected void beforDel(Long primaryKey, Long userId) {
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    protected void afterDel(Long primaryKey, Long userId) {
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    protected void beforRealDel(Long id) {
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    protected void afterRealDel(Long id) {
    }

    abstract protected E getE(Long primaryKey);
}
