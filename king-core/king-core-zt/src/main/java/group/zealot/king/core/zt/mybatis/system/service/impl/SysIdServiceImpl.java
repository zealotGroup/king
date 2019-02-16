package group.zealot.king.core.zt.mybatis.system.service.impl;

import group.zealot.king.core.zt.mybatis.system.dao.SysIdDao;
import group.zealot.king.core.zt.mybatis.system.entity.SysId;
import group.zealot.king.core.zt.mybatis.system.service.SysIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SysIdServiceImpl implements SysIdService {

    @Autowired
    private SysIdDao sysIdDao;

    public Integer getId() {
        SysId sysId = new SysId();
        sysId.setInsertTime(LocalDateTime.now());
        sysIdDao.insert(sysId);
        return sysId.getId();
    }
}
