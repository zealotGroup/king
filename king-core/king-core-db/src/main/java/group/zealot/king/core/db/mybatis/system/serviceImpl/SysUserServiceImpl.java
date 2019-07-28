package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.base.Constants;
import group.zealot.king.base.Funcation;
import group.zealot.king.base.security.DigestUtils;
import group.zealot.king.base.util.EncodeUtil;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.db.mybatis.base.BaseMapper;
import group.zealot.king.core.db.mybatis.base.BaseServiceImpl;
import group.zealot.king.core.zt.mif.entity.system.SysAuth;
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
        Funcation.AssertNotNull(sysUser, "username 对应的数据不存在");

        SysUser vo = new SysUser();
        vo.setId(sysUser.getId());
        vo.setPassword(newPassword);
        sysUserDao.update(vo);
    }

    @Override
    public SysUser insert(String username, byte[] password, String status, String level, String remark, Long userId) {

        Long id = sysIdService.getId();
        SysUser vo = new SysUser();
        vo.setCreateTime(LocalDateTime.now());
        vo.setCreateUserId(userId);
        vo.setUsername(username);
        vo.setPassword(getNewPassword(username, password));
        vo.setId(id);

        insert(vo);
        return vo;
    }

    @Override
    public SysUser update(Long id, byte[] password, String status, String level, String remark, Integer isDelete, Long userId) {
        SysUser old = getById(id);
        Funcation.AssertNotNull(old, "该ID用户不存在");

        SysUser vo = new SysUser();
        vo.setId(id);
        vo.setLastUpdateTime(LocalDateTime.now());
        vo.setLastUpdateUserId(userId);
        if (password != null && password.length > 0) {
            vo.setPassword(getNewPassword(old.getUsername(), password));
        }
        if (StringUtil.notEmpty(status)) {
            vo.setStatus(status);
        }
        if (StringUtil.notEmpty(level)) {
            vo.setLevel(level);
        }
        if (StringUtil.notEmpty(remark)) {
            vo.setRemark(remark);
        }
        if (isDelete != null) {
            vo.setIsDelete(isDelete);
        }
        update(vo);
        return vo;
    }

    @Override
    public void realDel(Long id) {
        SysUser old = getById(id);
        Funcation.AssertNotNull(old, "该id用户不存在，无法再物理删除");
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
        vo.setRoleData(sysAuthService.getSysRoleData(vo.getId()));
        vo.setRoleRoute(sysAuthService.getSysRoleRoute(vo.getId()));
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
