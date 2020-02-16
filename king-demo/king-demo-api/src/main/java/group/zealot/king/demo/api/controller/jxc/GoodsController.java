package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.entity.jxc.JxcGoods;
import group.zealot.king.core.zt.entity.admin.AdminLable;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.*;


@RestController
@RequestMapping("/jxc/goods")
public class GoodsController extends BaseController<JxcGoods, Long> {

    @RequestMapping("add")
    public JSONObject add(String name) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(name, "name为空");
            }

            @Override
            protected void dosomething() {
                JxcGoods vo = new JxcGoods();
                vo.setName(name);
                vo = jxcGoodsService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String name) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
                Funcation.AssertNotNull(name, "name为空");
            }

            @Override
            protected void dosomething() {
                JxcGoods vo = jxcGoodsService.getById(id);
                vo.setName(name);
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
}
