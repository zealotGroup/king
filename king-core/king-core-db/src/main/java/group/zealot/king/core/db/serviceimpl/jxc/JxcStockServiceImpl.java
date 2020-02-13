package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcStockService;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.jxc.JxcStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JxcStockServiceImpl extends BaseServiceImpl<JxcStock, Long> implements JxcStockService {
    @Autowired
    private JxcGoodsServiceImpl jxcGoodsServiceImpl;

    @Override
    public void formater(JxcStock jxcStock) {
        JxcGoods jxcGoods = jxcGoodsServiceImpl.getById(jxcStock.getGoodsId());
        jxcStock.setGoodsName(jxcGoods == null ? "商品已删除" : jxcGoods.getName());
    }
}
