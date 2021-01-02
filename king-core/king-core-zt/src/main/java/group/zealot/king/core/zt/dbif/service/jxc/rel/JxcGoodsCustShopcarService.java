package group.zealot.king.core.zt.dbif.service.jxc.rel;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsCustShopcar;

import java.math.BigDecimal;
import java.util.List;

public interface JxcGoodsCustShopcarService extends BaseService<JxcGoodsCustShopcar, Long> {
    JxcGoodsCustShopcar getByGoodsIdCustId(Long goodsId, Long custId);

    List<JSONObject> getShopcarGoodsList(Long wxUserId);

    void addGoods(Long goodsId, Long custId, BigDecimal size);

    void updateGoods(Long goodsId, Long custId, BigDecimal size);

    void delGoods(Long goodsId, Long custId);

    void delGoodsBatch(List<Long> goodsIdList, Long custId);

    void buyGoodsBatch(List<Long> goodsIdList, Long custId);
}
