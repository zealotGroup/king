package group.zealot.king.demo.api.controller.system.role;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.system.SysRoleData;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.sysRoleDataService;


@RestController
@RequestMapping("/system/role/data")
public class RoleDataController extends BaseController<SysRoleData, Long> {

    @RequestMapping("add")
    public JSONObject add(@ZTValid(NotBlank = true) String name) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                SysRoleData sysRoleData = new SysRoleData();
                sysRoleData.setName(name);
                sysRoleData = sysRoleDataService.insert(sysRoleData);

                JSONObject data = new JSONObject();
                data.put("vo", sysRoleData);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(@ZTValid(NotNull = true) Long id, @ZTValid(NotBlank = true) String name) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                SysRoleData sysRoleData = sysRoleDataService.getById(id);
                sysRoleData.setName(name);
                sysRoleDataService.update(sysRoleData);
            }
        }.result();
    }
}
