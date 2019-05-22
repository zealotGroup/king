package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.core.db.mybatis.system.dao.SysIdDao;
import group.zealot.king.core.zt.mif.entity.system.SysId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import group.zealot.king.core.zt.mif.service.system.SysIdService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@Service
public class SysIdServiceImpl implements SysIdService {

    @Autowired
    private SysIdDao sysIdDao;

    @Transactional(propagation = NOT_SUPPORTED)
    public Integer getId() {
        SysId sysId = new SysId();
        sysId.setInsertTime(LocalDateTime.now());
        sysIdDao.insert(sysId);
        return sysId.getId();
    }
}
