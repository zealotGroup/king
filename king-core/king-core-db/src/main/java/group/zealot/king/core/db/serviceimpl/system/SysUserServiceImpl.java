package group.zealot.king.core.db.serviceimpl.system;

import group.zealot.king.base.Funcation;
import group.zealot.king.base.security.DigestUtils;
import group.zealot.king.base.util.EncodeUtil;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.db.serviceimpl.BaseServiceImpl;
import group.zealot.king.core.zt.Passwd;
import group.zealot.king.core.zt.entity.system.SysAuth;
import group.zealot.king.core.zt.entity.system.SysRoleData;
import group.zealot.king.core.zt.entity.system.SysRoleRoute;
import group.zealot.king.core.zt.entity.system.SysUser;
import group.zealot.king.core.zt.dbif.service.system.SysUserService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.serviceimpl.ServiceImpls.*;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Long> implements SysUserService {

    @Override
    public String getNewPassword(String username, byte[] password) {
        //加密密码
        return Passwd.getNewPassword(password, username.getBytes());
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
        sysUser.setStatus(status);
        sysUser.setLevel(level);
        sysUser = insert(sysUser);

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
    protected org.springframework.data.domain.Page<SysUser> pageQuery(SysUser e, org.springframework.data.domain.PageRequest pageable) {
        ExampleMatcher likeMatcher = addLike(getMatcher(), "username");
        return jpaRepository.findAll(Example.of(e, likeMatcher), pageable);
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
