package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcStockService;
import group.zealot.king.core.zt.entity.admin.AdminUnit;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.jxc.JxcStock;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.serviceimpl.ServiceImpls.*;

@Service
public class JxcStockServiceImpl extends BaseServiceImpl<JxcStock, Long> implements JxcStockService {

    @Override
    public void formater(JxcStock jxcStock) {
        JxcGoods jxcGoods = jxcGoodsServiceImpl.getById(jxcStock.getGoodsId());
        jxcStock.setGoodsName(jxcGoods.getName());
        AdminUnit goodsSizeUnit = adminUnitServiceImpl.getById(jxcGoods.getSizeUnitId());
        jxcStock.setGoodsSizeUnitName(goodsSizeUnit.getName());
    }
}
