package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.entity.jxc.JxcSales;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.jxcSalesService;


@RestController
@RequestMapping("/jxc/sales")
public class SalesController extends BaseController<JxcSales, Long> {

    @RequestMapping("add")
    public JSONObject add(Long goodsId, Long custId, Long price, Long priceUnitId, Long size, Long sizeUnitId) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(goodsId, "goodsId 为空");
                Funcation.AssertNotNull(custId, "custId 为空");
                Funcation.AssertNotNull(price, "price 为空");
                Funcation.AssertNotNull(priceUnitId, "priceUnitId 为空");
                Funcation.AssertNotNull(size, "size 为空");
                Funcation.AssertNotNull(sizeUnitId, "sizeUnitId 为空");

            }

            @Override
            protected void dosomething() {
                JxcSales vo = new JxcSales();
                vo.setGoodsId(goodsId);
                vo.setCustId(custId);
                vo.setPrice(price);
                vo.setPriceUnitId(priceUnitId);
                vo.setSize(size);
                vo.setSizeUnitId(sizeUnitId);
                vo = jxcSalesService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, Long goodsId, Long custId, Long price, Long priceUnitId, Long size, Long sizeUnitId) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(goodsId, "goodsId 为空");
                Funcation.AssertNotNull(custId, "custId 为空");
                Funcation.AssertNotNull(price, "price 为空");
                Funcation.AssertNotNull(priceUnitId, "priceUnitId 为空");
                Funcation.AssertNotNull(size, "size 为空");
                Funcation.AssertNotNull(sizeUnitId, "sizeUnitId 为空");
            }

            @Override
            protected void dosomething() {
                JxcSales vo = jxcSalesService.getById(id);
                vo.setGoodsId(goodsId);
                vo.setCustId(custId);
                vo.setPrice(price);
                vo.setPriceUnitId(priceUnitId);
                vo.setSize(size);
                vo.setSizeUnitId(sizeUnitId);
                jxcSalesService.update(vo);
            }
        }.result();
    }
}
