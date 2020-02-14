package group.zealot.king.core.db.mybatis.jxc;

import group.zealot.king.core.db.mybatis.core.base.BaseDao;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.jxc.JxcLable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class JxcGoodsMapper extends BaseDao<JxcGoods, Long> {
    public List<JxcLable> getLableList(Long jxcGoodsId) {
        return selectList("getLableList", jxcGoodsId);
    }
}
