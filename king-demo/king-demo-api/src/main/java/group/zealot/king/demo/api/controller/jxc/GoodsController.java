package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsLable;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static group.zealot.king.core.zt.dbif.Services.*;


@RestController
@RequestMapping("/jxc/goods")
public class GoodsController extends BaseController<JxcGoods, Long> {

    @Override
    @RequestMapping("list")
    protected JSONObject list(Integer page, Integer limit, JxcGoods e) {
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
                if (StringUtil.isNotEmpty(e.getLableId())) {
                    String[] lableIds = e.getLableId().split(",");
                    List<Long> lableIdList = new ArrayList<>();
                    for (int i = 0; i < lableIds.length; i++) {
                        lableIdList.add(Long.valueOf(lableIds[i]));
                    }
                    e.setLableIds(lableIdList);
                }
                pageRequest.setFilters(e);
                Page<JxcGoods> page = baseService.pageQuery(pageRequest);
                baseService.formater(page.getList());

                JSONObject data = new JSONObject();
                data.put("total", page.getCount());
                data.put("list", page.toJSONArray());
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("add")
    public JSONObject add(String name, Long price, Long priceUnitId, Long sizeUnitId) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(name, "name 为空");
                Funcation.AssertNotNull(price, "price 为空");
                Funcation.AssertNotNull(priceUnitId, "priceUnitId 为空");
                Funcation.AssertNotNull(sizeUnitId, "sizeUnitId 为空");
            }

            @Override
            protected void dosomething() {
                JxcGoods vo = new JxcGoods();
                vo.setName(name);
                vo.setPrice(price);
                vo.setPriceUnitId(priceUnitId);
                vo.setSizeUnitId(sizeUnitId);
                vo = jxcGoodsService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String name, Long price, Long priceUnitId, Long sizeUnitId) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id 为空");
                Funcation.AssertNotNull(name, "name 为空");
                Funcation.AssertNotNull(price, "price 为空");
                Funcation.AssertNotNull(priceUnitId, "priceUnitId 为空");
                Funcation.AssertNotNull(sizeUnitId, "sizeUnitId 为空");
            }

            @Override
            protected void dosomething() {
                JxcGoods vo = jxcGoodsService.getById(id);
                vo.setName(name);
                vo.setPrice(price);
                vo.setPriceUnitId(priceUnitId);
                vo.setSizeUnitId(sizeUnitId);
                jxcGoodsService.update(vo);
            }
        }.result();
    }

    @RequestMapping("addLable")
    public JSONObject addLable(Long goodsId, String lableName) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(goodsId, "goodsId 为空");
                Funcation.AssertNotNull(lableName, "lableName 为空");
            }

            @Override
            protected void dosomething() {
                AdminLable adminLable = jxcGoodsService.addLable(goodsId, lableName);
                JSONObject data = new JSONObject();
                data.put("vo", adminLable);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("delLable")
    public JSONObject delLable(Long goodsId, Long lableId) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(goodsId, "goodsId 为空");
                Funcation.AssertNotNull(lableId, "lableId 为空");
            }

            @Override
            protected void dosomething() {
                JxcGoodsLable jxcGoodsLable = new JxcGoodsLable();
                jxcGoodsLable.setGoodsId(goodsId);
                jxcGoodsLable.setLableId(lableId);
                jxcGoodsLable = jxcGoodsLableService.get(jxcGoodsLable);
                if (jxcGoodsLable != null) {
                    jxcGoodsLableService.delete(jxcGoodsLable);
                }
                JSONObject data = new JSONObject();
                data.put("vo", jxcGoodsLable);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("getGoodsLableList")
    public JSONObject getGoodsLableList() {
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
}
