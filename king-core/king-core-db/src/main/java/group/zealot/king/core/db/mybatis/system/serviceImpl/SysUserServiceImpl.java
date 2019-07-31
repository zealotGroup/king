package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.base.Constants;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.security.DigestUtils;
import group.zealot.king.base.util.EncodeUtil;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.db.mybatis.base.BaseMapper;
import group.zealot.king.core.db.mybatis.base.BaseServiceImpl;
import group.zealot.king.core.zt.mif.entity.system.SysAuth;
import group.zealot.king.core.zt.mif.entity.system.SysRoleData;
import group.zealot.king.core.zt.mif.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.mif.entity.system.SysUser;
import group.zealot.king.core.zt.mif.service.system.SysUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static group.zealot.king.core.db.mybatis.Daos.*;
import static group.zealot.king.core.zt.mif.Services.sysAuthService;
import static group.zealot.king.core.zt.mif.Services.sysIdService;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Long> implements SysUserService {

    @Override
    public SysUser getByUsernameAndPassword(String username, byte[] password) {
        String newPassword = getNewPassword(username, password);
        SysUser vo = new SysUser();
        vo.setUsername(username);
        vo.setPassword(newPassword);
        return sysUserDao.get(vo);
    }

    @Override
    public SysUser getByUsername(String username) {
        SysUser vo = new SysUser();
        vo.setUsername(username);
        return sysUserDao.get(vo);
    }

    @Override
    public void updatePassword(String username, byte[] password) {
        String newPassword = getNewPassword(username, password);
        SysUser sysUser = getByUsername(username);

        SysUser vo = new SysUser();
        vo.setId(sysUser.getId());
        vo.setPassword(newPassword);
        sysUserDao.update(vo);
    }

    @Override
    public SysUser insert(String username, byte[] password, String status, String level, String remark,
                          Long roleDataId, Long roleRouteId, Long userId) {

        Long id = sysIdService.getId();
        SysUser sysUser = new SysUser();
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setCreateUserId(userId);
        sysUser.setUsername(username);
        sysUser.setPassword(getNewPassword(username, password));
        sysUser.setId(id);

        insert(sysUser);

        {
            SysAuth sysAuth = new SysAuth();
            sysAuth.setId(sysIdService.getId());
            sysAuth.setSysRoleDataId(roleDataId);
            sysAuthService.insert(sysAuth);
        }
        {
            SysAuth sysAuth = new SysAuth();
            sysAuth.setId(sysIdService.getId());
            sysAuth.setSysRoleRouteId(roleRouteId);
            sysAuthService.update(sysAuth);
        }
        return sysUser;
    }

    @Override
    public SysUser update(Long id, byte[] password, String status, String level, String remark, Integer isDelete,
                          Long roleDataId, Long roleRouteId, Long userId) {
        SysUser oldSysUser = getById(id);

        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setLastUpdateTime(LocalDateTime.now());
        sysUser.setLastUpdateUserId(userId);
        if (password != null && password.length > 0) {
            sysUser.setPassword(getNewPassword(oldSysUser.getUsername(), password));
        }
        if (StringUtil.notEmpty(status)) {
            sysUser.setStatus(status);
        }
        if (StringUtil.notEmpty(level)) {
            sysUser.setLevel(level);
        }
        if (StringUtil.notEmpty(remark)) {
            sysUser.setRemark(remark);
        }
        if (isDelete != null) {
            sysUser.setIsDelete(isDelete);
        }

        SysAuth oldSysAuthRoleData = sysAuthService.getSysAuthRoleData(id);
        Funcation.AssertNotNull(oldSysAuthRoleData, "该ID用户 数据角色不存在");
        if (roleDataId != null) {
            SysAuth vo = new SysAuth();
            vo.setId(oldSysAuthRoleData.getId());
            vo.setSysRoleDataId(roleDataId);
            sysAuthService.update(vo);
        }
        SysAuth oldSysAuthRoleRoute = sysAuthService.getSysAuthRoleRoute(id);
        if (roleRouteId != null) {
            SysAuth vo = new SysAuth();
            vo.setId(oldSysAuthRoleRoute.getId());
            vo.setSysRoleRouteId(roleRouteId);
            sysAuthService.update(vo);
        }
        update(sysUser);
        return sysUser;
    }

    @Override
    public void realDel(Long id) {
        //删除用户
        deleteById(id);
        //删除用户关联的角色
        SysAuth sysAuth = new SysAuth();
        sysAuth.setSysUserId(id);
        sysAuthService.delete(sysAuth);
    }

    public String getNewPassword(String username, byte[] password) {
        //加密密码
        byte[] hashPassword = DigestUtils.sha1(password, username.getBytes(), 1024);
        return EncodeUtil.encodeHex(hashPassword);
    }

    @Override
    public void formater(SysUser vo) {
        SysRoleData roleData = sysAuthService.getSysRoleData(vo.getId());
        if (roleData != null) {
            vo.setRoleDataId(roleData.getId());
            vo.setRoleDataName(roleData.getName());
        }
        SysRoleRoute roleRoute = sysAuthService.getSysRoleRoute(vo.getId());
        if (roleRoute != null) {
            vo.setRoleRouteId(roleRoute.getId());
            vo.setRoleRouteName(roleRoute.getName());
        }
        if (vo.getCreateUserId() != null) {
            vo.setCreateUserName(getById(vo.getCreateUserId()).getUsername());
        }
        if (vo.getLastUpdateUserId() != null) {
            vo.setLastUpdateUserName(getById(vo.getLastUpdateUserId()).getUsername());
        }
    }


    @Override
    protected BaseMapper<SysUser, Long> getBaseMapper() {
        return sysUserDao;
    }
}
