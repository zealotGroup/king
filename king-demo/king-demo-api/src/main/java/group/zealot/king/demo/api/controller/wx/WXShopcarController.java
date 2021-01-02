package group.zealot.king.demo.api.controller.wx;


import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.shiro.LoginUtil;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.demo.api.config.ResultTemple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static group.zealot.king.core.zt.dbif.Services.jxcGoodsCustShopcarService;


/**
 * @author zealot
 * @date 2020/3/8 14:15
 */
@RestController
@RequestMapping("/wx/shopcar")
public class WXShopcarController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("list")
    public JSONObject list() {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                List<JSONObject> list = jxcGoodsCustShopcarService.getShopcarGoodsList(LoginUtil.getWxUser().getId());
                JSONObject data = new JSONObject();
                data.put("list", list);
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("addGoods")
    public JSONObject addGoods(@ZTValid(NotNull = true) Long goodsId, @ZTValid(NotNull = true) BigDecimal size) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                jxcGoodsCustShopcarService.addGoods(goodsId, LoginUtil.getWxUser().getId(), size);
            }
        }.result();
    }

    @RequestMapping("updateGoods")
    public JSONObject updateGoods(@ZTValid(NotNull = true) Long goodsId, @ZTValid(NotNull = true) BigDecimal size) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                jxcGoodsCustShopcarService.updateGoods(goodsId, LoginUtil.getWxUser().getId(), size);
            }
        }.result();
    }

    @RequestMapping("delGoods")
    public JSONObject delGoods(@ZTValid(NotNull = true) Long goodsId) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                jxcGoodsCustShopcarService.delGoods(goodsId, LoginUtil.getWxUser().getId());
            }
        }.result();
    }

    @RequestMapping("delGoodsBatch")
    public JSONObject delGoodsBatch(@ZTValid(NotBlank = true) String goodsIds) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                List<Long> goodsIdList = new ArrayList<>();
                for (String goodsId : goodsIds.substring(1, goodsIds.length() - 1).split(",")) {
                    goodsIdList.add(Long.parseLong(goodsId));
                }
                if (!goodsIdList.isEmpty()) {
                    jxcGoodsCustShopcarService.delGoodsBatch(goodsIdList, LoginUtil.getWxUser().getId());
                }
            }
        }.result();
    }

    @RequestMapping("buyGoods")
    public JSONObject buyGoods(@ZTValid(NotBlank = true) String goodsIds) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                List<Long> goodsIdList = new ArrayList<>();
                for (String goodsId : goodsIds.substring(1, goodsIds.length() - 1).split(",")) {
                    goodsIdList.add(Long.parseLong(goodsId));
                }
                if (!goodsIdList.isEmpty()) {
                    jxcGoodsCustShopcarService.buyGoodsBatch(goodsIdList, LoginUtil.getWxUser().getId());
                }
            }
        }.result();
    }
}
