package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.core.db.mybatis.system.dao.SysIdDao;
import group.zealot.king.core.zt.mif.entity.system.SysId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import group.zealot.king.core.zt.mif.service.system.SysIdService;

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
