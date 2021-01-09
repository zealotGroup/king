package group.zealot.king.core.db.serviceimpl.jxc.rel;

import group.zealot.king.core.db.base.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.rel.JxcGoodsCustPriceService;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsCustPrice;
import org.springframework.stereotype.Service;

@Service
public class JxcGoodsCustPriceServiceImpl extends BaseServiceImpl<JxcGoodsCustPrice, Long> implements JxcGoodsCustPriceService {

    public JxcGoodsCustPrice getByGoodsIdCustId(Long goodsId, Long custId) {
        JxcGoodsCustPrice vo = new JxcGoodsCustPrice();
        vo.setGoodsId(goodsId);
        vo.setCustId(custId);
        return get(vo);
    }
}
