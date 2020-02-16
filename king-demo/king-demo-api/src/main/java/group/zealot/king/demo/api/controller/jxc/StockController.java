package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.entity.jxc.JxcStock;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.jxcStockService;


@RestController
@RequestMapping("/jxc/stock")
public class StockController extends BaseController<JxcStock, Long> {

    @RequestMapping("add")
    public JSONObject add(Long goodsId, Long totalSize, Long totalSales, Long currentSize, Long unitId) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(goodsId, "goodsId 为空");
                Funcation.AssertNotNull(totalSize, "totalSize 为空");
                Funcation.AssertNotNull(totalSales, "totalSales 为空");
                Funcation.AssertNotNull(currentSize, "currentSize 为空");
                Funcation.AssertNotNull(unitId, "unitId 为空");
            }

            @Override
            protected void dosomething() {
                JxcStock vo = new JxcStock();
                vo.setGoodsId(goodsId);
                vo.setTotalSize(totalSize);
                vo.setTotalSales(totalSales);
                vo.setCurrentSize(currentSize);
                vo.setUnitId(unitId);
                vo = jxcStockService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, Long totalSize, Long totalSales, Long currentSize, Long unitId) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
                Funcation.AssertNotNull(totalSize, "totalSize 为空");
                Funcation.AssertNotNull(totalSales, "totalSales 为空");
                Funcation.AssertNotNull(currentSize, "currentSize 为空");
                Funcation.AssertNotNull(unitId, "unitId 为空");
            }

            @Override
            protected void dosomething() {
                JxcStock vo = jxcStockService.getById(id);
                vo.setTotalSize(totalSize);
                vo.setTotalSales(totalSales);
                vo.setCurrentSize(currentSize);
                vo.setUnitId(unitId);
                jxcStockService.update(vo);
            }
        }.result();
    }
}
