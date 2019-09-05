package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.zt.mif.entity.system.SysRoute;
import group.zealot.king.core.zt.mif.service.BaseService;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.LoginUtil;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static group.zealot.king.core.zt.mif.Services.*;


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
                data.put("routes", sysAuthService.getRoute(LoginUtil.getSysUserId()));
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("add")
    public JSONObject add(String name, String remark, Long fId) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(name, "name为空");
//                SysRoute vo = sysRouteService.insert(name, remark, fId, LoginUtil.getSysUserId());
                JSONObject data = new JSONObject();
//                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String name, String remark, Long fId) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                Funcation.AssertNotNull(name, "name为空");
                SysRoute sysRoute = sysRouteService.getById(id);
                Funcation.AssertNotNull(sysRoute, "该ID数据不存在");

//                sysRouteService.update(id, name, remark, fId, LoginUtil.getSysUserId());
            }
        }.result();
    }
}
