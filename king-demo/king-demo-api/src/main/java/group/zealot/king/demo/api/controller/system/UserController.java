package group.zealot.king.demo.api.controller.system;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.entity.system.SysUser;
import group.zealot.king.demo.api.config.BaseController;
import group.zealot.king.demo.api.config.ResultTemple;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static group.zealot.king.core.zt.dbif.Services.*;


@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController<SysUser, Long> {
    @RequestMapping("add")
    public JSONObject add(String username, byte[] password, String status, String level,
                          Long roleDataId, Long roleRouteId) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(username, "username为空");
                Funcation.AssertNotNull(password, "password为空");
                Funcation.AssertNotNull(status, "status为空");
                Funcation.AssertNotNull(level, "level为空");
                Funcation.AssertNotNull(roleDataId, "roleDataId为空");
                Funcation.AssertNotNull(roleRouteId, "roleRouteId为空");
            }

            @Override
            protected void dosomething() {
                SysUser vo = sysUserService.insert(username, password, status, level, roleDataId, roleRouteId);
                JSONObject data = new JSONObject();
                data.put("vo", vo);
                resultJson.setData(data);
            }
        }.result();
    }

    @RequestMapping("update")
    public JSONObject update(Long id, String status, String level, Long roleDataId, Long roleRouteId) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
                Funcation.AssertNotNull(status, "status为空");
                Funcation.AssertNotNull(level, "level为空");
                Funcation.AssertNotNull(roleDataId, "roleDataId为空");
                Funcation.AssertNotNull(roleRouteId, "roleRouteId为空");
            }

            @Override
            protected void dosomething() {
                sysUserService.update(id, status, level, roleDataId, roleRouteId);
            }
        }.result();
    }

    @RequestMapping("updatePassword")
    public JSONObject update(Long id, byte[] password) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
                Funcation.AssertNotNull(password, "password为空");
            }

            @Override
            protected void dosomething() {
                SysUser sysUser = sysUserService.getById(id);
                sysUser.setUsername(sysUserService.getNewPassword(sysUser.getUsername(), password));
                sysUserService.update(sysUser);
            }
        }.result();
    }

    @Override
    @RequestMapping("del")
    public JSONObject del(Long id) {
        return new ResultTemple() {
            @Override
            protected void verification() {
                Funcation.AssertNotNull(id, "id为空");
            }

            @Override
            protected void dosomething() {
                sysUserService.deleteSysUser(id);
            }
        }.result();
    }
}
