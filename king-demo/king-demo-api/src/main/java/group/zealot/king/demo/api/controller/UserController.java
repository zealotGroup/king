package group.zealot.king.demo.api.controller;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.mif.entity.system.SysUser;
import group.zealot.king.core.zt.mif.service.BaseService;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.mif.Services.sysUserService;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<SysUser> {

    @Override
    protected BaseService<SysUser> getBaseService() {
        return sysUserService;
    }

    @RequestMapping("add")
    public JSONObject add(String username, String password, String status, String level, String remark,
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
                SysUser vo = new SysUser();
                vo.setUsername(username);
                vo.setPassword(password);
                vo.setStatus(status);
                vo.setLevel(level);
                vo.setRemark(remark);
                vo.setRoleDataId(roleDataId);
                vo.setRoleRouteId(roleRouteId);

                sysUserService.add(vo, getLoginUserId());
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String password, String status, String level, String remark,
                             Long roleDataId, Long roleRouteId) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                Funcation.AssertNotNull(id, "id为空");
                SysUser oldSysUser = sysUserService.getById(id);
                Funcation.AssertNotNull(oldSysUser, "该ID用户不存在");

                SysUser vo = new SysUser();
                vo.setId(id);
                vo.setPassword(password);
                vo.setStatus(status);
                vo.setLevel(level);
                vo.setRemark(remark);
                vo.setRoleDataId(roleDataId);
                vo.setRoleRouteId(roleRouteId);

                sysUserService.update(vo, getLoginUserId());
            }
        }.result();
    }
}
