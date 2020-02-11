package group.zealot.king.core.db;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.zt.dbif.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseServiceImpl<E, P extends Serializable> implements BaseService<E, P> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract BaseMapper<E, P> getBaseMapper();

    protected abstract JpaRepository<E, P> getJpaRepository();

    @Override
    public E getById(P p) {
        return getJpaRepository().findById(p).get();
    }

    @Override
    public E get(E e) {
        return getJpaRepository().findOne(getExample(e)).get();
    }

    @Override
    public void deleteById(P p) {
        getJpaRepository().deleteById(p);
    }

    @Override
    public void delete(E e) {
        getJpaRepository().delete(e);
    }

    @Override
    public E insert(E e) {
        return getJpaRepository().save(e);
    }

    @Override
    public E update(E e) {
        return getJpaRepository().save(e);
    }

    @Override
    public List<E> getList() {
        return getJpaRepository().findAll();
    }

    @Override
    public List<E> getList(E e) {
        return getJpaRepository().findAll(getExample(e));
    }

    public List<E> getList(Example<E> eExample, Sort sort) {
        return getJpaRepository().findAll(eExample, sort);
    }

    @Override
    public Page<E> pageQuery(PageRequest<E> pageRequest) {
        org.springframework.data.domain.PageRequest Pageable
                = org.springframework.data.domain.PageRequest.of(pageRequest.getPage() - 1, pageRequest.getLimit());
        org.springframework.data.domain.Page<E> page = getJpaRepository().findAll(getExample(pageRequest.getFilters()), Pageable);
        Page resultPage = new Page();
        ArrayList<E> list = new ArrayList<>();
        page.forEach(e -> list.add(e));

        resultPage.setList(list);
        resultPage.setCount(list.size());
        return resultPage;
    }

    @Override
    public void formater(List<E> list) {
        list.forEach(entity -> formater(entity));
    }

    /**
     * 子类若进行格式化，需覆盖此方法
     */
    @Override
    public void formater(E entity) {
    }

    protected Example<E> getExample(E e) {
        return Example.of(e);
    }

    protected Sort getSort() {
        return Sort.by(Sort.Direction.DESC, "id");
    }

}
