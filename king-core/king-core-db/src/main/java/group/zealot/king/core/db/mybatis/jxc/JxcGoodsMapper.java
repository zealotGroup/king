package group.zealot.king.core.db.mybatis.jxc;

import group.zealot.king.core.db.mybatis.core.base.BaseDao;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class JxcGoodsMapper extends BaseDao<JxcGoods, Long> {
    public List<AdminLable> getLableList(Long jxcGoodsId) {
        return selectList("getLableList", jxcGoodsId);
    }
}
