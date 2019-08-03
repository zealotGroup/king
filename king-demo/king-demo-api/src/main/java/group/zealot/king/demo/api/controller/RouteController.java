package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.core.zt.mif.entity.system.SysRoute;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.LoginUtil;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static group.zealot.king.core.zt.mif.Services.*;


@RestController
@RequestMapping("/route")
public class RouteController extends BaseController {

    @RequestMapping("list")
    public JSONObject list(Integer page, Integer limit, String name) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(page, "page为空");
                Funcation.AssertNotNull(limit, "limit为空");

                PageRequest<SysRoute> pageRequest = new PageRequest<>();
                pageRequest.setPage(page);
                pageRequest.setLimit(limit);
                SysRoute filters = new SysRoute();
                filters.setName(name);
                pageRequest.setFilters(filters);

                Page<SysRoute> page = sysRouteService.pageQuery(pageRequest);
                sysRouteService.formater(page.getList());

                JSONObject data = new JSONObject();
                data.put("total", page.getCount());
                data.put("list", page.toJSONArray());
                resultJson.set(data);
            }
        }.result();
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
}
