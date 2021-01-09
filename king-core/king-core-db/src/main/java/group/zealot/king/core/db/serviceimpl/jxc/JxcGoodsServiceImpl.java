package group.zealot.king.core.db.serviceimpl.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.db.base.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcGoodsService;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.core.zt.entity.admin.AdminPicture;
import group.zealot.king.core.zt.entity.admin.AdminUnit;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsCustPrice;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsCustShopcar;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsLable;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsPicture;
import org.springframework.stereotype.Service;

import java.util.List;

import static group.zealot.king.core.db.mybatis.Mappers.jxcGoodsMapper;
import static group.zealot.king.core.db.serviceimpl.ServiceImpls.*;

@Service
public class JxcGoodsServiceImpl extends BaseServiceImpl<JxcGoods, Long> implements JxcGoodsService {

    @Override
    public Page<JxcGoods> pageQuery(PageRequest<JxcGoods> pageRequest) {
        return dao.pageQuery(pageRequest);
    }

    @Override
    public void formater(JxcGoods jxcGoods) {
        AdminUnit priceUnit = adminUnitServiceImpl.getById(jxcGoods.getPriceUnitId());
        jxcGoods.setPriceUnitName(priceUnit.getName());
        AdminUnit sizeUnit = adminUnitServiceImpl.getById(jxcGoods.getSizeUnitId());
        jxcGoods.setSizeUnitName(sizeUnit.getName());
        List<AdminLable> lableList = jxcGoodsMapper.getLableListByGoodsId(jxcGoods.getId());
        jxcGoods.setLableList(lableList);
        List<AdminPicture> pictureList = jxcGoodsMapper.getPictureListByGoodsId(jxcGoods.getId());
        adminPictureServiceImpl.formater(pictureList);
        jxcGoods.setPictureList(pictureList);
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

    @Override
    public AdminPicture addPicture(Long goodsId, byte[] bytes) {
        AdminPicture adminPicture = new AdminPicture();
        adminPicture.setName(Funcation.nowTime() + "");
        adminPicture.setBytes(bytes);
        adminPicture = adminPictureServiceImpl.insert(adminPicture);

        JxcGoodsPicture jxcGoodsPicture = new JxcGoodsPicture();
        jxcGoodsPicture.setGoodsId(goodsId);
        jxcGoodsPicture.setPictureId(adminPicture.getId());
        jxcGoodsPictureServiceImpl.insert(jxcGoodsPicture);
        return adminPicture;
    }

    @Override
    public JSONObject getGoodsJxcCustDetail(Long goodsId, Long custId) {
        JxcGoods goods = getById(goodsId);
        formater(goods);
        JxcGoodsCustPrice price = jxcGoodsCustPriceServiceImpl.getByGoodsIdCustId(goodsId, custId);
        JxcGoodsCustShopcar jxcGoodsCustShopcar = jxcGoodsCustShopCarServiceImpl.getByGoodsIdCustId(goodsId, custId);

        JSONObject vo = new JSONObject();
        vo.put("goodsId", goods.getId());
        vo.put("name", goods.getName());
        vo.put("price", goods.getPrice());
        vo.put("priceUnitName", goods.getPriceUnitName());
        vo.put("sizeUnitName", goods.getSizeUnitName());
        vo.put("custPrice", price == null ? goods.getPrice() : price.getPrice());
        vo.put("size", jxcGoodsCustShopcar == null ? 0 : jxcGoodsCustShopcar.getSize());

        vo.put("goodsMaxSize", 99);//库存总数/单次可购买总数
        vo.put("pics", goods.getPictureList());
        vo.put("info", "商品详细介绍：123123阿斯顿发送到发送到");

        return vo;
    }
}
