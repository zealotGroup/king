package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.jxc.JxcSales;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static group.zealot.king.core.zt.dbif.Services.jxcSalesService;


@RestController
@RequestMapping("/jxc/sales")
public class SalesController extends BaseController<JxcSales, Long> {

    @RequestMapping("add")
    public JSONObject add(@ZTValid(NotNull = true) Long goodsId, @ZTValid(NotNull = true) Long custId, @ZTValid(NotNull = true) BigDecimal price, @ZTValid(NotNull = true) BigDecimal size) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                JxcSales vo = new JxcSales();
                vo.setGoodsId(goodsId);
                vo.setCustId(custId);
                vo.setPrice(price);
                vo.setSize(size);
                vo = jxcSalesService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(@ZTValid(NotNull = true) Long id, @ZTValid(NotNull = true) Long goodsId, @ZTValid(NotNull = true) Long custId, @ZTValid(NotNull = true) BigDecimal price, @ZTValid(NotNull = true) BigDecimal size) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                JxcSales vo = jxcSalesService.getById(id);
                vo.setGoodsId(goodsId);
                vo.setCustId(custId);
                vo.setPrice(price);
                vo.setSize(size);
                jxcSalesService.update(vo);
            }
        }.result();
    }
}
