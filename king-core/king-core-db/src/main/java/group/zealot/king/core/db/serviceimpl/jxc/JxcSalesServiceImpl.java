package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcSalesService;
import group.zealot.king.core.zt.entity.admin.AdminUnit;
import group.zealot.king.core.zt.entity.jxc.*;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.serviceimpl.ServiceImpls.*;

@Service
public class JxcSalesServiceImpl extends BaseServiceImpl<JxcSales, Long> implements JxcSalesService {
    @Override
    public Page<JxcSales> pageQuery(PageRequest<JxcSales> pageRequest) {
        return baseMapper.pageQuery(pageRequest);
    }

    @Override
    public void formater(JxcSales jxcSales) {
        JxcGoods goods = jxcGoodsServiceImpl.getById(jxcSales.getGoodsId());
        AdminUnit goodsPriceUnit = adminUnitServiceImpl.getById(goods.getPriceUnitId());
        AdminUnit goodsSizeUnit = adminUnitServiceImpl.getById(goods.getSizeUnitId());
        jxcSales.setGoodsName(goods.getName());
        jxcSales.setGoodsPrice(goods.getPrice());
        jxcSales.setGoodsPriceUnitName(goodsPriceUnit.getName());
        jxcSales.setGoodsSizeUnitName(goodsSizeUnit.getName());
        JxcCust cust = jxcCustServiceImpl.getById(jxcSales.getCustId());
        jxcSales.setCustName(cust.getName());
    }
}
