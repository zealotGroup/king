package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Constants;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.page.Page;
import group.zealot.king.base.page.PageRequest;
import group.zealot.king.base.util.NumberUtil;
import group.zealot.king.core.zt.mif.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.mif.entity.system.SysRoute;
import group.zealot.king.core.zt.mif.entity.system.SysUser;
import group.zealot.king.core.zt.mif.service.BaseService;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.LoginUtil;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.mif.Services.*;


@RestController
@RequestMapping("/roleRoute")
public class RoleRouteController extends BaseController<SysRoleRoute, Long> {

    @Override
    protected BaseService<SysRoleRoute, Long> getBaseService() {
        return sysRoleRouteService;
    }

    @RequestMapping("add")
    public JSONObject add(String username, byte[] password, String status, String level, String remark,
                          Long roleDataId, Long roleRouteId) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(username, "username为空");
                Funcation.AssertNotNull(password, "password为空");
                Funcation.AssertNotNull(status, "status为空");
                Funcation.AssertNotNull(level, "level为空");
                Funcation.AssertNotNull(remark, "remark为空");
                Funcation.AssertNotNull(roleDataId, "roleDataId为空");
                Funcation.AssertNotNull(roleRouteId, "roleRouteId为空");

                Funcation.AssertIsNull(sysUserService.getByUsername(username), "该用户名已存在");
                SysUser vo = sysUserService.insert(username, password, status, level, remark, roleDataId, roleRouteId, LoginUtil.getSysUserId());
                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, byte[] password, String status, String level, String remark,
                             Long roleDataId, Long roleRouteId) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                SysUser oldSysUser = sysUserService.getById(id);
                Funcation.AssertNotNull(oldSysUser, "该ID用户不存在");

                sysUserService.update(id, password, status, level, remark, null, roleDataId, roleRouteId, LoginUtil.getSysUserId());
            }
        }.result();
    }

}
