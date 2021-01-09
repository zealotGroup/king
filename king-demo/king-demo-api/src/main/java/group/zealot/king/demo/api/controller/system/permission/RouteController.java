package group.zealot.king.demo.api.controller.system.permission;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.aop.ZTValid;
import group.zealot.king.core.zt.entity.system.SysRoute;
import group.zealot.king.core.zt.entity.system.enums.RouteTypeEnum;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.sysRouteService;


@RestController
@RequestMapping("/system/permission/route")
public class RouteController extends BaseController<SysRoute, Long> {

    @RequestMapping("tree")
    public JSONObject tree(@ZTValid(NotBlank = true) Long id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                JSONObject data = new JSONObject();
                data.put("routeTree", sysRouteService.getRouteTree(id));
                resultJson.set(data);
            }
        }.result();
    }

    @RequestMapping("add")
    public JSONObject add(@ZTValid(NotBlank = true) String name, Long fId, @ZTValid(NotNull = true) Integer seq, @ZTValid(NotNull = true) RouteTypeEnum type) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                if (fId != null) {
                    SysRoute sysRoute = sysRouteService.getById(fId);
                    Funcation.AssertNotNull(sysRoute, "该FID数据不存在");
                }
            }

            @Override
            protected void dosomething() {
                SysRoute vo = new SysRoute();
                vo.setName(name);
                vo.setFId(fId);
                vo.setSeq(seq);
                vo.setType(type);
                vo = sysRouteService.insert(vo);

                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(@ZTValid(NotNull = true) Long id, @ZTValid(NotBlank = true) String name, Long fId, @ZTValid(NotNull = true) Integer seq, @ZTValid(NotNull = true) RouteTypeEnum type) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                if (fId != null) {
                    SysRoute sysRoute = sysRouteService.getById(fId);
                    Funcation.AssertNotNull(sysRoute, "该FID数据不存在");
                }
            }

            @Override
            protected void dosomething() {
                SysRoute vo = sysRouteService.getById(id);
                vo.setName(name);
                vo.setFId(fId);
                vo.setSeq(seq);
                vo.setType(type);
                sysRouteService.update(vo);
            }
        }.result();
    }
}
