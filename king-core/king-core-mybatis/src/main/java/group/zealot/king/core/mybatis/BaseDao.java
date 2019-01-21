package group.zealot.king.core.mybatis;

import java.io.Serializable;

public class BaseDao<E, P extends Serializable> extends AbsBaseDao<E, P> {
    @Override
    protected String getMapperNamesapce() {
        return getClass().getName();
    }
}
