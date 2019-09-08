package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.mif.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.mif.service.BaseService;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.mif.Services.*;


@RestController
@RequestMapping("/roleRoute")
public class RoleRouteController extends BaseController<SysRoleRoute> {

    @Override
    protected BaseService<SysRoleRoute> getBaseService() {
        return sysRoleRouteService;
    }

    @RequestMapping("add")
    public JSONObject add(String name) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(name, "name为空");
                SysRoleRoute vo = new SysRoleRoute();
                vo.setName(name);
                vo = sysRoleRouteService.add(vo, getLoginUserId());

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
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                Funcation.AssertNotNull(name, "name为空");
                SysRoleRoute oldSysRoleRoute = sysRoleRouteService.getById(id);
                Funcation.AssertNotNull(oldSysRoleRoute, "该ID用户不存在");

                SysRoleRoute vo = new SysRoleRoute();
                vo.setName(name);
                sysRoleRouteService.update(vo, getLoginUserId());
            }
        }.result();
    }

}
