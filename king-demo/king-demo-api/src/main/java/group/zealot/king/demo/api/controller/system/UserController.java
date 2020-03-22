package group.zealot.king.demo.api.controller.system;

import com.alibaba.fastjson.JSONObject;
import group.zealot.king.base.Funcation;
import group.zealot.king.core.zt.aop.ZTValid;
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
    public JSONObject add(@ZTValid(NotBlank = true) String username, @ZTValid(NotEmpty = true) byte[] password, @ZTValid(NotBlank = true) String status, @ZTValid(NotBlank = true) String level,
                          @ZTValid(NotBlank = true) Long roleDataId, @ZTValid(NotNull = true) Long roleRouteId) {
        return new ResultTemple() {
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
    public JSONObject update(@ZTValid(NotNull = true) Long id, @ZTValid(NotBlank = true) String status, @ZTValid(NotBlank = true) String level, @ZTValid(NotNull = true) Long roleDataId, @ZTValid(NotNull = true) Long roleRouteId) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                sysUserService.update(id, status, level, roleDataId, roleRouteId);
            }
        }.result();
    }

    @RequestMapping("updatePassword")
    public JSONObject update(@ZTValid(NotNull = true) Long id, @ZTValid(NotEmpty = true) byte[] password) {
        return new ResultTemple() {
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
    public JSONObject del(@ZTValid(NotNull = true) Long id) {
        return new ResultTemple() {
            @Override
            protected void dosomething() {
                sysUserService.deleteSysUser(id);
            }
        }.result();
    }
}
