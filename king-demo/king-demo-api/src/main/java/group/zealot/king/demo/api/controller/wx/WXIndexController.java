package group.zealot.king.demo.api.controller.wx;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.demo.api.config.ResultTemple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
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
    @Validated
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
    @Validated
    public JSONObject goodsList(Long goodsLableId, String searchLike, @NotNull Integer page, @NotNull Integer limit) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(page, "page为空");
                Funcation.AssertNotNull(limit, "limit为空");
            }

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
                list.forEach(obj -> {
                    JSONObject item = (JSONObject) obj;
                    if (item.getJSONArray("pictureList") != null && item.getJSONArray("pictureList").size() > 0) {
                        Integer pictureId = item.getJSONArray("pictureList").getJSONObject(0).getInteger("id");
                        item.put("pic", "http://localhost:8080/api/admin/picture/getPicture?id=" + pictureId + "&date=" + new Date());
                    }
                    item.remove("pictureList");
                    item.remove("lableList");

                    // cust 和商品的 定制化价格
                    //minPrice originalPrice
                    item.put("minPrice", item.getBigDecimal("price"));


                });

                JSONObject data = new JSONObject();
                data.put("total", page.getCount());
                data.put("list", list);
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("goods/detail")
    @Validated
    public JSONObject goodsDetail(@NotNull Long goodsId) {
        return new ResultTemple() {

            @Override
            protected void dosomething() {
                JxcGoods vo = jxcGoodsService.getById(goodsId);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.set(data);
            }
        }.result();
    }

}
