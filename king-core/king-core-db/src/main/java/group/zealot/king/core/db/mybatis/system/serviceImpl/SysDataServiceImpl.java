package group.zealot.king.core.db.mybatis.system.serviceImpl;

import group.zealot.king.core.db.mybatis.base.BaseMapper;
import group.zealot.king.core.db.mybatis.base.BaseServiceImpl;
import group.zealot.king.core.zt.mif.entity.system.SysData;
import group.zealot.king.core.zt.mif.service.system.SysDataService;
import org.springframework.stereotype.Service;

import static group.zealot.king.core.db.mybatis.Daos.sysDataDao;


@Service
public class SysDataServiceImpl extends BaseServiceImpl<SysData, Long> implements SysDataService {

    @Override
    protected BaseMapper<SysData, Long> getBaseMapper() {
        return sysDataDao;
    }
}
