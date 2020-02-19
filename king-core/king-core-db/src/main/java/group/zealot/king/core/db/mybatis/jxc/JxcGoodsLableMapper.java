package group.zealot.king.core.db.mybatis.jxc;

import group.zealot.king.core.db.mybatis.core.base.BaseDao;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsLable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class JxcGoodsLableMapper extends BaseDao<JxcGoodsLable, Long> {
    public List<AdminLable> getLableList() {
        return selectList("getLableList");
    }
}
