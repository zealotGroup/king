package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.dbif.service.BaseService;
import group.zealot.king.core.zt.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.entity.system.SysRoute;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.*;


@RestController
@RequestMapping("/route")
public class RouteController extends BaseController<SysRoute, Long> {

    @Override
    protected BaseService<SysRoute, Long> getBaseService() {
        return sysRouteService;
    }

    @RequestMapping("tree")
    public JSONObject tree() {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                JSONObject data = new JSONObject();
                data.put("routes", sysAuthService.getRoute(getLoginUserId()));
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("add")
    public JSONObject add(String name, Long fId) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(name, "name为空");
                Funcation.AssertNotNull(fId, "fId为空");
                SysRoute sysRoute = sysRouteService.getById(fId);
                Funcation.AssertNotNull(sysRoute, "该FID数据不存在");

                SysRoute vo = new SysRoute();
                vo.setName(name);
                vo.setFId(fId);
                vo = sysRouteService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String name, Long fId) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
                Funcation.AssertNotNull(name, "name为空");
                Funcation.AssertNotNull(fId, "fId为空");
                {
                    SysRoute sysRoute = sysRouteService.getById(id);
                    Funcation.AssertNotNull(sysRoute, "该ID数据不存在");
                }
                {
                    SysRoute sysRoute = sysRouteService.getById(fId);
                    Funcation.AssertNotNull(sysRoute, "该FID数据不存在");
                }
            }

            @Override
            protected void dosomething() {
                SysRoute vo = new SysRoute();
                vo.setName(name);
                vo.setFId(fId);
                sysRouteService.update(vo);
            }
        }.result();
    }
}
