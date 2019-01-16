package group.zealot.king.base.page;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Page<E> {
    public Page(PageRequest<E> pageRequest, int iTotalRecords) {
        this(new ArrayList<E>(0), iTotalRecords, pageRequest.getSEcho(), iTotalRecords);
    }

    public Page(List<E> aaData, int iTotalRecords, int sEcho, int iTotalDisplayRecords) {
        this.aaData = aaData;
        this.iTotalRecords = iTotalRecords;
        this.sEcho = sEcho;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    private List<E> aaData;
    private int iTotalRecords;
    private int sEcho;
    private int iTotalDisplayRecords;
}
