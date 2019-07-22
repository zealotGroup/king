package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.base.exception.BaseRuntimeException;
import group.zealot.king.base.util.StringUtil;
import group.zealot.king.core.db.mybatis.base.BaseService;
import group.zealot.king.core.zt.mif.entity.system.SysUser;
import group.zealot.king.core.zt.mif.service.system.SysRouteService;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.mybatis.Daos.*;

@Service
public class SysRouteServiceImpl extends BaseService implements SysRouteService {

    public SysUser getByUsernameAndPassword(String username, String password) {
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            return null;
        }
        SysUser vo = new SysUser();
        vo.setUsername(username);
        vo.setPassword(password);
        return sysUserDao.get(vo);
    }

    public SysUser getByUsername(String username) {
        if (StringUtil.isEmpty(username)) {
            return null;
        }
        SysUser vo = new SysUser();
        vo.setUsername(username);
        return sysUserDao.get(vo);
    }

    public void updatePassword(String username, String newPassword) {
        if (StringUtil.isEmpty(newPassword)) {
            throw new BaseRuntimeException("newPassword 不能为空");
        }
        SysUser sysUser = getByUsername(username);
        if (sysUser == null) {
            throw new BaseRuntimeException("username 对应的数据不存在");
        }
        SysUser vo = new SysUser();
        vo.setId(sysUser.getId());
        vo.setPassword(newPassword);
        sysUserDao.update(vo);
    }


}
