package group.zealot.king.demo.api.controller.admin;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.admin.AdminUnit;
import group.zealot.king.core.zt.entity.admin.enums.UnitTypeEnum;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.adminUnitService;


@RestController
@RequestMapping("/admin/unit")
public class UnitController extends BaseController<AdminUnit, Long> {

    @RequestMapping("add")
    public JSONObject add(@ZTValid(NotBlank = true) String name, @ZTValid(NotNull = true) UnitTypeEnum type) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                AdminUnit vo = new AdminUnit();
                vo.setName(name);
                vo.setType(type);
                vo = adminUnitService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(@ZTValid(NotNull = true) Long id, @ZTValid(NotBlank = true) String name, @ZTValid(NotNull = true) UnitTypeEnum type) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                AdminUnit vo = adminUnitService.getById(id);
                vo.setName(name);
                vo.setType(type);
                adminUnitService.update(vo);
            }
        }.result();
    }
}
