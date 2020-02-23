package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcPurchaseService;
import group.zealot.king.core.zt.entity.admin.AdminUnit;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.jxc.JxcPurchase;
import group.zealot.king.core.zt.entity.jxc.JxcSupplier;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.serviceimpl.ServiceImpls.*;

@Service
public class JxcPurchaseServiceImpl extends BaseServiceImpl<JxcPurchase, Long> implements JxcPurchaseService {
    @Override
    public Page<JxcPurchase> pageQuery(PageRequest<JxcPurchase> pageRequest) {
        return baseMapper.pageQuery(pageRequest);
    }

    @Override
    public void formater(JxcPurchase jxcPurchase) {
        JxcGoods goods = jxcGoodsServiceImpl.getById(jxcPurchase.getGoodsId());
        AdminUnit goodsPriceUnit = adminUnitServiceImpl.getById(goods.getPriceUnitId());
        AdminUnit goodsSizeUnit = adminUnitServiceImpl.getById(goods.getSizeUnitId());
        jxcPurchase.setGoodsName(goods.getName());
        jxcPurchase.setGoodsPrice(goods.getPrice());
        jxcPurchase.setGoodsPriceUnitName(goodsPriceUnit.getName());
        jxcPurchase.setGoodsSizeUnitName(goodsSizeUnit.getName());
        JxcSupplier supplier = jxcSupplierServiceImpl.getById(jxcPurchase.getSupplierId());
        jxcPurchase.setSupplierName(supplier.getName());
    }
}
