package group.zealot.king.demo.api.controller.wx;


import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.demo.api.config.ResultTemple;
import group.zealot.king.demo.api.config.WxLoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.jxcGoodsService;

/**
 * @author zealot
 * @date 2020/3/8 14:15
 */
@RestController
@RequestMapping("/wx/shopcar")
public class WXShopCarController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("goods/detail")
    public JSONObject goodsDetail(@ZTValid(NotNull = true) Long goodsId) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                JSONObject vo = jxcGoodsService.getGoodsJxcCustDetail(goodsId, WxLoginUtil.getJxcCust().getId());
                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.set(data);
            }
        }.result();
    }
}
