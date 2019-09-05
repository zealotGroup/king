package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.base.Funcation;
import group.zealot.king.base.security.DigestUtils;
import group.zealot.king.base.util.EncodeUtil;
import group.zealot.king.base.util.NumberUtil;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.db.mybatis.base.BaseDao;
import group.zealot.king.core.db.mybatis.base.BaseServiceAbs;
import group.zealot.king.core.zt.mif.entity.system.*;
import group.zealot.king.core.zt.mif.service.system.SysUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static group.zealot.king.core.db.mybatis.Daos.*;
import static group.zealot.king.core.zt.mif.Services.sysAuthService;
import static group.zealot.king.core.zt.mif.Services.sysIdService;

@Service
public class SysUserServiceImpl extends BaseServiceAbs<SysUser, Long> implements SysUserService {

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
    public SysUser add(SysUser sysUser) {
        Long id = sysIdService.getId();
        sysUser.setPassword(getNewPassword(sysUser.getUsername(), sysUser.getPassword().getBytes()));
        sysUser.setId(id);
        sysUserDao.insert(sysUser);

        {
            SysAuth sysAuth = new SysAuth();
            sysAuth.setId(sysIdService.getId());
            sysAuth.setSysRoleDataId(sysUser.getRoleDataId());
            sysAuthDao.insert(sysAuth);
        }
        {
            SysAuth sysAuth = new SysAuth();
            sysAuth.setId(sysIdService.getId());
            sysAuth.setSysRoleRouteId(sysUser.getRoleRouteId());
            sysAuthDao.update(sysAuth);
        }
        return sysUser;
    }

    @Override
    public SysUser update(SysUser sysUser) {
        SysUser oldSysUser = getById(sysUser.getId());

        SysUser vo = new SysUser();
        vo.setId(sysUser.getId());
        vo.setPassword(StringUtil.isNotEmpty(sysUser.getPassword()) ?
                getNewPassword(oldSysUser.getUsername(), sysUser.getPassword().getBytes())
                : null);
        vo.setStatus(StringUtil.notEmpty(sysUser.getStatus()) ? sysUser.getStatus() : null);
        vo.setLevel(StringUtil.notEmpty(sysUser.getLevel()) ? sysUser.getLevel() : null);
        vo.setRemark(StringUtil.notEmpty(sysUser.getRemark()) ? sysUser.getRemark() : null);

        if (sysUser.getRoleDataId() != null) {
            SysAuth oldSysAuthRoleData = sysAuthService.getSysAuthRoleData(sysUser.getRoleDataId());
            if (!NumberUtil.equals(oldSysAuthRoleData.getSysRoleDataId(), sysUser.getRoleDataId())) {
                SysAuth sysAuth = new SysAuth();
                sysAuth.setId(oldSysAuthRoleData.getId());
                sysAuth.setSysRoleDataId(sysUser.getRoleDataId());
                sysAuthDao.update(sysAuth);
            }
        }

        if (sysUser.getRoleRouteId() != null) {
            SysAuth oldSysAuthRoleRoute = sysAuthService.getSysAuthRoleRoute(sysUser.getRoleRouteId());
            if (!NumberUtil.equals(oldSysAuthRoleRoute.getSysRoleRouteId(), sysUser.getRoleRouteId())) {
                SysAuth sysAuth = new SysAuth();
                sysAuth.setId(oldSysAuthRoleRoute.getId());
                sysAuth.setSysRoleRouteId(sysUser.getRoleRouteId());
                sysAuthDao.update(sysAuth);
            }

        }
        sysUserDao.update(vo);
        return vo;
    }

    @Override
    public void realDel(Long id) {
        //删除用户
        sysUserDao.deleteById(id);
        //删除用户关联的角色
        SysAuth sysAuth = new SysAuth();
        sysAuth.setSysUserId(id);
        sysAuthDao.delete(sysAuth);
    }

    @Override
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
    protected BaseDao<SysUser, Long> getBaseDao() {
        return sysUserDao;
    }

    @Override
    protected SysUser getE(Long primaryKey) {
        SysUser vo = new SysUser();
        vo.setId(primaryKey);
        return vo;
    }
}
