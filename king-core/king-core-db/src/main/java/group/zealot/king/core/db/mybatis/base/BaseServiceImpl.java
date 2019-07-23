package group.zealot.king.core.db.mybatis.base;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<E, P extends Serializable> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract BaseMapper<E, P> getBaseMapper();

    public E getById(P primaryKey) {
        return getBaseMapper().getById(primaryKey);
    }

    public E get(E entity) {
        return getBaseMapper().get(entity);
    }

    public int deleteById(P primaryKey) {
        return getBaseMapper().deleteById(primaryKey);
    }

    public int delete(E entity) {
        return getBaseMapper().delete(entity);
    }

    public int insert(E entity) {
        return getBaseMapper().insert(entity);
    }

    public int update(E entity) {
        return getBaseMapper().update(entity);
    }

    public List<E> getList(E entity) {
        return getBaseMapper().getList(entity);
    }

    public Page<E> pageQuery(PageRequest<E> pageRequest) {
        return getBaseMapper().pageQuery(pageRequest);
    }
}
