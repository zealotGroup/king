package group.zealot.king.base.page;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Page<E> {
    public Page() {
        this(new ArrayList<>(0), 0);
    }

    public Page(int count) {
        this(new ArrayList<>(0), count);
    }

    public Page(List<E> list, int count) {
        this.list = list;
        this.count = count;
    }

    /**
     * 查询接口集
     */
    private List<E> list;
    /**
     * 满足筛选条件的总记录数
     */
    private int count;
}
