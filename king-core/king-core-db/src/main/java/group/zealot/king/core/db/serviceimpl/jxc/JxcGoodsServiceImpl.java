package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcGoodsService;
import group.zealot.king.core.zt.entity.admin.AdminUnit;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsLable;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import org.springframework.stereotype.Service;

import java.util.List;

import static group.zealot.king.core.db.mybatis.Mappers.*;
import static group.zealot.king.core.db.serviceimpl.ServiceImpls.*;

@Service
public class JxcGoodsServiceImpl extends BaseServiceImpl<JxcGoods, Long> implements JxcGoodsService {

    @Override
    public Page<JxcGoods> pageQuery(PageRequest<JxcGoods> pageRequest) {
        return baseMapper.pageQuery(pageRequest);
    }

    @Override
    public void formater(JxcGoods jxcGoods) {
        AdminUnit priceUnit = adminUnitServiceImpl.getById(jxcGoods.getPriceUnitId());
        jxcGoods.setPriceUnitName(priceUnit.getName());
        AdminUnit sizeUnit = adminUnitServiceImpl.getById(jxcGoods.getSizeUnitId());
        jxcGoods.setSizeUnitName(sizeUnit.getName());
        List<AdminLable> list = jxcGoodsMapper.getLableListByGoodsId(jxcGoods.getId());
        jxcGoods.setLableList(list);
    }

    @Override
    public AdminLable addLable(Long goodsId, String lableName) {
        AdminLable adminLable = new AdminLable();
        adminLable.setName(lableName);
        adminLable = adminLableServiceImpl.get(adminLable);

        if (adminLable == null) {
            adminLable = new AdminLable();
            adminLable.setName(lableName);
            adminLable = adminLableServiceImpl.insert(adminLable);
        }

        JxcGoodsLable jxcGoodsLable = new JxcGoodsLable();
        jxcGoodsLable.setGoodsId(goodsId);
        jxcGoodsLable.setLableId(adminLable.getId());
        jxcGoodsLableServiceImpl.insert(jxcGoodsLable);
        return adminLable;
    }
}
