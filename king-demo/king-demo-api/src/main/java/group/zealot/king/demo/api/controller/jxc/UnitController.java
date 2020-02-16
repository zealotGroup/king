package group.zealot.king.demo.api.controller.jxc;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.entity.jxc.JxcUnit;
import group.zealot.king.core.zt.entity.jxc.enums.UnitTypeEnum;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.jxcUnitService;


@RestController
@RequestMapping("/jxc/unit")
public class UnitController extends BaseController<JxcUnit, Long> {

    @RequestMapping("add")
    public JSONObject add(String name, Long vsId, Integer size, Integer vsSize, UnitTypeEnum type) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(name, "name 为空");
                Funcation.AssertNotNull(type, "type 为空");
                if (vsId != null) {
                    Funcation.AssertNotNull(size, "size 为空");
                    Funcation.AssertNotNull(vsSize, "vsSize 为空");
                }
            }

            @Override
            protected void dosomething() {
                JxcUnit vo = new JxcUnit();
                vo.setName(name);
                vo.setVsId(vsId);
                vo.setSize(size);
                vo.setVsSize(vsSize);
                vo.setType(type);
                vo = jxcUnitService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String name, Long vsId, Integer size, Integer vsSize, UnitTypeEnum type) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
                Funcation.AssertNotNull(name, "name 为空");
                Funcation.AssertNotNull(type, "type 为空");
                if (vsId != null) {
                    Funcation.AssertNotNull(size, "size 为空");
                    Funcation.AssertNotNull(vsSize, "vsSize 为空");
                }
            }

            @Override
            protected void dosomething() {
                JxcUnit vo = jxcUnitService.getById(id);
                vo.setName(name);
                vo.setVsId(vsId);
                vo.setSize(size);
                vo.setVsSize(vsSize);
                vo.setType(type);
                jxcUnitService.update(vo);
            }
        }.result();
    }
}
