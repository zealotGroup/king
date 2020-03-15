package group.zealot.king.core.db.serviceimpl.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.dbif.service.jxc.JxcGoodsService;
import group.zealot.king.core.zt.entity.admin.AdminPicture;
import group.zealot.king.core.zt.entity.admin.AdminUnit;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsCustPrice;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsLable;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsPicture;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsCustShopcar;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        adminPicture.setName(Funcation.createTime() + "");
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
        JxcGoodsCustShopcar shopcar = jxcGoodsCustShopCarServiceImpl.getByGoodsIdCustId(goodsId, custId);
        JxcGoodsCustPrice price = jxcGoodsCustPriceServiceImpl.getByGoodsIdCustId(goodsId, custId);

        JSONObject vo = new JSONObject();
        vo.put("name", goods.getName());
        vo.put("price", goods.getPrice());
        vo.put("priceUnitName", goods.getPriceUnitName());
        vo.put("sizeUnitName", goods.getSizeUnitName());
        vo.put("custPrice", price == null ? goods.getPrice() : price.getPrice());
        vo.put("size", shopcar == null ? 0 : shopcar.getSize());
        vo.put("shopcarGoodsSize", 3);

        List<String> picList = new ArrayList<>();
        if (goods.getPictureList() != null) {
            goods.getPictureList().forEach(item -> {
                picList.add("http://localhost:8080/api/admin/picture/getPicture?id=" + item.getId() + "&date=" + new Date());
            });
        }
        vo.put("pic", picList.isEmpty() ? null : picList.get(0));

        vo.put("goodsMaxSize", 99);//库存总数/单次可购买总数
        vo.put("pics", picList);
        vo.put("info", "商品详细介绍：123123阿斯顿发送到发送到");

        return vo;
    }
}
