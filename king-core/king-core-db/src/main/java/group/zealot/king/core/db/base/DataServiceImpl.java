package group.zealot.king.core.db.base;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zealot
 * @date 2020/6/1 19:58
 */
public abstract class DataServiceImpl<E> {
    public void formater(List<E> list) {
        if (list != null && !list.isEmpty()) {
            list.forEach(this::formater);
        }
    }

    abstract protected void formater(E e);

    protected E nullAble(Optional<E> optional) {
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    protected Page<E> ofPage(org.springframework.data.domain.Page<E> page) {
        Page<E> resultPage = new Page<>();
        ArrayList<E> list = new ArrayList<>();
        page.forEach(list::add);
        resultPage.setList(list);
        resultPage.setCount(page.getTotalElements());
        return resultPage;
    }

    protected org.springframework.data.domain.PageRequest ofPageRequest(PageRequest<E> pageRequest) {
        return org.springframework.data.domain.PageRequest.of(pageRequest.getPage() - 1, pageRequest.getLimit(), getSort());
    }

    protected Example<E> getExample(E e) {
        return Example.of(e, getMatcher());
    }

    protected Example<E> getListExample(E e) {
        return Example.of(e, getMatcher());
    }

    protected Sort getSort() {
        return Sort.by(Sort.Direction.DESC, "id");
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
