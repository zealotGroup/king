package group.zealot.king.core.db.serviceimpl.jxc.rel;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.rel.JxcGoodsCustShopcarService;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsCustShopcar;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static group.zealot.king.core.db.serviceimpl.ServiceImpls.jxcGoodsServiceImpl;

@Service
public class JxcGoodsCustShopcarServiceImpl extends BaseServiceImpl<JxcGoodsCustShopcar, Long> implements JxcGoodsCustShopcarService {

    public JxcGoodsCustShopcar getByGoodsIdCustId(Long goodsId, Long custId) {
        JxcGoodsCustShopcar vo = new JxcGoodsCustShopcar();
        vo.setGoodsId(goodsId);
        vo.setCustId(custId);
        return get(vo);
    }

    @Override
    public List<JSONObject> getShopcarGoodsList(Long custId) {
        JxcGoodsCustShopcar vo = new JxcGoodsCustShopcar();
        vo.setCustId(custId);
        List<JxcGoodsCustShopcar> list = getList(vo);
        List<JSONObject> shopcarGoodsList = new ArrayList<>(list.size());
        list.forEach(jxcGoodsCustShopcar -> {
            JSONObject jxcGoods = jxcGoodsServiceImpl.getGoodsJxcCustDetail(jxcGoodsCustShopcar.getGoodsId(), custId);
            shopcarGoodsList.add(jxcGoods);
        });
        return shopcarGoodsList;
    }

    @Override
    public void addGoods(Long goodsId, Long custId, BigDecimal size) {
        JxcGoodsCustShopcar vo = getByGoodsIdCustId(goodsId, custId);
        if (vo == null) {
            vo = new JxcGoodsCustShopcar();
            vo.setGoodsId(goodsId);
            vo.setCustId(custId);
            vo.setSize(size);
            insert(vo);
        } else {
            vo.setSize(vo.getSize().add(size));
            update(vo);
        }

    }

    @Override
    public void updateGoods(Long goodsId, Long custId, BigDecimal size) {
        JxcGoodsCustShopcar vo = getByGoodsIdCustId(goodsId, custId);
        if (vo == null) {
            throw new BaseRuntimeException("购物车不存在该商品");
        } else {
            vo.setSize(size);
            update(vo);
        }
    }

    @Override
    public void delGoods(Long goodsId, Long custId) {
        JxcGoodsCustShopcar vo = getByGoodsIdCustId(goodsId, custId);
        if (vo == null) {
            throw new BaseRuntimeException("购物车不存在该商品");
        } else {
            delete(vo);
        }
    }

    @Override
    public void delGoodsBatch(List<Long> goodsIdList, Long custId) {
        for (Long goodsId : goodsIdList) {
            delGoods(goodsId, custId);
        }
    }
}
