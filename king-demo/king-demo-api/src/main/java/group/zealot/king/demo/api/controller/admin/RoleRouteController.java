package group.zealot.king.demo.api.controller.admin;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.entity.system.SysRoleRoute;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static group.zealot.king.core.zt.dbif.Services.sysRoleRouteService;


@RestController
@RequestMapping("/admin/roleRoute")
public class RoleRouteController extends BaseController<SysRoleRoute, Long> {

    @RequestMapping("add")
    public JSONObject add(String name, @RequestParam(value = "route") List<Long> routeList) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(name, "name为空");
                Funcation.AssertNotNull(routeList.isEmpty() ? null : new Object(), "route为空");
            }

            @Override
            protected void dosomething() {
                SysRoleRoute vo = sysRoleRouteService.insert(name, routeList);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String name, @RequestParam(value = "route") List<Long> routeList) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
                Funcation.AssertNotNull(name, "name为空");
            }

            @Override
            protected void dosomething() {
                sysRoleRouteService.update(id, name, routeList);
            }
        }.result();
    }

}
