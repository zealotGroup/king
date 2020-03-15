package group.zealot.king.core.zt.dbif.service.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.admin.AdminPicture;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.admin.AdminLable;

public interface JxcGoodsService extends BaseService<JxcGoods, Long> {
    AdminLable addLable(Long goodsId, String lableName);

    AdminPicture addPicture(Long goodsId, byte[] bytes);

    JSONObject getGoodsJxcCustDetail(Long goodsId, Long custId);
}
