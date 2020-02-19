package group.zealot.king.core.db.serviceimpl;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.zt.dbif.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<E, P extends Serializable> implements BaseService<E, P> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    public BaseMapper<E, P> baseMapper;

    @Autowired
    public JpaRepository<E, P> jpaRepository;

    @Override
    public E getById(P p) {
        return nullAble(jpaRepository.findById(p));
    }

    @Override
    public E get(E e) {
        return nullAble(jpaRepository.findOne(getExample(e)));
    }

    @Override
    public void deleteById(P p) {
        jpaRepository.deleteById(p);
    }

    @Override
    public void delete(E e) {
        jpaRepository.delete(e);
    }

    @Override
    public E insert(E e) {
        return jpaRepository.save(e);
    }

    @Override
    public E update(E e) {
        return jpaRepository.save(e);
    }

    @Override
    public List<E> getList() {
        return jpaRepository.findAll();
    }

    @Override
    public List<E> getList(E e) {
        return jpaRepository.findAll(getExample(e));
    }

    public List<E> getList(Example<E> eExample, Sort sort) {
        return jpaRepository.findAll(eExample, sort);
    }

    @Override
    public Page<E> pageQuery(PageRequest<E> pageRequest) {
        if (pageRequest.getLimit() == -1) {
            pageRequest.setLimit(Integer.MAX_VALUE);
        }
        org.springframework.data.domain.PageRequest pageable
                = org.springframework.data.domain.PageRequest.of(pageRequest.getPage() - 1, pageRequest.getLimit());
        org.springframework.data.domain.Page<E> page = pageQuery(pageRequest.getFilters(), pageable);
        Page<E> resultPage = new Page<>();
        ArrayList<E> list = new ArrayList<>();
        page.forEach(e -> list.add(e));
        resultPage.setList(list);
        resultPage.setCount(page.getTotalElements());
        return resultPage;
    }

    protected org.springframework.data.domain.Page<E> pageQuery(E e, org.springframework.data.domain.PageRequest pageable) {
        return jpaRepository.findAll(getExample(e), pageable);
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
        return Example.of(e, getMatcher());
    }

    protected Sort getSort() {
        return Sort.by(Sort.Direction.DESC, "id");
    }

    private E nullAble(Optional<E> optional) {
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    protected ExampleMatcher getMatcher() {
        return ExampleMatcher.matchingAll();
    }

    protected ExampleMatcher addLike(ExampleMatcher matcher, String... strs) {
        for (String str : strs) {
            matcher = matcher.withMatcher(str, ExampleMatcher.GenericPropertyMatchers.contains());
        }
        return matcher;
    }
}
