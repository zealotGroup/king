package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.entity.jxc.JxcStock;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static group.zealot.king.core.zt.dbif.Services.jxcStockService;


@RestController
@RequestMapping("/jxc/stock")
public class StockController extends BaseController<JxcStock, Long> {

    @RequestMapping("add")
    public JSONObject add(Long goodsId, BigDecimal size) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(goodsId, "goodsId 为空");
                Funcation.AssertNotNull(size, "size 为空");
            }

            @Override
            protected void dosomething() {
                JxcStock vo = new JxcStock();
                vo.setGoodsId(goodsId);
                vo.setSize(size);
                vo = jxcStockService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, BigDecimal size) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
                Funcation.AssertNotNull(size, "size 为空");
            }

            @Override
            protected void dosomething() {
                JxcStock vo = jxcStockService.getById(id);
                vo.setSize(size);
                jxcStockService.update(vo);
            }
        }.result();
    }
}
