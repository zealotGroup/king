package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcGoodsService;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.jxc.JxcGoodsLable;
import group.zealot.king.core.zt.entity.jxc.JxcLable;
import org.springframework.stereotype.Service;

import java.util.List;

import static group.zealot.king.core.db.mybatis.Mappers.*;
import static group.zealot.king.core.db.serviceimpl.ServiceImpls.*;

@Service
public class JxcGoodsServiceImpl extends BaseServiceImpl<JxcGoods, Long> implements JxcGoodsService {
    @Override
    public void formater(JxcGoods jxcGoods) {
        List<JxcLable> list = jxcGoodsMapper.getLableList(jxcGoods.getId());
        jxcGoods.setLableList(list);
    }

    @Override
    public JxcLable addLable(Long goodsId, String lableName) {
        JxcLable jxcLable = new JxcLable();
        jxcLable.setName(lableName);
        jxcLable = jxcLableServiceImpl.get(jxcLable);

        if (jxcLable == null) {
            jxcLable = new JxcLable();
            jxcLable.setName(lableName);
            jxcLable = jxcLableServiceImpl.insert(jxcLable);
        }

        JxcGoodsLable jxcGoodsLable = new JxcGoodsLable();
        jxcGoodsLable.setGoodsId(goodsId);
        jxcGoodsLable.setLableId(jxcLable.getId());
        jxcGoodsLableServiceImpl.insert(jxcGoodsLable);
        return jxcLable;
    }
}
