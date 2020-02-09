package group.zealot.king.core.db.serviceimpl.system.serviceImpl;

import group.zealot.king.base.Funcation;
import group.zealot.king.base.security.DigestUtils;
import group.zealot.king.base.util.EncodeUtil;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.db.mybatis.core.base.BaseMapper;
import group.zealot.king.core.db.BaseServiceImpl;
import group.zealot.king.core.zt.entity.system.SysAuth;
import group.zealot.king.core.zt.entity.system.SysRoleData;
import group.zealot.king.core.zt.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.entity.system.SysUser;
import group.zealot.king.core.zt.dbif.service.system.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


import static group.zealot.king.core.db.mybatis.Mappers.*;
import static group.zealot.king.core.db.jpa.Repositorys.*;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Long> implements SysUserService {
    @Autowired
    private SysAuthServiceImpl sysAuthServiceImpl;

    @Override
    protected BaseMapper<SysUser, Long> getBaseMapper() {
        return sysUserDaoMapper;
    }

    @Override
    protected JpaRepository<SysUser, Long> getJpaRepository() {
        return sysUserRepository;
    }

    @Override
    public String getNewPassword(String username, byte[] password) {
        //加密密码
        byte[] hashPassword = DigestUtils.sha1(password, username.getBytes(), 1024);
        return EncodeUtil.encodeHex(hashPassword);
    }

    @Override
    public SysUser getByUsername(String username) {
        SysUser vo = new SysUser();
        vo.setUsername(username);
        return get(vo);
    }

    @Override
    public SysUser insert(String username, byte[] password, String status, String level, Long roleDataId, Long roleRouteId) {

        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(getNewPassword(username, password));
        insert(sysUser);

        {
            SysAuth sysAuth = new SysAuth();
            sysAuth.setSysUserId(sysUser.getId());
            sysAuth.setSysRoleDataId(roleDataId);
            sysAuthServiceImpl.insert(sysAuth);
        }
        {
            SysAuth sysAuth = new SysAuth();
            sysAuth.setSysUserId(sysUser.getId());
            sysAuth.setSysRoleRouteId(roleRouteId);
            sysAuthServiceImpl.insert(sysAuth);
        }
        return sysUser;
    }

    @Override
    public SysUser update(Long id, String status, String level, Long roleDataId, Long roleRouteId) {
        SysUser sysUser = getById(id);
        if (StringUtil.notEmpty(status)) {
            sysUser.setStatus(status);
        }
        if (StringUtil.notEmpty(level)) {
            sysUser.setLevel(level);
        }

        SysAuth sysAuthRoleData = sysAuthServiceImpl.getSysAuthRoleData(id);
        Funcation.AssertNotNull(sysAuthRoleData, "该ID用户 数据角色不存在");
        if (roleDataId != null) {
            sysAuthRoleData.setSysRoleDataId(roleDataId);
            sysAuthServiceImpl.update(sysAuthRoleData);
        }
        SysAuth sysAuthRoleRoute = sysAuthServiceImpl.getSysAuthRoleRoute(id);
        Funcation.AssertNotNull(sysAuthRoleData, "该ID用户 路由角色不存在");
        if (roleRouteId != null) {
            sysAuthRoleRoute.setSysRoleRouteId(roleRouteId);
            sysAuthServiceImpl.update(sysAuthRoleRoute);
        }
        update(sysUser);
        return sysUser;
    }

    @Override
    public void deleteSysUser(Long id) {
        //删除用户
        deleteById(id);
        //删除用户关联的角色
        SysAuth sysAuth = new SysAuth();
        sysAuth.setSysUserId(id);
        sysAuthServiceImpl.delete(sysAuth);
    }


    @Override
    public void formater(SysUser vo) {
        SysRoleData roleData = sysAuthServiceImpl.getSysRoleData(vo.getId());
        if (roleData != null) {
            vo.setRoleDataId(roleData.getId());
            vo.setRoleDataName(roleData.getName());
        }
        SysRoleRoute roleRoute = sysAuthServiceImpl.getSysRoleRoute(vo.getId());
        if (roleRoute != null) {
            vo.setRoleRouteId(roleRoute.getId());
            vo.setRoleRouteName(roleRoute.getName());
        }
    }
}
