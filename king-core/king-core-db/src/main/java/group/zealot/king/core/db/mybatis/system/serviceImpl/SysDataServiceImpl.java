package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.core.db.mybatis.base.BaseDao;
import group.zealot.king.core.db.mybatis.base.BaseServiceAbs;
import group.zealot.king.core.zt.mif.entity.system.SysAuth;
import group.zealot.king.core.zt.mif.entity.system.SysData;
import group.zealot.king.core.zt.mif.service.system.SysDataService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static group.zealot.king.core.db.mybatis.Daos.sysDataDao;


@Service
public class SysDataServiceImpl extends BaseServiceAbs<SysData, Long> implements SysDataService {

    @Override
    protected BaseDao<SysData, Long> getBaseDao() {
        return sysDataDao;
    }

    @Override
    protected SysData getE(Long primaryKey, Long userId) {
        SysData vo = new SysData();
        vo.setLastUpdateTime(LocalDateTime.now());
        vo.setLastUpdateUserId(userId);
        vo.setId(primaryKey);
        return vo;
    }
}
