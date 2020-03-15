package group.zealot.king.demo.api.config;

import group.zealot.king.base.Constants;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.core.zt.entity.system.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static group.zealot.king.core.zt.dbif.Services.*;

public class LoginUtil extends BaseLoginUtil {
    protected static Logger logger = LoggerFactory.getLogger(LoginUtil.class);

    public static String login(String username, byte[] password) {
        String newPassword = sysUserService.getNewPassword(username, password);
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(newPassword);
        sysUser = sysUserService.get(sysUser);

        Funcation.AssertNotNull(sysUser, "用户名或密码错误");
        if (!Constants.STATUS_ABLE.equals(sysUser.getStatus())) {
            throw new BaseRuntimeException("此用户已被禁用");
        }
        return createToken(sysUser);
    }

    public static SysUser getSysUser() {
        return getSession();
    }

}
