package group.zealot.king.core.db.serviceimpl.jxc.rel;

import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.rel.JxcGoodsCustShopcarService;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsCustShopcar;
import org.springframework.stereotype.Service;

@Service
public class JxcGoodsCustShopcarServiceImpl extends BaseServiceImpl<JxcGoodsCustShopcar, Long> implements JxcGoodsCustShopcarService {

    public JxcGoodsCustShopcar getByGoodsIdCustId(Long goodsId, Long custId) {
        JxcGoodsCustShopcar vo = new JxcGoodsCustShopcar();
        vo.setGoodsId(goodsId);
        vo.setCustId(custId);
        return get(vo);
    }
}
