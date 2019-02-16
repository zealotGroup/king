package group.zealot.king.core.zt.mybatis.service.impl;

import group.zealot.king.core.zt.mybatis.dao.SysIdDao;
import group.zealot.king.core.zt.mybatis.entity.SysId;
import group.zealot.king.core.zt.mybatis.service.SysIdService;
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
