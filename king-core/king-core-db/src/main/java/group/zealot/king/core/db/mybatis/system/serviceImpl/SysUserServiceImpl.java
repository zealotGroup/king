package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.base.Funcation;
import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.security.DigestUtils;
import group.zealot.king.base.util.EncodeUtil;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.db.mybatis.base.BaseMapper;
import group.zealot.king.core.db.mybatis.base.BaseServiceImpl;
import group.zealot.king.core.zt.mif.entity.system.SysUser;
import group.zealot.king.core.zt.mif.service.system.SysUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static group.zealot.king.core.db.mybatis.Daos.*;
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
        Funcation.NotNull(sysUser, "username 对应的数据不存在");

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

    public String getNewPassword(String username, byte[] password) {
        //加密密码
        byte[] hashPassword = DigestUtils.sha1(password, username.getBytes(), 1024);
        String newPassword = EncodeUtil.encodeHex(hashPassword);
        return newPassword;
    }


    @Override
    protected BaseMapper<SysUser, Long> getBaseMapper() {
        return sysUserDao;
    }
}
