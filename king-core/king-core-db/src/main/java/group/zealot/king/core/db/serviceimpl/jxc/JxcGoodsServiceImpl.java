package group.zealot.king.core.db.serviceimpl.jxc;

import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.zt.dbif.service.jxc.JxcGoodsService;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.jxc.JxcGoodsLable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JxcGoodsServiceImpl extends BaseServiceImpl<JxcGoods, Long> implements JxcGoodsService {
    @Autowired
    private JxcGoodsLableServiceImpl jxcGoodsLableServiceImpl;

    @Override
    public void formater(JxcGoods jxcGoods) {
        JxcGoodsLable jxcGoodsLable = new JxcGoodsLable();
        jxcGoodsLable.setGoodsId(jxcGoods.getId());
        List<JxcGoodsLable> list = jxcGoodsLableServiceImpl.getList(jxcGoodsLable);
        jxcGoods.setLableList(list);

    }
}
