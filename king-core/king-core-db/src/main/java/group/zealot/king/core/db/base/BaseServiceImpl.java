package group.zealot.king.core.db.base;

import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.db.mybatis.core.base.BaseDao;
import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

public abstract class BaseServiceImpl<E extends BaseEntity, P extends Serializable> extends DataServiceImpl<E> implements BaseService<E, P> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    protected BaseDao<E, P> dao;

    @Autowired(required = false)
    protected JpaRepository<E, P> jpa;

    protected Type type() {
        return Type.JPA;
    }

    @PostConstruct
    protected void checkDapJpa() {
        if (jpa == null && dao == null) {
            throw new BaseRuntimeException(getClass() + " dao && jpa is null");
        }
    }

    @Override
    public E getById(P p) {
        if (Type.JPA == type()) {
            return nullAble(jpa.findById(p));
        } else {
            return dao.getById(p);
        }
    }

    @Override
    public E get(E e) {
        if (Type.JPA == type()) {
            return nullAble(jpa.findOne(getExample(e)));
        } else {
            return dao.get(e);
        }
    }

    @Override
    public void deleteById(P p) {
        if (Type.JPA == type()) {
            jpa.deleteById(p);
        } else {
            dao.deleteById(p);
        }
    }

    @Override
    public void delete(E e) {
        if (Type.JPA == type()) {
            jpa.delete(e);
        } else {
            dao.delete(e);
        }
    }

    @Override
    public E insert(E e) {
        if (Type.JPA == type()) {
            return jpa.save(e);
        } else {
            return dao.insert(e);
        }

    }

    @Override
    public E update(E e) {
        if (Type.JPA == type()) {
            return jpa.save(e);
        } else {
            return dao.update(e);
        }
    }

    @Override
    public List<E> getList() {
        if (Type.JPA == type()) {
            return jpa.findAll();
        } else {
            return dao.getList();
        }
    }

    @Override
    public List<E> getList(E e) {
        if (Type.JPA == type()) {
            return jpa.findAll(getListExample(e));
        } else {
            return dao.getList(e);
        }
    }

    public List<E> getList(Example<E> eExample, Sort sort) {
        return jpa.findAll(eExample, sort);
    }

    @Override
    public Page<E> pageQuery(PageRequest<E> pageRequest) {
        if (pageRequest.getLimit() == -1) {
            pageRequest.setLimit(Integer.MAX_VALUE);
        }
        if (Type.JPA == type()) {
            org.springframework.data.domain.PageRequest pageable = ofPageRequest(pageRequest);
            org.springframework.data.domain.Page<E> page;
            if (pageRequest.getFilters() == null) {
                page = jpa.findAll(pageable);
            } else {
                page = jpa.findAll(getListExample(pageRequest.getFilters()), pageable);
            }
            return ofPage(page);
        } else {
            return dao.pageQuery(pageRequest);
        }
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    @Override
    public void formater(E entity) {
    }

    protected enum Type {
        JPA, DAO
    }
}
