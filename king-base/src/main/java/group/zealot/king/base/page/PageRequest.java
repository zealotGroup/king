package group.zealot.king.base.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest<E> {
    private int sEcho;
    private int iDisplayStart;
    private int endNum;
    private int iDisplayLength;

    private E filters;
}
