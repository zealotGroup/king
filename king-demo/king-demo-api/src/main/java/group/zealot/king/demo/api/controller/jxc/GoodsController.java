package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.core.zt.entity.admin.AdminPicture;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsLable;
import group.zealot.king.core.zt.entity.jxc.rel.JxcGoodsPicture;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static group.zealot.king.core.zt.dbif.Services.*;


@RestController
@RequestMapping("/jxc/goods")
public class GoodsController extends BaseController<JxcGoods, Long> {

    @Override
    @RequestMapping("list")
    protected JSONObject list(@ZTValid(NotNull = true) Integer page, @ZTValid(NotNull = true) Integer limit, JxcGoods e) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                PageRequest<JxcGoods> pageRequest = new PageRequest<>();
                pageRequest.setPage(page);
                pageRequest.setLimit(limit);
                JxcGoods filter = new JxcGoods();
                filter.setName(e.getName());
                if (StringUtil.isNotEmpty(e.getLableId())) {
                    String[] lableIds = e.getLableId().split(",");
                    List<Long> lableIdList = new ArrayList<>();
                    for (int i = 0; i < lableIds.length; i++) {
                        lableIdList.add(Long.valueOf(lableIds[i]));
                    }
                    filter.setLableIds(lableIdList);
                }
                pageRequest.setFilters(filter);
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
    public JSONObject add(@ZTValid(NotBlank = true) String name, @ZTValid(NotNull = true) BigDecimal price, @ZTValid(NotNull = true) Long priceUnitId, @ZTValid(NotNull = true) Long sizeUnitId) {
        return new ResultTemple() {
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
    public JSONObject update(@ZTValid(NotNull = true) Long id, @ZTValid(NotBlank = true) String name, @ZTValid(NotNull = true) BigDecimal price, @ZTValid(NotNull = true) Long priceUnitId, @ZTValid(NotNull = true) Long sizeUnitId) {
        return new ResultTemple() {
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
    public JSONObject addLable(@ZTValid(NotNull = true) Long goodsId, @ZTValid(NotBlank = true) String lableName) {
        return new ResultTemple() {
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
    public JSONObject delLable(@ZTValid(NotNull = true) Long goodsId, @ZTValid(NotNull = true) Long lableId) {
        return new ResultTemple() {
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


    @RequestMapping("addPicture")
    public JSONObject addPicture(@ZTValid(NotNull = true) Long goodsId, MultipartFile file) {
        return new ResultTemple() {
            byte[] bytes;

            @Override
            protected void verification() {
                try {
                    bytes = file.getBytes();
                } catch (IOException e) {
                    throw new BaseRuntimeException(e);
                }
            }

            @Override
            protected void dosomething() {
                AdminPicture addPicture = jxcGoodsService.addPicture(goodsId, bytes);
                JSONObject data = new JSONObject();
                data.put("vo", addPicture);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("delPicture")
    public JSONObject delPicture(@ZTValid(NotNull = true) Long goodsId, @ZTValid(NotNull = true) Long pictureId) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                JxcGoodsPicture jxcGoodsPicture = new JxcGoodsPicture();
                jxcGoodsPicture.setGoodsId(goodsId);
                jxcGoodsPicture.setPictureId(pictureId);
                jxcGoodsPicture = jxcGoodsPictureService.get(jxcGoodsPicture);
                if (jxcGoodsPicture != null) {
                    jxcGoodsPictureService.delete(jxcGoodsPicture);
                }
                JSONObject data = new JSONObject();
                data.put("vo", jxcGoodsPicture);
                resultJson.setData(data);
            }
        }.result();
    }
}
