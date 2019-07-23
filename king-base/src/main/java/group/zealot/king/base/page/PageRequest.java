package group.zealot.king.base.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest<E> {
    private int page;
    private int limit;

    private E filters;
}
