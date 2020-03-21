package group.zealot.king.demo.api.controller.wx;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.demo.api.config.ResultTemple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static group.zealot.king.core.zt.dbif.Services.jxcGoodsLableService;
import static group.zealot.king.core.zt.dbif.Services.jxcGoodsService;

/**
 * @author zealot
 * @date 2020/3/8 14:15
 */
@RestController
@RequestMapping("/wx/index")
public class WXIndexController {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @RequestMapping("goodsLable/list")
    public JSONObject goodsLableList() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                List<AdminLable> list = jxcGoodsLableService.getLableList();
                JSONObject data = new JSONObject();
                data.put("list", list);
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("goods/list")
    public JSONObject goodsList(Long goodsLableId, String searchLike,
                                @ZTValid(NotNull = true) Integer page, @ZTValid(NotNull = true) Integer limit) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                PageRequest<JxcGoods> pageRequest = new PageRequest<>();
                pageRequest.setPage(page);
                pageRequest.setLimit(limit);

                JxcGoods jxcGoods = new JxcGoods();
                jxcGoods.setName(searchLike);
                if (goodsLableId != null) {
                    List<Long> lableIds = new ArrayList<>();
                    lableIds.add(goodsLableId);
                    jxcGoods.setLableIds(lableIds);
                }
                pageRequest.setFilters(jxcGoods);

                Page<JxcGoods> page = jxcGoodsService.pageQuery(pageRequest);
                jxcGoodsService.formater(page.getList());

                JSONArray list = page.toJSONArray();

                JSONObject data = new JSONObject();
                data.put("total", page.getCount());
                data.put("list", list);
                resultJson.set(data);
            }
        }.result();
    }
}