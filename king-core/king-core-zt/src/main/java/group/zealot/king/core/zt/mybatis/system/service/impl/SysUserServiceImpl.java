package group.zealot.king.core.zt.mybatis.system.service.impl;

import group.zealot.king.core.zt.mybatis.system.dao.SysUserDao;
import group.zealot.king.core.zt.mybatis.system.entity.SysUser;
import group.zealot.king.core.zt.mybatis.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    public SysUser getByUsernameAndPassword(String username,String password){
        SysUser vo = new SysUser();
        vo.setUsername(username);
        vo.setPassword(password);
        return sysUserDao.get(vo);
    }
}
