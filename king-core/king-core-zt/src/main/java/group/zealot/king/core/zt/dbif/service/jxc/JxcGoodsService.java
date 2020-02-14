package group.zealot.king.core.zt.dbif.service.jxc;

import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.jxc.JxcLable;

public interface JxcGoodsService extends BaseService<JxcGoods, Long> {
    JxcLable addLable(Long goodsId, String lableName);
}
